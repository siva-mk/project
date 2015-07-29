package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.UserGroups;
import com.thillai.erp.repository.UserGroupsRepository;

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
 * Test class for the UserGroupsResource REST controller.
 *
 * @see UserGroupsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserGroupsResourceTest {

    private static final String DEFAULT_USER_GROUP_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_USER_GROUP_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_USER_GROUP_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_USER_GROUP_CODE = "UPDATED_TEXT";

    @Inject
    private UserGroupsRepository userGroupsRepository;

    private MockMvc restUserGroupsMockMvc;

    private UserGroups userGroups;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserGroupsResource userGroupsResource = new UserGroupsResource();
        ReflectionTestUtils.setField(userGroupsResource, "userGroupsRepository", userGroupsRepository);
        this.restUserGroupsMockMvc = MockMvcBuilders.standaloneSetup(userGroupsResource).build();
    }

    @Before
    public void initTest() {
        userGroups = new UserGroups();
        userGroups.setUserGroupName(DEFAULT_USER_GROUP_NAME);
        userGroups.setUserGroupCode(DEFAULT_USER_GROUP_CODE);
    }

    @Test
    @Transactional
    public void createUserGroups() throws Exception {
        // Validate the database is empty
        assertThat(userGroupsRepository.findAll()).hasSize(0);

        // Create the UserGroups
        restUserGroupsMockMvc.perform(post("/api/userGroupss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userGroups)))
                .andExpect(status().isCreated());

        // Validate the UserGroups in the database
        List<UserGroups> userGroupss = userGroupsRepository.findAll();
        assertThat(userGroupss).hasSize(1);
        UserGroups testUserGroups = userGroupss.iterator().next();
        assertThat(testUserGroups.getUserGroupName()).isEqualTo(DEFAULT_USER_GROUP_NAME);
        assertThat(testUserGroups.getUserGroupCode()).isEqualTo(DEFAULT_USER_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllUserGroupss() throws Exception {
        // Initialize the database
        userGroupsRepository.saveAndFlush(userGroups);

        // Get all the userGroupss
        restUserGroupsMockMvc.perform(get("/api/userGroupss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(userGroups.getId().intValue()))
                .andExpect(jsonPath("$.[0].userGroupName").value(DEFAULT_USER_GROUP_NAME.toString()))
                .andExpect(jsonPath("$.[0].userGroupCode").value(DEFAULT_USER_GROUP_CODE.toString()));
    }

    @Test
    @Transactional
    public void getUserGroups() throws Exception {
        // Initialize the database
        userGroupsRepository.saveAndFlush(userGroups);

        // Get the userGroups
        restUserGroupsMockMvc.perform(get("/api/userGroupss/{id}", userGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userGroups.getId().intValue()))
            .andExpect(jsonPath("$.userGroupName").value(DEFAULT_USER_GROUP_NAME.toString()))
            .andExpect(jsonPath("$.userGroupCode").value(DEFAULT_USER_GROUP_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserGroups() throws Exception {
        // Get the userGroups
        restUserGroupsMockMvc.perform(get("/api/userGroupss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserGroups() throws Exception {
        // Initialize the database
        userGroupsRepository.saveAndFlush(userGroups);

        // Update the userGroups
        userGroups.setUserGroupName(UPDATED_USER_GROUP_NAME);
        userGroups.setUserGroupCode(UPDATED_USER_GROUP_CODE);
        restUserGroupsMockMvc.perform(put("/api/userGroupss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userGroups)))
                .andExpect(status().isOk());

        // Validate the UserGroups in the database
        List<UserGroups> userGroupss = userGroupsRepository.findAll();
        assertThat(userGroupss).hasSize(1);
        UserGroups testUserGroups = userGroupss.iterator().next();
        assertThat(testUserGroups.getUserGroupName()).isEqualTo(UPDATED_USER_GROUP_NAME);
        assertThat(testUserGroups.getUserGroupCode()).isEqualTo(UPDATED_USER_GROUP_CODE);
    }

    @Test
    @Transactional
    public void deleteUserGroups() throws Exception {
        // Initialize the database
        userGroupsRepository.saveAndFlush(userGroups);

        // Get the userGroups
        restUserGroupsMockMvc.perform(delete("/api/userGroupss/{id}", userGroups.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserGroups> userGroupss = userGroupsRepository.findAll();
        assertThat(userGroupss).hasSize(0);
    }
}
