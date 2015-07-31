package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.OrderItemAttribute;
import com.thillai.erp.repository.OrderItemAttributeRepository;
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
 * REST controller for managing OrderItemAttribute.
 */
@RestController
@RequestMapping("/api")
public class OrderItemAttributeResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemAttributeResource.class);

    @Inject
    private OrderItemAttributeRepository orderItemAttributeRepository;

    /**
     * POST  /orderItemAttributes -> Create a new orderItemAttribute.
     */
    @RequestMapping(value = "/orderItemAttributes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OrderItemAttribute orderItemAttribute) throws URISyntaxException {
        log.debug("REST request to save OrderItemAttribute : {}", orderItemAttribute);
        if (orderItemAttribute.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new orderItemAttribute cannot already have an ID").build();
        }
        orderItemAttributeRepository.save(orderItemAttribute);
        return ResponseEntity.created(new URI("/api/orderItemAttributes/" + orderItemAttribute.getId())).build();
    }

    /**
     * PUT  /orderItemAttributes -> Updates an existing orderItemAttribute.
     */
    @RequestMapping(value = "/orderItemAttributes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OrderItemAttribute orderItemAttribute) throws URISyntaxException {
        log.debug("REST request to update OrderItemAttribute : {}", orderItemAttribute);
        if (orderItemAttribute.getId() == null) {
            return create(orderItemAttribute);
        }
        orderItemAttributeRepository.save(orderItemAttribute);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /orderItemAttributes -> get all the orderItemAttributes.
     */
    @RequestMapping(value = "/orderItemAttributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrderItemAttribute>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OrderItemAttribute> page = orderItemAttributeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderItemAttributes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderItemAttributes/:id -> get the "id" orderItemAttribute.
     */
    @RequestMapping(value = "/orderItemAttributes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderItemAttribute> get(@PathVariable Long id) {
        log.debug("REST request to get OrderItemAttribute : {}", id);
        return Optional.ofNullable(orderItemAttributeRepository.findOne(id))
            .map(orderItemAttribute -> new ResponseEntity<>(
                orderItemAttribute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderItemAttributes/:id -> delete the "id" orderItemAttribute.
     */
    @RequestMapping(value = "/orderItemAttributes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OrderItemAttribute : {}", id);
        orderItemAttributeRepository.delete(id);
    }
}
