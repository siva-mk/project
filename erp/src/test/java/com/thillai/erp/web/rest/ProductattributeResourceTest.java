package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.Productattribute;
import com.thillai.erp.repository.ProductattributeRepository;

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
 * Test class for the ProductattributeResource REST controller.
 *
 * @see ProductattributeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductattributeResourceTest {

    private static final String DEFAULT_ATTRIBUTE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_ATTRIBUTE_VALUE = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_VALUE = "UPDATED_TEXT";

    @Inject
    private ProductattributeRepository productattributeRepository;

    private MockMvc restProductattributeMockMvc;

    private Productattribute productattribute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductattributeResource productattributeResource = new ProductattributeResource();
        ReflectionTestUtils.setField(productattributeResource, "productattributeRepository", productattributeRepository);
        this.restProductattributeMockMvc = MockMvcBuilders.standaloneSetup(productattributeResource).build();
    }

    @Before
    public void initTest() {
        productattribute = new Productattribute();
        productattribute.setAttributeName(DEFAULT_ATTRIBUTE_NAME);
        productattribute.setAttributeValue(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void createProductattribute() throws Exception {
        // Validate the database is empty
        assertThat(productattributeRepository.findAll()).hasSize(0);

        // Create the Productattribute
        restProductattributeMockMvc.perform(post("/api/productattributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productattribute)))
                .andExpect(status().isCreated());

        // Validate the Productattribute in the database
        List<Productattribute> productattributes = productattributeRepository.findAll();
        assertThat(productattributes).hasSize(1);
        Productattribute testProductattribute = productattributes.iterator().next();
        assertThat(testProductattribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testProductattribute.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductattributes() throws Exception {
        // Initialize the database
        productattributeRepository.saveAndFlush(productattribute);

        // Get all the productattributes
        restProductattributeMockMvc.perform(get("/api/productattributes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(productattribute.getId().intValue()))
                .andExpect(jsonPath("$.[0].attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
                .andExpect(jsonPath("$.[0].attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getProductattribute() throws Exception {
        // Initialize the database
        productattributeRepository.saveAndFlush(productattribute);

        // Get the productattribute
        restProductattributeMockMvc.perform(get("/api/productattributes/{id}", productattribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(productattribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductattribute() throws Exception {
        // Get the productattribute
        restProductattributeMockMvc.perform(get("/api/productattributes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductattribute() throws Exception {
        // Initialize the database
        productattributeRepository.saveAndFlush(productattribute);

        // Update the productattribute
        productattribute.setAttributeName(UPDATED_ATTRIBUTE_NAME);
        productattribute.setAttributeValue(UPDATED_ATTRIBUTE_VALUE);
        restProductattributeMockMvc.perform(put("/api/productattributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productattribute)))
                .andExpect(status().isOk());

        // Validate the Productattribute in the database
        List<Productattribute> productattributes = productattributeRepository.findAll();
        assertThat(productattributes).hasSize(1);
        Productattribute testProductattribute = productattributes.iterator().next();
        assertThat(testProductattribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testProductattribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void deleteProductattribute() throws Exception {
        // Initialize the database
        productattributeRepository.saveAndFlush(productattribute);

        // Get the productattribute
        restProductattributeMockMvc.perform(delete("/api/productattributes/{id}", productattribute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Productattribute> productattributes = productattributeRepository.findAll();
        assertThat(productattributes).hasSize(0);
    }
}
