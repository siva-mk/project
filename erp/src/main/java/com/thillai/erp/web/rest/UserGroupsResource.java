package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.UserGroups;
import com.thillai.erp.repository.UserGroupsRepository;
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
 * REST controller for managing UserGroups.
 */
@RestController
@RequestMapping("/api")
public class UserGroupsResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupsResource.class);

    @Inject
    private UserGroupsRepository userGroupsRepository;

    /**
     * POST  /userGroupss -> Create a new userGroups.
     */
    @RequestMapping(value = "/userGroupss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody UserGroups userGroups) throws URISyntaxException {
        log.debug("REST request to save UserGroups : {}", userGroups);
        if (userGroups.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userGroups cannot already have an ID").build();
        }
        userGroupsRepository.save(userGroups);
        return ResponseEntity.created(new URI("/api/userGroupss/" + userGroups.getId())).build();
    }

    /**
     * PUT  /userGroupss -> Updates an existing userGroups.
     */
    @RequestMapping(value = "/userGroupss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody UserGroups userGroups) throws URISyntaxException {
        log.debug("REST request to update UserGroups : {}", userGroups);
        if (userGroups.getId() == null) {
            return create(userGroups);
        }
        userGroupsRepository.save(userGroups);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /userGroupss -> get all the userGroupss.
     */
    @RequestMapping(value = "/userGroupss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UserGroups>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<UserGroups> page = userGroupsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userGroupss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /userGroupss/:id -> get the "id" userGroups.
     */
    @RequestMapping(value = "/userGroupss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserGroups> get(@PathVariable Long id) {
        log.debug("REST request to get UserGroups : {}", id);
        return Optional.ofNullable(userGroupsRepository.findOne(id))
            .map(userGroups -> new ResponseEntity<>(
                userGroups,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userGroupss/:id -> delete the "id" userGroups.
     */
    @RequestMapping(value = "/userGroupss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete UserGroups : {}", id);
        userGroupsRepository.delete(id);
    }
}
