package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.Invoice;
import com.thillai.erp.repository.InvoiceRepository;

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
 * Test class for the InvoiceResource REST controller.
 *
 * @see InvoiceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InvoiceResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final Integer DEFAULT_BILLING_ACCOUNT_ID = 0;
    private static final Integer UPDATED_BILLING_ACCOUNT_ID = 1;

    private static final DateTime DEFAULT_INVOICE_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_INVOICE_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_INVOICE_DATE_STR = dateTimeFormatter.print(DEFAULT_INVOICE_DATE);
    private static final String DEFAULT_INVOICE_MESSAGE = "SAMPLE_TEXT";
    private static final String UPDATED_INVOICE_MESSAGE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private InvoiceRepository invoiceRepository;

    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoiceResource invoiceResource = new InvoiceResource();
        ReflectionTestUtils.setField(invoiceResource, "invoiceRepository", invoiceRepository);
        this.restInvoiceMockMvc = MockMvcBuilders.standaloneSetup(invoiceResource).build();
    }

    @Before
    public void initTest() {
        invoice = new Invoice();
        invoice.setBillingAccount_id(DEFAULT_BILLING_ACCOUNT_ID);
        invoice.setInvoiceDate(DEFAULT_INVOICE_DATE);
        invoice.setInvoiceMessage(DEFAULT_INVOICE_MESSAGE);
        invoice.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        // Validate the database is empty
        assertThat(invoiceRepository.findAll()).hasSize(0);

        // Create the Invoice
        restInvoiceMockMvc.perform(post("/api/invoices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoice)))
                .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(1);
        Invoice testInvoice = invoices.iterator().next();
        assertThat(testInvoice.getBillingAccount_id()).isEqualTo(DEFAULT_BILLING_ACCOUNT_ID);
        assertThat(testInvoice.getInvoiceDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoice.getInvoiceMessage()).isEqualTo(DEFAULT_INVOICE_MESSAGE);
        assertThat(testInvoice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoices
        restInvoiceMockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(invoice.getId().intValue()))
                .andExpect(jsonPath("$.[0].billingAccount_id").value(DEFAULT_BILLING_ACCOUNT_ID))
                .andExpect(jsonPath("$.[0].invoiceDate").value(DEFAULT_INVOICE_DATE_STR))
                .andExpect(jsonPath("$.[0].invoiceMessage").value(DEFAULT_INVOICE_MESSAGE.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.billingAccount_id").value(DEFAULT_BILLING_ACCOUNT_ID))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE_STR))
            .andExpect(jsonPath("$.invoiceMessage").value(DEFAULT_INVOICE_MESSAGE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Update the invoice
        invoice.setBillingAccount_id(UPDATED_BILLING_ACCOUNT_ID);
        invoice.setInvoiceDate(UPDATED_INVOICE_DATE);
        invoice.setInvoiceMessage(UPDATED_INVOICE_MESSAGE);
        invoice.setDescription(UPDATED_DESCRIPTION);
        restInvoiceMockMvc.perform(put("/api/invoices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoice)))
                .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(1);
        Invoice testInvoice = invoices.iterator().next();
        assertThat(testInvoice.getBillingAccount_id()).isEqualTo(UPDATED_BILLING_ACCOUNT_ID);
        assertThat(testInvoice.getInvoiceDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoice.getInvoiceMessage()).isEqualTo(UPDATED_INVOICE_MESSAGE);
        assertThat(testInvoice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(0);
    }
}
