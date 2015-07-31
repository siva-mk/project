package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.InventoryItemAttribute;
import com.thillai.erp.repository.InventoryItemAttributeRepository;

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
 * Test class for the InventoryItemAttributeResource REST controller.
 *
 * @see InventoryItemAttributeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InventoryItemAttributeResourceTest {

    private static final String DEFAULT_ATTRIBUTE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_ATTRIBUTE_VALUE = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE_VALUE = "UPDATED_TEXT";

    @Inject
    private InventoryItemAttributeRepository inventoryItemAttributeRepository;

    private MockMvc restInventoryItemAttributeMockMvc;

    private InventoryItemAttribute inventoryItemAttribute;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InventoryItemAttributeResource inventoryItemAttributeResource = new InventoryItemAttributeResource();
        ReflectionTestUtils.setField(inventoryItemAttributeResource, "inventoryItemAttributeRepository", inventoryItemAttributeRepository);
        this.restInventoryItemAttributeMockMvc = MockMvcBuilders.standaloneSetup(inventoryItemAttributeResource).build();
    }

    @Before
    public void initTest() {
        inventoryItemAttribute = new InventoryItemAttribute();
        inventoryItemAttribute.setAttributeName(DEFAULT_ATTRIBUTE_NAME);
        inventoryItemAttribute.setAttributeValue(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void createInventoryItemAttribute() throws Exception {
        // Validate the database is empty
        assertThat(inventoryItemAttributeRepository.findAll()).hasSize(0);

        // Create the InventoryItemAttribute
        restInventoryItemAttributeMockMvc.perform(post("/api/inventoryItemAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItemAttribute)))
                .andExpect(status().isCreated());

        // Validate the InventoryItemAttribute in the database
        List<InventoryItemAttribute> inventoryItemAttributes = inventoryItemAttributeRepository.findAll();
        assertThat(inventoryItemAttributes).hasSize(1);
        InventoryItemAttribute testInventoryItemAttribute = inventoryItemAttributes.iterator().next();
        assertThat(testInventoryItemAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testInventoryItemAttribute.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemAttributes() throws Exception {
        // Initialize the database
        inventoryItemAttributeRepository.saveAndFlush(inventoryItemAttribute);

        // Get all the inventoryItemAttributes
        restInventoryItemAttributeMockMvc.perform(get("/api/inventoryItemAttributes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(inventoryItemAttribute.getId().intValue()))
                .andExpect(jsonPath("$.[0].attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
                .andExpect(jsonPath("$.[0].attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getInventoryItemAttribute() throws Exception {
        // Initialize the database
        inventoryItemAttributeRepository.saveAndFlush(inventoryItemAttribute);

        // Get the inventoryItemAttribute
        restInventoryItemAttributeMockMvc.perform(get("/api/inventoryItemAttributes/{id}", inventoryItemAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(inventoryItemAttribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryItemAttribute() throws Exception {
        // Get the inventoryItemAttribute
        restInventoryItemAttributeMockMvc.perform(get("/api/inventoryItemAttributes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItemAttribute() throws Exception {
        // Initialize the database
        inventoryItemAttributeRepository.saveAndFlush(inventoryItemAttribute);

        // Update the inventoryItemAttribute
        inventoryItemAttribute.setAttributeName(UPDATED_ATTRIBUTE_NAME);
        inventoryItemAttribute.setAttributeValue(UPDATED_ATTRIBUTE_VALUE);
        restInventoryItemAttributeMockMvc.perform(put("/api/inventoryItemAttributes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItemAttribute)))
                .andExpect(status().isOk());

        // Validate the InventoryItemAttribute in the database
        List<InventoryItemAttribute> inventoryItemAttributes = inventoryItemAttributeRepository.findAll();
        assertThat(inventoryItemAttributes).hasSize(1);
        InventoryItemAttribute testInventoryItemAttribute = inventoryItemAttributes.iterator().next();
        assertThat(testInventoryItemAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testInventoryItemAttribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void deleteInventoryItemAttribute() throws Exception {
        // Initialize the database
        inventoryItemAttributeRepository.saveAndFlush(inventoryItemAttribute);

        // Get the inventoryItemAttribute
        restInventoryItemAttributeMockMvc.perform(delete("/api/inventoryItemAttributes/{id}", inventoryItemAttribute.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InventoryItemAttribute> inventoryItemAttributes = inventoryItemAttributeRepository.findAll();
        assertThat(inventoryItemAttributes).hasSize(0);
    }
}
