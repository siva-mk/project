package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.Producttype;
import com.thillai.erp.repository.ProducttypeRepository;

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
 * Test class for the ProducttypeResource REST controller.
 *
 * @see ProducttypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProducttypeResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private ProducttypeRepository producttypeRepository;

    private MockMvc restProducttypeMockMvc;

    private Producttype producttype;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProducttypeResource producttypeResource = new ProducttypeResource();
        ReflectionTestUtils.setField(producttypeResource, "producttypeRepository", producttypeRepository);
        this.restProducttypeMockMvc = MockMvcBuilders.standaloneSetup(producttypeResource).build();
    }

    @Before
    public void initTest() {
        producttype = new Producttype();
        producttype.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProducttype() throws Exception {
        // Validate the database is empty
        assertThat(producttypeRepository.findAll()).hasSize(0);

        // Create the Producttype
        restProducttypeMockMvc.perform(post("/api/producttypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(producttype)))
                .andExpect(status().isCreated());

        // Validate the Producttype in the database
        List<Producttype> producttypes = producttypeRepository.findAll();
        assertThat(producttypes).hasSize(1);
        Producttype testProducttype = producttypes.iterator().next();
        assertThat(testProducttype.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProducttypes() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        // Get all the producttypes
        restProducttypeMockMvc.perform(get("/api/producttypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(producttype.getId().intValue()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getProducttype() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        // Get the producttype
        restProducttypeMockMvc.perform(get("/api/producttypes/{id}", producttype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(producttype.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProducttype() throws Exception {
        // Get the producttype
        restProducttypeMockMvc.perform(get("/api/producttypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducttype() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        // Update the producttype
        producttype.setDescription(UPDATED_DESCRIPTION);
        restProducttypeMockMvc.perform(put("/api/producttypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(producttype)))
                .andExpect(status().isOk());

        // Validate the Producttype in the database
        List<Producttype> producttypes = producttypeRepository.findAll();
        assertThat(producttypes).hasSize(1);
        Producttype testProducttype = producttypes.iterator().next();
        assertThat(testProducttype.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteProducttype() throws Exception {
        // Initialize the database
        producttypeRepository.saveAndFlush(producttype);

        // Get the producttype
        restProducttypeMockMvc.perform(delete("/api/producttypes/{id}", producttype.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Producttype> producttypes = producttypeRepository.findAll();
        assertThat(producttypes).hasSize(0);
    }
}
