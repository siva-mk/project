package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.OrderAttribute;
import com.thillai.erp.repository.OrderAttributeRepository;

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
 * Test class for the OrderAttributeResource REST controller.
 *
 * @see OrderAttributeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderAttributeResourceTest {

    private static final String DEFAULT_ATTRIBUTE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_ATTRIBUTE_VALUE = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_VALUE = "UPDATED_TEXT";

    @Inject
    private OrderAttributeRepository orderAttributeRepository;

    private MockMvc restOrderAttributeMockMvc;

    private OrderAttribute orderAttribute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderAttributeResource orderAttributeResource = new OrderAttributeResource();
        ReflectionTestUtils.setField(orderAttributeResource, "orderAttributeRepository", orderAttributeRepository);
        this.restOrderAttributeMockMvc = MockMvcBuilders.standaloneSetup(orderAttributeResource).build();
    }

    @Before
    public void initTest() {
        orderAttribute = new OrderAttribute();
        orderAttribute.setAttributeName(DEFAULT_ATTRIBUTE_NAME);
        orderAttribute.setAttributeValue(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void createOrderAttribute() throws Exception {
        // Validate the database is empty
        assertThat(orderAttributeRepository.findAll()).hasSize(0);

        // Create the OrderAttribute
        restOrderAttributeMockMvc.perform(post("/api/orderAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderAttribute)))
                .andExpect(status().isCreated());

        // Validate the OrderAttribute in the database
        List<OrderAttribute> orderAttributes = orderAttributeRepository.findAll();
        assertThat(orderAttributes).hasSize(1);
        OrderAttribute testOrderAttribute = orderAttributes.iterator().next();
        assertThat(testOrderAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testOrderAttribute.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllOrderAttributes() throws Exception {
        // Initialize the database
        orderAttributeRepository.saveAndFlush(orderAttribute);

        // Get all the orderAttributes
        restOrderAttributeMockMvc.perform(get("/api/orderAttributes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(orderAttribute.getId().intValue()))
                .andExpect(jsonPath("$.[0].attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
                .andExpect(jsonPath("$.[0].attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getOrderAttribute() throws Exception {
        // Initialize the database
        orderAttributeRepository.saveAndFlush(orderAttribute);

        // Get the orderAttribute
        restOrderAttributeMockMvc.perform(get("/api/orderAttributes/{id}", orderAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderAttribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderAttribute() throws Exception {
        // Get the orderAttribute
        restOrderAttributeMockMvc.perform(get("/api/orderAttributes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderAttribute() throws Exception {
        // Initialize the database
        orderAttributeRepository.saveAndFlush(orderAttribute);

        // Update the orderAttribute
        orderAttribute.setAttributeName(UPDATED_ATTRIBUTE_NAME);
        orderAttribute.setAttributeValue(UPDATED_ATTRIBUTE_VALUE);
        restOrderAttributeMockMvc.perform(put("/api/orderAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderAttribute)))
                .andExpect(status().isOk());

        // Validate the OrderAttribute in the database
        List<OrderAttribute> orderAttributes = orderAttributeRepository.findAll();
        assertThat(orderAttributes).hasSize(1);
        OrderAttribute testOrderAttribute = orderAttributes.iterator().next();
        assertThat(testOrderAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testOrderAttribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void deleteOrderAttribute() throws Exception {
        // Initialize the database
        orderAttributeRepository.saveAndFlush(orderAttribute);

        // Get the orderAttribute
        restOrderAttributeMockMvc.perform(delete("/api/orderAttributes/{id}", orderAttribute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderAttribute> orderAttributes = orderAttributeRepository.findAll();
        assertThat(orderAttributes).hasSize(0);
    }
}
