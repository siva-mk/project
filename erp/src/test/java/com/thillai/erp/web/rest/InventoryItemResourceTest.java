package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.InventoryItem;
import com.thillai.erp.repository.InventoryItemRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InventoryItemResource REST controller.
 *
 * @see InventoryItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InventoryItemResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_DATE_TIME_RECEIVED = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_TIME_RECEIVED = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_TIME_RECEIVED_STR = dateTimeFormatter.print(DEFAULT_DATE_TIME_RECEIVED);

    private static final DateTime DEFAULT_EXPIRY_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_EXPIRY_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_EXPIRY_DATE_STR = dateTimeFormatter.print(DEFAULT_EXPIRY_DATE);

    private static final Integer DEFAULT_FACILITY_ID = 0;
    private static final Integer UPDATED_FACILITY_ID = 1;

    private static final Integer DEFAULT_CONTAINER_ID = 0;
    private static final Integer UPDATED_CONTAINER_ID = 1;

    private static final Integer DEFAULT_LOT_ID = 0;
    private static final Integer UPDATED_LOT_ID = 1;

    private static final Integer DEFAULT_UOM_ID = 0;
    private static final Integer UPDATED_UOM_ID = 1;
    private static final String DEFAULT_COMMENTS = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENTS = "UPDATED_TEXT";

    private static final Integer DEFAULT_QUANTITY_ON_HAND = 0;
    private static final Integer UPDATED_QUANTITY_ON_HAND = 1;

    private static final Integer DEFAULT_AVAILABLE_TO_PROMISE = 0;
    private static final Integer UPDATED_AVAILABLE_TO_PROMISE = 1;
    private static final String DEFAULT_SERIAL_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_SERIAL_NUMBER = "UPDATED_TEXT";

    @Inject
    private InventoryItemRepository inventoryItemRepository;

    private MockMvc restInventoryItemMockMvc;

    private InventoryItem inventoryItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InventoryItemResource inventoryItemResource = new InventoryItemResource();
        ReflectionTestUtils.setField(inventoryItemResource, "inventoryItemRepository", inventoryItemRepository);
        this.restInventoryItemMockMvc = MockMvcBuilders.standaloneSetup(inventoryItemResource).build();
    }

    @Before
    public void initTest() {
        inventoryItem = new InventoryItem();
        inventoryItem.setDateTimeReceived(DEFAULT_DATE_TIME_RECEIVED);
        inventoryItem.setExpiryDate(DEFAULT_EXPIRY_DATE);
        inventoryItem.setFacility_id(DEFAULT_FACILITY_ID);
        inventoryItem.setContainer_id(DEFAULT_CONTAINER_ID);
        inventoryItem.setLot_id(DEFAULT_LOT_ID);
        inventoryItem.setUOM_id(DEFAULT_UOM_ID);
        inventoryItem.setComments(DEFAULT_COMMENTS);
        inventoryItem.setQuantityOnHand(DEFAULT_QUANTITY_ON_HAND);
        inventoryItem.setAvailableToPromise(DEFAULT_AVAILABLE_TO_PROMISE);
        inventoryItem.setSerialNumber(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void createInventoryItem() throws Exception {
        // Validate the database is empty
        assertThat(inventoryItemRepository.findAll()).hasSize(0);

        // Create the InventoryItem
        restInventoryItemMockMvc.perform(post("/api/inventoryItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItem)))
                .andExpect(status().isCreated());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        assertThat(inventoryItems).hasSize(1);
        InventoryItem testInventoryItem = inventoryItems.iterator().next();
        assertThat(testInventoryItem.getDateTimeReceived().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_TIME_RECEIVED);
        assertThat(testInventoryItem.getExpiryDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testInventoryItem.getFacility_id()).isEqualTo(DEFAULT_FACILITY_ID);
        assertThat(testInventoryItem.getContainer_id()).isEqualTo(DEFAULT_CONTAINER_ID);
        assertThat(testInventoryItem.getLot_id()).isEqualTo(DEFAULT_LOT_ID);
        assertThat(testInventoryItem.getUOM_id()).isEqualTo(DEFAULT_UOM_ID);
        assertThat(testInventoryItem.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testInventoryItem.getQuantityOnHand()).isEqualTo(DEFAULT_QUANTITY_ON_HAND);
        assertThat(testInventoryItem.getAvailableToPromise()).isEqualTo(DEFAULT_AVAILABLE_TO_PROMISE);
        assertThat(testInventoryItem.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItems() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItems
        restInventoryItemMockMvc.perform(get("/api/inventoryItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(inventoryItem.getId().intValue()))
                .andExpect(jsonPath("$.[0].dateTimeReceived").value(DEFAULT_DATE_TIME_RECEIVED_STR))
                .andExpect(jsonPath("$.[0].expiryDate").value(DEFAULT_EXPIRY_DATE_STR))
                .andExpect(jsonPath("$.[0].facility_id").value(DEFAULT_FACILITY_ID))
                .andExpect(jsonPath("$.[0].container_id").value(DEFAULT_CONTAINER_ID))
                .andExpect(jsonPath("$.[0].lot_id").value(DEFAULT_LOT_ID))
                .andExpect(jsonPath("$.[0].UOM_id").value(DEFAULT_UOM_ID))
                .andExpect(jsonPath("$.[0].comments").value(DEFAULT_COMMENTS.toString()))
                .andExpect(jsonPath("$.[0].quantityOnHand").value(DEFAULT_QUANTITY_ON_HAND))
                .andExpect(jsonPath("$.[0].availableToPromise").value(DEFAULT_AVAILABLE_TO_PROMISE))
                .andExpect(jsonPath("$.[0].serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getInventoryItem() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get the inventoryItem
        restInventoryItemMockMvc.perform(get("/api/inventoryItems/{id}", inventoryItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(inventoryItem.getId().intValue()))
            .andExpect(jsonPath("$.dateTimeReceived").value(DEFAULT_DATE_TIME_RECEIVED_STR))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE_STR))
            .andExpect(jsonPath("$.facility_id").value(DEFAULT_FACILITY_ID))
            .andExpect(jsonPath("$.container_id").value(DEFAULT_CONTAINER_ID))
            .andExpect(jsonPath("$.lot_id").value(DEFAULT_LOT_ID))
            .andExpect(jsonPath("$.UOM_id").value(DEFAULT_UOM_ID))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.quantityOnHand").value(DEFAULT_QUANTITY_ON_HAND))
            .andExpect(jsonPath("$.availableToPromise").value(DEFAULT_AVAILABLE_TO_PROMISE))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryItem() throws Exception {
        // Get the inventoryItem
        restInventoryItemMockMvc.perform(get("/api/inventoryItems/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItem() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Update the inventoryItem
        inventoryItem.setDateTimeReceived(UPDATED_DATE_TIME_RECEIVED);
        inventoryItem.setExpiryDate(UPDATED_EXPIRY_DATE);
        inventoryItem.setFacility_id(UPDATED_FACILITY_ID);
        inventoryItem.setContainer_id(UPDATED_CONTAINER_ID);
        inventoryItem.setLot_id(UPDATED_LOT_ID);
        inventoryItem.setUOM_id(UPDATED_UOM_ID);
        inventoryItem.setComments(UPDATED_COMMENTS);
        inventoryItem.setQuantityOnHand(UPDATED_QUANTITY_ON_HAND);
        inventoryItem.setAvailableToPromise(UPDATED_AVAILABLE_TO_PROMISE);
        inventoryItem.setSerialNumber(UPDATED_SERIAL_NUMBER);
        restInventoryItemMockMvc.perform(put("/api/inventoryItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItem)))
                .andExpect(status().isOk());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        assertThat(inventoryItems).hasSize(1);
        InventoryItem testInventoryItem = inventoryItems.iterator().next();
        assertThat(testInventoryItem.getDateTimeReceived().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_TIME_RECEIVED);
        assertThat(testInventoryItem.getExpiryDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testInventoryItem.getFacility_id()).isEqualTo(UPDATED_FACILITY_ID);
        assertThat(testInventoryItem.getContainer_id()).isEqualTo(UPDATED_CONTAINER_ID);
        assertThat(testInventoryItem.getLot_id()).isEqualTo(UPDATED_LOT_ID);
        assertThat(testInventoryItem.getUOM_id()).isEqualTo(UPDATED_UOM_ID);
        assertThat(testInventoryItem.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testInventoryItem.getQuantityOnHand()).isEqualTo(UPDATED_QUANTITY_ON_HAND);
        assertThat(testInventoryItem.getAvailableToPromise()).isEqualTo(UPDATED_AVAILABLE_TO_PROMISE);
        assertThat(testInventoryItem.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void deleteInventoryItem() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get the inventoryItem
        restInventoryItemMockMvc.perform(delete("/api/inventoryItems/{id}", inventoryItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        assertThat(inventoryItems).hasSize(0);
    }
}
