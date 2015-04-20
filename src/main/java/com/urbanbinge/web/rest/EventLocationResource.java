package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.EventLocation;
import com.urbanbinge.repository.EventLocationRepository;
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
 * REST controller for managing EventLocation.
 */
@RestController
@RequestMapping("/api")
public class EventLocationResource {

    private final Logger log = LoggerFactory.getLogger(EventLocationResource.class);

    @Inject
    private EventLocationRepository eventLocationRepository;

    /**
     * POST  /eventLocations -> Create a new eventLocation.
     */
    @RequestMapping(value = "/eventLocations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody EventLocation eventLocation) throws URISyntaxException {
        log.debug("REST request to save EventLocation : {}", eventLocation);
        if (eventLocation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new eventLocation cannot already have an ID").build();
        }
        eventLocationRepository.save(eventLocation);
        return ResponseEntity.created(new URI("/api/eventLocations/" + eventLocation.getId())).build();
    }

    /**
     * PUT  /eventLocations -> Updates an existing eventLocation.
     */
    @RequestMapping(value = "/eventLocations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody EventLocation eventLocation) throws URISyntaxException {
        log.debug("REST request to update EventLocation : {}", eventLocation);
        if (eventLocation.getId() == null) {
            return create(eventLocation);
        }
        eventLocationRepository.save(eventLocation);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /eventLocations -> get all the eventLocations.
     */
    @RequestMapping(value = "/eventLocations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EventLocation>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<EventLocation> page = eventLocationRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventLocations", offset, limit);
        return new ResponseEntity<List<EventLocation>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eventLocations/:id -> get the "id" eventLocation.
     */
    @RequestMapping(value = "/eventLocations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventLocation> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get EventLocation : {}", id);
        EventLocation eventLocation = eventLocationRepository.findOne(id);
        if (eventLocation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventLocation, HttpStatus.OK);
    }

    /**
     * DELETE  /eventLocations/:id -> delete the "id" eventLocation.
     */
    @RequestMapping(value = "/eventLocations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete EventLocation : {}", id);
        eventLocationRepository.delete(id);
    }
}
