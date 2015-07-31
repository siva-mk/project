package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.InvoiceItem;
import com.thillai.erp.repository.InvoiceItemRepository;
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
 * REST controller for managing InvoiceItem.
 */
@RestController
@RequestMapping("/api")
public class InvoiceItemResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemResource.class);

    @Inject
    private InvoiceItemRepository invoiceItemRepository;

    /**
     * POST  /invoiceItems -> Create a new invoiceItem.
     */
    @RequestMapping(value = "/invoiceItems",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InvoiceItem invoiceItem) throws URISyntaxException {
        log.debug("REST request to save InvoiceItem : {}", invoiceItem);
        if (invoiceItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new invoiceItem cannot already have an ID").build();
        }
        invoiceItemRepository.save(invoiceItem);
        return ResponseEntity.created(new URI("/api/invoiceItems/" + invoiceItem.getId())).build();
    }

    /**
     * PUT  /invoiceItems -> Updates an existing invoiceItem.
     */
    @RequestMapping(value = "/invoiceItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InvoiceItem invoiceItem) throws URISyntaxException {
        log.debug("REST request to update InvoiceItem : {}", invoiceItem);
        if (invoiceItem.getId() == null) {
            return create(invoiceItem);
        }
        invoiceItemRepository.save(invoiceItem);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /invoiceItems -> get all the invoiceItems.
     */
    @RequestMapping(value = "/invoiceItems",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InvoiceItem>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<InvoiceItem> page = invoiceItemRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoiceItems", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /invoiceItems/:id -> get the "id" invoiceItem.
     */
    @RequestMapping(value = "/invoiceItems/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceItem> get(@PathVariable Long id) {
        log.debug("REST request to get InvoiceItem : {}", id);
        return Optional.ofNullable(invoiceItemRepository.findOne(id))
            .map(invoiceItem -> new ResponseEntity<>(
                invoiceItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /invoiceItems/:id -> delete the "id" invoiceItem.
     */
    @RequestMapping(value = "/invoiceItems/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceItem : {}", id);
        invoiceItemRepository.delete(id);
    }
}
