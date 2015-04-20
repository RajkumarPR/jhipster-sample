package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.PromoCodeUser;
import com.urbanbinge.repository.PromoCodeUserRepository;
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
 * REST controller for managing PromoCodeUser.
 */
@RestController
@RequestMapping("/api")
public class PromoCodeUserResource {

    private final Logger log = LoggerFactory.getLogger(PromoCodeUserResource.class);

    @Inject
    private PromoCodeUserRepository promoCodeUserRepository;

    /**
     * POST  /promoCodeUsers -> Create a new promoCodeUser.
     */
    @RequestMapping(value = "/promoCodeUsers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody PromoCodeUser promoCodeUser) throws URISyntaxException {
        log.debug("REST request to save PromoCodeUser : {}", promoCodeUser);
        if (promoCodeUser.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new promoCodeUser cannot already have an ID").build();
        }
        promoCodeUserRepository.save(promoCodeUser);
        return ResponseEntity.created(new URI("/api/promoCodeUsers/" + promoCodeUser.getId())).build();
    }

    /**
     * PUT  /promoCodeUsers -> Updates an existing promoCodeUser.
     */
    @RequestMapping(value = "/promoCodeUsers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody PromoCodeUser promoCodeUser) throws URISyntaxException {
        log.debug("REST request to update PromoCodeUser : {}", promoCodeUser);
        if (promoCodeUser.getId() == null) {
            return create(promoCodeUser);
        }
        promoCodeUserRepository.save(promoCodeUser);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /promoCodeUsers -> get all the promoCodeUsers.
     */
    @RequestMapping(value = "/promoCodeUsers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PromoCodeUser>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<PromoCodeUser> page = promoCodeUserRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/promoCodeUsers", offset, limit);
        return new ResponseEntity<List<PromoCodeUser>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /promoCodeUsers/:id -> get the "id" promoCodeUser.
     */
    @RequestMapping(value = "/promoCodeUsers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PromoCodeUser> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get PromoCodeUser : {}", id);
        PromoCodeUser promoCodeUser = promoCodeUserRepository.findOne(id);
        if (promoCodeUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(promoCodeUser, HttpStatus.OK);
    }

    /**
     * DELETE  /promoCodeUsers/:id -> delete the "id" promoCodeUser.
     */
    @RequestMapping(value = "/promoCodeUsers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete PromoCodeUser : {}", id);
        promoCodeUserRepository.delete(id);
    }
}
