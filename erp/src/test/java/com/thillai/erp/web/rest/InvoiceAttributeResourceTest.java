package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.InvoiceAttribute;
import com.thillai.erp.repository.InvoiceAttributeRepository;

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
 * Test class for the InvoiceAttributeResource REST controller.
 *
 * @see InvoiceAttributeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InvoiceAttributeResourceTest {

    private static final String DEFAULT_ATTRIBUTE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_ATTRIBUTE_VALUE = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_VALUE = "UPDATED_TEXT";

    @Inject
    private InvoiceAttributeRepository invoiceAttributeRepository;

    private MockMvc restInvoiceAttributeMockMvc;

    private InvoiceAttribute invoiceAttribute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoiceAttributeResource invoiceAttributeResource = new InvoiceAttributeResource();
        ReflectionTestUtils.setField(invoiceAttributeResource, "invoiceAttributeRepository", invoiceAttributeRepository);
        this.restInvoiceAttributeMockMvc = MockMvcBuilders.standaloneSetup(invoiceAttributeResource).build();
    }

    @Before
    public void initTest() {
        invoiceAttribute = new InvoiceAttribute();
        invoiceAttribute.setAttributeName(DEFAULT_ATTRIBUTE_NAME);
        invoiceAttribute.setAttributeValue(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void createInvoiceAttribute() throws Exception {
        // Validate the database is empty
        assertThat(invoiceAttributeRepository.findAll()).hasSize(0);

        // Create the InvoiceAttribute
        restInvoiceAttributeMockMvc.perform(post("/api/invoiceAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceAttribute)))
                .andExpect(status().isCreated());

        // Validate the InvoiceAttribute in the database
        List<InvoiceAttribute> invoiceAttributes = invoiceAttributeRepository.findAll();
        assertThat(invoiceAttributes).hasSize(1);
        InvoiceAttribute testInvoiceAttribute = invoiceAttributes.iterator().next();
        assertThat(testInvoiceAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testInvoiceAttribute.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllInvoiceAttributes() throws Exception {
        // Initialize the database
        invoiceAttributeRepository.saveAndFlush(invoiceAttribute);

        // Get all the invoiceAttributes
        restInvoiceAttributeMockMvc.perform(get("/api/invoiceAttributes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(invoiceAttribute.getId().intValue()))
                .andExpect(jsonPath("$.[0].attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
                .andExpect(jsonPath("$.[0].attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getInvoiceAttribute() throws Exception {
        // Initialize the database
        invoiceAttributeRepository.saveAndFlush(invoiceAttribute);

        // Get the invoiceAttribute
        restInvoiceAttributeMockMvc.perform(get("/api/invoiceAttributes/{id}", invoiceAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(invoiceAttribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceAttribute() throws Exception {
        // Get the invoiceAttribute
        restInvoiceAttributeMockMvc.perform(get("/api/invoiceAttributes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceAttribute() throws Exception {
        // Initialize the database
        invoiceAttributeRepository.saveAndFlush(invoiceAttribute);

        // Update the invoiceAttribute
        invoiceAttribute.setAttributeName(UPDATED_ATTRIBUTE_NAME);
        invoiceAttribute.setAttributeValue(UPDATED_ATTRIBUTE_VALUE);
        restInvoiceAttributeMockMvc.perform(put("/api/invoiceAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceAttribute)))
                .andExpect(status().isOk());

        // Validate the InvoiceAttribute in the database
        List<InvoiceAttribute> invoiceAttributes = invoiceAttributeRepository.findAll();
        assertThat(invoiceAttributes).hasSize(1);
        InvoiceAttribute testInvoiceAttribute = invoiceAttributes.iterator().next();
        assertThat(testInvoiceAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testInvoiceAttribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void deleteInvoiceAttribute() throws Exception {
        // Initialize the database
        invoiceAttributeRepository.saveAndFlush(invoiceAttribute);

        // Get the invoiceAttribute
        restInvoiceAttributeMockMvc.perform(delete("/api/invoiceAttributes/{id}", invoiceAttribute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceAttribute> invoiceAttributes = invoiceAttributeRepository.findAll();
        assertThat(invoiceAttributes).hasSize(0);
    }
}
