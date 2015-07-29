package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.AppFeatures;
import com.thillai.erp.repository.AppFeaturesRepository;
import com.thillai.erp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AppFeatures.
 */
@RestController
@RequestMapping("/api")
public class AppFeaturesResource {

    private final Logger log = LoggerFactory.getLogger(AppFeaturesResource.class);

    @Inject
    private AppFeaturesRepository appFeaturesRepository;

    /**
     * POST  /appFeaturess -> Create a new appFeatures.
     */
    @RequestMapping(value = "/appFeaturess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody AppFeatures appFeatures) throws URISyntaxException {
        log.debug("REST request to save AppFeatures : {}", appFeatures);
        if (appFeatures.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new appFeatures cannot already have an ID").build();
        }
        appFeaturesRepository.save(appFeatures);
        return ResponseEntity.created(new URI("/api/appFeaturess/" + appFeatures.getId())).build();
    }

    /**
     * PUT  /appFeaturess -> Updates an existing appFeatures.
     */
    @RequestMapping(value = "/appFeaturess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody AppFeatures appFeatures) throws URISyntaxException {
        log.debug("REST request to update AppFeatures : {}", appFeatures);
        if (appFeatures.getId() == null) {
            return create(appFeatures);
        }
        appFeaturesRepository.save(appFeatures);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /appFeaturess -> get all the appFeaturess.
     */
    @RequestMapping(value = "/appFeaturess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AppFeatures>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<AppFeatures> page = appFeaturesRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/appFeaturess", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /appFeaturess/:id -> get the "id" appFeatures.
     */
    @RequestMapping(value = "/appFeaturess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppFeatures> get(@PathVariable Long id) {
        log.debug("REST request to get AppFeatures : {}", id);
        return Optional.ofNullable(appFeaturesRepository.findOne(id))
            .map(appFeatures -> new ResponseEntity<>(
                appFeatures,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /appFeaturess/:id -> delete the "id" appFeatures.
     */
    @RequestMapping(value = "/appFeaturess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete AppFeatures : {}", id);
        appFeaturesRepository.delete(id);
    }
}
