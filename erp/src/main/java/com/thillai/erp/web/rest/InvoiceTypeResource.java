package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.InvoiceType;
import com.thillai.erp.repository.InvoiceTypeRepository;
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
 * REST controller for managing InvoiceType.
 */
@RestController
@RequestMapping("/api")
public class InvoiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceTypeResource.class);

    @Inject
    private InvoiceTypeRepository invoiceTypeRepository;

    /**
     * POST  /invoiceTypes -> Create a new invoiceType.
     */
    @RequestMapping(value = "/invoiceTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InvoiceType invoiceType) throws URISyntaxException {
        log.debug("REST request to save InvoiceType : {}", invoiceType);
        if (invoiceType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new invoiceType cannot already have an ID").build();
        }
        invoiceTypeRepository.save(invoiceType);
        return ResponseEntity.created(new URI("/api/invoiceTypes/" + invoiceType.getId())).build();
    }

    /**
     * PUT  /invoiceTypes -> Updates an existing invoiceType.
     */
    @RequestMapping(value = "/invoiceTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InvoiceType invoiceType) throws URISyntaxException {
        log.debug("REST request to update InvoiceType : {}", invoiceType);
        if (invoiceType.getId() == null) {
            return create(invoiceType);
        }
        invoiceTypeRepository.save(invoiceType);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /invoiceTypes -> get all the invoiceTypes.
     */
    @RequestMapping(value = "/invoiceTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InvoiceType>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<InvoiceType> page = invoiceTypeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoiceTypes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /invoiceTypes/:id -> get the "id" invoiceType.
     */
    @RequestMapping(value = "/invoiceTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceType> get(@PathVariable Long id) {
        log.debug("REST request to get InvoiceType : {}", id);
        return Optional.ofNullable(invoiceTypeRepository.findOne(id))
            .map(invoiceType -> new ResponseEntity<>(
                invoiceType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /invoiceTypes/:id -> delete the "id" invoiceType.
     */
    @RequestMapping(value = "/invoiceTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceType : {}", id);
        invoiceTypeRepository.delete(id);
    }
}
