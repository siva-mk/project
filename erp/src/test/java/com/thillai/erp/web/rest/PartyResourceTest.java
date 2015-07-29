package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.Party;
import com.thillai.erp.repository.PartyRepository;

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
 * Test class for the PartyResource REST controller.
 *
 * @see PartyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PartyResourceTest {


    @Inject
    private PartyRepository partyRepository;

    private MockMvc restPartyMockMvc;

    private Party party;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartyResource partyResource = new PartyResource();
        ReflectionTestUtils.setField(partyResource, "partyRepository", partyRepository);
        this.restPartyMockMvc = MockMvcBuilders.standaloneSetup(partyResource).build();
    }

    @Before
    public void initTest() {
        party = new Party();
    }

    @Test
    @Transactional
    public void createParty() throws Exception {
        // Validate the database is empty
        assertThat(partyRepository.findAll()).hasSize(0);

        // Create the Party
        restPartyMockMvc.perform(post("/api/partys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(party)))
                .andExpect(status().isCreated());

        // Validate the Party in the database
        List<Party> partys = partyRepository.findAll();
        assertThat(partys).hasSize(1);
        Party testParty = partys.iterator().next();
    }

    @Test
    @Transactional
    public void getAllPartys() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partys
        restPartyMockMvc.perform(get("/api/partys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(party.getId().intValue()));
    }

    @Test
    @Transactional
    public void getParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get the party
        restPartyMockMvc.perform(get("/api/partys/{id}", party.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(party.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParty() throws Exception {
        // Get the party
        restPartyMockMvc.perform(get("/api/partys/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Update the party
        restPartyMockMvc.perform(put("/api/partys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(party)))
                .andExpect(status().isOk());

        // Validate the Party in the database
        List<Party> partys = partyRepository.findAll();
        assertThat(partys).hasSize(1);
        Party testParty = partys.iterator().next();
    }

    @Test
    @Transactional
    public void deleteParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get the party
        restPartyMockMvc.perform(delete("/api/partys/{id}", party.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Party> partys = partyRepository.findAll();
        assertThat(partys).hasSize(0);
    }
}
