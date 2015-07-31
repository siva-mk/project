package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.ShipmentItemBilling;
import com.thillai.erp.repository.ShipmentItemBillingRepository;
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
 * REST controller for managing ShipmentItemBilling.
 */
@RestController
@RequestMapping("/api")
public class ShipmentItemBillingResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentItemBillingResource.class);

    @Inject
    private ShipmentItemBillingRepository shipmentItemBillingRepository;

    /**
     * POST  /shipmentItemBillings -> Create a new shipmentItemBilling.
     */
    @RequestMapping(value = "/shipmentItemBillings",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody ShipmentItemBilling shipmentItemBilling) throws URISyntaxException {
        log.debug("REST request to save ShipmentItemBilling : {}", shipmentItemBilling);
        if (shipmentItemBilling.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new shipmentItemBilling cannot already have an ID").build();
        }
        shipmentItemBillingRepository.save(shipmentItemBilling);
        return ResponseEntity.created(new URI("/api/shipmentItemBillings/" + shipmentItemBilling.getId())).build();
    }

    /**
     * PUT  /shipmentItemBillings -> Updates an existing shipmentItemBilling.
     */
    @RequestMapping(value = "/shipmentItemBillings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody ShipmentItemBilling shipmentItemBilling) throws URISyntaxException {
        log.debug("REST request to update ShipmentItemBilling : {}", shipmentItemBilling);
        if (shipmentItemBilling.getId() == null) {
            return create(shipmentItemBilling);
        }
        shipmentItemBillingRepository.save(shipmentItemBilling);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /shipmentItemBillings -> get all the shipmentItemBillings.
     */
    @RequestMapping(value = "/shipmentItemBillings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ShipmentItemBilling>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ShipmentItemBilling> page = shipmentItemBillingRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipmentItemBillings", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shipmentItemBillings/:id -> get the "id" shipmentItemBilling.
     */
    @RequestMapping(value = "/shipmentItemBillings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShipmentItemBilling> get(@PathVariable Long id) {
        log.debug("REST request to get ShipmentItemBilling : {}", id);
        return Optional.ofNullable(shipmentItemBillingRepository.findOne(id))
            .map(shipmentItemBilling -> new ResponseEntity<>(
                shipmentItemBilling,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shipmentItemBillings/:id -> delete the "id" shipmentItemBilling.
     */
    @RequestMapping(value = "/shipmentItemBillings/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentItemBilling : {}", id);
        shipmentItemBillingRepository.delete(id);
    }
}
