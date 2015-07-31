package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.OrderItemBilling;
import com.thillai.erp.repository.OrderItemBillingRepository;

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
 * Test class for the OrderItemBillingResource REST controller.
 *
 * @see OrderItemBillingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderItemBillingResourceTest {


    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final Long DEFAULT_AMOUNT = 0L;
    private static final Long UPDATED_AMOUNT = 1L;

    @Inject
    private OrderItemBillingRepository orderItemBillingRepository;

    private MockMvc restOrderItemBillingMockMvc;

    private OrderItemBilling orderItemBilling;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderItemBillingResource orderItemBillingResource = new OrderItemBillingResource();
        ReflectionTestUtils.setField(orderItemBillingResource, "orderItemBillingRepository", orderItemBillingRepository);
        this.restOrderItemBillingMockMvc = MockMvcBuilders.standaloneSetup(orderItemBillingResource).build();
    }

    @Before
    public void initTest() {
        orderItemBilling = new OrderItemBilling();
        orderItemBilling.setQuantity(DEFAULT_QUANTITY);
        orderItemBilling.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createOrderItemBilling() throws Exception {
        // Validate the database is empty
        assertThat(orderItemBillingRepository.findAll()).hasSize(0);

        // Create the OrderItemBilling
        restOrderItemBillingMockMvc.perform(post("/api/orderItemBillings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItemBilling)))
                .andExpect(status().isCreated());

        // Validate the OrderItemBilling in the database
        List<OrderItemBilling> orderItemBillings = orderItemBillingRepository.findAll();
        assertThat(orderItemBillings).hasSize(1);
        OrderItemBilling testOrderItemBilling = orderItemBillings.iterator().next();
        assertThat(testOrderItemBilling.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderItemBilling.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillings() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillings
        restOrderItemBillingMockMvc.perform(get("/api/orderItemBillings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(orderItemBilling.getId().intValue()))
                .andExpect(jsonPath("$.[0].quantity").value(DEFAULT_QUANTITY))
                .andExpect(jsonPath("$.[0].amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getOrderItemBilling() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get the orderItemBilling
        restOrderItemBillingMockMvc.perform(get("/api/orderItemBillings/{id}", orderItemBilling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderItemBilling.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderItemBilling() throws Exception {
        // Get the orderItemBilling
        restOrderItemBillingMockMvc.perform(get("/api/orderItemBillings/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItemBilling() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Update the orderItemBilling
        orderItemBilling.setQuantity(UPDATED_QUANTITY);
        orderItemBilling.setAmount(UPDATED_AMOUNT);
        restOrderItemBillingMockMvc.perform(put("/api/orderItemBillings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItemBilling)))
                .andExpect(status().isOk());

        // Validate the OrderItemBilling in the database
        List<OrderItemBilling> orderItemBillings = orderItemBillingRepository.findAll();
        assertThat(orderItemBillings).hasSize(1);
        OrderItemBilling testOrderItemBilling = orderItemBillings.iterator().next();
        assertThat(testOrderItemBilling.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderItemBilling.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteOrderItemBilling() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get the orderItemBilling
        restOrderItemBillingMockMvc.perform(delete("/api/orderItemBillings/{id}", orderItemBilling.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderItemBilling> orderItemBillings = orderItemBillingRepository.findAll();
        assertThat(orderItemBillings).hasSize(0);
    }
}
