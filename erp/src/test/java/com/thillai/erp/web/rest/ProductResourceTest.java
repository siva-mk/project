package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.Product;
import com.thillai.erp.repository.ProductRepository;

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
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final Integer DEFAULT_MANUFACTURER_PARTY_ID = 0;
    private static final Integer UPDATED_MANUFACTURER_PARTY_ID = 1;

    private static final DateTime DEFAULT_INTRODUCTION_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_INTRODUCTION_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_INTRODUCTION_DATE_STR = dateTimeFormatter.print(DEFAULT_INTRODUCTION_DATE);

    private static final DateTime DEFAULT_SALES_DISCONTINUATION_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_SALES_DISCONTINUATION_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_SALES_DISCONTINUATION_DATE_STR = dateTimeFormatter.print(DEFAULT_SALES_DISCONTINUATION_DATE);

    private static final DateTime DEFAULT_SUPPORT_DISCONTINUATION_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_SUPPORT_DISCONTINUATION_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_SUPPORT_DISCONTINUATION_DATE_STR = dateTimeFormatter.print(DEFAULT_SUPPORT_DISCONTINUATION_DATE);
    private static final String DEFAULT_PRODUCT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_PRODUCT_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_INTERNAL_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_INTERNAL_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_BRAND_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_BRAND_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_COMMENTS = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENTS = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_INVENTORY_MESSAGE = "SAMPLE_TEXT";
    private static final String UPDATED_INVENTORY_MESSAGE = "UPDATED_TEXT";

    private static final Integer DEFAULT_REQUIRED_INVENTORY = 0;
    private static final Integer UPDATED_REQUIRED_INVENTORY = 1;
    private static final String DEFAULT_SMALL_IMAGE_URL = "SAMPLE_TEXT";
    private static final String UPDATED_SMALL_IMAGE_URL = "UPDATED_TEXT";
    private static final String DEFAULT_LARGE_IMALE_URL = "SAMPLE_TEXT";
    private static final String UPDATED_LARGE_IMALE_URL = "UPDATED_TEXT";

    private static final Integer DEFAULT_QUANTITY_UOMID = 0;
    private static final Integer UPDATED_QUANTITY_UOMID = 1;

    private static final Integer DEFAULT_QUANTITY_INCLUDED = 0;
    private static final Integer UPDATED_QUANTITY_INCLUDED = 1;

    private static final Integer DEFAULT_PIECES_INCLUDED = 0;
    private static final Integer UPDATED_PIECES_INCLUDED = 1;

    private static final Integer DEFAULT_WEIGHT_UOMID = 0;
    private static final Integer UPDATED_WEIGHT_UOMID = 1;

    private static final Integer DEFAULT_WEIGHT = 0;
    private static final Integer UPDATED_WEIGHT = 1;

    private static final Boolean DEFAULT_TAXABLE = false;
    private static final Boolean UPDATED_TAXABLE = true;

    private static final DateTime DEFAULT_CREATED_ON = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CREATED_ON = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CREATED_ON_STR = dateTimeFormatter.print(DEFAULT_CREATED_ON);
    private static final String DEFAULT_CREATED_BY = "SAMPLE_TEXT";
    private static final String UPDATED_CREATED_BY = "UPDATED_TEXT";

    private static final DateTime DEFAULT_MODIFIED_ON = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_MODIFIED_ON = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_MODIFIED_ON_STR = dateTimeFormatter.print(DEFAULT_MODIFIED_ON);
    private static final String DEFAULT_MODIFIED_BY = "SAMPLE_TEXT";
    private static final String UPDATED_MODIFIED_BY = "UPDATED_TEXT";

    @Inject
    private ProductRepository productRepository;

    private MockMvc restProductMockMvc;

    private Product product;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource();
        ReflectionTestUtils.setField(productResource, "productRepository", productRepository);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource).build();
    }

    @Before
    public void initTest() {
        product = new Product();
        product.setManufacturerPartyId(DEFAULT_MANUFACTURER_PARTY_ID);
        product.setIntroductionDate(DEFAULT_INTRODUCTION_DATE);
        product.setSalesDiscontinuationDate(DEFAULT_SALES_DISCONTINUATION_DATE);
        product.setSupportDiscontinuationDate(DEFAULT_SUPPORT_DISCONTINUATION_DATE);
        product.setProductName(DEFAULT_PRODUCT_NAME);
        product.setInternalName(DEFAULT_INTERNAL_NAME);
        product.setBrandName(DEFAULT_BRAND_NAME);
        product.setComments(DEFAULT_COMMENTS);
        product.setDescription(DEFAULT_DESCRIPTION);
        product.setInventoryMessage(DEFAULT_INVENTORY_MESSAGE);
        product.setRequiredInventory(DEFAULT_REQUIRED_INVENTORY);
        product.setSmallImageURL(DEFAULT_SMALL_IMAGE_URL);
        product.setLargeImaleURL(DEFAULT_LARGE_IMALE_URL);
        product.setQuantityUOMId(DEFAULT_QUANTITY_UOMID);
        product.setQuantityIncluded(DEFAULT_QUANTITY_INCLUDED);
        product.setPiecesIncluded(DEFAULT_PIECES_INCLUDED);
        product.setWeightUOMId(DEFAULT_WEIGHT_UOMID);
        product.setWeight(DEFAULT_WEIGHT);
        product.setTaxable(DEFAULT_TAXABLE);
        product.setCreatedOn(DEFAULT_CREATED_ON);
        product.setCreatedBy(DEFAULT_CREATED_BY);
        product.setModifiedOn(DEFAULT_MODIFIED_ON);
        product.setModifiedBy(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        // Validate the database is empty
        assertThat(productRepository.findAll()).hasSize(0);

        // Create the Product
        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        Product testProduct = products.iterator().next();
        assertThat(testProduct.getManufacturerPartyId()).isEqualTo(DEFAULT_MANUFACTURER_PARTY_ID);
        assertThat(testProduct.getIntroductionDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_INTRODUCTION_DATE);
        assertThat(testProduct.getSalesDiscontinuationDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_SALES_DISCONTINUATION_DATE);
        assertThat(testProduct.getSupportDiscontinuationDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_SUPPORT_DISCONTINUATION_DATE);
        assertThat(testProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProduct.getInternalName()).isEqualTo(DEFAULT_INTERNAL_NAME);
        assertThat(testProduct.getBrandName()).isEqualTo(DEFAULT_BRAND_NAME);
        assertThat(testProduct.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getInventoryMessage()).isEqualTo(DEFAULT_INVENTORY_MESSAGE);
        assertThat(testProduct.getRequiredInventory()).isEqualTo(DEFAULT_REQUIRED_INVENTORY);
        assertThat(testProduct.getSmallImageURL()).isEqualTo(DEFAULT_SMALL_IMAGE_URL);
        assertThat(testProduct.getLargeImaleURL()).isEqualTo(DEFAULT_LARGE_IMALE_URL);
        assertThat(testProduct.getQuantityUOMId()).isEqualTo(DEFAULT_QUANTITY_UOMID);
        assertThat(testProduct.getQuantityIncluded()).isEqualTo(DEFAULT_QUANTITY_INCLUDED);
        assertThat(testProduct.getPiecesIncluded()).isEqualTo(DEFAULT_PIECES_INCLUDED);
        assertThat(testProduct.getWeightUOMId()).isEqualTo(DEFAULT_WEIGHT_UOMID);
        assertThat(testProduct.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProduct.getTaxable()).isEqualTo(DEFAULT_TAXABLE);
        assertThat(testProduct.getCreatedOn().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProduct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProduct.getModifiedOn().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testProduct.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the products
        restProductMockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(product.getId().intValue()))
                .andExpect(jsonPath("$.[0].manufacturerPartyId").value(DEFAULT_MANUFACTURER_PARTY_ID))
                .andExpect(jsonPath("$.[0].introductionDate").value(DEFAULT_INTRODUCTION_DATE_STR))
                .andExpect(jsonPath("$.[0].salesDiscontinuationDate").value(DEFAULT_SALES_DISCONTINUATION_DATE_STR))
                .andExpect(jsonPath("$.[0].supportDiscontinuationDate").value(DEFAULT_SUPPORT_DISCONTINUATION_DATE_STR))
                .andExpect(jsonPath("$.[0].productName").value(DEFAULT_PRODUCT_NAME.toString()))
                .andExpect(jsonPath("$.[0].internalName").value(DEFAULT_INTERNAL_NAME.toString()))
                .andExpect(jsonPath("$.[0].brandName").value(DEFAULT_BRAND_NAME.toString()))
                .andExpect(jsonPath("$.[0].comments").value(DEFAULT_COMMENTS.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].inventoryMessage").value(DEFAULT_INVENTORY_MESSAGE.toString()))
                .andExpect(jsonPath("$.[0].requiredInventory").value(DEFAULT_REQUIRED_INVENTORY))
                .andExpect(jsonPath("$.[0].smallImageURL").value(DEFAULT_SMALL_IMAGE_URL.toString()))
                .andExpect(jsonPath("$.[0].largeImaleURL").value(DEFAULT_LARGE_IMALE_URL.toString()))
                .andExpect(jsonPath("$.[0].quantityUOMId").value(DEFAULT_QUANTITY_UOMID))
                .andExpect(jsonPath("$.[0].quantityIncluded").value(DEFAULT_QUANTITY_INCLUDED))
                .andExpect(jsonPath("$.[0].piecesIncluded").value(DEFAULT_PIECES_INCLUDED))
                .andExpect(jsonPath("$.[0].weightUOMId").value(DEFAULT_WEIGHT_UOMID))
                .andExpect(jsonPath("$.[0].weight").value(DEFAULT_WEIGHT))
                .andExpect(jsonPath("$.[0].taxable").value(DEFAULT_TAXABLE.booleanValue()))
                .andExpect(jsonPath("$.[0].createdOn").value(DEFAULT_CREATED_ON_STR))
                .andExpect(jsonPath("$.[0].createdBy").value(DEFAULT_CREATED_BY.toString()))
                .andExpect(jsonPath("$.[0].modifiedOn").value(DEFAULT_MODIFIED_ON_STR))
                .andExpect(jsonPath("$.[0].modifiedBy").value(DEFAULT_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.manufacturerPartyId").value(DEFAULT_MANUFACTURER_PARTY_ID))
            .andExpect(jsonPath("$.introductionDate").value(DEFAULT_INTRODUCTION_DATE_STR))
            .andExpect(jsonPath("$.salesDiscontinuationDate").value(DEFAULT_SALES_DISCONTINUATION_DATE_STR))
            .andExpect(jsonPath("$.supportDiscontinuationDate").value(DEFAULT_SUPPORT_DISCONTINUATION_DATE_STR))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.internalName").value(DEFAULT_INTERNAL_NAME.toString()))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.inventoryMessage").value(DEFAULT_INVENTORY_MESSAGE.toString()))
            .andExpect(jsonPath("$.requiredInventory").value(DEFAULT_REQUIRED_INVENTORY))
            .andExpect(jsonPath("$.smallImageURL").value(DEFAULT_SMALL_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.largeImaleURL").value(DEFAULT_LARGE_IMALE_URL.toString()))
            .andExpect(jsonPath("$.quantityUOMId").value(DEFAULT_QUANTITY_UOMID))
            .andExpect(jsonPath("$.quantityIncluded").value(DEFAULT_QUANTITY_INCLUDED))
            .andExpect(jsonPath("$.piecesIncluded").value(DEFAULT_PIECES_INCLUDED))
            .andExpect(jsonPath("$.weightUOMId").value(DEFAULT_WEIGHT_UOMID))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.taxable").value(DEFAULT_TAXABLE.booleanValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON_STR))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON_STR))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Update the product
        product.setManufacturerPartyId(UPDATED_MANUFACTURER_PARTY_ID);
        product.setIntroductionDate(UPDATED_INTRODUCTION_DATE);
        product.setSalesDiscontinuationDate(UPDATED_SALES_DISCONTINUATION_DATE);
        product.setSupportDiscontinuationDate(UPDATED_SUPPORT_DISCONTINUATION_DATE);
        product.setProductName(UPDATED_PRODUCT_NAME);
        product.setInternalName(UPDATED_INTERNAL_NAME);
        product.setBrandName(UPDATED_BRAND_NAME);
        product.setComments(UPDATED_COMMENTS);
        product.setDescription(UPDATED_DESCRIPTION);
        product.setInventoryMessage(UPDATED_INVENTORY_MESSAGE);
        product.setRequiredInventory(UPDATED_REQUIRED_INVENTORY);
        product.setSmallImageURL(UPDATED_SMALL_IMAGE_URL);
        product.setLargeImaleURL(UPDATED_LARGE_IMALE_URL);
        product.setQuantityUOMId(UPDATED_QUANTITY_UOMID);
        product.setQuantityIncluded(UPDATED_QUANTITY_INCLUDED);
        product.setPiecesIncluded(UPDATED_PIECES_INCLUDED);
        product.setWeightUOMId(UPDATED_WEIGHT_UOMID);
        product.setWeight(UPDATED_WEIGHT);
        product.setTaxable(UPDATED_TAXABLE);
        product.setCreatedOn(UPDATED_CREATED_ON);
        product.setCreatedBy(UPDATED_CREATED_BY);
        product.setModifiedOn(UPDATED_MODIFIED_ON);
        product.setModifiedBy(UPDATED_MODIFIED_BY);
        restProductMockMvc.perform(put("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        Product testProduct = products.iterator().next();
        assertThat(testProduct.getManufacturerPartyId()).isEqualTo(UPDATED_MANUFACTURER_PARTY_ID);
        assertThat(testProduct.getIntroductionDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_INTRODUCTION_DATE);
        assertThat(testProduct.getSalesDiscontinuationDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_SALES_DISCONTINUATION_DATE);
        assertThat(testProduct.getSupportDiscontinuationDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_SUPPORT_DISCONTINUATION_DATE);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getInternalName()).isEqualTo(UPDATED_INTERNAL_NAME);
        assertThat(testProduct.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testProduct.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getInventoryMessage()).isEqualTo(UPDATED_INVENTORY_MESSAGE);
        assertThat(testProduct.getRequiredInventory()).isEqualTo(UPDATED_REQUIRED_INVENTORY);
        assertThat(testProduct.getSmallImageURL()).isEqualTo(UPDATED_SMALL_IMAGE_URL);
        assertThat(testProduct.getLargeImaleURL()).isEqualTo(UPDATED_LARGE_IMALE_URL);
        assertThat(testProduct.getQuantityUOMId()).isEqualTo(UPDATED_QUANTITY_UOMID);
        assertThat(testProduct.getQuantityIncluded()).isEqualTo(UPDATED_QUANTITY_INCLUDED);
        assertThat(testProduct.getPiecesIncluded()).isEqualTo(UPDATED_PIECES_INCLUDED);
        assertThat(testProduct.getWeightUOMId()).isEqualTo(UPDATED_WEIGHT_UOMID);
        assertThat(testProduct.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProduct.getTaxable()).isEqualTo(UPDATED_TAXABLE);
        assertThat(testProduct.getCreatedOn().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getModifiedOn().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testProduct.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(0);
    }
}
