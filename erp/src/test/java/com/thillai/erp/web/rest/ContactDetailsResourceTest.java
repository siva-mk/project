package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.ContactDetails;
import com.thillai.erp.repository.ContactDetailsRepository;

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
 * Test class for the ContactDetailsResource REST controller.
 *
 * @see ContactDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContactDetailsResourceTest {

    private static final String DEFAULT_USER = "SAMPLE_TEXT";
    private static final String UPDATED_USER = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS1 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS1 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS2 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS2 = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Inject
    private ContactDetailsRepository contactDetailsRepository;

    private MockMvc restContactDetailsMockMvc;

    private ContactDetails contactDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactDetailsResource contactDetailsResource = new ContactDetailsResource();
        ReflectionTestUtils.setField(contactDetailsResource, "contactDetailsRepository", contactDetailsRepository);
        this.restContactDetailsMockMvc = MockMvcBuilders.standaloneSetup(contactDetailsResource).build();
    }

    @Before
    public void initTest() {
        contactDetails = new ContactDetails();
        contactDetails.setUser(DEFAULT_USER);
        contactDetails.setAddress1(DEFAULT_ADDRESS1);
        contactDetails.setAddress2(DEFAULT_ADDRESS2);
        contactDetails.setActive(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createContactDetails() throws Exception {
        // Validate the database is empty
        assertThat(contactDetailsRepository.findAll()).hasSize(0);

        // Create the ContactDetails
        restContactDetailsMockMvc.perform(post("/api/contactDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contactDetails)))
                .andExpect(status().isCreated());

        // Validate the ContactDetails in the database
        List<ContactDetails> contactDetailss = contactDetailsRepository.findAll();
        assertThat(contactDetailss).hasSize(1);
        ContactDetails testContactDetails = contactDetailss.iterator().next();
        assertThat(testContactDetails.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testContactDetails.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testContactDetails.getAddress2()).isEqualTo(DEFAULT_ADDRESS2);
        assertThat(testContactDetails.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllContactDetailss() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailss
        restContactDetailsMockMvc.perform(get("/api/contactDetailss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(contactDetails.getId().intValue()))
                .andExpect(jsonPath("$.[0].user").value(DEFAULT_USER.toString()))
                .andExpect(jsonPath("$.[0].address1").value(DEFAULT_ADDRESS1.toString()))
                .andExpect(jsonPath("$.[0].address2").value(DEFAULT_ADDRESS2.toString()))
                .andExpect(jsonPath("$.[0].active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get the contactDetails
        restContactDetailsMockMvc.perform(get("/api/contactDetailss/{id}", contactDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contactDetails.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS2.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContactDetails() throws Exception {
        // Get the contactDetails
        restContactDetailsMockMvc.perform(get("/api/contactDetailss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Update the contactDetails
        contactDetails.setUser(UPDATED_USER);
        contactDetails.setAddress1(UPDATED_ADDRESS1);
        contactDetails.setAddress2(UPDATED_ADDRESS2);
        contactDetails.setActive(UPDATED_ACTIVE);
        restContactDetailsMockMvc.perform(put("/api/contactDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contactDetails)))
                .andExpect(status().isOk());

        // Validate the ContactDetails in the database
        List<ContactDetails> contactDetailss = contactDetailsRepository.findAll();
        assertThat(contactDetailss).hasSize(1);
        ContactDetails testContactDetails = contactDetailss.iterator().next();
        assertThat(testContactDetails.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testContactDetails.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testContactDetails.getAddress2()).isEqualTo(UPDATED_ADDRESS2);
        assertThat(testContactDetails.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get the contactDetails
        restContactDetailsMockMvc.perform(delete("/api/contactDetailss/{id}", contactDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactDetails> contactDetailss = contactDetailsRepository.findAll();
        assertThat(contactDetailss).hasSize(0);
    }
}
