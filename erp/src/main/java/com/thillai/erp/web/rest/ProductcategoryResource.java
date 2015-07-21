package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.Productcategory;
import com.thillai.erp.repository.ProductcategoryRepository;
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
 * REST controller for managing Productcategory.
 */
@RestController
@RequestMapping("/api")
public class ProductcategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductcategoryResource.class);

    @Inject
    private ProductcategoryRepository productcategoryRepository;

    /**
     * POST  /productcategorys -> Create a new productcategory.
     */
    @RequestMapping(value = "/productcategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Productcategory productcategory) throws URISyntaxException {
        log.debug("REST request to save Productcategory : {}", productcategory);
        if (productcategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new productcategory cannot already have an ID").build();
        }
        productcategoryRepository.save(productcategory);
        return ResponseEntity.created(new URI("/api/productcategorys/" + productcategory.getId())).build();
    }

    /**
     * PUT  /productcategorys -> Updates an existing productcategory.
     */
    @RequestMapping(value = "/productcategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Productcategory productcategory) throws URISyntaxException {
        log.debug("REST request to update Productcategory : {}", productcategory);
        if (productcategory.getId() == null) {
            return create(productcategory);
        }
        productcategoryRepository.save(productcategory);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /productcategorys -> get all the productcategorys.
     */
    @RequestMapping(value = "/productcategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Productcategory>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Productcategory> page = productcategoryRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productcategorys", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /productcategorys/:id -> get the "id" productcategory.
     */
    @RequestMapping(value = "/productcategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Productcategory> get(@PathVariable Long id) {
        log.debug("REST request to get Productcategory : {}", id);
        return Optional.ofNullable(productcategoryRepository.findOne(id))
            .map(productcategory -> new ResponseEntity<>(
                productcategory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productcategorys/:id -> delete the "id" productcategory.
     */
    @RequestMapping(value = "/productcategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Productcategory : {}", id);
        productcategoryRepository.delete(id);
    }
}
