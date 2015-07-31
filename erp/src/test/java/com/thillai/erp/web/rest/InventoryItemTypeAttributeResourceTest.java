package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.InventoryItemTypeAttribute;
import com.thillai.erp.repository.InventoryItemTypeAttributeRepository;

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
 * Test class for the InventoryItemTypeAttributeResource REST controller.
 *
 * @see InventoryItemTypeAttributeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InventoryItemTypeAttributeResourceTest {

    private static final String DEFAULT_ATTRIBUTE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_NAME = "UPDATED_TEXT";

    @Inject
    private InventoryItemTypeAttributeRepository inventoryItemTypeAttributeRepository;

    private MockMvc restInventoryItemTypeAttributeMockMvc;

    private InventoryItemTypeAttribute inventoryItemTypeAttribute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InventoryItemTypeAttributeResource inventoryItemTypeAttributeResource = new InventoryItemTypeAttributeResource();
        ReflectionTestUtils.setField(inventoryItemTypeAttributeResource, "inventoryItemTypeAttributeRepository", inventoryItemTypeAttributeRepository);
        this.restInventoryItemTypeAttributeMockMvc = MockMvcBuilders.standaloneSetup(inventoryItemTypeAttributeResource).build();
    }

    @Before
    public void initTest() {
        inventoryItemTypeAttribute = new InventoryItemTypeAttribute();
        inventoryItemTypeAttribute.setAttributeName(DEFAULT_ATTRIBUTE_NAME);
    }

    @Test
    @Transactional
    public void createInventoryItemTypeAttribute() throws Exception {
        // Validate the database is empty
        assertThat(inventoryItemTypeAttributeRepository.findAll()).hasSize(0);

        // Create the InventoryItemTypeAttribute
        restInventoryItemTypeAttributeMockMvc.perform(post("/api/inventoryItemTypeAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItemTypeAttribute)))
                .andExpect(status().isCreated());

        // Validate the InventoryItemTypeAttribute in the database
        List<InventoryItemTypeAttribute> inventoryItemTypeAttributes = inventoryItemTypeAttributeRepository.findAll();
        assertThat(inventoryItemTypeAttributes).hasSize(1);
        InventoryItemTypeAttribute testInventoryItemTypeAttribute = inventoryItemTypeAttributes.iterator().next();
        assertThat(testInventoryItemTypeAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
    }

    @Test
    @Transactional
    public void getAllInventoryItemTypeAttributes() throws Exception {
        // Initialize the database
        inventoryItemTypeAttributeRepository.saveAndFlush(inventoryItemTypeAttribute);

        // Get all the inventoryItemTypeAttributes
        restInventoryItemTypeAttributeMockMvc.perform(get("/api/inventoryItemTypeAttributes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(inventoryItemTypeAttribute.getId().intValue()))
                .andExpect(jsonPath("$.[0].attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getInventoryItemTypeAttribute() throws Exception {
        // Initialize the database
        inventoryItemTypeAttributeRepository.saveAndFlush(inventoryItemTypeAttribute);

        // Get the inventoryItemTypeAttribute
        restInventoryItemTypeAttributeMockMvc.perform(get("/api/inventoryItemTypeAttributes/{id}", inventoryItemTypeAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(inventoryItemTypeAttribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryItemTypeAttribute() throws Exception {
        // Get the inventoryItemTypeAttribute
        restInventoryItemTypeAttributeMockMvc.perform(get("/api/inventoryItemTypeAttributes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItemTypeAttribute() throws Exception {
        // Initialize the database
        inventoryItemTypeAttributeRepository.saveAndFlush(inventoryItemTypeAttribute);

        // Update the inventoryItemTypeAttribute
        inventoryItemTypeAttribute.setAttributeName(UPDATED_ATTRIBUTE_NAME);
        restInventoryItemTypeAttributeMockMvc.perform(put("/api/inventoryItemTypeAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItemTypeAttribute)))
                .andExpect(status().isOk());

        // Validate the InventoryItemTypeAttribute in the database
        List<InventoryItemTypeAttribute> inventoryItemTypeAttributes = inventoryItemTypeAttributeRepository.findAll();
        assertThat(inventoryItemTypeAttributes).hasSize(1);
        InventoryItemTypeAttribute testInventoryItemTypeAttribute = inventoryItemTypeAttributes.iterator().next();
        assertThat(testInventoryItemTypeAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
    }

    @Test
    @Transactional
    public void deleteInventoryItemTypeAttribute() throws Exception {
        // Initialize the database
        inventoryItemTypeAttributeRepository.saveAndFlush(inventoryItemTypeAttribute);

        // Get the inventoryItemTypeAttribute
        restInventoryItemTypeAttributeMockMvc.perform(delete("/api/inventoryItemTypeAttributes/{id}", inventoryItemTypeAttribute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InventoryItemTypeAttribute> inventoryItemTypeAttributes = inventoryItemTypeAttributeRepository.findAll();
        assertThat(inventoryItemTypeAttributes).hasSize(0);
    }
}
