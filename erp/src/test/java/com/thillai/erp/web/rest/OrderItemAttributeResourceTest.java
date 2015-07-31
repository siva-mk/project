package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.OrderItemAttribute;
import com.thillai.erp.repository.OrderItemAttributeRepository;

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
 * Test class for the OrderItemAttributeResource REST controller.
 *
 * @see OrderItemAttributeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderItemAttributeResourceTest {

    private static final String DEFAULT_ATTRIBUTE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_ATTRIBUTE_VALUE = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_VALUE = "UPDATED_TEXT";

    private static final Integer DEFAULT_ORDER_ITEM_SEQ_ID = 0;
    private static final Integer UPDATED_ORDER_ITEM_SEQ_ID = 1;

    @Inject
    private OrderItemAttributeRepository orderItemAttributeRepository;

    private MockMvc restOrderItemAttributeMockMvc;

    private OrderItemAttribute orderItemAttribute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderItemAttributeResource orderItemAttributeResource = new OrderItemAttributeResource();
        ReflectionTestUtils.setField(orderItemAttributeResource, "orderItemAttributeRepository", orderItemAttributeRepository);
        this.restOrderItemAttributeMockMvc = MockMvcBuilders.standaloneSetup(orderItemAttributeResource).build();
    }

    @Before
    public void initTest() {
        orderItemAttribute = new OrderItemAttribute();
        orderItemAttribute.setAttributeName(DEFAULT_ATTRIBUTE_NAME);
        orderItemAttribute.setAttributeValue(DEFAULT_ATTRIBUTE_VALUE);
        orderItemAttribute.setOrderItemSeq_id(DEFAULT_ORDER_ITEM_SEQ_ID);
    }

    @Test
    @Transactional
    public void createOrderItemAttribute() throws Exception {
        // Validate the database is empty
        assertThat(orderItemAttributeRepository.findAll()).hasSize(0);

        // Create the OrderItemAttribute
        restOrderItemAttributeMockMvc.perform(post("/api/orderItemAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItemAttribute)))
                .andExpect(status().isCreated());

        // Validate the OrderItemAttribute in the database
        List<OrderItemAttribute> orderItemAttributes = orderItemAttributeRepository.findAll();
        assertThat(orderItemAttributes).hasSize(1);
        OrderItemAttribute testOrderItemAttribute = orderItemAttributes.iterator().next();
        assertThat(testOrderItemAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testOrderItemAttribute.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
        assertThat(testOrderItemAttribute.getOrderItemSeq_id()).isEqualTo(DEFAULT_ORDER_ITEM_SEQ_ID);
    }

    @Test
    @Transactional
    public void getAllOrderItemAttributes() throws Exception {
        // Initialize the database
        orderItemAttributeRepository.saveAndFlush(orderItemAttribute);

        // Get all the orderItemAttributes
        restOrderItemAttributeMockMvc.perform(get("/api/orderItemAttributes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(orderItemAttribute.getId().intValue()))
                .andExpect(jsonPath("$.[0].attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
                .andExpect(jsonPath("$.[0].attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()))
                .andExpect(jsonPath("$.[0].orderItemSeq_id").value(DEFAULT_ORDER_ITEM_SEQ_ID));
    }

    @Test
    @Transactional
    public void getOrderItemAttribute() throws Exception {
        // Initialize the database
        orderItemAttributeRepository.saveAndFlush(orderItemAttribute);

        // Get the orderItemAttribute
        restOrderItemAttributeMockMvc.perform(get("/api/orderItemAttributes/{id}", orderItemAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderItemAttribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()))
            .andExpect(jsonPath("$.orderItemSeq_id").value(DEFAULT_ORDER_ITEM_SEQ_ID));
    }

    @Test
    @Transactional
    public void getNonExistingOrderItemAttribute() throws Exception {
        // Get the orderItemAttribute
        restOrderItemAttributeMockMvc.perform(get("/api/orderItemAttributes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItemAttribute() throws Exception {
        // Initialize the database
        orderItemAttributeRepository.saveAndFlush(orderItemAttribute);

        // Update the orderItemAttribute
        orderItemAttribute.setAttributeName(UPDATED_ATTRIBUTE_NAME);
        orderItemAttribute.setAttributeValue(UPDATED_ATTRIBUTE_VALUE);
        orderItemAttribute.setOrderItemSeq_id(UPDATED_ORDER_ITEM_SEQ_ID);
        restOrderItemAttributeMockMvc.perform(put("/api/orderItemAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItemAttribute)))
                .andExpect(status().isOk());

        // Validate the OrderItemAttribute in the database
        List<OrderItemAttribute> orderItemAttributes = orderItemAttributeRepository.findAll();
        assertThat(orderItemAttributes).hasSize(1);
        OrderItemAttribute testOrderItemAttribute = orderItemAttributes.iterator().next();
        assertThat(testOrderItemAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testOrderItemAttribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
        assertThat(testOrderItemAttribute.getOrderItemSeq_id()).isEqualTo(UPDATED_ORDER_ITEM_SEQ_ID);
    }

    @Test
    @Transactional
    public void deleteOrderItemAttribute() throws Exception {
        // Initialize the database
        orderItemAttributeRepository.saveAndFlush(orderItemAttribute);

        // Get the orderItemAttribute
        restOrderItemAttributeMockMvc.perform(delete("/api/orderItemAttributes/{id}", orderItemAttribute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderItemAttribute> orderItemAttributes = orderItemAttributeRepository.findAll();
        assertThat(orderItemAttributes).hasSize(0);
    }
}
