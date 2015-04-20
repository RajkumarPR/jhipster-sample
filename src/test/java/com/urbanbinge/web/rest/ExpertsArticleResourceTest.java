package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.ExpertsArticle;
import com.urbanbinge.repository.ExpertsArticleRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExpertsArticleResource REST controller.
 *
 * @see ExpertsArticleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ExpertsArticleResourceTest {

    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_CONTENT = "SAMPLE_TEXT";
    private static final String UPDATED_CONTENT = "UPDATED_TEXT";

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Long DEFAULT_U_ID = 0L;
    private static final Long UPDATED_U_ID = 1L;

    private static final LocalDate DEFAULT_CREATED_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_CREATED_DATE = new LocalDate();

    @Inject
    private ExpertsArticleRepository expertsArticleRepository;

    private MockMvc restExpertsArticleMockMvc;

    private ExpertsArticle expertsArticle;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExpertsArticleResource expertsArticleResource = new ExpertsArticleResource();
        ReflectionTestUtils.setField(expertsArticleResource, "expertsArticleRepository", expertsArticleRepository);
        this.restExpertsArticleMockMvc = MockMvcBuilders.standaloneSetup(expertsArticleResource).build();
    }

    @Before
    public void initTest() {
        expertsArticle = new ExpertsArticle();
        expertsArticle.setTitle(DEFAULT_TITLE);
        expertsArticle.setContent(DEFAULT_CONTENT);
        expertsArticle.setIsApproved(DEFAULT_IS_APPROVED);
        expertsArticle.setIsDeleted(DEFAULT_IS_DELETED);
        expertsArticle.setuId(DEFAULT_U_ID);
        expertsArticle.setCreatedDate(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createExpertsArticle() throws Exception {
        int databaseSizeBeforeCreate = expertsArticleRepository.findAll().size();

        // Create the ExpertsArticle
        restExpertsArticleMockMvc.perform(post("/api/expertsArticles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(expertsArticle)))
                .andExpect(status().isCreated());

        // Validate the ExpertsArticle in the database
        List<ExpertsArticle> expertsArticles = expertsArticleRepository.findAll();
        assertThat(expertsArticles).hasSize(databaseSizeBeforeCreate + 1);
        ExpertsArticle testExpertsArticle = expertsArticles.get(expertsArticles.size() - 1);
        assertThat(testExpertsArticle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExpertsArticle.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testExpertsArticle.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testExpertsArticle.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testExpertsArticle.getuId()).isEqualTo(DEFAULT_U_ID);
        assertThat(testExpertsArticle.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllExpertsArticles() throws Exception {
        // Initialize the database
        expertsArticleRepository.saveAndFlush(expertsArticle);

        // Get all the expertsArticles
        restExpertsArticleMockMvc.perform(get("/api/expertsArticles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(expertsArticle.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
                .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
                .andExpect(jsonPath("$.[*].uId").value(hasItem(DEFAULT_U_ID.intValue())))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getExpertsArticle() throws Exception {
        // Initialize the database
        expertsArticleRepository.saveAndFlush(expertsArticle);

        // Get the expertsArticle
        restExpertsArticleMockMvc.perform(get("/api/expertsArticles/{id}", expertsArticle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(expertsArticle.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.uId").value(DEFAULT_U_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExpertsArticle() throws Exception {
        // Get the expertsArticle
        restExpertsArticleMockMvc.perform(get("/api/expertsArticles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpertsArticle() throws Exception {
        // Initialize the database
        expertsArticleRepository.saveAndFlush(expertsArticle);
		
		int databaseSizeBeforeUpdate = expertsArticleRepository.findAll().size();

        // Update the expertsArticle
        expertsArticle.setTitle(UPDATED_TITLE);
        expertsArticle.setContent(UPDATED_CONTENT);
        expertsArticle.setIsApproved(UPDATED_IS_APPROVED);
        expertsArticle.setIsDeleted(UPDATED_IS_DELETED);
        expertsArticle.setuId(UPDATED_U_ID);
        expertsArticle.setCreatedDate(UPDATED_CREATED_DATE);
        restExpertsArticleMockMvc.perform(put("/api/expertsArticles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(expertsArticle)))
                .andExpect(status().isOk());

        // Validate the ExpertsArticle in the database
        List<ExpertsArticle> expertsArticles = expertsArticleRepository.findAll();
        assertThat(expertsArticles).hasSize(databaseSizeBeforeUpdate);
        ExpertsArticle testExpertsArticle = expertsArticles.get(expertsArticles.size() - 1);
        assertThat(testExpertsArticle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExpertsArticle.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testExpertsArticle.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testExpertsArticle.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testExpertsArticle.getuId()).isEqualTo(UPDATED_U_ID);
        assertThat(testExpertsArticle.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void deleteExpertsArticle() throws Exception {
        // Initialize the database
        expertsArticleRepository.saveAndFlush(expertsArticle);
		
		int databaseSizeBeforeDelete = expertsArticleRepository.findAll().size();

        // Get the expertsArticle
        restExpertsArticleMockMvc.perform(delete("/api/expertsArticles/{id}", expertsArticle.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExpertsArticle> expertsArticles = expertsArticleRepository.findAll();
        assertThat(expertsArticles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
