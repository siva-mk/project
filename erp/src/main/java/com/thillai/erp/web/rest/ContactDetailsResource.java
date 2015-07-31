package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.ContactDetails;
import com.thillai.erp.repository.ContactDetailsRepository;
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
 * REST controller for managing ContactDetails.
 */
@RestController
@RequestMapping("/api")
public class ContactDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ContactDetailsResource.class);

    @Inject
    private ContactDetailsRepository contactDetailsRepository;

    /**
     * POST  /contactDetailss -> Create a new contactDetails.
     */
    @RequestMapping(value = "/contactDetailss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ContactDetails contactDetails) throws URISyntaxException {
        log.debug("REST request to save ContactDetails : {}", contactDetails);
        if (contactDetails.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new contactDetails cannot already have an ID").build();
        }
        contactDetailsRepository.save(contactDetails);
        return ResponseEntity.created(new URI("/api/contactDetailss/" + contactDetails.getId())).build();
    }

    /**
     * PUT  /contactDetailss -> Updates an existing contactDetails.
     */
    @RequestMapping(value = "/contactDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ContactDetails contactDetails) throws URISyntaxException {
        log.debug("REST request to update ContactDetails : {}", contactDetails);
        if (contactDetails.getId() == null) {
            return create(contactDetails);
        }
        contactDetailsRepository.save(contactDetails);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /contactDetailss -> get all the contactDetailss.
     */
    @RequestMapping(value = "/contactDetailss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ContactDetails>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ContactDetails> page = contactDetailsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contactDetailss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contactDetailss/:id -> get the "id" contactDetails.
     */
    @RequestMapping(value = "/contactDetailss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContactDetails> get(@PathVariable Long id) {
        log.debug("REST request to get ContactDetails : {}", id);
        return Optional.ofNullable(contactDetailsRepository.findOne(id))
            .map(contactDetails -> new ResponseEntity<>(
                contactDetails,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contactDetailss/:id -> delete the "id" contactDetails.
     */
    @RequestMapping(value = "/contactDetailss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ContactDetails : {}", id);
        contactDetailsRepository.delete(id);
    }
}
