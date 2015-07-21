package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.Productcategory;
import com.thillai.erp.repository.ProductcategoryRepository;

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
 * Test class for the ProductcategoryResource REST controller.
 *
 * @see ProductcategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductcategoryResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private ProductcategoryRepository productcategoryRepository;

    private MockMvc restProductcategoryMockMvc;

    private Productcategory productcategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductcategoryResource productcategoryResource = new ProductcategoryResource();
        ReflectionTestUtils.setField(productcategoryResource, "productcategoryRepository", productcategoryRepository);
        this.restProductcategoryMockMvc = MockMvcBuilders.standaloneSetup(productcategoryResource).build();
    }

    @Before
    public void initTest() {
        productcategory = new Productcategory();
        productcategory.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductcategory() throws Exception {
        // Validate the database is empty
        assertThat(productcategoryRepository.findAll()).hasSize(0);

        // Create the Productcategory
        restProductcategoryMockMvc.perform(post("/api/productcategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productcategory)))
                .andExpect(status().isCreated());

        // Validate the Productcategory in the database
        List<Productcategory> productcategorys = productcategoryRepository.findAll();
        assertThat(productcategorys).hasSize(1);
        Productcategory testProductcategory = productcategorys.iterator().next();
        assertThat(testProductcategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductcategorys() throws Exception {
        // Initialize the database
        productcategoryRepository.saveAndFlush(productcategory);

        // Get all the productcategorys
        restProductcategoryMockMvc.perform(get("/api/productcategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(productcategory.getId().intValue()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getProductcategory() throws Exception {
        // Initialize the database
        productcategoryRepository.saveAndFlush(productcategory);

        // Get the productcategory
        restProductcategoryMockMvc.perform(get("/api/productcategorys/{id}", productcategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(productcategory.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductcategory() throws Exception {
        // Get the productcategory
        restProductcategoryMockMvc.perform(get("/api/productcategorys/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductcategory() throws Exception {
        // Initialize the database
        productcategoryRepository.saveAndFlush(productcategory);

        // Update the productcategory
        productcategory.setDescription(UPDATED_DESCRIPTION);
        restProductcategoryMockMvc.perform(put("/api/productcategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productcategory)))
                .andExpect(status().isOk());

        // Validate the Productcategory in the database
        List<Productcategory> productcategorys = productcategoryRepository.findAll();
        assertThat(productcategorys).hasSize(1);
        Productcategory testProductcategory = productcategorys.iterator().next();
        assertThat(testProductcategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteProductcategory() throws Exception {
        // Initialize the database
        productcategoryRepository.saveAndFlush(productcategory);

        // Get the productcategory
        restProductcategoryMockMvc.perform(delete("/api/productcategorys/{id}", productcategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Productcategory> productcategorys = productcategoryRepository.findAll();
        assertThat(productcategorys).hasSize(0);
    }
}
