package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.InventoryItemTypeAttribute;
import com.thillai.erp.repository.InventoryItemTypeAttributeRepository;
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
 * REST controller for managing InventoryItemTypeAttribute.
 */
@RestController
@RequestMapping("/api")
public class InventoryItemTypeAttributeResource {

    private final Logger log = LoggerFactory.getLogger(InventoryItemTypeAttributeResource.class);

    @Inject
    private InventoryItemTypeAttributeRepository inventoryItemTypeAttributeRepository;

    /**
     * POST  /inventoryItemTypeAttributes -> Create a new inventoryItemTypeAttribute.
     */
    @RequestMapping(value = "/inventoryItemTypeAttributes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InventoryItemTypeAttribute inventoryItemTypeAttribute) throws URISyntaxException {
        log.debug("REST request to save InventoryItemTypeAttribute : {}", inventoryItemTypeAttribute);
        if (inventoryItemTypeAttribute.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new inventoryItemTypeAttribute cannot already have an ID").build();
        }
        inventoryItemTypeAttributeRepository.save(inventoryItemTypeAttribute);
        return ResponseEntity.created(new URI("/api/inventoryItemTypeAttributes/" + inventoryItemTypeAttribute.getId())).build();
    }

    /**
     * PUT  /inventoryItemTypeAttributes -> Updates an existing inventoryItemTypeAttribute.
     */
    @RequestMapping(value = "/inventoryItemTypeAttributes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InventoryItemTypeAttribute inventoryItemTypeAttribute) throws URISyntaxException {
        log.debug("REST request to update InventoryItemTypeAttribute : {}", inventoryItemTypeAttribute);
        if (inventoryItemTypeAttribute.getId() == null) {
            return create(inventoryItemTypeAttribute);
        }
        inventoryItemTypeAttributeRepository.save(inventoryItemTypeAttribute);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /inventoryItemTypeAttributes -> get all the inventoryItemTypeAttributes.
     */
    @RequestMapping(value = "/inventoryItemTypeAttributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InventoryItemTypeAttribute>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<InventoryItemTypeAttribute> page = inventoryItemTypeAttributeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventoryItemTypeAttributes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inventoryItemTypeAttributes/:id -> get the "id" inventoryItemTypeAttribute.
     */
    @RequestMapping(value = "/inventoryItemTypeAttributes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InventoryItemTypeAttribute> get(@PathVariable Long id) {
        log.debug("REST request to get InventoryItemTypeAttribute : {}", id);
        return Optional.ofNullable(inventoryItemTypeAttributeRepository.findOne(id))
            .map(inventoryItemTypeAttribute -> new ResponseEntity<>(
                inventoryItemTypeAttribute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /inventoryItemTypeAttributes/:id -> delete the "id" inventoryItemTypeAttribute.
     */
    @RequestMapping(value = "/inventoryItemTypeAttributes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete InventoryItemTypeAttribute : {}", id);
        inventoryItemTypeAttributeRepository.delete(id);
    }
}
