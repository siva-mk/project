package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.InvoiceType;
import com.thillai.erp.repository.InvoiceTypeRepository;

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
 * Test class for the InvoiceTypeResource REST controller.
 *
 * @see InvoiceTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InvoiceTypeResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private InvoiceTypeRepository invoiceTypeRepository;

    private MockMvc restInvoiceTypeMockMvc;

    private InvoiceType invoiceType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoiceTypeResource invoiceTypeResource = new InvoiceTypeResource();
        ReflectionTestUtils.setField(invoiceTypeResource, "invoiceTypeRepository", invoiceTypeRepository);
        this.restInvoiceTypeMockMvc = MockMvcBuilders.standaloneSetup(invoiceTypeResource).build();
    }

    @Before
    public void initTest() {
        invoiceType = new InvoiceType();
        invoiceType.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInvoiceType() throws Exception {
        // Validate the database is empty
        assertThat(invoiceTypeRepository.findAll()).hasSize(0);

        // Create the InvoiceType
        restInvoiceTypeMockMvc.perform(post("/api/invoiceTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceType)))
                .andExpect(status().isCreated());

        // Validate the InvoiceType in the database
        List<InvoiceType> invoiceTypes = invoiceTypeRepository.findAll();
        assertThat(invoiceTypes).hasSize(1);
        InvoiceType testInvoiceType = invoiceTypes.iterator().next();
        assertThat(testInvoiceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoiceTypes() throws Exception {
        // Initialize the database
        invoiceTypeRepository.saveAndFlush(invoiceType);

        // Get all the invoiceTypes
        restInvoiceTypeMockMvc.perform(get("/api/invoiceTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(invoiceType.getId().intValue()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getInvoiceType() throws Exception {
        // Initialize the database
        invoiceTypeRepository.saveAndFlush(invoiceType);

        // Get the invoiceType
        restInvoiceTypeMockMvc.perform(get("/api/invoiceTypes/{id}", invoiceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(invoiceType.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceType() throws Exception {
        // Get the invoiceType
        restInvoiceTypeMockMvc.perform(get("/api/invoiceTypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceType() throws Exception {
        // Initialize the database
        invoiceTypeRepository.saveAndFlush(invoiceType);

        // Update the invoiceType
        invoiceType.setDescription(UPDATED_DESCRIPTION);
        restInvoiceTypeMockMvc.perform(put("/api/invoiceTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceType)))
                .andExpect(status().isOk());

        // Validate the InvoiceType in the database
        List<InvoiceType> invoiceTypes = invoiceTypeRepository.findAll();
        assertThat(invoiceTypes).hasSize(1);
        InvoiceType testInvoiceType = invoiceTypes.iterator().next();
        assertThat(testInvoiceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteInvoiceType() throws Exception {
        // Initialize the database
        invoiceTypeRepository.saveAndFlush(invoiceType);

        // Get the invoiceType
        restInvoiceTypeMockMvc.perform(delete("/api/invoiceTypes/{id}", invoiceType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceType> invoiceTypes = invoiceTypeRepository.findAll();
        assertThat(invoiceTypes).hasSize(0);
    }
}
