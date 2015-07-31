package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.UserGroupRights;
import com.thillai.erp.repository.UserGroupRightsRepository;

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
 * Test class for the UserGroupRightsResource REST controller.
 *
 * @see UserGroupRightsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserGroupRightsResourceTest {


    @Inject
    private UserGroupRightsRepository userGroupRightsRepository;

    private MockMvc restUserGroupRightsMockMvc;

    private UserGroupRights userGroupRights;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserGroupRightsResource userGroupRightsResource = new UserGroupRightsResource();
        ReflectionTestUtils.setField(userGroupRightsResource, "userGroupRightsRepository", userGroupRightsRepository);
        this.restUserGroupRightsMockMvc = MockMvcBuilders.standaloneSetup(userGroupRightsResource).build();
    }

    @Before
    public void initTest() {
        userGroupRights = new UserGroupRights();
    }

    @Test
    @Transactional
    public void createUserGroupRights() throws Exception {
        // Validate the database is empty
        assertThat(userGroupRightsRepository.findAll()).hasSize(0);

        // Create the UserGroupRights
        restUserGroupRightsMockMvc.perform(post("/api/userGroupRightss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userGroupRights)))
                .andExpect(status().isCreated());

        // Validate the UserGroupRights in the database
        List<UserGroupRights> userGroupRightss = userGroupRightsRepository.findAll();
        assertThat(userGroupRightss).hasSize(1);
        UserGroupRights testUserGroupRights = userGroupRightss.iterator().next();
    }

    @Test
    @Transactional
    public void getAllUserGroupRightss() throws Exception {
        // Initialize the database
        userGroupRightsRepository.saveAndFlush(userGroupRights);

        // Get all the userGroupRightss
        restUserGroupRightsMockMvc.perform(get("/api/userGroupRightss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(userGroupRights.getId().intValue()));
    }

    @Test
    @Transactional
    public void getUserGroupRights() throws Exception {
        // Initialize the database
        userGroupRightsRepository.saveAndFlush(userGroupRights);

        // Get the userGroupRights
        restUserGroupRightsMockMvc.perform(get("/api/userGroupRightss/{id}", userGroupRights.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userGroupRights.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserGroupRights() throws Exception {
        // Get the userGroupRights
        restUserGroupRightsMockMvc.perform(get("/api/userGroupRightss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserGroupRights() throws Exception {
        // Initialize the database
        userGroupRightsRepository.saveAndFlush(userGroupRights);

        // Update the userGroupRights
        restUserGroupRightsMockMvc.perform(put("/api/userGroupRightss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userGroupRights)))
                .andExpect(status().isOk());

        // Validate the UserGroupRights in the database
        List<UserGroupRights> userGroupRightss = userGroupRightsRepository.findAll();
        assertThat(userGroupRightss).hasSize(1);
        UserGroupRights testUserGroupRights = userGroupRightss.iterator().next();
    }

    @Test
    @Transactional
    public void deleteUserGroupRights() throws Exception {
        // Initialize the database
        userGroupRightsRepository.saveAndFlush(userGroupRights);

        // Get the userGroupRights
        restUserGroupRightsMockMvc.perform(delete("/api/userGroupRightss/{id}", userGroupRights.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserGroupRights> userGroupRightss = userGroupRightsRepository.findAll();
        assertThat(userGroupRightss).hasSize(0);
    }
}
