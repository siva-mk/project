package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.InventoryItemStatus;
import com.thillai.erp.repository.InventoryItemStatusRepository;

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
 * Test class for the InventoryItemStatusResource REST controller.
 *
 * @see InventoryItemStatusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InventoryItemStatusResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_STATUS_DATE_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_STATUS_DATE_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_STATUS_DATE_TIME_STR = dateTimeFormatter.print(DEFAULT_STATUS_DATE_TIME);

    @Inject
    private InventoryItemStatusRepository inventoryItemStatusRepository;

    private MockMvc restInventoryItemStatusMockMvc;

    private InventoryItemStatus inventoryItemStatus;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InventoryItemStatusResource inventoryItemStatusResource = new InventoryItemStatusResource();
        ReflectionTestUtils.setField(inventoryItemStatusResource, "inventoryItemStatusRepository", inventoryItemStatusRepository);
        this.restInventoryItemStatusMockMvc = MockMvcBuilders.standaloneSetup(inventoryItemStatusResource).build();
    }

    @Before
    public void initTest() {
        inventoryItemStatus = new InventoryItemStatus();
        inventoryItemStatus.setStatusDateTime(DEFAULT_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void createInventoryItemStatus() throws Exception {
        // Validate the database is empty
        assertThat(inventoryItemStatusRepository.findAll()).hasSize(0);

        // Create the InventoryItemStatus
        restInventoryItemStatusMockMvc.perform(post("/api/inventoryItemStatuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItemStatus)))
                .andExpect(status().isCreated());

        // Validate the InventoryItemStatus in the database
        List<InventoryItemStatus> inventoryItemStatuss = inventoryItemStatusRepository.findAll();
        assertThat(inventoryItemStatuss).hasSize(1);
        InventoryItemStatus testInventoryItemStatus = inventoryItemStatuss.iterator().next();
        assertThat(testInventoryItemStatus.getStatusDateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllInventoryItemStatuss() throws Exception {
        // Initialize the database
        inventoryItemStatusRepository.saveAndFlush(inventoryItemStatus);

        // Get all the inventoryItemStatuss
        restInventoryItemStatusMockMvc.perform(get("/api/inventoryItemStatuss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(inventoryItemStatus.getId().intValue()))
                .andExpect(jsonPath("$.[0].statusDateTime").value(DEFAULT_STATUS_DATE_TIME_STR));
    }

    @Test
    @Transactional
    public void getInventoryItemStatus() throws Exception {
        // Initialize the database
        inventoryItemStatusRepository.saveAndFlush(inventoryItemStatus);

        // Get the inventoryItemStatus
        restInventoryItemStatusMockMvc.perform(get("/api/inventoryItemStatuss/{id}", inventoryItemStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(inventoryItemStatus.getId().intValue()))
            .andExpect(jsonPath("$.statusDateTime").value(DEFAULT_STATUS_DATE_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryItemStatus() throws Exception {
        // Get the inventoryItemStatus
        restInventoryItemStatusMockMvc.perform(get("/api/inventoryItemStatuss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItemStatus() throws Exception {
        // Initialize the database
        inventoryItemStatusRepository.saveAndFlush(inventoryItemStatus);

        // Update the inventoryItemStatus
        inventoryItemStatus.setStatusDateTime(UPDATED_STATUS_DATE_TIME);
        restInventoryItemStatusMockMvc.perform(put("/api/inventoryItemStatuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItemStatus)))
                .andExpect(status().isOk());

        // Validate the InventoryItemStatus in the database
        List<InventoryItemStatus> inventoryItemStatuss = inventoryItemStatusRepository.findAll();
        assertThat(inventoryItemStatuss).hasSize(1);
        InventoryItemStatus testInventoryItemStatus = inventoryItemStatuss.iterator().next();
        assertThat(testInventoryItemStatus.getStatusDateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void deleteInventoryItemStatus() throws Exception {
        // Initialize the database
        inventoryItemStatusRepository.saveAndFlush(inventoryItemStatus);

        // Get the inventoryItemStatus
        restInventoryItemStatusMockMvc.perform(delete("/api/inventoryItemStatuss/{id}", inventoryItemStatus.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InventoryItemStatus> inventoryItemStatuss = inventoryItemStatusRepository.findAll();
        assertThat(inventoryItemStatuss).hasSize(0);
    }
}
