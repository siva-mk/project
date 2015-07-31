package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.OrderAttribute;
import com.thillai.erp.repository.OrderAttributeRepository;
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
 * REST controller for managing OrderAttribute.
 */
@RestController
@RequestMapping("/api")
public class OrderAttributeResource {

    private final Logger log = LoggerFactory.getLogger(OrderAttributeResource.class);

    @Inject
    private OrderAttributeRepository orderAttributeRepository;

    /**
     * POST  /orderAttributes -> Create a new orderAttribute.
     */
    @RequestMapping(value = "/orderAttributes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OrderAttribute orderAttribute) throws URISyntaxException {
        log.debug("REST request to save OrderAttribute : {}", orderAttribute);
        if (orderAttribute.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new orderAttribute cannot already have an ID").build();
        }
        orderAttributeRepository.save(orderAttribute);
        return ResponseEntity.created(new URI("/api/orderAttributes/" + orderAttribute.getId())).build();
    }

    /**
     * PUT  /orderAttributes -> Updates an existing orderAttribute.
     */
    @RequestMapping(value = "/orderAttributes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OrderAttribute orderAttribute) throws URISyntaxException {
        log.debug("REST request to update OrderAttribute : {}", orderAttribute);
        if (orderAttribute.getId() == null) {
            return create(orderAttribute);
        }
        orderAttributeRepository.save(orderAttribute);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /orderAttributes -> get all the orderAttributes.
     */
    @RequestMapping(value = "/orderAttributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrderAttribute>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OrderAttribute> page = orderAttributeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderAttributes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderAttributes/:id -> get the "id" orderAttribute.
     */
    @RequestMapping(value = "/orderAttributes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderAttribute> get(@PathVariable Long id) {
        log.debug("REST request to get OrderAttribute : {}", id);
        return Optional.ofNullable(orderAttributeRepository.findOne(id))
            .map(orderAttribute -> new ResponseEntity<>(
                orderAttribute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderAttributes/:id -> delete the "id" orderAttribute.
     */
    @RequestMapping(value = "/orderAttributes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OrderAttribute : {}", id);
        orderAttributeRepository.delete(id);
    }
}
