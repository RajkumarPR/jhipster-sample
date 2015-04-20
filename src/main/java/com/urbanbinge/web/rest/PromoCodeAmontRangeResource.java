package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.PromoCodeAmontRange;
import com.urbanbinge.repository.PromoCodeAmontRangeRepository;
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
 * REST controller for managing PromoCodeAmontRange.
 */
@RestController
@RequestMapping("/api")
public class PromoCodeAmontRangeResource {

    private final Logger log = LoggerFactory.getLogger(PromoCodeAmontRangeResource.class);

    @Inject
    private PromoCodeAmontRangeRepository promoCodeAmontRangeRepository;

    /**
     * POST  /promoCodeAmontRanges -> Create a new promoCodeAmontRange.
     */
    @RequestMapping(value = "/promoCodeAmontRanges",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody PromoCodeAmontRange promoCodeAmontRange) throws URISyntaxException {
        log.debug("REST request to save PromoCodeAmontRange : {}", promoCodeAmontRange);
        if (promoCodeAmontRange.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new promoCodeAmontRange cannot already have an ID").build();
        }
        promoCodeAmontRangeRepository.save(promoCodeAmontRange);
        return ResponseEntity.created(new URI("/api/promoCodeAmontRanges/" + promoCodeAmontRange.getId())).build();
    }

    /**
     * PUT  /promoCodeAmontRanges -> Updates an existing promoCodeAmontRange.
     */
    @RequestMapping(value = "/promoCodeAmontRanges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody PromoCodeAmontRange promoCodeAmontRange) throws URISyntaxException {
        log.debug("REST request to update PromoCodeAmontRange : {}", promoCodeAmontRange);
        if (promoCodeAmontRange.getId() == null) {
            return create(promoCodeAmontRange);
        }
        promoCodeAmontRangeRepository.save(promoCodeAmontRange);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /promoCodeAmontRanges -> get all the promoCodeAmontRanges.
     */
    @RequestMapping(value = "/promoCodeAmontRanges",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PromoCodeAmontRange>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<PromoCodeAmontRange> page = promoCodeAmontRangeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/promoCodeAmontRanges", offset, limit);
        return new ResponseEntity<List<PromoCodeAmontRange>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /promoCodeAmontRanges/:id -> get the "id" promoCodeAmontRange.
     */
    @RequestMapping(value = "/promoCodeAmontRanges/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PromoCodeAmontRange> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get PromoCodeAmontRange : {}", id);
        PromoCodeAmontRange promoCodeAmontRange = promoCodeAmontRangeRepository.findOne(id);
        if (promoCodeAmontRange == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(promoCodeAmontRange, HttpStatus.OK);
    }

    /**
     * DELETE  /promoCodeAmontRanges/:id -> delete the "id" promoCodeAmontRange.
     */
    @RequestMapping(value = "/promoCodeAmontRanges/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete PromoCodeAmontRange : {}", id);
        promoCodeAmontRangeRepository.delete(id);
    }
}
