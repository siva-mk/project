package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.OrderItem;
import com.thillai.erp.repository.OrderItemRepository;

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
 * Test class for the OrderItemResource REST controller.
 *
 * @see OrderItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderItemResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final Integer DEFAULT_BUDGET_ID = 0;
    private static final Integer UPDATED_BUDGET_ID = 1;

    private static final Integer DEFAULT_BUDGET_ITEM_ID = 0;
    private static final Integer UPDATED_BUDGET_ITEM_ID = 1;

    private static final Integer DEFAULT_PRODUCT_FEADUTE_ID = 0;
    private static final Integer UPDATED_PRODUCT_FEADUTE_ID = 1;

    private static final Integer DEFAULT_QUOTE_ID = 0;
    private static final Integer UPDATED_QUOTE_ID = 1;

    private static final Integer DEFAULT_QUOTE_ITEM_ID = 0;
    private static final Integer UPDATED_QUOTE_ITEM_ID = 1;

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final Long DEFAULT_UNIT_PRICE = 0L;
    private static final Long UPDATED_UNIT_PRICE = 1L;

    private static final Long DEFAULT_UNIT_LIST_PRICE = 0L;
    private static final Long UPDATED_UNIT_LIST_PRICE = 1L;

    private static final Long DEFAULT_UNIT_AVERAGE_COST = 0L;
    private static final Long UPDATED_UNIT_AVERAGE_COST = 1L;

    private static final DateTime DEFAULT_ESTIMATED_DELIVERY_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_ESTIMATED_DELIVERY_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_ESTIMATED_DELIVERY_DATE_STR = dateTimeFormatter.print(DEFAULT_ESTIMATED_DELIVERY_DATE);
    private static final String DEFAULT_ITEM_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_ITEM_DESCRIPTION = "UPDATED_TEXT";

    private static final Integer DEFAULT_CORRESPONDING_PO_ID = 0;
    private static final Integer UPDATED_CORRESPONDING_PO_ID = 1;

    @Inject
    private OrderItemRepository orderItemRepository;

    private MockMvc restOrderItemMockMvc;

    private OrderItem orderItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderItemResource orderItemResource = new OrderItemResource();
        ReflectionTestUtils.setField(orderItemResource, "orderItemRepository", orderItemRepository);
        this.restOrderItemMockMvc = MockMvcBuilders.standaloneSetup(orderItemResource).build();
    }

    @Before
    public void initTest() {
        orderItem = new OrderItem();
        orderItem.setBudget_id(DEFAULT_BUDGET_ID);
        orderItem.setBudgetItem_id(DEFAULT_BUDGET_ITEM_ID);
        orderItem.setProductFeadute_id(DEFAULT_PRODUCT_FEADUTE_ID);
        orderItem.setQuote_id(DEFAULT_QUOTE_ID);
        orderItem.setQuoteItem_id(DEFAULT_QUOTE_ITEM_ID);
        orderItem.setQuantity(DEFAULT_QUANTITY);
        orderItem.setUnitPrice(DEFAULT_UNIT_PRICE);
        orderItem.setUnitListPrice(DEFAULT_UNIT_LIST_PRICE);
        orderItem.setUnitAverageCost(DEFAULT_UNIT_AVERAGE_COST);
        orderItem.setEstimatedDeliveryDate(DEFAULT_ESTIMATED_DELIVERY_DATE);
        orderItem.setItemDescription(DEFAULT_ITEM_DESCRIPTION);
        orderItem.setCorrespondingPo_id(DEFAULT_CORRESPONDING_PO_ID);
    }

    @Test
    @Transactional
    public void createOrderItem() throws Exception {
        // Validate the database is empty
        assertThat(orderItemRepository.findAll()).hasSize(0);

        // Create the OrderItem
        restOrderItemMockMvc.perform(post("/api/orderItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItem)))
                .andExpect(status().isCreated());

        // Validate the OrderItem in the database
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems).hasSize(1);
        OrderItem testOrderItem = orderItems.iterator().next();
        assertThat(testOrderItem.getBudget_id()).isEqualTo(DEFAULT_BUDGET_ID);
        assertThat(testOrderItem.getBudgetItem_id()).isEqualTo(DEFAULT_BUDGET_ITEM_ID);
        assertThat(testOrderItem.getProductFeadute_id()).isEqualTo(DEFAULT_PRODUCT_FEADUTE_ID);
        assertThat(testOrderItem.getQuote_id()).isEqualTo(DEFAULT_QUOTE_ID);
        assertThat(testOrderItem.getQuoteItem_id()).isEqualTo(DEFAULT_QUOTE_ITEM_ID);
        assertThat(testOrderItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testOrderItem.getUnitListPrice()).isEqualTo(DEFAULT_UNIT_LIST_PRICE);
        assertThat(testOrderItem.getUnitAverageCost()).isEqualTo(DEFAULT_UNIT_AVERAGE_COST);
        assertThat(testOrderItem.getEstimatedDeliveryDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ESTIMATED_DELIVERY_DATE);
        assertThat(testOrderItem.getItemDescription()).isEqualTo(DEFAULT_ITEM_DESCRIPTION);
        assertThat(testOrderItem.getCorrespondingPo_id()).isEqualTo(DEFAULT_CORRESPONDING_PO_ID);
    }

    @Test
    @Transactional
    public void getAllOrderItems() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItems
        restOrderItemMockMvc.perform(get("/api/orderItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(orderItem.getId().intValue()))
                .andExpect(jsonPath("$.[0].budget_id").value(DEFAULT_BUDGET_ID))
                .andExpect(jsonPath("$.[0].budgetItem_id").value(DEFAULT_BUDGET_ITEM_ID))
                .andExpect(jsonPath("$.[0].productFeadute_id").value(DEFAULT_PRODUCT_FEADUTE_ID))
                .andExpect(jsonPath("$.[0].quote_id").value(DEFAULT_QUOTE_ID))
                .andExpect(jsonPath("$.[0].quoteItem_id").value(DEFAULT_QUOTE_ITEM_ID))
                .andExpect(jsonPath("$.[0].quantity").value(DEFAULT_QUANTITY))
                .andExpect(jsonPath("$.[0].unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
                .andExpect(jsonPath("$.[0].unitListPrice").value(DEFAULT_UNIT_LIST_PRICE.intValue()))
                .andExpect(jsonPath("$.[0].unitAverageCost").value(DEFAULT_UNIT_AVERAGE_COST.intValue()))
                .andExpect(jsonPath("$.[0].estimatedDeliveryDate").value(DEFAULT_ESTIMATED_DELIVERY_DATE_STR))
                .andExpect(jsonPath("$.[0].itemDescription").value(DEFAULT_ITEM_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].correspondingPo_id").value(DEFAULT_CORRESPONDING_PO_ID));
    }

    @Test
    @Transactional
    public void getOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/orderItems/{id}", orderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderItem.getId().intValue()))
            .andExpect(jsonPath("$.budget_id").value(DEFAULT_BUDGET_ID))
            .andExpect(jsonPath("$.budgetItem_id").value(DEFAULT_BUDGET_ITEM_ID))
            .andExpect(jsonPath("$.productFeadute_id").value(DEFAULT_PRODUCT_FEADUTE_ID))
            .andExpect(jsonPath("$.quote_id").value(DEFAULT_QUOTE_ID))
            .andExpect(jsonPath("$.quoteItem_id").value(DEFAULT_QUOTE_ITEM_ID))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.unitListPrice").value(DEFAULT_UNIT_LIST_PRICE.intValue()))
            .andExpect(jsonPath("$.unitAverageCost").value(DEFAULT_UNIT_AVERAGE_COST.intValue()))
            .andExpect(jsonPath("$.estimatedDeliveryDate").value(DEFAULT_ESTIMATED_DELIVERY_DATE_STR))
            .andExpect(jsonPath("$.itemDescription").value(DEFAULT_ITEM_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.correspondingPo_id").value(DEFAULT_CORRESPONDING_PO_ID));
    }

    @Test
    @Transactional
    public void getNonExistingOrderItem() throws Exception {
        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/orderItems/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Update the orderItem
        orderItem.setBudget_id(UPDATED_BUDGET_ID);
        orderItem.setBudgetItem_id(UPDATED_BUDGET_ITEM_ID);
        orderItem.setProductFeadute_id(UPDATED_PRODUCT_FEADUTE_ID);
        orderItem.setQuote_id(UPDATED_QUOTE_ID);
        orderItem.setQuoteItem_id(UPDATED_QUOTE_ITEM_ID);
        orderItem.setQuantity(UPDATED_QUANTITY);
        orderItem.setUnitPrice(UPDATED_UNIT_PRICE);
        orderItem.setUnitListPrice(UPDATED_UNIT_LIST_PRICE);
        orderItem.setUnitAverageCost(UPDATED_UNIT_AVERAGE_COST);
        orderItem.setEstimatedDeliveryDate(UPDATED_ESTIMATED_DELIVERY_DATE);
        orderItem.setItemDescription(UPDATED_ITEM_DESCRIPTION);
        orderItem.setCorrespondingPo_id(UPDATED_CORRESPONDING_PO_ID);
        restOrderItemMockMvc.perform(put("/api/orderItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItem)))
                .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems).hasSize(1);
        OrderItem testOrderItem = orderItems.iterator().next();
        assertThat(testOrderItem.getBudget_id()).isEqualTo(UPDATED_BUDGET_ID);
        assertThat(testOrderItem.getBudgetItem_id()).isEqualTo(UPDATED_BUDGET_ITEM_ID);
        assertThat(testOrderItem.getProductFeadute_id()).isEqualTo(UPDATED_PRODUCT_FEADUTE_ID);
        assertThat(testOrderItem.getQuote_id()).isEqualTo(UPDATED_QUOTE_ID);
        assertThat(testOrderItem.getQuoteItem_id()).isEqualTo(UPDATED_QUOTE_ITEM_ID);
        assertThat(testOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testOrderItem.getUnitListPrice()).isEqualTo(UPDATED_UNIT_LIST_PRICE);
        assertThat(testOrderItem.getUnitAverageCost()).isEqualTo(UPDATED_UNIT_AVERAGE_COST);
        assertThat(testOrderItem.getEstimatedDeliveryDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ESTIMATED_DELIVERY_DATE);
        assertThat(testOrderItem.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
        assertThat(testOrderItem.getCorrespondingPo_id()).isEqualTo(UPDATED_CORRESPONDING_PO_ID);
    }

    @Test
    @Transactional
    public void deleteOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get the orderItem
        restOrderItemMockMvc.perform(delete("/api/orderItems/{id}", orderItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems).hasSize(0);
    }
}
