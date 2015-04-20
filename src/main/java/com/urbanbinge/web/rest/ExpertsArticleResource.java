package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.ExpertsArticle;
import com.urbanbinge.repository.ExpertsArticleRepository;
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
 * REST controller for managing ExpertsArticle.
 */
@RestController
@RequestMapping("/api")
public class ExpertsArticleResource {

    private final Logger log = LoggerFactory.getLogger(ExpertsArticleResource.class);

    @Inject
    private ExpertsArticleRepository expertsArticleRepository;

    /**
     * POST  /expertsArticles -> Create a new expertsArticle.
     */
    @RequestMapping(value = "/expertsArticles",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ExpertsArticle expertsArticle) throws URISyntaxException {
        log.debug("REST request to save ExpertsArticle : {}", expertsArticle);
        if (expertsArticle.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new expertsArticle cannot already have an ID").build();
        }
        expertsArticleRepository.save(expertsArticle);
        return ResponseEntity.created(new URI("/api/expertsArticles/" + expertsArticle.getId())).build();
    }

    /**
     * PUT  /expertsArticles -> Updates an existing expertsArticle.
     */
    @RequestMapping(value = "/expertsArticles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ExpertsArticle expertsArticle) throws URISyntaxException {
        log.debug("REST request to update ExpertsArticle : {}", expertsArticle);
        if (expertsArticle.getId() == null) {
            return create(expertsArticle);
        }
        expertsArticleRepository.save(expertsArticle);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /expertsArticles -> get all the expertsArticles.
     */
    @RequestMapping(value = "/expertsArticles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ExpertsArticle>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ExpertsArticle> page = expertsArticleRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/expertsArticles", offset, limit);
        return new ResponseEntity<List<ExpertsArticle>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /expertsArticles/:id -> get the "id" expertsArticle.
     */
    @RequestMapping(value = "/expertsArticles/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExpertsArticle> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ExpertsArticle : {}", id);
        ExpertsArticle expertsArticle = expertsArticleRepository.findOne(id);
        if (expertsArticle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(expertsArticle, HttpStatus.OK);
    }

    /**
     * DELETE  /expertsArticles/:id -> delete the "id" expertsArticle.
     */
    @RequestMapping(value = "/expertsArticles/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ExpertsArticle : {}", id);
        expertsArticleRepository.delete(id);
    }
}
