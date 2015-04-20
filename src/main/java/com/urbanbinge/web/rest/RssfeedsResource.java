package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.Rssfeeds;
import com.urbanbinge.repository.RssfeedsRepository;
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
 * REST controller for managing Rssfeeds.
 */
@RestController
@RequestMapping("/api")
public class RssfeedsResource {

    private final Logger log = LoggerFactory.getLogger(RssfeedsResource.class);

    @Inject
    private RssfeedsRepository rssfeedsRepository;

    /**
     * POST  /rssfeedss -> Create a new rssfeeds.
     */
    @RequestMapping(value = "/rssfeedss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Rssfeeds rssfeeds) throws URISyntaxException {
        log.debug("REST request to save Rssfeeds : {}", rssfeeds);
        if (rssfeeds.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new rssfeeds cannot already have an ID").build();
        }
        rssfeedsRepository.save(rssfeeds);
        return ResponseEntity.created(new URI("/api/rssfeedss/" + rssfeeds.getId())).build();
    }

    /**
     * PUT  /rssfeedss -> Updates an existing rssfeeds.
     */
    @RequestMapping(value = "/rssfeedss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Rssfeeds rssfeeds) throws URISyntaxException {
        log.debug("REST request to update Rssfeeds : {}", rssfeeds);
        if (rssfeeds.getId() == null) {
            return create(rssfeeds);
        }
        rssfeedsRepository.save(rssfeeds);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /rssfeedss -> get all the rssfeedss.
     */
    @RequestMapping(value = "/rssfeedss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Rssfeeds>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Rssfeeds> page = rssfeedsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rssfeedss", offset, limit);
        return new ResponseEntity<List<Rssfeeds>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rssfeedss/:id -> get the "id" rssfeeds.
     */
    @RequestMapping(value = "/rssfeedss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rssfeeds> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Rssfeeds : {}", id);
        Rssfeeds rssfeeds = rssfeedsRepository.findOne(id);
        if (rssfeeds == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rssfeeds, HttpStatus.OK);
    }

    /**
     * DELETE  /rssfeedss/:id -> delete the "id" rssfeeds.
     */
    @RequestMapping(value = "/rssfeedss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Rssfeeds : {}", id);
        rssfeedsRepository.delete(id);
    }
}
