package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.ShipmentItem;
import com.thillai.erp.repository.ShipmentItemRepository;

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
 * Test class for the ShipmentItemResource REST controller.
 *
 * @see ShipmentItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShipmentItemResourceTest {


    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;
    private static final String DEFAULT_SHIPMENT_CONTENT_DESC = "SAMPLE_TEXT";
    private static final String UPDATED_SHIPMENT_CONTENT_DESC = "UPDATED_TEXT";

    @Inject
    private ShipmentItemRepository shipmentItemRepository;

    private MockMvc restShipmentItemMockMvc;

    private ShipmentItem shipmentItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentItemResource shipmentItemResource = new ShipmentItemResource();
        ReflectionTestUtils.setField(shipmentItemResource, "shipmentItemRepository", shipmentItemRepository);
        this.restShipmentItemMockMvc = MockMvcBuilders.standaloneSetup(shipmentItemResource).build();
    }

    @Before
    public void initTest() {
        shipmentItem = new ShipmentItem();
        shipmentItem.setQuantity(DEFAULT_QUANTITY);
        shipmentItem.setShipmentContentDesc(DEFAULT_SHIPMENT_CONTENT_DESC);
    }

    @Test
    @Transactional
    public void createShipmentItem() throws Exception {
        // Validate the database is empty
        assertThat(shipmentItemRepository.findAll()).hasSize(0);

        // Create the ShipmentItem
        restShipmentItemMockMvc.perform(post("/api/shipmentItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipmentItem)))
                .andExpect(status().isCreated());

        // Validate the ShipmentItem in the database
        List<ShipmentItem> shipmentItems = shipmentItemRepository.findAll();
        assertThat(shipmentItems).hasSize(1);
        ShipmentItem testShipmentItem = shipmentItems.iterator().next();
        assertThat(testShipmentItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testShipmentItem.getShipmentContentDesc()).isEqualTo(DEFAULT_SHIPMENT_CONTENT_DESC);
    }

    @Test
    @Transactional
    public void getAllShipmentItems() throws Exception {
        // Initialize the database
        shipmentItemRepository.saveAndFlush(shipmentItem);

        // Get all the shipmentItems
        restShipmentItemMockMvc.perform(get("/api/shipmentItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(shipmentItem.getId().intValue()))
                .andExpect(jsonPath("$.[0].quantity").value(DEFAULT_QUANTITY))
                .andExpect(jsonPath("$.[0].shipmentContentDesc").value(DEFAULT_SHIPMENT_CONTENT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getShipmentItem() throws Exception {
        // Initialize the database
        shipmentItemRepository.saveAndFlush(shipmentItem);

        // Get the shipmentItem
        restShipmentItemMockMvc.perform(get("/api/shipmentItems/{id}", shipmentItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shipmentItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.shipmentContentDesc").value(DEFAULT_SHIPMENT_CONTENT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentItem() throws Exception {
        // Get the shipmentItem
        restShipmentItemMockMvc.perform(get("/api/shipmentItems/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentItem() throws Exception {
        // Initialize the database
        shipmentItemRepository.saveAndFlush(shipmentItem);

        // Update the shipmentItem
        shipmentItem.setQuantity(UPDATED_QUANTITY);
        shipmentItem.setShipmentContentDesc(UPDATED_SHIPMENT_CONTENT_DESC);
        restShipmentItemMockMvc.perform(put("/api/shipmentItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipmentItem)))
                .andExpect(status().isOk());

        // Validate the ShipmentItem in the database
        List<ShipmentItem> shipmentItems = shipmentItemRepository.findAll();
        assertThat(shipmentItems).hasSize(1);
        ShipmentItem testShipmentItem = shipmentItems.iterator().next();
        assertThat(testShipmentItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testShipmentItem.getShipmentContentDesc()).isEqualTo(UPDATED_SHIPMENT_CONTENT_DESC);
    }

    @Test
    @Transactional
    public void deleteShipmentItem() throws Exception {
        // Initialize the database
        shipmentItemRepository.saveAndFlush(shipmentItem);

        // Get the shipmentItem
        restShipmentItemMockMvc.perform(delete("/api/shipmentItems/{id}", shipmentItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentItem> shipmentItems = shipmentItemRepository.findAll();
        assertThat(shipmentItems).hasSize(0);
    }
}
