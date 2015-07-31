package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.OrderHeader;
import com.thillai.erp.repository.OrderHeaderRepository;
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
 * REST controller for managing OrderHeader.
 */
@RestController
@RequestMapping("/api")
public class OrderHeaderResource {

    private final Logger log = LoggerFactory.getLogger(OrderHeaderResource.class);

    @Inject
    private OrderHeaderRepository orderHeaderRepository;

    /**
     * POST  /orderHeaders -> Create a new orderHeader.
     */
    @RequestMapping(value = "/orderHeaders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OrderHeader orderHeader) throws URISyntaxException {
        log.debug("REST request to save OrderHeader : {}", orderHeader);
        if (orderHeader.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new orderHeader cannot already have an ID").build();
        }
        orderHeaderRepository.save(orderHeader);
        return ResponseEntity.created(new URI("/api/orderHeaders/" + orderHeader.getId())).build();
    }

    /**
     * PUT  /orderHeaders -> Updates an existing orderHeader.
     */
    @RequestMapping(value = "/orderHeaders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OrderHeader orderHeader) throws URISyntaxException {
        log.debug("REST request to update OrderHeader : {}", orderHeader);
        if (orderHeader.getId() == null) {
            return create(orderHeader);
        }
        orderHeaderRepository.save(orderHeader);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /orderHeaders -> get all the orderHeaders.
     */
    @RequestMapping(value = "/orderHeaders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrderHeader>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OrderHeader> page = orderHeaderRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderHeaders", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderHeaders/:id -> get the "id" orderHeader.
     */
    @RequestMapping(value = "/orderHeaders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderHeader> get(@PathVariable Long id) {
        log.debug("REST request to get OrderHeader : {}", id);
        return Optional.ofNullable(orderHeaderRepository.findOne(id))
            .map(orderHeader -> new ResponseEntity<>(
                orderHeader,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderHeaders/:id -> delete the "id" orderHeader.
     */
    @RequestMapping(value = "/orderHeaders/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OrderHeader : {}", id);
        orderHeaderRepository.delete(id);
    }
}
