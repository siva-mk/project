package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.InventoryItemType;
import com.thillai.erp.repository.InventoryItemTypeRepository;

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
 * Test class for the InventoryItemTypeResource REST controller.
 *
 * @see InventoryItemTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InventoryItemTypeResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private InventoryItemTypeRepository inventoryItemTypeRepository;

    private MockMvc restInventoryItemTypeMockMvc;

    private InventoryItemType inventoryItemType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InventoryItemTypeResource inventoryItemTypeResource = new InventoryItemTypeResource();
        ReflectionTestUtils.setField(inventoryItemTypeResource, "inventoryItemTypeRepository", inventoryItemTypeRepository);
        this.restInventoryItemTypeMockMvc = MockMvcBuilders.standaloneSetup(inventoryItemTypeResource).build();
    }

    @Before
    public void initTest() {
        inventoryItemType = new InventoryItemType();
        inventoryItemType.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInventoryItemType() throws Exception {
        // Validate the database is empty
        assertThat(inventoryItemTypeRepository.findAll()).hasSize(0);

        // Create the InventoryItemType
        restInventoryItemTypeMockMvc.perform(post("/api/inventoryItemTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItemType)))
                .andExpect(status().isCreated());

        // Validate the InventoryItemType in the database
        List<InventoryItemType> inventoryItemTypes = inventoryItemTypeRepository.findAll();
        assertThat(inventoryItemTypes).hasSize(1);
        InventoryItemType testInventoryItemType = inventoryItemTypes.iterator().next();
        assertThat(testInventoryItemType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInventoryItemTypes() throws Exception {
        // Initialize the database
        inventoryItemTypeRepository.saveAndFlush(inventoryItemType);

        // Get all the inventoryItemTypes
        restInventoryItemTypeMockMvc.perform(get("/api/inventoryItemTypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(inventoryItemType.getId().intValue()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getInventoryItemType() throws Exception {
        // Initialize the database
        inventoryItemTypeRepository.saveAndFlush(inventoryItemType);

        // Get the inventoryItemType
        restInventoryItemTypeMockMvc.perform(get("/api/inventoryItemTypes/{id}", inventoryItemType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(inventoryItemType.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryItemType() throws Exception {
        // Get the inventoryItemType
        restInventoryItemTypeMockMvc.perform(get("/api/inventoryItemTypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItemType() throws Exception {
        // Initialize the database
        inventoryItemTypeRepository.saveAndFlush(inventoryItemType);

        // Update the inventoryItemType
        inventoryItemType.setDescription(UPDATED_DESCRIPTION);
        restInventoryItemTypeMockMvc.perform(put("/api/inventoryItemTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryItemType)))
                .andExpect(status().isOk());

        // Validate the InventoryItemType in the database
        List<InventoryItemType> inventoryItemTypes = inventoryItemTypeRepository.findAll();
        assertThat(inventoryItemTypes).hasSize(1);
        InventoryItemType testInventoryItemType = inventoryItemTypes.iterator().next();
        assertThat(testInventoryItemType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteInventoryItemType() throws Exception {
        // Initialize the database
        inventoryItemTypeRepository.saveAndFlush(inventoryItemType);

        // Get the inventoryItemType
        restInventoryItemTypeMockMvc.perform(delete("/api/inventoryItemTypes/{id}", inventoryItemType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InventoryItemType> inventoryItemTypes = inventoryItemTypeRepository.findAll();
        assertThat(inventoryItemTypes).hasSize(0);
    }
}
