package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.InvoiceAttribute;
import com.thillai.erp.repository.InvoiceAttributeRepository;
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
 * REST controller for managing InvoiceAttribute.
 */
@RestController
@RequestMapping("/api")
public class InvoiceAttributeResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceAttributeResource.class);

    @Inject
    private InvoiceAttributeRepository invoiceAttributeRepository;

    /**
     * POST  /invoiceAttributes -> Create a new invoiceAttribute.
     */
    @RequestMapping(value = "/invoiceAttributes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InvoiceAttribute invoiceAttribute) throws URISyntaxException {
        log.debug("REST request to save InvoiceAttribute : {}", invoiceAttribute);
        if (invoiceAttribute.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new invoiceAttribute cannot already have an ID").build();
        }
        invoiceAttributeRepository.save(invoiceAttribute);
        return ResponseEntity.created(new URI("/api/invoiceAttributes/" + invoiceAttribute.getId())).build();
    }

    /**
     * PUT  /invoiceAttributes -> Updates an existing invoiceAttribute.
     */
    @RequestMapping(value = "/invoiceAttributes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InvoiceAttribute invoiceAttribute) throws URISyntaxException {
        log.debug("REST request to update InvoiceAttribute : {}", invoiceAttribute);
        if (invoiceAttribute.getId() == null) {
            return create(invoiceAttribute);
        }
        invoiceAttributeRepository.save(invoiceAttribute);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /invoiceAttributes -> get all the invoiceAttributes.
     */
    @RequestMapping(value = "/invoiceAttributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InvoiceAttribute>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<InvoiceAttribute> page = invoiceAttributeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoiceAttributes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /invoiceAttributes/:id -> get the "id" invoiceAttribute.
     */
    @RequestMapping(value = "/invoiceAttributes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InvoiceAttribute> get(@PathVariable Long id) {
        log.debug("REST request to get InvoiceAttribute : {}", id);
        return Optional.ofNullable(invoiceAttributeRepository.findOne(id))
            .map(invoiceAttribute -> new ResponseEntity<>(
                invoiceAttribute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /invoiceAttributes/:id -> delete the "id" invoiceAttribute.
     */
    @RequestMapping(value = "/invoiceAttributes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceAttribute : {}", id);
        invoiceAttributeRepository.delete(id);
    }
}
