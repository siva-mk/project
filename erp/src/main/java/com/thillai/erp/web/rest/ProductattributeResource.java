package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.Productattribute;
import com.thillai.erp.repository.ProductattributeRepository;
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
 * REST controller for managing Productattribute.
 */
@RestController
@RequestMapping("/api")
public class ProductattributeResource {

    private final Logger log = LoggerFactory.getLogger(ProductattributeResource.class);

    @Inject
    private ProductattributeRepository productattributeRepository;

    /**
     * POST  /productattributes -> Create a new productattribute.
     */
    @RequestMapping(value = "/productattributes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Productattribute productattribute) throws URISyntaxException {
        log.debug("REST request to save Productattribute : {}", productattribute);
        if (productattribute.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new productattribute cannot already have an ID").build();
        }
        productattributeRepository.save(productattribute);
        return ResponseEntity.created(new URI("/api/productattributes/" + productattribute.getId())).build();
    }

    /**
     * PUT  /productattributes -> Updates an existing productattribute.
     */
    @RequestMapping(value = "/productattributes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Productattribute productattribute) throws URISyntaxException {
        log.debug("REST request to update Productattribute : {}", productattribute);
        if (productattribute.getId() == null) {
            return create(productattribute);
        }
        productattributeRepository.save(productattribute);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /productattributes -> get all the productattributes.
     */
    @RequestMapping(value = "/productattributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Productattribute>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Productattribute> page = productattributeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productattributes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /productattributes/:id -> get the "id" productattribute.
     */
    @RequestMapping(value = "/productattributes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Productattribute> get(@PathVariable Long id) {
        log.debug("REST request to get Productattribute : {}", id);
        return Optional.ofNullable(productattributeRepository.findOne(id))
            .map(productattribute -> new ResponseEntity<>(
                productattribute,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productattributes/:id -> delete the "id" productattribute.
     */
    @RequestMapping(value = "/productattributes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Productattribute : {}", id);
        productattributeRepository.delete(id);
    }
}
