package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.InvoiceItem;
import com.thillai.erp.repository.InvoiceItemRepository;

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
 * Test class for the InvoiceItemResource REST controller.
 *
 * @see InvoiceItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InvoiceItemResourceTest {


    private static final Integer DEFAULT_INVOICE_ITEM_SEQ_ID = 0;
    private static final Integer UPDATED_INVOICE_ITEM_SEQ_ID = 1;

    private static final Integer DEFAULT_INVOICE_ITEM_TYPE_ID = 0;
    private static final Integer UPDATED_INVOICE_ITEM_TYPE_ID = 1;

    private static final Integer DEFAULT_PRODUCT_FEATURE_ID = 0;
    private static final Integer UPDATED_PRODUCT_FEATURE_ID = 1;

    private static final Integer DEFAULT_UOM_ID = 0;
    private static final Integer UPDATED_UOM_ID = 1;

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final Long DEFAULT_AMOUNT = 0L;
    private static final Long UPDATED_AMOUNT = 1L;
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private InvoiceItemRepository invoiceItemRepository;

    private MockMvc restInvoiceItemMockMvc;

    private InvoiceItem invoiceItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoiceItemResource invoiceItemResource = new InvoiceItemResource();
        ReflectionTestUtils.setField(invoiceItemResource, "invoiceItemRepository", invoiceItemRepository);
        this.restInvoiceItemMockMvc = MockMvcBuilders.standaloneSetup(invoiceItemResource).build();
    }

    @Before
    public void initTest() {
        invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemSeq_id(DEFAULT_INVOICE_ITEM_SEQ_ID);
        invoiceItem.setInvoiceItemType_id(DEFAULT_INVOICE_ITEM_TYPE_ID);
        invoiceItem.setProductFeature_id(DEFAULT_PRODUCT_FEATURE_ID);
        invoiceItem.setUOM_id(DEFAULT_UOM_ID);
        invoiceItem.setQuantity(DEFAULT_QUANTITY);
        invoiceItem.setAmount(DEFAULT_AMOUNT);
        invoiceItem.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInvoiceItem() throws Exception {
        // Validate the database is empty
        assertThat(invoiceItemRepository.findAll()).hasSize(0);

        // Create the InvoiceItem
        restInvoiceItemMockMvc.perform(post("/api/invoiceItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
                .andExpect(status().isCreated());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItems = invoiceItemRepository.findAll();
        assertThat(invoiceItems).hasSize(1);
        InvoiceItem testInvoiceItem = invoiceItems.iterator().next();
        assertThat(testInvoiceItem.getInvoiceItemSeq_id()).isEqualTo(DEFAULT_INVOICE_ITEM_SEQ_ID);
        assertThat(testInvoiceItem.getInvoiceItemType_id()).isEqualTo(DEFAULT_INVOICE_ITEM_TYPE_ID);
        assertThat(testInvoiceItem.getProductFeature_id()).isEqualTo(DEFAULT_PRODUCT_FEATURE_ID);
        assertThat(testInvoiceItem.getUOM_id()).isEqualTo(DEFAULT_UOM_ID);
        assertThat(testInvoiceItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInvoiceItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInvoiceItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoiceItems() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItems
        restInvoiceItemMockMvc.perform(get("/api/invoiceItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(invoiceItem.getId().intValue()))
                .andExpect(jsonPath("$.[0].invoiceItemSeq_id").value(DEFAULT_INVOICE_ITEM_SEQ_ID))
                .andExpect(jsonPath("$.[0].invoiceItemType_id").value(DEFAULT_INVOICE_ITEM_TYPE_ID))
                .andExpect(jsonPath("$.[0].productFeature_id").value(DEFAULT_PRODUCT_FEATURE_ID))
                .andExpect(jsonPath("$.[0].UOM_id").value(DEFAULT_UOM_ID))
                .andExpect(jsonPath("$.[0].quantity").value(DEFAULT_QUANTITY))
                .andExpect(jsonPath("$.[0].amount").value(DEFAULT_AMOUNT.intValue()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get the invoiceItem
        restInvoiceItemMockMvc.perform(get("/api/invoiceItems/{id}", invoiceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(invoiceItem.getId().intValue()))
            .andExpect(jsonPath("$.invoiceItemSeq_id").value(DEFAULT_INVOICE_ITEM_SEQ_ID))
            .andExpect(jsonPath("$.invoiceItemType_id").value(DEFAULT_INVOICE_ITEM_TYPE_ID))
            .andExpect(jsonPath("$.productFeature_id").value(DEFAULT_PRODUCT_FEATURE_ID))
            .andExpect(jsonPath("$.UOM_id").value(DEFAULT_UOM_ID))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceItem() throws Exception {
        // Get the invoiceItem
        restInvoiceItemMockMvc.perform(get("/api/invoiceItems/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Update the invoiceItem
        invoiceItem.setInvoiceItemSeq_id(UPDATED_INVOICE_ITEM_SEQ_ID);
        invoiceItem.setInvoiceItemType_id(UPDATED_INVOICE_ITEM_TYPE_ID);
        invoiceItem.setProductFeature_id(UPDATED_PRODUCT_FEATURE_ID);
        invoiceItem.setUOM_id(UPDATED_UOM_ID);
        invoiceItem.setQuantity(UPDATED_QUANTITY);
        invoiceItem.setAmount(UPDATED_AMOUNT);
        invoiceItem.setDescription(UPDATED_DESCRIPTION);
        restInvoiceItemMockMvc.perform(put("/api/invoiceItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
                .andExpect(status().isOk());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItems = invoiceItemRepository.findAll();
        assertThat(invoiceItems).hasSize(1);
        InvoiceItem testInvoiceItem = invoiceItems.iterator().next();
        assertThat(testInvoiceItem.getInvoiceItemSeq_id()).isEqualTo(UPDATED_INVOICE_ITEM_SEQ_ID);
        assertThat(testInvoiceItem.getInvoiceItemType_id()).isEqualTo(UPDATED_INVOICE_ITEM_TYPE_ID);
        assertThat(testInvoiceItem.getProductFeature_id()).isEqualTo(UPDATED_PRODUCT_FEATURE_ID);
        assertThat(testInvoiceItem.getUOM_id()).isEqualTo(UPDATED_UOM_ID);
        assertThat(testInvoiceItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInvoiceItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInvoiceItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get the invoiceItem
        restInvoiceItemMockMvc.perform(delete("/api/invoiceItems/{id}", invoiceItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceItem> invoiceItems = invoiceItemRepository.findAll();
        assertThat(invoiceItems).hasSize(0);
    }
}
