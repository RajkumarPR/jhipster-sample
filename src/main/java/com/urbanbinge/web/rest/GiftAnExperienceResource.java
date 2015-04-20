package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.GiftAnExperience;
import com.urbanbinge.repository.GiftAnExperienceRepository;
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
 * REST controller for managing GiftAnExperience.
 */
@RestController
@RequestMapping("/api")
public class GiftAnExperienceResource {

    private final Logger log = LoggerFactory.getLogger(GiftAnExperienceResource.class);

    @Inject
    private GiftAnExperienceRepository giftAnExperienceRepository;

    /**
     * POST  /giftAnExperiences -> Create a new giftAnExperience.
     */
    @RequestMapping(value = "/giftAnExperiences",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody GiftAnExperience giftAnExperience) throws URISyntaxException {
        log.debug("REST request to save GiftAnExperience : {}", giftAnExperience);
        if (giftAnExperience.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new giftAnExperience cannot already have an ID").build();
        }
        giftAnExperienceRepository.save(giftAnExperience);
        return ResponseEntity.created(new URI("/api/giftAnExperiences/" + giftAnExperience.getId())).build();
    }

    /**
     * PUT  /giftAnExperiences -> Updates an existing giftAnExperience.
     */
    @RequestMapping(value = "/giftAnExperiences",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody GiftAnExperience giftAnExperience) throws URISyntaxException {
        log.debug("REST request to update GiftAnExperience : {}", giftAnExperience);
        if (giftAnExperience.getId() == null) {
            return create(giftAnExperience);
        }
        giftAnExperienceRepository.save(giftAnExperience);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /giftAnExperiences -> get all the giftAnExperiences.
     */
    @RequestMapping(value = "/giftAnExperiences",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GiftAnExperience>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<GiftAnExperience> page = giftAnExperienceRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/giftAnExperiences", offset, limit);
        return new ResponseEntity<List<GiftAnExperience>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /giftAnExperiences/:id -> get the "id" giftAnExperience.
     */
    @RequestMapping(value = "/giftAnExperiences/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GiftAnExperience> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get GiftAnExperience : {}", id);
        GiftAnExperience giftAnExperience = giftAnExperienceRepository.findOne(id);
        if (giftAnExperience == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(giftAnExperience, HttpStatus.OK);
    }

    /**
     * DELETE  /giftAnExperiences/:id -> delete the "id" giftAnExperience.
     */
    @RequestMapping(value = "/giftAnExperiences/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete GiftAnExperience : {}", id);
        giftAnExperienceRepository.delete(id);
    }
}
