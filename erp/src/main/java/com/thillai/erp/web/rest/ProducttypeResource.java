package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.Producttype;
import com.thillai.erp.repository.ProducttypeRepository;
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
 * REST controller for managing Producttype.
 */
@RestController
@RequestMapping("/api")
public class ProducttypeResource {

    private final Logger log = LoggerFactory.getLogger(ProducttypeResource.class);

    @Inject
    private ProducttypeRepository producttypeRepository;

    /**
     * POST  /producttypes -> Create a new producttype.
     */
    @RequestMapping(value = "/producttypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Producttype producttype) throws URISyntaxException {
        log.debug("REST request to save Producttype : {}", producttype);
        if (producttype.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new producttype cannot already have an ID").build();
        }
        producttypeRepository.save(producttype);
        return ResponseEntity.created(new URI("/api/producttypes/" + producttype.getId())).build();
    }

    /**
     * PUT  /producttypes -> Updates an existing producttype.
     */
    @RequestMapping(value = "/producttypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Producttype producttype) throws URISyntaxException {
        log.debug("REST request to update Producttype : {}", producttype);
        if (producttype.getId() == null) {
            return create(producttype);
        }
        producttypeRepository.save(producttype);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /producttypes -> get all the producttypes.
     */
    @RequestMapping(value = "/producttypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Producttype>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Producttype> page = producttypeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/producttypes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /producttypes/:id -> get the "id" producttype.
     */
    @RequestMapping(value = "/producttypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Producttype> get(@PathVariable Long id) {
        log.debug("REST request to get Producttype : {}", id);
        return Optional.ofNullable(producttypeRepository.findOne(id))
            .map(producttype -> new ResponseEntity<>(
                producttype,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /producttypes/:id -> delete the "id" producttype.
     */
    @RequestMapping(value = "/producttypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Producttype : {}", id);
        producttypeRepository.delete(id);
    }
}
