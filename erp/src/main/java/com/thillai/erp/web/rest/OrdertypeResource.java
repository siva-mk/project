package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.Ordertype;
import com.thillai.erp.repository.OrdertypeRepository;
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
 * REST controller for managing Ordertype.
 */
@RestController
@RequestMapping("/api")
public class OrdertypeResource {

    private final Logger log = LoggerFactory.getLogger(OrdertypeResource.class);

    @Inject
    private OrdertypeRepository ordertypeRepository;

    /**
     * POST  /ordertypes -> Create a new ordertype.
     */
    @RequestMapping(value = "/ordertypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Ordertype ordertype) throws URISyntaxException {
        log.debug("REST request to save Ordertype : {}", ordertype);
        if (ordertype.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new ordertype cannot already have an ID").build();
        }
        ordertypeRepository.save(ordertype);
        return ResponseEntity.created(new URI("/api/ordertypes/" + ordertype.getId())).build();
    }

    /**
     * PUT  /ordertypes -> Updates an existing ordertype.
     */
    @RequestMapping(value = "/ordertypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Ordertype ordertype) throws URISyntaxException {
        log.debug("REST request to update Ordertype : {}", ordertype);
        if (ordertype.getId() == null) {
            return create(ordertype);
        }
        ordertypeRepository.save(ordertype);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /ordertypes -> get all the ordertypes.
     */
    @RequestMapping(value = "/ordertypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Ordertype>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Ordertype> page = ordertypeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordertypes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ordertypes/:id -> get the "id" ordertype.
     */
    @RequestMapping(value = "/ordertypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ordertype> get(@PathVariable Long id) {
        log.debug("REST request to get Ordertype : {}", id);
        return Optional.ofNullable(ordertypeRepository.findOne(id))
            .map(ordertype -> new ResponseEntity<>(
                ordertype,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ordertypes/:id -> delete the "id" ordertype.
     */
    @RequestMapping(value = "/ordertypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Ordertype : {}", id);
        ordertypeRepository.delete(id);
    }
}
