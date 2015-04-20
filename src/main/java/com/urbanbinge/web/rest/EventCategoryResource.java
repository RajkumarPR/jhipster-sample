package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.EventCategory;
import com.urbanbinge.repository.EventCategoryRepository;
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
 * REST controller for managing EventCategory.
 */
@RestController
@RequestMapping("/api")
public class EventCategoryResource {

    private final Logger log = LoggerFactory.getLogger(EventCategoryResource.class);

    @Inject
    private EventCategoryRepository eventCategoryRepository;

    /**
     * POST  /eventCategorys -> Create a new eventCategory.
     */
    @RequestMapping(value = "/eventCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody EventCategory eventCategory) throws URISyntaxException {
        log.debug("REST request to save EventCategory : {}", eventCategory);
        if (eventCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new eventCategory cannot already have an ID").build();
        }
        eventCategoryRepository.save(eventCategory);
        return ResponseEntity.created(new URI("/api/eventCategorys/" + eventCategory.getId())).build();
    }

    /**
     * PUT  /eventCategorys -> Updates an existing eventCategory.
     */
    @RequestMapping(value = "/eventCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody EventCategory eventCategory) throws URISyntaxException {
        log.debug("REST request to update EventCategory : {}", eventCategory);
        if (eventCategory.getId() == null) {
            return create(eventCategory);
        }
        eventCategoryRepository.save(eventCategory);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /eventCategorys -> get all the eventCategorys.
     */
    @RequestMapping(value = "/eventCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EventCategory>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<EventCategory> page = eventCategoryRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventCategorys", offset, limit);
        return new ResponseEntity<List<EventCategory>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eventCategorys/:id -> get the "id" eventCategory.
     */
    @RequestMapping(value = "/eventCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventCategory> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get EventCategory : {}", id);
        EventCategory eventCategory = eventCategoryRepository.findOne(id);
        if (eventCategory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventCategory, HttpStatus.OK);
    }

    /**
     * DELETE  /eventCategorys/:id -> delete the "id" eventCategory.
     */
    @RequestMapping(value = "/eventCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete EventCategory : {}", id);
        eventCategoryRepository.delete(id);
    }
}
