package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.PartyTypes;
import com.thillai.erp.repository.PartyTypesRepository;

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
 * Test class for the PartyTypesResource REST controller.
 *
 * @see PartyTypesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PartyTypesResourceTest {

    private static final String DEFAULT_PARTY_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_PARTY_TYPE = "UPDATED_TEXT";

    @Inject
    private PartyTypesRepository partyTypesRepository;

    private MockMvc restPartyTypesMockMvc;

    private PartyTypes partyTypes;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartyTypesResource partyTypesResource = new PartyTypesResource();
        ReflectionTestUtils.setField(partyTypesResource, "partyTypesRepository", partyTypesRepository);
        this.restPartyTypesMockMvc = MockMvcBuilders.standaloneSetup(partyTypesResource).build();
    }

    @Before
    public void initTest() {
        partyTypes = new PartyTypes();
        partyTypes.setPartyType(DEFAULT_PARTY_TYPE);
    }

    @Test
    @Transactional
    public void createPartyTypes() throws Exception {
        // Validate the database is empty
        assertThat(partyTypesRepository.findAll()).hasSize(0);

        // Create the PartyTypes
        restPartyTypesMockMvc.perform(post("/api/partyTypess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partyTypes)))
                .andExpect(status().isCreated());

        // Validate the PartyTypes in the database
        List<PartyTypes> partyTypess = partyTypesRepository.findAll();
        assertThat(partyTypess).hasSize(1);
        PartyTypes testPartyTypes = partyTypess.iterator().next();
        assertThat(testPartyTypes.getPartyType()).isEqualTo(DEFAULT_PARTY_TYPE);
    }

    @Test
    @Transactional
    public void getAllPartyTypess() throws Exception {
        // Initialize the database
        partyTypesRepository.saveAndFlush(partyTypes);

        // Get all the partyTypess
        restPartyTypesMockMvc.perform(get("/api/partyTypess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(partyTypes.getId().intValue()))
                .andExpect(jsonPath("$.[0].partyType").value(DEFAULT_PARTY_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getPartyTypes() throws Exception {
        // Initialize the database
        partyTypesRepository.saveAndFlush(partyTypes);

        // Get the partyTypes
        restPartyTypesMockMvc.perform(get("/api/partyTypess/{id}", partyTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(partyTypes.getId().intValue()))
            .andExpect(jsonPath("$.partyType").value(DEFAULT_PARTY_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartyTypes() throws Exception {
        // Get the partyTypes
        restPartyTypesMockMvc.perform(get("/api/partyTypess/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyTypes() throws Exception {
        // Initialize the database
        partyTypesRepository.saveAndFlush(partyTypes);

        // Update the partyTypes
        partyTypes.setPartyType(UPDATED_PARTY_TYPE);
        restPartyTypesMockMvc.perform(put("/api/partyTypess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partyTypes)))
                .andExpect(status().isOk());

        // Validate the PartyTypes in the database
        List<PartyTypes> partyTypess = partyTypesRepository.findAll();
        assertThat(partyTypess).hasSize(1);
        PartyTypes testPartyTypes = partyTypess.iterator().next();
        assertThat(testPartyTypes.getPartyType()).isEqualTo(UPDATED_PARTY_TYPE);
    }

    @Test
    @Transactional
    public void deletePartyTypes() throws Exception {
        // Initialize the database
        partyTypesRepository.saveAndFlush(partyTypes);

        // Get the partyTypes
        restPartyTypesMockMvc.perform(delete("/api/partyTypess/{id}", partyTypes.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PartyTypes> partyTypess = partyTypesRepository.findAll();
        assertThat(partyTypess).hasSize(0);
    }
}
