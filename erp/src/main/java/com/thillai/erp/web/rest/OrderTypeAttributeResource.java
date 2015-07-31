package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.OrderTypeAttribute;
import com.thillai.erp.repository.OrderTypeAttributeRepository;
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
 * REST controller for managing OrderTypeAttribute.
 */
@RestController
@RequestMapping("/api")
public class OrderTypeAttributeResource {

    private final Logger log = LoggerFactory.getLogger(OrderTypeAttributeResource.class);

    @Inject
    private OrderTypeAttributeRepository orderTypeAttributeRepository;

    /**
     * POST  /orderTypeAttributes -> Create a new orderTypeAttribute.
     */
    @RequestMapping(value = "/orderTypeAttributes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OrderTypeAttribute orderTypeAttribute) throws URISyntaxException {
        log.debug("REST request to save OrderTypeAttribute : {}", orderTypeAttribute);
        if (orderTypeAttribute.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new orderTypeAttribute cannot already have an ID").build();
        }
        orderTypeAttributeRepository.save(orderTypeAttribute);
        return ResponseEntity.created(new URI("/api/orderTypeAttributes/" + orderTypeAttribute.getId())).build();
    }

    /**
     * PUT  /orderTypeAttributes -> Updates an existing orderTypeAttribute.
     */
    @RequestMapping(value = "/orderTypeAttributes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OrderTypeAttribute orderTypeAttribute) throws URISyntaxException {
        log.debug("REST request to update OrderTypeAttribute : {}", orderTypeAttribute);
        if (orderTypeAttribute.getId() == null) {
            return create(orderTypeAttribute);
        }
        orderTypeAttributeRepository.save(orderTypeAttribute);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /orderTypeAttributes -> get all the orderTypeAttributes.
     */
    @RequestMapping(value = "/orderTypeAttributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrderTypeAttribute>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OrderTypeAttribute> page = orderTypeAttributeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderTypeAttributes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderTypeAttributes/:id -> get the "id" orderTypeAttribute.
     */
    @RequestMapping(value = "/orderTypeAttributes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderTypeAttribute> get(@PathVariable Long id) {
        log.debug("REST request to get OrderTypeAttribute : {}", id);
        return Optional.ofNullable(orderTypeAttributeRepository.findOne(id))
            .map(orderTypeAttribute -> new ResponseEntity<>(
                orderTypeAttribute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderTypeAttributes/:id -> delete the "id" orderTypeAttribute.
     */
    @RequestMapping(value = "/orderTypeAttributes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OrderTypeAttribute : {}", id);
        orderTypeAttributeRepository.delete(id);
    }
}
