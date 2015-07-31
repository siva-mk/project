package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.OrderTypeAttribute;
import com.thillai.erp.repository.OrderTypeAttributeRepository;

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
 * Test class for the OrderTypeAttributeResource REST controller.
 *
 * @see OrderTypeAttributeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderTypeAttributeResourceTest {

    private static final String DEFAULT_ATTRIBUTE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_NAME = "UPDATED_TEXT";

    @Inject
    private OrderTypeAttributeRepository orderTypeAttributeRepository;

    private MockMvc restOrderTypeAttributeMockMvc;

    private OrderTypeAttribute orderTypeAttribute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderTypeAttributeResource orderTypeAttributeResource = new OrderTypeAttributeResource();
        ReflectionTestUtils.setField(orderTypeAttributeResource, "orderTypeAttributeRepository", orderTypeAttributeRepository);
        this.restOrderTypeAttributeMockMvc = MockMvcBuilders.standaloneSetup(orderTypeAttributeResource).build();
    }

    @Before
    public void initTest() {
        orderTypeAttribute = new OrderTypeAttribute();
        orderTypeAttribute.setAttributeName(DEFAULT_ATTRIBUTE_NAME);
    }

    @Test
    @Transactional
    public void createOrderTypeAttribute() throws Exception {
        // Validate the database is empty
        assertThat(orderTypeAttributeRepository.findAll()).hasSize(0);

        // Create the OrderTypeAttribute
        restOrderTypeAttributeMockMvc.perform(post("/api/orderTypeAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderTypeAttribute)))
                .andExpect(status().isCreated());

        // Validate the OrderTypeAttribute in the database
        List<OrderTypeAttribute> orderTypeAttributes = orderTypeAttributeRepository.findAll();
        assertThat(orderTypeAttributes).hasSize(1);
        OrderTypeAttribute testOrderTypeAttribute = orderTypeAttributes.iterator().next();
        assertThat(testOrderTypeAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
    }

    @Test
    @Transactional
    public void getAllOrderTypeAttributes() throws Exception {
        // Initialize the database
        orderTypeAttributeRepository.saveAndFlush(orderTypeAttribute);

        // Get all the orderTypeAttributes
        restOrderTypeAttributeMockMvc.perform(get("/api/orderTypeAttributes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(orderTypeAttribute.getId().intValue()))
                .andExpect(jsonPath("$.[0].attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getOrderTypeAttribute() throws Exception {
        // Initialize the database
        orderTypeAttributeRepository.saveAndFlush(orderTypeAttribute);

        // Get the orderTypeAttribute
        restOrderTypeAttributeMockMvc.perform(get("/api/orderTypeAttributes/{id}", orderTypeAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderTypeAttribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderTypeAttribute() throws Exception {
        // Get the orderTypeAttribute
        restOrderTypeAttributeMockMvc.perform(get("/api/orderTypeAttributes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderTypeAttribute() throws Exception {
        // Initialize the database
        orderTypeAttributeRepository.saveAndFlush(orderTypeAttribute);

        // Update the orderTypeAttribute
        orderTypeAttribute.setAttributeName(UPDATED_ATTRIBUTE_NAME);
        restOrderTypeAttributeMockMvc.perform(put("/api/orderTypeAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderTypeAttribute)))
                .andExpect(status().isOk());

        // Validate the OrderTypeAttribute in the database
        List<OrderTypeAttribute> orderTypeAttributes = orderTypeAttributeRepository.findAll();
        assertThat(orderTypeAttributes).hasSize(1);
        OrderTypeAttribute testOrderTypeAttribute = orderTypeAttributes.iterator().next();
        assertThat(testOrderTypeAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
    }

    @Test
    @Transactional
    public void deleteOrderTypeAttribute() throws Exception {
        // Initialize the database
        orderTypeAttributeRepository.saveAndFlush(orderTypeAttribute);

        // Get the orderTypeAttribute
        restOrderTypeAttributeMockMvc.perform(delete("/api/orderTypeAttributes/{id}", orderTypeAttribute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderTypeAttribute> orderTypeAttributes = orderTypeAttributeRepository.findAll();
        assertThat(orderTypeAttributes).hasSize(0);
    }
}
