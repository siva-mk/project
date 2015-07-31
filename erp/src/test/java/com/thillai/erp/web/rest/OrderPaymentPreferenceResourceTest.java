package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.OrderPaymentPreference;
import com.thillai.erp.repository.OrderPaymentPreferenceRepository;

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
 * Test class for the OrderPaymentPreferenceResource REST controller.
 *
 * @see OrderPaymentPreferenceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderPaymentPreferenceResourceTest {


    private static final Integer DEFAULT_PAYMENT_METHOD_ID = 0;
    private static final Integer UPDATED_PAYMENT_METHOD_ID = 1;

    private static final Long DEFAULT_MAX_AMOUNT = 0L;
    private static final Long UPDATED_MAX_AMOUNT = 1L;
    private static final String DEFAULT_AUTH_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_AUTH_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_AUTH_MESSAGE = "SAMPLE_TEXT";
    private static final String UPDATED_AUTH_MESSAGE = "UPDATED_TEXT";

    @Inject
    private OrderPaymentPreferenceRepository orderPaymentPreferenceRepository;

    private MockMvc restOrderPaymentPreferenceMockMvc;

    private OrderPaymentPreference orderPaymentPreference;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderPaymentPreferenceResource orderPaymentPreferenceResource = new OrderPaymentPreferenceResource();
        ReflectionTestUtils.setField(orderPaymentPreferenceResource, "orderPaymentPreferenceRepository", orderPaymentPreferenceRepository);
        this.restOrderPaymentPreferenceMockMvc = MockMvcBuilders.standaloneSetup(orderPaymentPreferenceResource).build();
    }

    @Before
    public void initTest() {
        orderPaymentPreference = new OrderPaymentPreference();
        orderPaymentPreference.setPaymentMethod_id(DEFAULT_PAYMENT_METHOD_ID);
        orderPaymentPreference.setMaxAmount(DEFAULT_MAX_AMOUNT);
        orderPaymentPreference.setAuthCode(DEFAULT_AUTH_CODE);
        orderPaymentPreference.setAuthMessage(DEFAULT_AUTH_MESSAGE);
    }

    @Test
    @Transactional
    public void createOrderPaymentPreference() throws Exception {
        // Validate the database is empty
        assertThat(orderPaymentPreferenceRepository.findAll()).hasSize(0);

        // Create the OrderPaymentPreference
        restOrderPaymentPreferenceMockMvc.perform(post("/api/orderPaymentPreferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderPaymentPreference)))
                .andExpect(status().isCreated());

        // Validate the OrderPaymentPreference in the database
        List<OrderPaymentPreference> orderPaymentPreferences = orderPaymentPreferenceRepository.findAll();
        assertThat(orderPaymentPreferences).hasSize(1);
        OrderPaymentPreference testOrderPaymentPreference = orderPaymentPreferences.iterator().next();
        assertThat(testOrderPaymentPreference.getPaymentMethod_id()).isEqualTo(DEFAULT_PAYMENT_METHOD_ID);
        assertThat(testOrderPaymentPreference.getMaxAmount()).isEqualTo(DEFAULT_MAX_AMOUNT);
        assertThat(testOrderPaymentPreference.getAuthCode()).isEqualTo(DEFAULT_AUTH_CODE);
        assertThat(testOrderPaymentPreference.getAuthMessage()).isEqualTo(DEFAULT_AUTH_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllOrderPaymentPreferences() throws Exception {
        // Initialize the database
        orderPaymentPreferenceRepository.saveAndFlush(orderPaymentPreference);

        // Get all the orderPaymentPreferences
        restOrderPaymentPreferenceMockMvc.perform(get("/api/orderPaymentPreferences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(orderPaymentPreference.getId().intValue()))
                .andExpect(jsonPath("$.[0].paymentMethod_id").value(DEFAULT_PAYMENT_METHOD_ID))
                .andExpect(jsonPath("$.[0].maxAmount").value(DEFAULT_MAX_AMOUNT.intValue()))
                .andExpect(jsonPath("$.[0].authCode").value(DEFAULT_AUTH_CODE.toString()))
                .andExpect(jsonPath("$.[0].authMessage").value(DEFAULT_AUTH_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getOrderPaymentPreference() throws Exception {
        // Initialize the database
        orderPaymentPreferenceRepository.saveAndFlush(orderPaymentPreference);

        // Get the orderPaymentPreference
        restOrderPaymentPreferenceMockMvc.perform(get("/api/orderPaymentPreferences/{id}", orderPaymentPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderPaymentPreference.getId().intValue()))
            .andExpect(jsonPath("$.paymentMethod_id").value(DEFAULT_PAYMENT_METHOD_ID))
            .andExpect(jsonPath("$.maxAmount").value(DEFAULT_MAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.authCode").value(DEFAULT_AUTH_CODE.toString()))
            .andExpect(jsonPath("$.authMessage").value(DEFAULT_AUTH_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderPaymentPreference() throws Exception {
        // Get the orderPaymentPreference
        restOrderPaymentPreferenceMockMvc.perform(get("/api/orderPaymentPreferences/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderPaymentPreference() throws Exception {
        // Initialize the database
        orderPaymentPreferenceRepository.saveAndFlush(orderPaymentPreference);

        // Update the orderPaymentPreference
        orderPaymentPreference.setPaymentMethod_id(UPDATED_PAYMENT_METHOD_ID);
        orderPaymentPreference.setMaxAmount(UPDATED_MAX_AMOUNT);
        orderPaymentPreference.setAuthCode(UPDATED_AUTH_CODE);
        orderPaymentPreference.setAuthMessage(UPDATED_AUTH_MESSAGE);
        restOrderPaymentPreferenceMockMvc.perform(put("/api/orderPaymentPreferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderPaymentPreference)))
                .andExpect(status().isOk());

        // Validate the OrderPaymentPreference in the database
        List<OrderPaymentPreference> orderPaymentPreferences = orderPaymentPreferenceRepository.findAll();
        assertThat(orderPaymentPreferences).hasSize(1);
        OrderPaymentPreference testOrderPaymentPreference = orderPaymentPreferences.iterator().next();
        assertThat(testOrderPaymentPreference.getPaymentMethod_id()).isEqualTo(UPDATED_PAYMENT_METHOD_ID);
        assertThat(testOrderPaymentPreference.getMaxAmount()).isEqualTo(UPDATED_MAX_AMOUNT);
        assertThat(testOrderPaymentPreference.getAuthCode()).isEqualTo(UPDATED_AUTH_CODE);
        assertThat(testOrderPaymentPreference.getAuthMessage()).isEqualTo(UPDATED_AUTH_MESSAGE);
    }

    @Test
    @Transactional
    public void deleteOrderPaymentPreference() throws Exception {
        // Initialize the database
        orderPaymentPreferenceRepository.saveAndFlush(orderPaymentPreference);

        // Get the orderPaymentPreference
        restOrderPaymentPreferenceMockMvc.perform(delete("/api/orderPaymentPreferences/{id}", orderPaymentPreference.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderPaymentPreference> orderPaymentPreferences = orderPaymentPreferenceRepository.findAll();
        assertThat(orderPaymentPreferences).hasSize(0);
    }
}
