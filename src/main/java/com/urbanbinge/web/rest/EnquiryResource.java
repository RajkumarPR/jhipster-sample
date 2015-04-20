package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.Enquiry;
import com.urbanbinge.repository.EnquiryRepository;
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
 * REST controller for managing Enquiry.
 */
@RestController
@RequestMapping("/api")
public class EnquiryResource {

    private final Logger log = LoggerFactory.getLogger(EnquiryResource.class);

    @Inject
    private EnquiryRepository enquiryRepository;

    /**
     * POST  /enquirys -> Create a new enquiry.
     */
    @RequestMapping(value = "/enquirys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Enquiry enquiry) throws URISyntaxException {
        log.debug("REST request to save Enquiry : {}", enquiry);
        if (enquiry.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new enquiry cannot already have an ID").build();
        }
        enquiryRepository.save(enquiry);
        return ResponseEntity.created(new URI("/api/enquirys/" + enquiry.getId())).build();
    }

    /**
     * PUT  /enquirys -> Updates an existing enquiry.
     */
    @RequestMapping(value = "/enquirys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Enquiry enquiry) throws URISyntaxException {
        log.debug("REST request to update Enquiry : {}", enquiry);
        if (enquiry.getId() == null) {
            return create(enquiry);
        }
        enquiryRepository.save(enquiry);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /enquirys -> get all the enquirys.
     */
    @RequestMapping(value = "/enquirys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Enquiry>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Enquiry> page = enquiryRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enquirys", offset, limit);
        return new ResponseEntity<List<Enquiry>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /enquirys/:id -> get the "id" enquiry.
     */
    @RequestMapping(value = "/enquirys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Enquiry> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Enquiry : {}", id);
        Enquiry enquiry = enquiryRepository.findOne(id);
        if (enquiry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(enquiry, HttpStatus.OK);
    }

    /**
     * DELETE  /enquirys/:id -> delete the "id" enquiry.
     */
    @RequestMapping(value = "/enquirys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Enquiry : {}", id);
        enquiryRepository.delete(id);
    }
}
