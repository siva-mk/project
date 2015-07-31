package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.InventoryItem;
import com.thillai.erp.repository.InventoryItemRepository;
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
 * REST controller for managing InventoryItem.
 */
@RestController
@RequestMapping("/api")
public class InventoryItemResource {

    private final Logger log = LoggerFactory.getLogger(InventoryItemResource.class);

    @Inject
    private InventoryItemRepository inventoryItemRepository;

    /**
     * POST  /inventoryItems -> Create a new inventoryItem.
     */
    @RequestMapping(value = "/inventoryItems",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InventoryItem inventoryItem) throws URISyntaxException {
        log.debug("REST request to save InventoryItem : {}", inventoryItem);
        if (inventoryItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new inventoryItem cannot already have an ID").build();
        }
        inventoryItemRepository.save(inventoryItem);
        return ResponseEntity.created(new URI("/api/inventoryItems/" + inventoryItem.getId())).build();
    }

    /**
     * PUT  /inventoryItems -> Updates an existing inventoryItem.
     */
    @RequestMapping(value = "/inventoryItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InventoryItem inventoryItem) throws URISyntaxException {
        log.debug("REST request to update InventoryItem : {}", inventoryItem);
        if (inventoryItem.getId() == null) {
            return create(inventoryItem);
        }
        inventoryItemRepository.save(inventoryItem);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /inventoryItems -> get all the inventoryItems.
     */
    @RequestMapping(value = "/inventoryItems",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InventoryItem>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<InventoryItem> page = inventoryItemRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventoryItems", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inventoryItems/:id -> get the "id" inventoryItem.
     */
    @RequestMapping(value = "/inventoryItems/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InventoryItem> get(@PathVariable Long id) {
        log.debug("REST request to get InventoryItem : {}", id);
        return Optional.ofNullable(inventoryItemRepository.findOne(id))
            .map(inventoryItem -> new ResponseEntity<>(
                inventoryItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /inventoryItems/:id -> delete the "id" inventoryItem.
     */
    @RequestMapping(value = "/inventoryItems/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete InventoryItem : {}", id);
        inventoryItemRepository.delete(id);
    }
}
