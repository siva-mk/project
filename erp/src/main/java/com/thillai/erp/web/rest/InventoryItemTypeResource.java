package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.InventoryItemType;
import com.thillai.erp.repository.InventoryItemTypeRepository;
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
 * REST controller for managing InventoryItemType.
 */
@RestController
@RequestMapping("/api")
public class InventoryItemTypeResource {

    private final Logger log = LoggerFactory.getLogger(InventoryItemTypeResource.class);

    @Inject
    private InventoryItemTypeRepository inventoryItemTypeRepository;

    /**
     * POST  /inventoryItemTypes -> Create a new inventoryItemType.
     */
    @RequestMapping(value = "/inventoryItemTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InventoryItemType inventoryItemType) throws URISyntaxException {
        log.debug("REST request to save InventoryItemType : {}", inventoryItemType);
        if (inventoryItemType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new inventoryItemType cannot already have an ID").build();
        }
        inventoryItemTypeRepository.save(inventoryItemType);
        return ResponseEntity.created(new URI("/api/inventoryItemTypes/" + inventoryItemType.getId())).build();
    }

    /**
     * PUT  /inventoryItemTypes -> Updates an existing inventoryItemType.
     */
    @RequestMapping(value = "/inventoryItemTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InventoryItemType inventoryItemType) throws URISyntaxException {
        log.debug("REST request to update InventoryItemType : {}", inventoryItemType);
        if (inventoryItemType.getId() == null) {
            return create(inventoryItemType);
        }
        inventoryItemTypeRepository.save(inventoryItemType);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /inventoryItemTypes -> get all the inventoryItemTypes.
     */
    @RequestMapping(value = "/inventoryItemTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InventoryItemType>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<InventoryItemType> page = inventoryItemTypeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventoryItemTypes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inventoryItemTypes/:id -> get the "id" inventoryItemType.
     */
    @RequestMapping(value = "/inventoryItemTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InventoryItemType> get(@PathVariable Long id) {
        log.debug("REST request to get InventoryItemType : {}", id);
        return Optional.ofNullable(inventoryItemTypeRepository.findOne(id))
            .map(inventoryItemType -> new ResponseEntity<>(
                inventoryItemType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /inventoryItemTypes/:id -> delete the "id" inventoryItemType.
     */
    @RequestMapping(value = "/inventoryItemTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete InventoryItemType : {}", id);
        inventoryItemTypeRepository.delete(id);
    }
}
