package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.ShipmentType;
import com.thillai.erp.repository.ShipmentTypeRepository;

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
 * Test class for the ShipmentTypeResource REST controller.
 *
 * @see ShipmentTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShipmentTypeResourceTest {

    private static final String DEFAULT_SHIPMEN_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_SHIPMEN_TYPE = "UPDATED_TEXT";

    @Inject
    private ShipmentTypeRepository shipmentTypeRepository;

    private MockMvc restShipmentTypeMockMvc;

    private ShipmentType shipmentType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentTypeResource shipmentTypeResource = new ShipmentTypeResource();
        ReflectionTestUtils.setField(shipmentTypeResource, "shipmentTypeRepository", shipmentTypeRepository);
        this.restShipmentTypeMockMvc = MockMvcBuilders.standaloneSetup(shipmentTypeResource).build();
    }

    @Before
    public void initTest() {
        shipmentType = new ShipmentType();
        shipmentType.setShipmenType(DEFAULT_SHIPMEN_TYPE);
    }

    @Test
    @Transactional
    public void createShipmentType() throws Exception {
        // Validate the database is empty
        assertThat(shipmentTypeRepository.findAll()).hasSize(0);

        // Create the ShipmentType
        restShipmentTypeMockMvc.perform(post("/api/shipmentTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipmentType)))
                .andExpect(status().isCreated());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypes = shipmentTypeRepository.findAll();
        assertThat(shipmentTypes).hasSize(1);
        ShipmentType testShipmentType = shipmentTypes.iterator().next();
        assertThat(testShipmentType.getShipmenType()).isEqualTo(DEFAULT_SHIPMEN_TYPE);
    }

    @Test
    @Transactional
    public void getAllShipmentTypes() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        // Get all the shipmentTypes
        restShipmentTypeMockMvc.perform(get("/api/shipmentTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(shipmentType.getId().intValue()))
                .andExpect(jsonPath("$.[0].shipmenType").value(DEFAULT_SHIPMEN_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        // Get the shipmentType
        restShipmentTypeMockMvc.perform(get("/api/shipmentTypes/{id}", shipmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shipmentType.getId().intValue()))
            .andExpect(jsonPath("$.shipmenType").value(DEFAULT_SHIPMEN_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentType() throws Exception {
        // Get the shipmentType
        restShipmentTypeMockMvc.perform(get("/api/shipmentTypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        // Update the shipmentType
        shipmentType.setShipmenType(UPDATED_SHIPMEN_TYPE);
        restShipmentTypeMockMvc.perform(put("/api/shipmentTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipmentType)))
                .andExpect(status().isOk());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypes = shipmentTypeRepository.findAll();
        assertThat(shipmentTypes).hasSize(1);
        ShipmentType testShipmentType = shipmentTypes.iterator().next();
        assertThat(testShipmentType.getShipmenType()).isEqualTo(UPDATED_SHIPMEN_TYPE);
    }

    @Test
    @Transactional
    public void deleteShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        // Get the shipmentType
        restShipmentTypeMockMvc.perform(delete("/api/shipmentTypes/{id}", shipmentType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentType> shipmentTypes = shipmentTypeRepository.findAll();
        assertThat(shipmentTypes).hasSize(0);
    }
}
