package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.OrderStatus;
import com.thillai.erp.repository.OrderStatusRepository;
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
 * REST controller for managing OrderStatus.
 */
@RestController
@RequestMapping("/api")
public class OrderStatusResource {

    private final Logger log = LoggerFactory.getLogger(OrderStatusResource.class);

    @Inject
    private OrderStatusRepository orderStatusRepository;

    /**
     * POST  /orderStatuss -> Create a new orderStatus.
     */
    @RequestMapping(value = "/orderStatuss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OrderStatus orderStatus) throws URISyntaxException {
        log.debug("REST request to save OrderStatus : {}", orderStatus);
        if (orderStatus.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new orderStatus cannot already have an ID").build();
        }
        orderStatusRepository.save(orderStatus);
        return ResponseEntity.created(new URI("/api/orderStatuss/" + orderStatus.getId())).build();
    }

    /**
     * PUT  /orderStatuss -> Updates an existing orderStatus.
     */
    @RequestMapping(value = "/orderStatuss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OrderStatus orderStatus) throws URISyntaxException {
        log.debug("REST request to update OrderStatus : {}", orderStatus);
        if (orderStatus.getId() == null) {
            return create(orderStatus);
        }
        orderStatusRepository.save(orderStatus);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /orderStatuss -> get all the orderStatuss.
     */
    @RequestMapping(value = "/orderStatuss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrderStatus>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OrderStatus> page = orderStatusRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderStatuss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderStatuss/:id -> get the "id" orderStatus.
     */
    @RequestMapping(value = "/orderStatuss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderStatus> get(@PathVariable Long id) {
        log.debug("REST request to get OrderStatus : {}", id);
        return Optional.ofNullable(orderStatusRepository.findOne(id))
            .map(orderStatus -> new ResponseEntity<>(
                orderStatus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderStatuss/:id -> delete the "id" orderStatus.
     */
    @RequestMapping(value = "/orderStatuss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OrderStatus : {}", id);
        orderStatusRepository.delete(id);
    }
}
