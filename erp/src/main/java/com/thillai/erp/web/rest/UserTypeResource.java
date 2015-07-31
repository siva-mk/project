package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.UserType;
import com.thillai.erp.repository.UserTypeRepository;
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
 * REST controller for managing UserType.
 */
@RestController
@RequestMapping("/api")
public class UserTypeResource {

    private final Logger log = LoggerFactory.getLogger(UserTypeResource.class);

    @Inject
    private UserTypeRepository userTypeRepository;

    /**
     * POST  /userTypes -> Create a new userType.
     */
    @RequestMapping(value = "/userTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody UserType userType) throws URISyntaxException {
        log.debug("REST request to save UserType : {}", userType);
        if (userType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userType cannot already have an ID").build();
        }
        userTypeRepository.save(userType);
        return ResponseEntity.created(new URI("/api/userTypes/" + userType.getId())).build();
    }

    /**
     * PUT  /userTypes -> Updates an existing userType.
     */
    @RequestMapping(value = "/userTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody UserType userType) throws URISyntaxException {
        log.debug("REST request to update UserType : {}", userType);
        if (userType.getId() == null) {
            return create(userType);
        }
        userTypeRepository.save(userType);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /userTypes -> get all the userTypes.
     */
    @RequestMapping(value = "/userTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UserType>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<UserType> page = userTypeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userTypes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /userTypes/:id -> get the "id" userType.
     */
    @RequestMapping(value = "/userTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserType> get(@PathVariable Long id) {
        log.debug("REST request to get UserType : {}", id);
        return Optional.ofNullable(userTypeRepository.findOne(id))
            .map(userType -> new ResponseEntity<>(
                userType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userTypes/:id -> delete the "id" userType.
     */
    @RequestMapping(value = "/userTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete UserType : {}", id);
        userTypeRepository.delete(id);
    }
}
