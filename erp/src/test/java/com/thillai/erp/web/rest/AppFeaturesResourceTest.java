package com.thillai.erp.web.rest;

import com.thillai.erp.Application;
import com.thillai.erp.domain.AppFeatures;
import com.thillai.erp.repository.AppFeaturesRepository;

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
 * Test class for the AppFeaturesResource REST controller.
 *
 * @see AppFeaturesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AppFeaturesResourceTest {

    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_PAGE_DETAILS = "SAMPLE_TEXT";
    private static final String UPDATED_PAGE_DETAILS = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Inject
    private AppFeaturesRepository appFeaturesRepository;

    private MockMvc restAppFeaturesMockMvc;

    private AppFeatures appFeatures;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppFeaturesResource appFeaturesResource = new AppFeaturesResource();
        ReflectionTestUtils.setField(appFeaturesResource, "appFeaturesRepository", appFeaturesRepository);
        this.restAppFeaturesMockMvc = MockMvcBuilders.standaloneSetup(appFeaturesResource).build();
    }

    @Before
    public void initTest() {
        appFeatures = new AppFeatures();
        appFeatures.setDescription(DEFAULT_DESCRIPTION);
        appFeatures.setPageDetails(DEFAULT_PAGE_DETAILS);
        appFeatures.setActive(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAppFeatures() throws Exception {
        // Validate the database is empty
        assertThat(appFeaturesRepository.findAll()).hasSize(0);

        // Create the AppFeatures
        restAppFeaturesMockMvc.perform(post("/api/appFeaturess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(appFeatures)))
                .andExpect(status().isCreated());

        // Validate the AppFeatures in the database
        List<AppFeatures> appFeaturess = appFeaturesRepository.findAll();
        assertThat(appFeaturess).hasSize(1);
        AppFeatures testAppFeatures = appFeaturess.iterator().next();
        assertThat(testAppFeatures.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppFeatures.getPageDetails()).isEqualTo(DEFAULT_PAGE_DETAILS);
        assertThat(testAppFeatures.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAppFeaturess() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturess
        restAppFeaturesMockMvc.perform(get("/api/appFeaturess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(appFeatures.getId().intValue()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].pageDetails").value(DEFAULT_PAGE_DETAILS.toString()))
                .andExpect(jsonPath("$.[0].active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAppFeatures() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get the appFeatures
        restAppFeaturesMockMvc.perform(get("/api/appFeaturess/{id}", appFeatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(appFeatures.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pageDetails").value(DEFAULT_PAGE_DETAILS.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAppFeatures() throws Exception {
        // Get the appFeatures
        restAppFeaturesMockMvc.perform(get("/api/appFeaturess/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppFeatures() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Update the appFeatures
        appFeatures.setDescription(UPDATED_DESCRIPTION);
        appFeatures.setPageDetails(UPDATED_PAGE_DETAILS);
        appFeatures.setActive(UPDATED_ACTIVE);
        restAppFeaturesMockMvc.perform(put("/api/appFeaturess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(appFeatures)))
                .andExpect(status().isOk());

        // Validate the AppFeatures in the database
        List<AppFeatures> appFeaturess = appFeaturesRepository.findAll();
        assertThat(appFeaturess).hasSize(1);
        AppFeatures testAppFeatures = appFeaturess.iterator().next();
        assertThat(testAppFeatures.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppFeatures.getPageDetails()).isEqualTo(UPDATED_PAGE_DETAILS);
        assertThat(testAppFeatures.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteAppFeatures() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get the appFeatures
        restAppFeaturesMockMvc.perform(delete("/api/appFeaturess/{id}", appFeatures.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AppFeatures> appFeaturess = appFeaturesRepository.findAll();
        assertThat(appFeaturess).hasSize(0);
    }
}
