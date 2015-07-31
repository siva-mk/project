package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.UserRights;
import com.thillai.erp.repository.UserRightsRepository;
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
 * REST controller for managing UserRights.
 */
@RestController
@RequestMapping("/api")
public class UserRightsResource {

    private final Logger log = LoggerFactory.getLogger(UserRightsResource.class);

    @Inject
    private UserRightsRepository userRightsRepository;

    /**
     * POST  /userRightss -> Create a new userRights.
     */
    @RequestMapping(value = "/userRightss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody UserRights userRights) throws URISyntaxException {
        log.debug("REST request to save UserRights : {}", userRights);
        if (userRights.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userRights cannot already have an ID").build();
        }
        userRightsRepository.save(userRights);
        return ResponseEntity.created(new URI("/api/userRightss/" + userRights.getId())).build();
    }

    /**
     * PUT  /userRightss -> Updates an existing userRights.
     */
    @RequestMapping(value = "/userRightss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody UserRights userRights) throws URISyntaxException {
        log.debug("REST request to update UserRights : {}", userRights);
        if (userRights.getId() == null) {
            return create(userRights);
        }
        userRightsRepository.save(userRights);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /userRightss -> get all the userRightss.
     */
    @RequestMapping(value = "/userRightss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UserRights>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<UserRights> page = userRightsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userRightss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /userRightss/:id -> get the "id" userRights.
     */
    @RequestMapping(value = "/userRightss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserRights> get(@PathVariable Long id) {
        log.debug("REST request to get UserRights : {}", id);
        return Optional.ofNullable(userRightsRepository.findOne(id))
            .map(userRights -> new ResponseEntity<>(
                userRights,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userRightss/:id -> delete the "id" userRights.
     */
    @RequestMapping(value = "/userRightss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete UserRights : {}", id);
        userRightsRepository.delete(id);
    }
}
