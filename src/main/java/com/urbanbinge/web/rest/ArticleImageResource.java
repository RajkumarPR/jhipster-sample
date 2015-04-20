package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.ArticleImage;
import com.urbanbinge.repository.ArticleImageRepository;
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
 * REST controller for managing ArticleImage.
 */
@RestController
@RequestMapping("/api")
public class ArticleImageResource {

    private final Logger log = LoggerFactory.getLogger(ArticleImageResource.class);

    @Inject
    private ArticleImageRepository articleImageRepository;

    /**
     * POST  /articleImages -> Create a new articleImage.
     */
    @RequestMapping(value = "/articleImages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ArticleImage articleImage) throws URISyntaxException {
        log.debug("REST request to save ArticleImage : {}", articleImage);
        if (articleImage.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new articleImage cannot already have an ID").build();
        }
        articleImageRepository.save(articleImage);
        return ResponseEntity.created(new URI("/api/articleImages/" + articleImage.getId())).build();
    }

    /**
     * PUT  /articleImages -> Updates an existing articleImage.
     */
    @RequestMapping(value = "/articleImages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ArticleImage articleImage) throws URISyntaxException {
        log.debug("REST request to update ArticleImage : {}", articleImage);
        if (articleImage.getId() == null) {
            return create(articleImage);
        }
        articleImageRepository.save(articleImage);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /articleImages -> get all the articleImages.
     */
    @RequestMapping(value = "/articleImages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ArticleImage>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ArticleImage> page = articleImageRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/articleImages", offset, limit);
        return new ResponseEntity<List<ArticleImage>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /articleImages/:id -> get the "id" articleImage.
     */
    @RequestMapping(value = "/articleImages/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ArticleImage> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ArticleImage : {}", id);
        ArticleImage articleImage = articleImageRepository.findOne(id);
        if (articleImage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleImage, HttpStatus.OK);
    }

    /**
     * DELETE  /articleImages/:id -> delete the "id" articleImage.
     */
    @RequestMapping(value = "/articleImages/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ArticleImage : {}", id);
        articleImageRepository.delete(id);
    }
}
