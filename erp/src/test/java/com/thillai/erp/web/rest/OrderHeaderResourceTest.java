package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.OrderHeader;
import com.thillai.erp.repository.OrderHeaderRepository;

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
 * Test class for the OrderHeaderResource REST controller.
 *
 * @see OrderHeaderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderHeaderResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_ORDER_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_ORDER_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_ORDER_DATE_STR = dateTimeFormatter.print(DEFAULT_ORDER_DATE);

    private static final DateTime DEFAULT_ENTRY_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_ENTRY_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_ENTRY_DATE_STR = dateTimeFormatter.print(DEFAULT_ENTRY_DATE);

    private static final Integer DEFAULT_VISIT_ID = 0;
    private static final Integer UPDATED_VISIT_ID = 1;
    private static final String DEFAULT_CREATED_BY = "SAMPLE_TEXT";
    private static final String UPDATED_CREATED_BY = "UPDATED_TEXT";

    private static final Integer DEFAULT_SYNC_STATUS_ID = 0;
    private static final Integer UPDATED_SYNC_STATUS_ID = 1;

    private static final Integer DEFAULT_BILLING_ACCOUNT_ID = 0;
    private static final Integer UPDATED_BILLING_ACCOUNT_ID = 1;

    private static final Integer DEFAULT_ORIGIN_FACILITY_ID = 0;
    private static final Integer UPDATED_ORIGIN_FACILITY_ID = 1;

    private static final Long DEFAULT_GRAND_TOTAL = 0L;
    private static final Long UPDATED_GRAND_TOTAL = 1L;

    @Inject
    private OrderHeaderRepository orderHeaderRepository;

    private MockMvc restOrderHeaderMockMvc;

    private OrderHeader orderHeader;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderHeaderResource orderHeaderResource = new OrderHeaderResource();
        ReflectionTestUtils.setField(orderHeaderResource, "orderHeaderRepository", orderHeaderRepository);
        this.restOrderHeaderMockMvc = MockMvcBuilders.standaloneSetup(orderHeaderResource).build();
    }

    @Before
    public void initTest() {
        orderHeader = new OrderHeader();
        orderHeader.setOrderDate(DEFAULT_ORDER_DATE);
        orderHeader.setEntryDate(DEFAULT_ENTRY_DATE);
        orderHeader.setVisit_id(DEFAULT_VISIT_ID);
        orderHeader.setCreatedBy(DEFAULT_CREATED_BY);
        orderHeader.setSyncStatus_id(DEFAULT_SYNC_STATUS_ID);
        orderHeader.setBillingAccount_id(DEFAULT_BILLING_ACCOUNT_ID);
        orderHeader.setOriginFacility_id(DEFAULT_ORIGIN_FACILITY_ID);
        orderHeader.setGrandTotal(DEFAULT_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void createOrderHeader() throws Exception {
        // Validate the database is empty
        assertThat(orderHeaderRepository.findAll()).hasSize(0);

        // Create the OrderHeader
        restOrderHeaderMockMvc.perform(post("/api/orderHeaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderHeader)))
                .andExpect(status().isCreated());

        // Validate the OrderHeader in the database
        List<OrderHeader> orderHeaders = orderHeaderRepository.findAll();
        assertThat(orderHeaders).hasSize(1);
        OrderHeader testOrderHeader = orderHeaders.iterator().next();
        assertThat(testOrderHeader.getOrderDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrderHeader.getEntryDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ENTRY_DATE);
        assertThat(testOrderHeader.getVisit_id()).isEqualTo(DEFAULT_VISIT_ID);
        assertThat(testOrderHeader.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrderHeader.getSyncStatus_id()).isEqualTo(DEFAULT_SYNC_STATUS_ID);
        assertThat(testOrderHeader.getBillingAccount_id()).isEqualTo(DEFAULT_BILLING_ACCOUNT_ID);
        assertThat(testOrderHeader.getOriginFacility_id()).isEqualTo(DEFAULT_ORIGIN_FACILITY_ID);
        assertThat(testOrderHeader.getGrandTotal()).isEqualTo(DEFAULT_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderHeaders() throws Exception {
        // Initialize the database
        orderHeaderRepository.saveAndFlush(orderHeader);

        // Get all the orderHeaders
        restOrderHeaderMockMvc.perform(get("/api/orderHeaders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(orderHeader.getId().intValue()))
                .andExpect(jsonPath("$.[0].orderDate").value(DEFAULT_ORDER_DATE_STR))
                .andExpect(jsonPath("$.[0].entryDate").value(DEFAULT_ENTRY_DATE_STR))
                .andExpect(jsonPath("$.[0].visit_id").value(DEFAULT_VISIT_ID))
                .andExpect(jsonPath("$.[0].createdBy").value(DEFAULT_CREATED_BY.toString()))
                .andExpect(jsonPath("$.[0].syncStatus_id").value(DEFAULT_SYNC_STATUS_ID))
                .andExpect(jsonPath("$.[0].billingAccount_id").value(DEFAULT_BILLING_ACCOUNT_ID))
                .andExpect(jsonPath("$.[0].originFacility_id").value(DEFAULT_ORIGIN_FACILITY_ID))
                .andExpect(jsonPath("$.[0].grandTotal").value(DEFAULT_GRAND_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getOrderHeader() throws Exception {
        // Initialize the database
        orderHeaderRepository.saveAndFlush(orderHeader);

        // Get the orderHeader
        restOrderHeaderMockMvc.perform(get("/api/orderHeaders/{id}", orderHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderHeader.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE_STR))
            .andExpect(jsonPath("$.entryDate").value(DEFAULT_ENTRY_DATE_STR))
            .andExpect(jsonPath("$.visit_id").value(DEFAULT_VISIT_ID))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.syncStatus_id").value(DEFAULT_SYNC_STATUS_ID))
            .andExpect(jsonPath("$.billingAccount_id").value(DEFAULT_BILLING_ACCOUNT_ID))
            .andExpect(jsonPath("$.originFacility_id").value(DEFAULT_ORIGIN_FACILITY_ID))
            .andExpect(jsonPath("$.grandTotal").value(DEFAULT_GRAND_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderHeader() throws Exception {
        // Get the orderHeader
        restOrderHeaderMockMvc.perform(get("/api/orderHeaders/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderHeader() throws Exception {
        // Initialize the database
        orderHeaderRepository.saveAndFlush(orderHeader);

        // Update the orderHeader
        orderHeader.setOrderDate(UPDATED_ORDER_DATE);
        orderHeader.setEntryDate(UPDATED_ENTRY_DATE);
        orderHeader.setVisit_id(UPDATED_VISIT_ID);
        orderHeader.setCreatedBy(UPDATED_CREATED_BY);
        orderHeader.setSyncStatus_id(UPDATED_SYNC_STATUS_ID);
        orderHeader.setBillingAccount_id(UPDATED_BILLING_ACCOUNT_ID);
        orderHeader.setOriginFacility_id(UPDATED_ORIGIN_FACILITY_ID);
        orderHeader.setGrandTotal(UPDATED_GRAND_TOTAL);
        restOrderHeaderMockMvc.perform(put("/api/orderHeaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderHeader)))
                .andExpect(status().isOk());

        // Validate the OrderHeader in the database
        List<OrderHeader> orderHeaders = orderHeaderRepository.findAll();
        assertThat(orderHeaders).hasSize(1);
        OrderHeader testOrderHeader = orderHeaders.iterator().next();
        assertThat(testOrderHeader.getOrderDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrderHeader.getEntryDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ENTRY_DATE);
        assertThat(testOrderHeader.getVisit_id()).isEqualTo(UPDATED_VISIT_ID);
        assertThat(testOrderHeader.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrderHeader.getSyncStatus_id()).isEqualTo(UPDATED_SYNC_STATUS_ID);
        assertThat(testOrderHeader.getBillingAccount_id()).isEqualTo(UPDATED_BILLING_ACCOUNT_ID);
        assertThat(testOrderHeader.getOriginFacility_id()).isEqualTo(UPDATED_ORIGIN_FACILITY_ID);
        assertThat(testOrderHeader.getGrandTotal()).isEqualTo(UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void deleteOrderHeader() throws Exception {
        // Initialize the database
        orderHeaderRepository.saveAndFlush(orderHeader);

        // Get the orderHeader
        restOrderHeaderMockMvc.perform(delete("/api/orderHeaders/{id}", orderHeader.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderHeader> orderHeaders = orderHeaderRepository.findAll();
        assertThat(orderHeaders).hasSize(0);
    }
}
