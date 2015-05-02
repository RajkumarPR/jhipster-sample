package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.EventTicket;
import com.urbanbinge.repository.EventTicketRepository;
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
 * REST controller for managing EventTicket.
 */
@RestController
@RequestMapping("/api")
public class EventTicketResource {

    private final Logger log = LoggerFactory.getLogger(EventTicketResource.class);

    @Inject
    private EventTicketRepository eventTicketRepository;

    /**
     * POST  /eventTickets -> Create a new eventTicket.
     */
    @RequestMapping(value = "/eventTickets",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody EventTicket eventTicket) throws URISyntaxException {
        log.debug("REST request to save EventTicket : {}", eventTicket);
        if (eventTicket.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new eventTicket cannot already have an ID").build();
        }
        eventTicketRepository.save(eventTicket);
        return ResponseEntity.created(new URI("/api/eventTickets/" + eventTicket.getId())).build();
    }

    /**
     * PUT  /eventTickets -> Updates an existing eventTicket.
     */
    @RequestMapping(value = "/eventTickets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody EventTicket eventTicket) throws URISyntaxException {
        log.debug("REST request to update EventTicket : {}", eventTicket);
        if (eventTicket.getId() == null) {
            return create(eventTicket);
        }
        eventTicketRepository.save(eventTicket);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /eventTickets -> get all the eventTickets.
     */
    @RequestMapping(value = "/eventTickets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EventTicket>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<EventTicket> page = eventTicketRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventTickets", offset, limit);
        return new ResponseEntity<List<EventTicket>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eventTickets/:id -> get the "id" eventTicket.
     */
    @RequestMapping(value = "/eventTickets/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventTicket> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get EventTicket : {}", id);
        EventTicket eventTicket = eventTicketRepository.findOne(id);
        if (eventTicket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventTicket, HttpStatus.OK);
    }

    /**
     * DELETE  /eventTickets/:id -> delete the "id" eventTicket.
     */
    @RequestMapping(value = "/eventTickets/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete EventTicket : {}", id);
        eventTicketRepository.delete(id);
    }
}
