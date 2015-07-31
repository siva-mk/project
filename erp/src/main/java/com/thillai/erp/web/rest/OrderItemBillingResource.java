package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.OrderItemBilling;
import com.thillai.erp.repository.OrderItemBillingRepository;
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
 * REST controller for managing OrderItemBilling.
 */
@RestController
@RequestMapping("/api")
public class OrderItemBillingResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemBillingResource.class);

    @Inject
    private OrderItemBillingRepository orderItemBillingRepository;

    /**
     * POST  /orderItemBillings -> Create a new orderItemBilling.
     */
    @RequestMapping(value = "/orderItemBillings",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OrderItemBilling orderItemBilling) throws URISyntaxException {
        log.debug("REST request to save OrderItemBilling : {}", orderItemBilling);
        if (orderItemBilling.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new orderItemBilling cannot already have an ID").build();
        }
        orderItemBillingRepository.save(orderItemBilling);
        return ResponseEntity.created(new URI("/api/orderItemBillings/" + orderItemBilling.getId())).build();
    }

    /**
     * PUT  /orderItemBillings -> Updates an existing orderItemBilling.
     */
    @RequestMapping(value = "/orderItemBillings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OrderItemBilling orderItemBilling) throws URISyntaxException {
        log.debug("REST request to update OrderItemBilling : {}", orderItemBilling);
        if (orderItemBilling.getId() == null) {
            return create(orderItemBilling);
        }
        orderItemBillingRepository.save(orderItemBilling);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /orderItemBillings -> get all the orderItemBillings.
     */
    @RequestMapping(value = "/orderItemBillings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrderItemBilling>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OrderItemBilling> page = orderItemBillingRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderItemBillings", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderItemBillings/:id -> get the "id" orderItemBilling.
     */
    @RequestMapping(value = "/orderItemBillings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderItemBilling> get(@PathVariable Long id) {
        log.debug("REST request to get OrderItemBilling : {}", id);
        return Optional.ofNullable(orderItemBillingRepository.findOne(id))
            .map(orderItemBilling -> new ResponseEntity<>(
                orderItemBilling,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderItemBillings/:id -> delete the "id" orderItemBilling.
     */
    @RequestMapping(value = "/orderItemBillings/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OrderItemBilling : {}", id);
        orderItemBillingRepository.delete(id);
    }
}
