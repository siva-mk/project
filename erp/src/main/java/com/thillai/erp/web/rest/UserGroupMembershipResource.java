package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.UserGroupMembership;
import com.thillai.erp.repository.UserGroupMembershipRepository;
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
 * REST controller for managing UserGroupMembership.
 */
@RestController
@RequestMapping("/api")
public class UserGroupMembershipResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupMembershipResource.class);

    @Inject
    private UserGroupMembershipRepository userGroupMembershipRepository;

    /**
     * POST  /userGroupMemberships -> Create a new userGroupMembership.
     */
    @RequestMapping(value = "/userGroupMemberships",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody UserGroupMembership userGroupMembership) throws URISyntaxException {
        log.debug("REST request to save UserGroupMembership : {}", userGroupMembership);
        if (userGroupMembership.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userGroupMembership cannot already have an ID").build();
        }
        userGroupMembershipRepository.save(userGroupMembership);
        return ResponseEntity.created(new URI("/api/userGroupMemberships/" + userGroupMembership.getId())).build();
    }

    /**
     * PUT  /userGroupMemberships -> Updates an existing userGroupMembership.
     */
    @RequestMapping(value = "/userGroupMemberships",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody UserGroupMembership userGroupMembership) throws URISyntaxException {
        log.debug("REST request to update UserGroupMembership : {}", userGroupMembership);
        if (userGroupMembership.getId() == null) {
            return create(userGroupMembership);
        }
        userGroupMembershipRepository.save(userGroupMembership);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /userGroupMemberships -> get all the userGroupMemberships.
     */
    @RequestMapping(value = "/userGroupMemberships",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UserGroupMembership>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<UserGroupMembership> page = userGroupMembershipRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userGroupMemberships", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /userGroupMemberships/:id -> get the "id" userGroupMembership.
     */
    @RequestMapping(value = "/userGroupMemberships/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserGroupMembership> get(@PathVariable Long id) {
        log.debug("REST request to get UserGroupMembership : {}", id);
        return Optional.ofNullable(userGroupMembershipRepository.findOne(id))
            .map(userGroupMembership -> new ResponseEntity<>(
                userGroupMembership,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userGroupMemberships/:id -> delete the "id" userGroupMembership.
     */
    @RequestMapping(value = "/userGroupMemberships/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete UserGroupMembership : {}", id);
        userGroupMembershipRepository.delete(id);
    }
}
