package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.UserGroupRights;
import com.thillai.erp.repository.UserGroupRightsRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserGroupRights.
 */
@RestController
@RequestMapping("/api")
public class UserGroupRightsResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupRightsResource.class);

    @Inject
    private UserGroupRightsRepository userGroupRightsRepository;

    /**
     * POST  /userGroupRightss -> Create a new userGroupRights.
     */
    @RequestMapping(value = "/userGroupRightss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody UserGroupRights userGroupRights) throws URISyntaxException {
        log.debug("REST request to save UserGroupRights : {}", userGroupRights);
        if (userGroupRights.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userGroupRights cannot already have an ID").build();
        }
        userGroupRightsRepository.save(userGroupRights);
        return ResponseEntity.created(new URI("/api/userGroupRightss/" + userGroupRights.getId())).build();
    }

    /**
     * PUT  /userGroupRightss -> Updates an existing userGroupRights.
     */
    @RequestMapping(value = "/userGroupRightss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody UserGroupRights userGroupRights) throws URISyntaxException {
        log.debug("REST request to update UserGroupRights : {}", userGroupRights);
        if (userGroupRights.getId() == null) {
            return create(userGroupRights);
        }
        userGroupRightsRepository.save(userGroupRights);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /userGroupRightss -> get all the userGroupRightss.
     */
    @RequestMapping(value = "/userGroupRightss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UserGroupRights>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<UserGroupRights> page = userGroupRightsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userGroupRightss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /userGroupRightss/:id -> get the "id" userGroupRights.
     */
    @RequestMapping(value = "/userGroupRightss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserGroupRights> get(@PathVariable Long id) {
        log.debug("REST request to get UserGroupRights : {}", id);
        return Optional.ofNullable(userGroupRightsRepository.findOne(id))
            .map(userGroupRights -> new ResponseEntity<>(
                userGroupRights,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userGroupRightss/:id -> delete the "id" userGroupRights.
     */
    @RequestMapping(value = "/userGroupRightss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete UserGroupRights : {}", id);
        userGroupRightsRepository.delete(id);
    }
}
