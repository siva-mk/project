package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.ShipmentItemBilling;
import com.thillai.erp.repository.ShipmentItemBillingRepository;

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
 * Test class for the ShipmentItemBillingResource REST controller.
 *
 * @see ShipmentItemBillingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShipmentItemBillingResourceTest {


    @Inject
    private ShipmentItemBillingRepository shipmentItemBillingRepository;

    private MockMvc restShipmentItemBillingMockMvc;

    private ShipmentItemBilling shipmentItemBilling;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentItemBillingResource shipmentItemBillingResource = new ShipmentItemBillingResource();
        ReflectionTestUtils.setField(shipmentItemBillingResource, "shipmentItemBillingRepository", shipmentItemBillingRepository);
        this.restShipmentItemBillingMockMvc = MockMvcBuilders.standaloneSetup(shipmentItemBillingResource).build();
    }

    @Before
    public void initTest() {
        shipmentItemBilling = new ShipmentItemBilling();
    }

    @Test
    @Transactional
    public void createShipmentItemBilling() throws Exception {
        // Validate the database is empty
        assertThat(shipmentItemBillingRepository.findAll()).hasSize(0);

        // Create the ShipmentItemBilling
        restShipmentItemBillingMockMvc.perform(post("/api/shipmentItemBillings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipmentItemBilling)))
                .andExpect(status().isCreated());

        // Validate the ShipmentItemBilling in the database
        List<ShipmentItemBilling> shipmentItemBillings = shipmentItemBillingRepository.findAll();
        assertThat(shipmentItemBillings).hasSize(1);
        ShipmentItemBilling testShipmentItemBilling = shipmentItemBillings.iterator().next();
    }

    @Test
    @Transactional
    public void getAllShipmentItemBillings() throws Exception {
        // Initialize the database
        shipmentItemBillingRepository.saveAndFlush(shipmentItemBilling);

        // Get all the shipmentItemBillings
        restShipmentItemBillingMockMvc.perform(get("/api/shipmentItemBillings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(shipmentItemBilling.getId().intValue()));
    }

    @Test
    @Transactional
    public void getShipmentItemBilling() throws Exception {
        // Initialize the database
        shipmentItemBillingRepository.saveAndFlush(shipmentItemBilling);

        // Get the shipmentItemBilling
        restShipmentItemBillingMockMvc.perform(get("/api/shipmentItemBillings/{id}", shipmentItemBilling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shipmentItemBilling.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentItemBilling() throws Exception {
        // Get the shipmentItemBilling
        restShipmentItemBillingMockMvc.perform(get("/api/shipmentItemBillings/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentItemBilling() throws Exception {
        // Initialize the database
        shipmentItemBillingRepository.saveAndFlush(shipmentItemBilling);

        // Update the shipmentItemBilling
        restShipmentItemBillingMockMvc.perform(put("/api/shipmentItemBillings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipmentItemBilling)))
                .andExpect(status().isOk());

        // Validate the ShipmentItemBilling in the database
        List<ShipmentItemBilling> shipmentItemBillings = shipmentItemBillingRepository.findAll();
        assertThat(shipmentItemBillings).hasSize(1);
        ShipmentItemBilling testShipmentItemBilling = shipmentItemBillings.iterator().next();
    }

    @Test
    @Transactional
    public void deleteShipmentItemBilling() throws Exception {
        // Initialize the database
        shipmentItemBillingRepository.saveAndFlush(shipmentItemBilling);

        // Get the shipmentItemBilling
        restShipmentItemBillingMockMvc.perform(delete("/api/shipmentItemBillings/{id}", shipmentItemBilling.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentItemBilling> shipmentItemBillings = shipmentItemBillingRepository.findAll();
        assertThat(shipmentItemBillings).hasSize(0);
    }
}
