package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.EventCategorMap;
import com.urbanbinge.repository.EventCategorMapRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing EventCategorMap.
 */
@RestController
@RequestMapping("/api")
public class EventCategorMapResource {

    private final Logger log = LoggerFactory.getLogger(EventCategorMapResource.class);

    @Inject
    private EventCategorMapRepository eventCategorMapRepository;

    /**
     * POST  /eventCategorMaps -> Create a new eventCategorMap.
     */
    @RequestMapping(value = "/eventCategorMaps",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody EventCategorMap eventCategorMap) throws URISyntaxException {
        log.debug("REST request to save EventCategorMap : {}", eventCategorMap);
        if (eventCategorMap.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new eventCategorMap cannot already have an ID").build();
        }
        eventCategorMapRepository.save(eventCategorMap);
        return ResponseEntity.created(new URI("/api/eventCategorMaps/" + eventCategorMap.getId())).build();
    }

    /**
     * PUT  /eventCategorMaps -> Updates an existing eventCategorMap.
     */
    @RequestMapping(value = "/eventCategorMaps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody EventCategorMap eventCategorMap) throws URISyntaxException {
        log.debug("REST request to update EventCategorMap : {}", eventCategorMap);
        if (eventCategorMap.getId() == null) {
            return create(eventCategorMap);
        }
        eventCategorMapRepository.save(eventCategorMap);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /eventCategorMaps -> get all the eventCategorMaps.
     */
    @RequestMapping(value = "/eventCategorMaps",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EventCategorMap>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<EventCategorMap> page = eventCategorMapRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventCategorMaps", offset, limit);
        return new ResponseEntity<List<EventCategorMap>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eventCategorMaps/:id -> get the "id" eventCategorMap.
     */
    @RequestMapping(value = "/eventCategorMaps/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventCategorMap> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get EventCategorMap : {}", id);
        EventCategorMap eventCategorMap = eventCategorMapRepository.findOne(id);
        if (eventCategorMap == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventCategorMap, HttpStatus.OK);
    }

    /**
     * DELETE  /eventCategorMaps/:id -> delete the "id" eventCategorMap.
     */
    @RequestMapping(value = "/eventCategorMaps/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete EventCategorMap : {}", id);
        eventCategorMapRepository.delete(id);
    }
}
