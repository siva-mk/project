package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.InventoryItemAttribute;
import com.thillai.erp.repository.InventoryItemAttributeRepository;
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
 * REST controller for managing InventoryItemAttribute.
 */
@RestController
@RequestMapping("/api")
public class InventoryItemAttributeResource {

    private final Logger log = LoggerFactory.getLogger(InventoryItemAttributeResource.class);

    @Inject
    private InventoryItemAttributeRepository inventoryItemAttributeRepository;

    /**
     * POST  /inventoryItemAttributes -> Create a new inventoryItemAttribute.
     */
    @RequestMapping(value = "/inventoryItemAttributes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InventoryItemAttribute inventoryItemAttribute) throws URISyntaxException {
        log.debug("REST request to save InventoryItemAttribute : {}", inventoryItemAttribute);
        if (inventoryItemAttribute.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new inventoryItemAttribute cannot already have an ID").build();
        }
        inventoryItemAttributeRepository.save(inventoryItemAttribute);
        return ResponseEntity.created(new URI("/api/inventoryItemAttributes/" + inventoryItemAttribute.getId())).build();
    }

    /**
     * PUT  /inventoryItemAttributes -> Updates an existing inventoryItemAttribute.
     */
    @RequestMapping(value = "/inventoryItemAttributes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InventoryItemAttribute inventoryItemAttribute) throws URISyntaxException {
        log.debug("REST request to update InventoryItemAttribute : {}", inventoryItemAttribute);
        if (inventoryItemAttribute.getId() == null) {
            return create(inventoryItemAttribute);
        }
        inventoryItemAttributeRepository.save(inventoryItemAttribute);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /inventoryItemAttributes -> get all the inventoryItemAttributes.
     */
    @RequestMapping(value = "/inventoryItemAttributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InventoryItemAttribute>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<InventoryItemAttribute> page = inventoryItemAttributeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventoryItemAttributes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inventoryItemAttributes/:id -> get the "id" inventoryItemAttribute.
     */
    @RequestMapping(value = "/inventoryItemAttributes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InventoryItemAttribute> get(@PathVariable Long id) {
        log.debug("REST request to get InventoryItemAttribute : {}", id);
        return Optional.ofNullable(inventoryItemAttributeRepository.findOne(id))
            .map(inventoryItemAttribute -> new ResponseEntity<>(
                inventoryItemAttribute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /inventoryItemAttributes/:id -> delete the "id" inventoryItemAttribute.
     */
    @RequestMapping(value = "/inventoryItemAttributes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete InventoryItemAttribute : {}", id);
        inventoryItemAttributeRepository.delete(id);
    }
}
