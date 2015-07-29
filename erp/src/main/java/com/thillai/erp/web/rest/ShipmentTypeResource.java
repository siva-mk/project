package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.ShipmentType;
import com.thillai.erp.repository.ShipmentTypeRepository;
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
 * REST controller for managing ShipmentType.
 */
@RestController
@RequestMapping("/api")
public class ShipmentTypeResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentTypeResource.class);

    @Inject
    private ShipmentTypeRepository shipmentTypeRepository;

    /**
     * POST  /shipmentTypes -> Create a new shipmentType.
     */
    @RequestMapping(value = "/shipmentTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ShipmentType shipmentType) throws URISyntaxException {
        log.debug("REST request to save ShipmentType : {}", shipmentType);
        if (shipmentType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new shipmentType cannot already have an ID").build();
        }
        shipmentTypeRepository.save(shipmentType);
        return ResponseEntity.created(new URI("/api/shipmentTypes/" + shipmentType.getId())).build();
    }

    /**
     * PUT  /shipmentTypes -> Updates an existing shipmentType.
     */
    @RequestMapping(value = "/shipmentTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ShipmentType shipmentType) throws URISyntaxException {
        log.debug("REST request to update ShipmentType : {}", shipmentType);
        if (shipmentType.getId() == null) {
            return create(shipmentType);
        }
        shipmentTypeRepository.save(shipmentType);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /shipmentTypes -> get all the shipmentTypes.
     */
    @RequestMapping(value = "/shipmentTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ShipmentType>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ShipmentType> page = shipmentTypeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipmentTypes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shipmentTypes/:id -> get the "id" shipmentType.
     */
    @RequestMapping(value = "/shipmentTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShipmentType> get(@PathVariable Long id) {
        log.debug("REST request to get ShipmentType : {}", id);
        return Optional.ofNullable(shipmentTypeRepository.findOne(id))
            .map(shipmentType -> new ResponseEntity<>(
                shipmentType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shipmentTypes/:id -> delete the "id" shipmentType.
     */
    @RequestMapping(value = "/shipmentTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentType : {}", id);
        shipmentTypeRepository.delete(id);
    }
}
