package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.UserType;
import com.thillai.erp.repository.UserTypeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserTypeResource REST controller.
 *
 * @see UserTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserTypeResourceTest {

    private static final String DEFAULT_USER_TYPE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_USER_TYPE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_USER_TYPE_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_USER_TYPE_CODE = "UPDATED_TEXT";

    @Inject
    private UserTypeRepository userTypeRepository;

    private MockMvc restUserTypeMockMvc;

    private UserType userType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserTypeResource userTypeResource = new UserTypeResource();
        ReflectionTestUtils.setField(userTypeResource, "userTypeRepository", userTypeRepository);
        this.restUserTypeMockMvc = MockMvcBuilders.standaloneSetup(userTypeResource).build();
    }

    @Before
    public void initTest() {
        userType = new UserType();
        userType.setUserTypeName(DEFAULT_USER_TYPE_NAME);
        userType.setUserTypeCode(DEFAULT_USER_TYPE_CODE);
    }

    @Test
    @Transactional
    public void createUserType() throws Exception {
        // Validate the database is empty
        assertThat(userTypeRepository.findAll()).hasSize(0);

        // Create the UserType
        restUserTypeMockMvc.perform(post("/api/userTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userType)))
                .andExpect(status().isCreated());

        // Validate the UserType in the database
        List<UserType> userTypes = userTypeRepository.findAll();
        assertThat(userTypes).hasSize(1);
        UserType testUserType = userTypes.iterator().next();
        assertThat(testUserType.getUserTypeName()).isEqualTo(DEFAULT_USER_TYPE_NAME);
        assertThat(testUserType.getUserTypeCode()).isEqualTo(DEFAULT_USER_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllUserTypes() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get all the userTypes
        restUserTypeMockMvc.perform(get("/api/userTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(userType.getId().intValue()))
                .andExpect(jsonPath("$.[0].userTypeName").value(DEFAULT_USER_TYPE_NAME.toString()))
                .andExpect(jsonPath("$.[0].userTypeCode").value(DEFAULT_USER_TYPE_CODE.toString()));
    }

    @Test
    @Transactional
    public void getUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get the userType
        restUserTypeMockMvc.perform(get("/api/userTypes/{id}", userType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userType.getId().intValue()))
            .andExpect(jsonPath("$.userTypeName").value(DEFAULT_USER_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.userTypeCode").value(DEFAULT_USER_TYPE_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserType() throws Exception {
        // Get the userType
        restUserTypeMockMvc.perform(get("/api/userTypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Update the userType
        userType.setUserTypeName(UPDATED_USER_TYPE_NAME);
        userType.setUserTypeCode(UPDATED_USER_TYPE_CODE);
        restUserTypeMockMvc.perform(put("/api/userTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userType)))
                .andExpect(status().isOk());

        // Validate the UserType in the database
        List<UserType> userTypes = userTypeRepository.findAll();
        assertThat(userTypes).hasSize(1);
        UserType testUserType = userTypes.iterator().next();
        assertThat(testUserType.getUserTypeName()).isEqualTo(UPDATED_USER_TYPE_NAME);
        assertThat(testUserType.getUserTypeCode()).isEqualTo(UPDATED_USER_TYPE_CODE);
    }

    @Test
    @Transactional
    public void deleteUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get the userType
        restUserTypeMockMvc.perform(delete("/api/userTypes/{id}", userType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserType> userTypes = userTypeRepository.findAll();
        assertThat(userTypes).hasSize(0);
    }
}
