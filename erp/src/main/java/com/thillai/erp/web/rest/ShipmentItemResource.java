package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.ShipmentItem;
import com.thillai.erp.repository.ShipmentItemRepository;
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
 * REST controller for managing ShipmentItem.
 */
@RestController
@RequestMapping("/api")
public class ShipmentItemResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentItemResource.class);

    @Inject
    private ShipmentItemRepository shipmentItemRepository;

    /**
     * POST  /shipmentItems -> Create a new shipmentItem.
     */
    @RequestMapping(value = "/shipmentItems",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ShipmentItem shipmentItem) throws URISyntaxException {
        log.debug("REST request to save ShipmentItem : {}", shipmentItem);
        if (shipmentItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new shipmentItem cannot already have an ID").build();
        }
        shipmentItemRepository.save(shipmentItem);
        return ResponseEntity.created(new URI("/api/shipmentItems/" + shipmentItem.getId())).build();
    }

    /**
     * PUT  /shipmentItems -> Updates an existing shipmentItem.
     */
    @RequestMapping(value = "/shipmentItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ShipmentItem shipmentItem) throws URISyntaxException {
        log.debug("REST request to update ShipmentItem : {}", shipmentItem);
        if (shipmentItem.getId() == null) {
            return create(shipmentItem);
        }
        shipmentItemRepository.save(shipmentItem);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /shipmentItems -> get all the shipmentItems.
     */
    @RequestMapping(value = "/shipmentItems",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ShipmentItem>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ShipmentItem> page = shipmentItemRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipmentItems", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shipmentItems/:id -> get the "id" shipmentItem.
     */
    @RequestMapping(value = "/shipmentItems/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ShipmentItem> get(@PathVariable Long id) {
        log.debug("REST request to get ShipmentItem : {}", id);
        return Optional.ofNullable(shipmentItemRepository.findOne(id))
            .map(shipmentItem -> new ResponseEntity<>(
                shipmentItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shipmentItems/:id -> delete the "id" shipmentItem.
     */
    @RequestMapping(value = "/shipmentItems/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentItem : {}", id);
        shipmentItemRepository.delete(id);
    }
}
