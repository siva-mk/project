package com.thillai.erp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thillai.erp.domain.PartyTypes;
import com.thillai.erp.repository.PartyTypesRepository;
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
 * REST controller for managing PartyTypes.
 */
@RestController
@RequestMapping("/api")
public class PartyTypesResource {

    private final Logger log = LoggerFactory.getLogger(PartyTypesResource.class);

    @Inject
    private PartyTypesRepository partyTypesRepository;

    /**
     * POST  /partyTypess -> Create a new partyTypes.
     */
    @RequestMapping(value = "/partyTypess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody PartyTypes partyTypes) throws URISyntaxException {
        log.debug("REST request to save PartyTypes : {}", partyTypes);
        if (partyTypes.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new partyTypes cannot already have an ID").build();
        }
        partyTypesRepository.save(partyTypes);
        return ResponseEntity.created(new URI("/api/partyTypess/" + partyTypes.getId())).build();
    }

    /**
     * PUT  /partyTypess -> Updates an existing partyTypes.
     */
    @RequestMapping(value = "/partyTypess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody PartyTypes partyTypes) throws URISyntaxException {
        log.debug("REST request to update PartyTypes : {}", partyTypes);
        if (partyTypes.getId() == null) {
            return create(partyTypes);
        }
        partyTypesRepository.save(partyTypes);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /partyTypess -> get all the partyTypess.
     */
    @RequestMapping(value = "/partyTypess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PartyTypes>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<PartyTypes> page = partyTypesRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/partyTypess", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /partyTypess/:id -> get the "id" partyTypes.
     */
    @RequestMapping(value = "/partyTypess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PartyTypes> get(@PathVariable Long id) {
        log.debug("REST request to get PartyTypes : {}", id);
        return Optional.ofNullable(partyTypesRepository.findOne(id))
            .map(partyTypes -> new ResponseEntity<>(
                partyTypes,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /partyTypess/:id -> delete the "id" partyTypes.
     */
    @RequestMapping(value = "/partyTypess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete PartyTypes : {}", id);
        partyTypesRepository.delete(id);
    }
}
