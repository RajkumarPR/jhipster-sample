package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.Cities;
import com.urbanbinge.repository.CitiesRepository;
import com.urbanbinge.web.rest.util.PaginationUtil;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Cities.
 */
@RestController
@RequestMapping("/api")
public class CitiesResource {

    private final Logger log = LoggerFactory.getLogger(CitiesResource.class);

    @Inject
    private CitiesRepository citiesRepository;

    /**
     * POST  /citiess -> Create a new cities.
     */
    @RequestMapping(value = "/citiess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Cities cities) throws URISyntaxException {
        log.debug("REST request to save Cities : {}", cities);
        if (cities.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cities cannot already have an ID").build();
        }
        citiesRepository.save(cities);
        return ResponseEntity.created(new URI("/api/citiess/" + cities.getId())).build();
    }

    /**
     * PUT  /citiess -> Updates an existing cities.
     */
    @RequestMapping(value = "/citiess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Cities cities) throws URISyntaxException {
        log.debug("REST request to update Cities : {}", cities);
        if (cities.getId() == null) {
            return create(cities);
        }
        citiesRepository.save(cities);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /citiess -> get all the citiess.
     */
    @RequestMapping(value = "/citiess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cities>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Cities> page = citiesRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/citiess", offset, limit);
        return new ResponseEntity<List<Cities>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /citiess/:id -> get the "id" cities.
     */
    @RequestMapping(value = "/citiess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cities> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Cities : {}", id);
        Cities cities = citiesRepository.findOne(id);
        if (cities == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    /**
     * DELETE  /citiess/:id -> delete the "id" cities.
     */
    @RequestMapping(value = "/citiess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Cities : {}", id);
        citiesRepository.delete(id);
    }
}
