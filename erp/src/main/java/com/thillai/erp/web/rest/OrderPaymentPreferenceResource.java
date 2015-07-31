package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.OrderPaymentPreference;
import com.thillai.erp.repository.OrderPaymentPreferenceRepository;
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
 * REST controller for managing OrderPaymentPreference.
 */
@RestController
@RequestMapping("/api")
public class OrderPaymentPreferenceResource {

    private final Logger log = LoggerFactory.getLogger(OrderPaymentPreferenceResource.class);

    @Inject
    private OrderPaymentPreferenceRepository orderPaymentPreferenceRepository;

    /**
     * POST  /orderPaymentPreferences -> Create a new orderPaymentPreference.
     */
    @RequestMapping(value = "/orderPaymentPreferences",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OrderPaymentPreference orderPaymentPreference) throws URISyntaxException {
        log.debug("REST request to save OrderPaymentPreference : {}", orderPaymentPreference);
        if (orderPaymentPreference.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new orderPaymentPreference cannot already have an ID").build();
        }
        orderPaymentPreferenceRepository.save(orderPaymentPreference);
        return ResponseEntity.created(new URI("/api/orderPaymentPreferences/" + orderPaymentPreference.getId())).build();
    }

    /**
     * PUT  /orderPaymentPreferences -> Updates an existing orderPaymentPreference.
     */
    @RequestMapping(value = "/orderPaymentPreferences",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OrderPaymentPreference orderPaymentPreference) throws URISyntaxException {
        log.debug("REST request to update OrderPaymentPreference : {}", orderPaymentPreference);
        if (orderPaymentPreference.getId() == null) {
            return create(orderPaymentPreference);
        }
        orderPaymentPreferenceRepository.save(orderPaymentPreference);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /orderPaymentPreferences -> get all the orderPaymentPreferences.
     */
    @RequestMapping(value = "/orderPaymentPreferences",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<OrderPaymentPreference>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OrderPaymentPreference> page = orderPaymentPreferenceRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderPaymentPreferences", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderPaymentPreferences/:id -> get the "id" orderPaymentPreference.
     */
    @RequestMapping(value = "/orderPaymentPreferences/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderPaymentPreference> get(@PathVariable Long id) {
        log.debug("REST request to get OrderPaymentPreference : {}", id);
        return Optional.ofNullable(orderPaymentPreferenceRepository.findOne(id))
            .map(orderPaymentPreference -> new ResponseEntity<>(
                orderPaymentPreference,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderPaymentPreferences/:id -> delete the "id" orderPaymentPreference.
     */
    @RequestMapping(value = "/orderPaymentPreferences/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OrderPaymentPreference : {}", id);
        orderPaymentPreferenceRepository.delete(id);
    }
}
