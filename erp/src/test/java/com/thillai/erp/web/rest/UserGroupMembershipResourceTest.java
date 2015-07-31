package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.UserGroupMembership;
import com.thillai.erp.repository.UserGroupMembershipRepository;

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
 * Test class for the UserGroupMembershipResource REST controller.
 *
 * @see UserGroupMembershipResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserGroupMembershipResourceTest {


    private static final Integer DEFAULT_USER_ID = 0;
    private static final Integer UPDATED_USER_ID = 1;

    @Inject
    private UserGroupMembershipRepository userGroupMembershipRepository;

    private MockMvc restUserGroupMembershipMockMvc;

    private UserGroupMembership userGroupMembership;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserGroupMembershipResource userGroupMembershipResource = new UserGroupMembershipResource();
        ReflectionTestUtils.setField(userGroupMembershipResource, "userGroupMembershipRepository", userGroupMembershipRepository);
        this.restUserGroupMembershipMockMvc = MockMvcBuilders.standaloneSetup(userGroupMembershipResource).build();
    }

    @Before
    public void initTest() {
        userGroupMembership = new UserGroupMembership();
        userGroupMembership.setUser_id(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createUserGroupMembership() throws Exception {
        // Validate the database is empty
        assertThat(userGroupMembershipRepository.findAll()).hasSize(0);

        // Create the UserGroupMembership
        restUserGroupMembershipMockMvc.perform(post("/api/userGroupMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userGroupMembership)))
                .andExpect(status().isCreated());

        // Validate the UserGroupMembership in the database
        List<UserGroupMembership> userGroupMemberships = userGroupMembershipRepository.findAll();
        assertThat(userGroupMemberships).hasSize(1);
        UserGroupMembership testUserGroupMembership = userGroupMemberships.iterator().next();
        assertThat(testUserGroupMembership.getUser_id()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void getAllUserGroupMemberships() throws Exception {
        // Initialize the database
        userGroupMembershipRepository.saveAndFlush(userGroupMembership);

        // Get all the userGroupMemberships
        restUserGroupMembershipMockMvc.perform(get("/api/userGroupMemberships"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(userGroupMembership.getId().intValue()))
                .andExpect(jsonPath("$.[0].user_id").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    public void getUserGroupMembership() throws Exception {
        // Initialize the database
        userGroupMembershipRepository.saveAndFlush(userGroupMembership);

        // Get the userGroupMembership
        restUserGroupMembershipMockMvc.perform(get("/api/userGroupMemberships/{id}", userGroupMembership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userGroupMembership.getId().intValue()))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    public void getNonExistingUserGroupMembership() throws Exception {
        // Get the userGroupMembership
        restUserGroupMembershipMockMvc.perform(get("/api/userGroupMemberships/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserGroupMembership() throws Exception {
        // Initialize the database
        userGroupMembershipRepository.saveAndFlush(userGroupMembership);

        // Update the userGroupMembership
        userGroupMembership.setUser_id(UPDATED_USER_ID);
        restUserGroupMembershipMockMvc.perform(put("/api/userGroupMemberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userGroupMembership)))
                .andExpect(status().isOk());

        // Validate the UserGroupMembership in the database
        List<UserGroupMembership> userGroupMemberships = userGroupMembershipRepository.findAll();
        assertThat(userGroupMemberships).hasSize(1);
        UserGroupMembership testUserGroupMembership = userGroupMemberships.iterator().next();
        assertThat(testUserGroupMembership.getUser_id()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void deleteUserGroupMembership() throws Exception {
        // Initialize the database
        userGroupMembershipRepository.saveAndFlush(userGroupMembership);

        // Get the userGroupMembership
        restUserGroupMembershipMockMvc.perform(delete("/api/userGroupMemberships/{id}", userGroupMembership.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserGroupMembership> userGroupMemberships = userGroupMembershipRepository.findAll();
        assertThat(userGroupMemberships).hasSize(0);
    }
}
