package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.InventoryItemStatus;
import com.thillai.erp.repository.InventoryItemStatusRepository;
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
 * REST controller for managing InventoryItemStatus.
 */
@RestController
@RequestMapping("/api")
public class InventoryItemStatusResource {

    private final Logger log = LoggerFactory.getLogger(InventoryItemStatusResource.class);

    @Inject
    private InventoryItemStatusRepository inventoryItemStatusRepository;

    /**
     * POST  /inventoryItemStatuss -> Create a new inventoryItemStatus.
     */
    @RequestMapping(value = "/inventoryItemStatuss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InventoryItemStatus inventoryItemStatus) throws URISyntaxException {
        log.debug("REST request to save InventoryItemStatus : {}", inventoryItemStatus);
        if (inventoryItemStatus.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new inventoryItemStatus cannot already have an ID").build();
        }
        inventoryItemStatusRepository.save(inventoryItemStatus);
        return ResponseEntity.created(new URI("/api/inventoryItemStatuss/" + inventoryItemStatus.getId())).build();
    }

    /**
     * PUT  /inventoryItemStatuss -> Updates an existing inventoryItemStatus.
     */
    @RequestMapping(value = "/inventoryItemStatuss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InventoryItemStatus inventoryItemStatus) throws URISyntaxException {
        log.debug("REST request to update InventoryItemStatus : {}", inventoryItemStatus);
        if (inventoryItemStatus.getId() == null) {
            return create(inventoryItemStatus);
        }
        inventoryItemStatusRepository.save(inventoryItemStatus);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /inventoryItemStatuss -> get all the inventoryItemStatuss.
     */
    @RequestMapping(value = "/inventoryItemStatuss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InventoryItemStatus>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<InventoryItemStatus> page = inventoryItemStatusRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventoryItemStatuss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inventoryItemStatuss/:id -> get the "id" inventoryItemStatus.
     */
    @RequestMapping(value = "/inventoryItemStatuss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InventoryItemStatus> get(@PathVariable Long id) {
        log.debug("REST request to get InventoryItemStatus : {}", id);
        return Optional.ofNullable(inventoryItemStatusRepository.findOne(id))
            .map(inventoryItemStatus -> new ResponseEntity<>(
                inventoryItemStatus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /inventoryItemStatuss/:id -> delete the "id" inventoryItemStatus.
     */
    @RequestMapping(value = "/inventoryItemStatuss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete InventoryItemStatus : {}", id);
        inventoryItemStatusRepository.delete(id);
    }
}
