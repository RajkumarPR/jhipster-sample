package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.ArticleComment;
import com.urbanbinge.repository.ArticleCommentRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticleCommentResource REST controller.
 *
 * @see ArticleCommentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ArticleCommentResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_COMMENT = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENT = "UPDATED_TEXT";
    private static final String DEFAULT_COMMENTER = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENTER = "UPDATED_TEXT";

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final DateTime DEFAULT_COMMENTED_ON = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_COMMENTED_ON = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_COMMENTED_ON_STR = dateTimeFormatter.print(DEFAULT_COMMENTED_ON);

    @Inject
    private ArticleCommentRepository articleCommentRepository;

    private MockMvc restArticleCommentMockMvc;

    private ArticleComment articleComment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArticleCommentResource articleCommentResource = new ArticleCommentResource();
        ReflectionTestUtils.setField(articleCommentResource, "articleCommentRepository", articleCommentRepository);
        this.restArticleCommentMockMvc = MockMvcBuilders.standaloneSetup(articleCommentResource).build();
    }

    @Before
    public void initTest() {
        articleComment = new ArticleComment();
        articleComment.setComment(DEFAULT_COMMENT);
        articleComment.setCommenter(DEFAULT_COMMENTER);
        articleComment.setIsApproved(DEFAULT_IS_APPROVED);
        articleComment.setCommentedOn(DEFAULT_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void createArticleComment() throws Exception {
        int databaseSizeBeforeCreate = articleCommentRepository.findAll().size();

        // Create the ArticleComment
        restArticleCommentMockMvc.perform(post("/api/articleComments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(articleComment)))
                .andExpect(status().isCreated());

        // Validate the ArticleComment in the database
        List<ArticleComment> articleComments = articleCommentRepository.findAll();
        assertThat(articleComments).hasSize(databaseSizeBeforeCreate + 1);
        ArticleComment testArticleComment = articleComments.get(articleComments.size() - 1);
        assertThat(testArticleComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testArticleComment.getCommenter()).isEqualTo(DEFAULT_COMMENTER);
        assertThat(testArticleComment.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testArticleComment.getCommentedOn().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllArticleComments() throws Exception {
        // Initialize the database
        articleCommentRepository.saveAndFlush(articleComment);

        // Get all the articleComments
        restArticleCommentMockMvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(articleComment.getId().intValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].commenter").value(hasItem(DEFAULT_COMMENTER.toString())))
                .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
                .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON_STR)));
    }

    @Test
    @Transactional
    public void getArticleComment() throws Exception {
        // Initialize the database
        articleCommentRepository.saveAndFlush(articleComment);

        // Get the articleComment
        restArticleCommentMockMvc.perform(get("/api/articleComments/{id}", articleComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(articleComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.commenter").value(DEFAULT_COMMENTER.toString()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.commentedOn").value(DEFAULT_COMMENTED_ON_STR));
    }

    @Test
    @Transactional
    public void getNonExistingArticleComment() throws Exception {
        // Get the articleComment
        restArticleCommentMockMvc.perform(get("/api/articleComments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticleComment() throws Exception {
        // Initialize the database
        articleCommentRepository.saveAndFlush(articleComment);
		
		int databaseSizeBeforeUpdate = articleCommentRepository.findAll().size();

        // Update the articleComment
        articleComment.setComment(UPDATED_COMMENT);
        articleComment.setCommenter(UPDATED_COMMENTER);
        articleComment.setIsApproved(UPDATED_IS_APPROVED);
        articleComment.setCommentedOn(UPDATED_COMMENTED_ON);
        restArticleCommentMockMvc.perform(put("/api/articleComments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(articleComment)))
                .andExpect(status().isOk());

        // Validate the ArticleComment in the database
        List<ArticleComment> articleComments = articleCommentRepository.findAll();
        assertThat(articleComments).hasSize(databaseSizeBeforeUpdate);
        ArticleComment testArticleComment = articleComments.get(articleComments.size() - 1);
        assertThat(testArticleComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testArticleComment.getCommenter()).isEqualTo(UPDATED_COMMENTER);
        assertThat(testArticleComment.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testArticleComment.getCommentedOn().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void deleteArticleComment() throws Exception {
        // Initialize the database
        articleCommentRepository.saveAndFlush(articleComment);
		
		int databaseSizeBeforeDelete = articleCommentRepository.findAll().size();

        // Get the articleComment
        restArticleCommentMockMvc.perform(delete("/api/articleComments/{id}", articleComment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ArticleComment> articleComments = articleCommentRepository.findAll();
        assertThat(articleComments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
