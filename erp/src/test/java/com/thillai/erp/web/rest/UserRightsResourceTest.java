package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.UserRights;
import com.thillai.erp.repository.UserRightsRepository;

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
 * Test class for the UserRightsResource REST controller.
 *
 * @see UserRightsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserRightsResourceTest {


    private static final Integer DEFAULT_USER_ID = 0;
    private static final Integer UPDATED_USER_ID = 1;

    @Inject
    private UserRightsRepository userRightsRepository;

    private MockMvc restUserRightsMockMvc;

    private UserRights userRights;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserRightsResource userRightsResource = new UserRightsResource();
        ReflectionTestUtils.setField(userRightsResource, "userRightsRepository", userRightsRepository);
        this.restUserRightsMockMvc = MockMvcBuilders.standaloneSetup(userRightsResource).build();
    }

    @Before
    public void initTest() {
        userRights = new UserRights();
        userRights.setUser_id(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createUserRights() throws Exception {
        // Validate the database is empty
        assertThat(userRightsRepository.findAll()).hasSize(0);

        // Create the UserRights
        restUserRightsMockMvc.perform(post("/api/userRightss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userRights)))
                .andExpect(status().isCreated());

        // Validate the UserRights in the database
        List<UserRights> userRightss = userRightsRepository.findAll();
        assertThat(userRightss).hasSize(1);
        UserRights testUserRights = userRightss.iterator().next();
        assertThat(testUserRights.getUser_id()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void getAllUserRightss() throws Exception {
        // Initialize the database
        userRightsRepository.saveAndFlush(userRights);

        // Get all the userRightss
        restUserRightsMockMvc.perform(get("/api/userRightss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(userRights.getId().intValue()))
                .andExpect(jsonPath("$.[0].user_id").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    public void getUserRights() throws Exception {
        // Initialize the database
        userRightsRepository.saveAndFlush(userRights);

        // Get the userRights
        restUserRightsMockMvc.perform(get("/api/userRightss/{id}", userRights.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userRights.getId().intValue()))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    public void getNonExistingUserRights() throws Exception {
        // Get the userRights
        restUserRightsMockMvc.perform(get("/api/userRightss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserRights() throws Exception {
        // Initialize the database
        userRightsRepository.saveAndFlush(userRights);

        // Update the userRights
        userRights.setUser_id(UPDATED_USER_ID);
        restUserRightsMockMvc.perform(put("/api/userRightss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userRights)))
                .andExpect(status().isOk());

        // Validate the UserRights in the database
        List<UserRights> userRightss = userRightsRepository.findAll();
        assertThat(userRightss).hasSize(1);
        UserRights testUserRights = userRightss.iterator().next();
        assertThat(testUserRights.getUser_id()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void deleteUserRights() throws Exception {
        // Initialize the database
        userRightsRepository.saveAndFlush(userRights);

        // Get the userRights
        restUserRightsMockMvc.perform(delete("/api/userRightss/{id}", userRights.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserRights> userRightss = userRightsRepository.findAll();
        assertThat(userRightss).hasSize(0);
    }
}
