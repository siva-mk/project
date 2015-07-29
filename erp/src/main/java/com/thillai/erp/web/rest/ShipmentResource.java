package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.Shipment;
import com.thillai.erp.repository.ShipmentRepository;
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
 * REST controller for managing Shipment.
 */
@RestController
@RequestMapping("/api")
public class ShipmentResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentResource.class);

    @Inject
    private ShipmentRepository shipmentRepository;

    /**
     * POST  /shipments -> Create a new shipment.
     */
    @RequestMapping(value = "/shipments",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Shipment shipment) throws URISyntaxException {
        log.debug("REST request to save Shipment : {}", shipment);
        if (shipment.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new shipment cannot already have an ID").build();
        }
        shipmentRepository.save(shipment);
        return ResponseEntity.created(new URI("/api/shipments/" + shipment.getId())).build();
    }

    /**
     * PUT  /shipments -> Updates an existing shipment.
     */
    @RequestMapping(value = "/shipments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Shipment shipment) throws URISyntaxException {
        log.debug("REST request to update Shipment : {}", shipment);
        if (shipment.getId() == null) {
            return create(shipment);
        }
        shipmentRepository.save(shipment);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /shipments -> get all the shipments.
     */
    @RequestMapping(value = "/shipments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Shipment>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Shipment> page = shipmentRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipments", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shipments/:id -> get the "id" shipment.
     */
    @RequestMapping(value = "/shipments/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Shipment> get(@PathVariable Long id) {
        log.debug("REST request to get Shipment : {}", id);
        return Optional.ofNullable(shipmentRepository.findOne(id))
            .map(shipment -> new ResponseEntity<>(
                shipment,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shipments/:id -> delete the "id" shipment.
     */
    @RequestMapping(value = "/shipments/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Shipment : {}", id);
        shipmentRepository.delete(id);
    }
}
