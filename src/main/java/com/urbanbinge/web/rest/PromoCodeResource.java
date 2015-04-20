package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.PromoCode;
import com.urbanbinge.repository.PromoCodeRepository;
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
 * REST controller for managing PromoCode.
 */
@RestController
@RequestMapping("/api")
public class PromoCodeResource {

    private final Logger log = LoggerFactory.getLogger(PromoCodeResource.class);

    @Inject
    private PromoCodeRepository promoCodeRepository;

    /**
     * POST  /promoCodes -> Create a new promoCode.
     */
    @RequestMapping(value = "/promoCodes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody PromoCode promoCode) throws URISyntaxException {
        log.debug("REST request to save PromoCode : {}", promoCode);
        if (promoCode.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new promoCode cannot already have an ID").build();
        }
        promoCodeRepository.save(promoCode);
        return ResponseEntity.created(new URI("/api/promoCodes/" + promoCode.getId())).build();
    }

    /**
     * PUT  /promoCodes -> Updates an existing promoCode.
     */
    @RequestMapping(value = "/promoCodes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody PromoCode promoCode) throws URISyntaxException {
        log.debug("REST request to update PromoCode : {}", promoCode);
        if (promoCode.getId() == null) {
            return create(promoCode);
        }
        promoCodeRepository.save(promoCode);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /promoCodes -> get all the promoCodes.
     */
    @RequestMapping(value = "/promoCodes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PromoCode>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<PromoCode> page = promoCodeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/promoCodes", offset, limit);
        return new ResponseEntity<List<PromoCode>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /promoCodes/:id -> get the "id" promoCode.
     */
    @RequestMapping(value = "/promoCodes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PromoCode> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get PromoCode : {}", id);
        PromoCode promoCode = promoCodeRepository.findOne(id);
        if (promoCode == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(promoCode, HttpStatus.OK);
    }

    /**
     * DELETE  /promoCodes/:id -> delete the "id" promoCode.
     */
    @RequestMapping(value = "/promoCodes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete PromoCode : {}", id);
        promoCodeRepository.delete(id);
    }
}
