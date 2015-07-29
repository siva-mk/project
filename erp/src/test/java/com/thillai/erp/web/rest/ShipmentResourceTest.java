package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.Shipment;
import com.thillai.erp.repository.ShipmentRepository;

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
 * Test class for the ShipmentResource REST controller.
 *
 * @see ShipmentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShipmentResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_ESTIMATE_SHIP_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_ESTIMATE_SHIP_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_ESTIMATE_SHIP_DATE_STR = dateTimeFormatter.print(DEFAULT_ESTIMATE_SHIP_DATE);

    private static final DateTime DEFAULT_ESTIMATED_READY_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_ESTIMATED_READY_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_ESTIMATED_READY_DATE_STR = dateTimeFormatter.print(DEFAULT_ESTIMATED_READY_DATE);

    private static final DateTime DEFAULT_ESTIMATED_ARRIVAL_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_ESTIMATED_ARRIVAL_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_ESTIMATED_ARRIVAL_DATE_STR = dateTimeFormatter.print(DEFAULT_ESTIMATED_ARRIVAL_DATE);

    private static final Long DEFAULT_ESTIMATED_SHIP_COST = 0L;
    private static final Long UPDATED_ESTIMATED_SHIP_COST = 1L;

    private static final Long DEFAULT_ACTUAL_SHIP_COST = 0L;
    private static final Long UPDATED_ACTUAL_SHIP_COST = 1L;

    private static final DateTime DEFAULT_LATEST_CANCEL_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_LATEST_CANCEL_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_LATEST_CANCEL_DATE_STR = dateTimeFormatter.print(DEFAULT_LATEST_CANCEL_DATE);
    private static final String DEFAULT_HANDLING_INSTRUCTION = "SAMPLE_TEXT";
    private static final String UPDATED_HANDLING_INSTRUCTION = "UPDATED_TEXT";

    private static final DateTime DEFAULT_LAST_UPDATED = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_LAST_UPDATED = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_LAST_UPDATED_STR = dateTimeFormatter.print(DEFAULT_LAST_UPDATED);

    @Inject
    private ShipmentRepository shipmentRepository;

    private MockMvc restShipmentMockMvc;

    private Shipment shipment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentResource shipmentResource = new ShipmentResource();
        ReflectionTestUtils.setField(shipmentResource, "shipmentRepository", shipmentRepository);
        this.restShipmentMockMvc = MockMvcBuilders.standaloneSetup(shipmentResource).build();
    }

    @Before
    public void initTest() {
        shipment = new Shipment();
        shipment.setEstimateShipDate(DEFAULT_ESTIMATE_SHIP_DATE);
        shipment.setEstimatedReadyDate(DEFAULT_ESTIMATED_READY_DATE);
        shipment.setEstimatedArrivalDate(DEFAULT_ESTIMATED_ARRIVAL_DATE);
        shipment.setEstimatedShipCost(DEFAULT_ESTIMATED_SHIP_COST);
        shipment.setActualShipCost(DEFAULT_ACTUAL_SHIP_COST);
        shipment.setLatestCancelDate(DEFAULT_LATEST_CANCEL_DATE);
        shipment.setHandlingInstruction(DEFAULT_HANDLING_INSTRUCTION);
        shipment.setLastUpdated(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void createShipment() throws Exception {
        // Validate the database is empty
        assertThat(shipmentRepository.findAll()).hasSize(0);

        // Create the Shipment
        restShipmentMockMvc.perform(post("/api/shipments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment)))
                .andExpect(status().isCreated());

        // Validate the Shipment in the database
        List<Shipment> shipments = shipmentRepository.findAll();
        assertThat(shipments).hasSize(1);
        Shipment testShipment = shipments.iterator().next();
        assertThat(testShipment.getEstimateShipDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ESTIMATE_SHIP_DATE);
        assertThat(testShipment.getEstimatedReadyDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ESTIMATED_READY_DATE);
        assertThat(testShipment.getEstimatedArrivalDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ESTIMATED_ARRIVAL_DATE);
        assertThat(testShipment.getEstimatedShipCost()).isEqualTo(DEFAULT_ESTIMATED_SHIP_COST);
        assertThat(testShipment.getActualShipCost()).isEqualTo(DEFAULT_ACTUAL_SHIP_COST);
        assertThat(testShipment.getLatestCancelDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_LATEST_CANCEL_DATE);
        assertThat(testShipment.getHandlingInstruction()).isEqualTo(DEFAULT_HANDLING_INSTRUCTION);
        assertThat(testShipment.getLastUpdated().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void getAllShipments() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get all the shipments
        restShipmentMockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(shipment.getId().intValue()))
                .andExpect(jsonPath("$.[0].estimateShipDate").value(DEFAULT_ESTIMATE_SHIP_DATE_STR))
                .andExpect(jsonPath("$.[0].estimatedReadyDate").value(DEFAULT_ESTIMATED_READY_DATE_STR))
                .andExpect(jsonPath("$.[0].estimatedArrivalDate").value(DEFAULT_ESTIMATED_ARRIVAL_DATE_STR))
                .andExpect(jsonPath("$.[0].estimatedShipCost").value(DEFAULT_ESTIMATED_SHIP_COST.intValue()))
                .andExpect(jsonPath("$.[0].actualShipCost").value(DEFAULT_ACTUAL_SHIP_COST.intValue()))
                .andExpect(jsonPath("$.[0].latestCancelDate").value(DEFAULT_LATEST_CANCEL_DATE_STR))
                .andExpect(jsonPath("$.[0].handlingInstruction").value(DEFAULT_HANDLING_INSTRUCTION.toString()))
                .andExpect(jsonPath("$.[0].lastUpdated").value(DEFAULT_LAST_UPDATED_STR));
    }

    @Test
    @Transactional
    public void getShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get the shipment
        restShipmentMockMvc.perform(get("/api/shipments/{id}", shipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shipment.getId().intValue()))
            .andExpect(jsonPath("$.estimateShipDate").value(DEFAULT_ESTIMATE_SHIP_DATE_STR))
            .andExpect(jsonPath("$.estimatedReadyDate").value(DEFAULT_ESTIMATED_READY_DATE_STR))
            .andExpect(jsonPath("$.estimatedArrivalDate").value(DEFAULT_ESTIMATED_ARRIVAL_DATE_STR))
            .andExpect(jsonPath("$.estimatedShipCost").value(DEFAULT_ESTIMATED_SHIP_COST.intValue()))
            .andExpect(jsonPath("$.actualShipCost").value(DEFAULT_ACTUAL_SHIP_COST.intValue()))
            .andExpect(jsonPath("$.latestCancelDate").value(DEFAULT_LATEST_CANCEL_DATE_STR))
            .andExpect(jsonPath("$.handlingInstruction").value(DEFAULT_HANDLING_INSTRUCTION.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingShipment() throws Exception {
        // Get the shipment
        restShipmentMockMvc.perform(get("/api/shipments/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Update the shipment
        shipment.setEstimateShipDate(UPDATED_ESTIMATE_SHIP_DATE);
        shipment.setEstimatedReadyDate(UPDATED_ESTIMATED_READY_DATE);
        shipment.setEstimatedArrivalDate(UPDATED_ESTIMATED_ARRIVAL_DATE);
        shipment.setEstimatedShipCost(UPDATED_ESTIMATED_SHIP_COST);
        shipment.setActualShipCost(UPDATED_ACTUAL_SHIP_COST);
        shipment.setLatestCancelDate(UPDATED_LATEST_CANCEL_DATE);
        shipment.setHandlingInstruction(UPDATED_HANDLING_INSTRUCTION);
        shipment.setLastUpdated(UPDATED_LAST_UPDATED);
        restShipmentMockMvc.perform(put("/api/shipments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment)))
                .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipments = shipmentRepository.findAll();
        assertThat(shipments).hasSize(1);
        Shipment testShipment = shipments.iterator().next();
        assertThat(testShipment.getEstimateShipDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ESTIMATE_SHIP_DATE);
        assertThat(testShipment.getEstimatedReadyDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ESTIMATED_READY_DATE);
        assertThat(testShipment.getEstimatedArrivalDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ESTIMATED_ARRIVAL_DATE);
        assertThat(testShipment.getEstimatedShipCost()).isEqualTo(UPDATED_ESTIMATED_SHIP_COST);
        assertThat(testShipment.getActualShipCost()).isEqualTo(UPDATED_ACTUAL_SHIP_COST);
        assertThat(testShipment.getLatestCancelDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_LATEST_CANCEL_DATE);
        assertThat(testShipment.getHandlingInstruction()).isEqualTo(UPDATED_HANDLING_INSTRUCTION);
        assertThat(testShipment.getLastUpdated().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void deleteShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get the shipment
        restShipmentMockMvc.perform(delete("/api/shipments/{id}", shipment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Shipment> shipments = shipmentRepository.findAll();
        assertThat(shipments).hasSize(0);
    }
}
