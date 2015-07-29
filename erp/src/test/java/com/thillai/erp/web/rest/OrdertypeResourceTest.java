package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.Ordertype;
import com.thillai.erp.repository.OrdertypeRepository;

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
 * Test class for the OrdertypeResource REST controller.
 *
 * @see OrdertypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrdertypeResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private OrdertypeRepository ordertypeRepository;

    private MockMvc restOrdertypeMockMvc;

    private Ordertype ordertype;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrdertypeResource ordertypeResource = new OrdertypeResource();
        ReflectionTestUtils.setField(ordertypeResource, "ordertypeRepository", ordertypeRepository);
        this.restOrdertypeMockMvc = MockMvcBuilders.standaloneSetup(ordertypeResource).build();
    }

    @Before
    public void initTest() {
        ordertype = new Ordertype();
        ordertype.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOrdertype() throws Exception {
        // Validate the database is empty
        assertThat(ordertypeRepository.findAll()).hasSize(0);

        // Create the Ordertype
        restOrdertypeMockMvc.perform(post("/api/ordertypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordertype)))
                .andExpect(status().isCreated());

        // Validate the Ordertype in the database
        List<Ordertype> ordertypes = ordertypeRepository.findAll();
        assertThat(ordertypes).hasSize(1);
        Ordertype testOrdertype = ordertypes.iterator().next();
        assertThat(testOrdertype.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOrdertypes() throws Exception {
        // Initialize the database
        ordertypeRepository.saveAndFlush(ordertype);

        // Get all the ordertypes
        restOrdertypeMockMvc.perform(get("/api/ordertypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(ordertype.getId().intValue()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getOrdertype() throws Exception {
        // Initialize the database
        ordertypeRepository.saveAndFlush(ordertype);

        // Get the ordertype
        restOrdertypeMockMvc.perform(get("/api/ordertypes/{id}", ordertype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ordertype.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdertype() throws Exception {
        // Get the ordertype
        restOrdertypeMockMvc.perform(get("/api/ordertypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdertype() throws Exception {
        // Initialize the database
        ordertypeRepository.saveAndFlush(ordertype);

        // Update the ordertype
        ordertype.setDescription(UPDATED_DESCRIPTION);
        restOrdertypeMockMvc.perform(put("/api/ordertypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordertype)))
                .andExpect(status().isOk());

        // Validate the Ordertype in the database
        List<Ordertype> ordertypes = ordertypeRepository.findAll();
        assertThat(ordertypes).hasSize(1);
        Ordertype testOrdertype = ordertypes.iterator().next();
        assertThat(testOrdertype.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteOrdertype() throws Exception {
        // Initialize the database
        ordertypeRepository.saveAndFlush(ordertype);

        // Get the ordertype
        restOrdertypeMockMvc.perform(delete("/api/ordertypes/{id}", ordertype.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ordertype> ordertypes = ordertypeRepository.findAll();
        assertThat(ordertypes).hasSize(0);
    }
}
