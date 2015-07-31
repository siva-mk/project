package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.OrderStatus;
import com.thillai.erp.repository.OrderStatusRepository;

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
 * Test class for the OrderStatusResource REST controller.
 *
 * @see OrderStatusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderStatusResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final Integer DEFAULT_ORDER_ITEM_ID = 0;
    private static final Integer UPDATED_ORDER_ITEM_ID = 1;

    private static final DateTime DEFAULT_STATUS_DATE_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_STATUS_DATE_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_STATUS_DATE_TIME_STR = dateTimeFormatter.print(DEFAULT_STATUS_DATE_TIME);

    @Inject
    private OrderStatusRepository orderStatusRepository;

    private MockMvc restOrderStatusMockMvc;

    private OrderStatus orderStatus;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderStatusResource orderStatusResource = new OrderStatusResource();
        ReflectionTestUtils.setField(orderStatusResource, "orderStatusRepository", orderStatusRepository);
        this.restOrderStatusMockMvc = MockMvcBuilders.standaloneSetup(orderStatusResource).build();
    }

    @Before
    public void initTest() {
        orderStatus = new OrderStatus();
        orderStatus.setOrderItem_id(DEFAULT_ORDER_ITEM_ID);
        orderStatus.setStatusDateTime(DEFAULT_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void createOrderStatus() throws Exception {
        // Validate the database is empty
        assertThat(orderStatusRepository.findAll()).hasSize(0);

        // Create the OrderStatus
        restOrderStatusMockMvc.perform(post("/api/orderStatuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderStatus)))
                .andExpect(status().isCreated());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatuss = orderStatusRepository.findAll();
        assertThat(orderStatuss).hasSize(1);
        OrderStatus testOrderStatus = orderStatuss.iterator().next();
        assertThat(testOrderStatus.getOrderItem_id()).isEqualTo(DEFAULT_ORDER_ITEM_ID);
        assertThat(testOrderStatus.getStatusDateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOrderStatuss() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatuss
        restOrderStatusMockMvc.perform(get("/api/orderStatuss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(orderStatus.getId().intValue()))
                .andExpect(jsonPath("$.[0].orderItem_id").value(DEFAULT_ORDER_ITEM_ID))
                .andExpect(jsonPath("$.[0].statusDateTime").value(DEFAULT_STATUS_DATE_TIME_STR));
    }

    @Test
    @Transactional
    public void getOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get the orderStatus
        restOrderStatusMockMvc.perform(get("/api/orderStatuss/{id}", orderStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderStatus.getId().intValue()))
            .andExpect(jsonPath("$.orderItem_id").value(DEFAULT_ORDER_ITEM_ID))
            .andExpect(jsonPath("$.statusDateTime").value(DEFAULT_STATUS_DATE_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingOrderStatus() throws Exception {
        // Get the orderStatus
        restOrderStatusMockMvc.perform(get("/api/orderStatuss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Update the orderStatus
        orderStatus.setOrderItem_id(UPDATED_ORDER_ITEM_ID);
        orderStatus.setStatusDateTime(UPDATED_STATUS_DATE_TIME);
        restOrderStatusMockMvc.perform(put("/api/orderStatuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderStatus)))
                .andExpect(status().isOk());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatuss = orderStatusRepository.findAll();
        assertThat(orderStatuss).hasSize(1);
        OrderStatus testOrderStatus = orderStatuss.iterator().next();
        assertThat(testOrderStatus.getOrderItem_id()).isEqualTo(UPDATED_ORDER_ITEM_ID);
        assertThat(testOrderStatus.getStatusDateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void deleteOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get the orderStatus
        restOrderStatusMockMvc.perform(delete("/api/orderStatuss/{id}", orderStatus.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderStatus> orderStatuss = orderStatusRepository.findAll();
        assertThat(orderStatuss).hasSize(0);
    }
}
