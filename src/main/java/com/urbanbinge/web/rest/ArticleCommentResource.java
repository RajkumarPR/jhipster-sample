package com.urbanbinge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urbanbinge.domain.ArticleComment;
import com.urbanbinge.repository.ArticleCommentRepository;
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
 * REST controller for managing ArticleComment.
 */
@RestController
@RequestMapping("/api")
public class ArticleCommentResource {

    private final Logger log = LoggerFactory.getLogger(ArticleCommentResource.class);

    @Inject
    private ArticleCommentRepository articleCommentRepository;

    /**
     * POST  /articleComments -> Create a new articleComment.
     */
    @RequestMapping(value = "/articleComments",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ArticleComment articleComment) throws URISyntaxException {
        log.debug("REST request to save ArticleComment : {}", articleComment);
        if (articleComment.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new articleComment cannot already have an ID").build();
        }
        articleCommentRepository.save(articleComment);
        return ResponseEntity.created(new URI("/api/articleComments/" + articleComment.getId())).build();
    }

    /**
     * PUT  /articleComments -> Updates an existing articleComment.
     */
    @RequestMapping(value = "/articleComments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ArticleComment articleComment) throws URISyntaxException {
        log.debug("REST request to update ArticleComment : {}", articleComment);
        if (articleComment.getId() == null) {
            return create(articleComment);
        }
        articleCommentRepository.save(articleComment);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /articleComments -> get all the articleComments.
     */
    @RequestMapping(value = "/articleComments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ArticleComment>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ArticleComment> page = articleCommentRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/articleComments", offset, limit);
        return new ResponseEntity<List<ArticleComment>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /articleComments/:id -> get the "id" articleComment.
     */
    @RequestMapping(value = "/articleComments/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ArticleComment> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ArticleComment : {}", id);
        ArticleComment articleComment = articleCommentRepository.findOne(id);
        if (articleComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleComment, HttpStatus.OK);
    }

    /**
     * DELETE  /articleComments/:id -> delete the "id" articleComment.
     */
    @RequestMapping(value = "/articleComments/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ArticleComment : {}", id);
        articleCommentRepository.delete(id);
    }
}
