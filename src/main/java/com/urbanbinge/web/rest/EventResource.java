package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.Event;
import com.urbanbinge.repository.EventRepository;
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
 * REST controller for managing Event.
 */
@RestController
@RequestMapping("/api")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    @Inject
    private EventRepository eventRepository;

    /**
     * POST  /events -> Create a new event.
     */
    @RequestMapping(value = "/events",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Event event) throws URISyntaxException {
        log.debug("REST request to save Event : {}", event);
        if (event.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new event cannot already have an ID").build();
        }
        eventRepository.save(event);
        return ResponseEntity.created(new URI("/api/events/" + event.getId())).build();
    }

    /**
     * PUT  /events -> Updates an existing event.
     */
    @RequestMapping(value = "/events",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Event event) throws URISyntaxException {
        log.debug("REST request to update Event : {}", event);
        if (event.getId() == null) {
            return create(event);
        }
        eventRepository.save(event);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /events -> get all the events.
     */
    @RequestMapping(value = "/events",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Event>> getAll(@RequestParam(value = "page" , required = false) Integer offset, @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Event> page = eventRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events", offset, limit);
        return new ResponseEntity<List<Event>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /events/:id -> get the "id" event.
     */
    @RequestMapping(value = "/events/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Event> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Event : {}", id);
        Event event = eventRepository.findOne(id);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    /**
     * DELETE  /events/:id -> delete the "id" event.
     */
    @RequestMapping(value = "/events/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Event : {}", id);
        eventRepository.delete(id);
    }
}
