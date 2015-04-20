package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.ArticleImage;
import com.urbanbinge.repository.ArticleImageRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticleImageResource REST controller.
 *
 * @see ArticleImageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ArticleImageResourceTest {

    private static final String DEFAULT_IMAGE_URL = "SAMPLE_TEXT";
    private static final String UPDATED_IMAGE_URL = "UPDATED_TEXT";

    @Inject
    private ArticleImageRepository articleImageRepository;

    private MockMvc restArticleImageMockMvc;

    private ArticleImage articleImage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArticleImageResource articleImageResource = new ArticleImageResource();
        ReflectionTestUtils.setField(articleImageResource, "articleImageRepository", articleImageRepository);
        this.restArticleImageMockMvc = MockMvcBuilders.standaloneSetup(articleImageResource).build();
    }

    @Before
    public void initTest() {
        articleImage = new ArticleImage();
        articleImage.setImageUrl(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void createArticleImage() throws Exception {
        int databaseSizeBeforeCreate = articleImageRepository.findAll().size();

        // Create the ArticleImage
        restArticleImageMockMvc.perform(post("/api/articleImages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(articleImage)))
                .andExpect(status().isCreated());

        // Validate the ArticleImage in the database
        List<ArticleImage> articleImages = articleImageRepository.findAll();
        assertThat(articleImages).hasSize(databaseSizeBeforeCreate + 1);
        ArticleImage testArticleImage = articleImages.get(articleImages.size() - 1);
        assertThat(testArticleImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllArticleImages() throws Exception {
        // Initialize the database
        articleImageRepository.saveAndFlush(articleImage);

        // Get all the articleImages
        restArticleImageMockMvc.perform(get("/api/articleImages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(articleImage.getId().intValue())))
                .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    public void getArticleImage() throws Exception {
        // Initialize the database
        articleImageRepository.saveAndFlush(articleImage);

        // Get the articleImage
        restArticleImageMockMvc.perform(get("/api/articleImages/{id}", articleImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(articleImage.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticleImage() throws Exception {
        // Get the articleImage
        restArticleImageMockMvc.perform(get("/api/articleImages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticleImage() throws Exception {
        // Initialize the database
        articleImageRepository.saveAndFlush(articleImage);
		
		int databaseSizeBeforeUpdate = articleImageRepository.findAll().size();

        // Update the articleImage
        articleImage.setImageUrl(UPDATED_IMAGE_URL);
        restArticleImageMockMvc.perform(put("/api/articleImages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(articleImage)))
                .andExpect(status().isOk());

        // Validate the ArticleImage in the database
        List<ArticleImage> articleImages = articleImageRepository.findAll();
        assertThat(articleImages).hasSize(databaseSizeBeforeUpdate);
        ArticleImage testArticleImage = articleImages.get(articleImages.size() - 1);
        assertThat(testArticleImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void deleteArticleImage() throws Exception {
        // Initialize the database
        articleImageRepository.saveAndFlush(articleImage);
		
		int databaseSizeBeforeDelete = articleImageRepository.findAll().size();

        // Get the articleImage
        restArticleImageMockMvc.perform(delete("/api/articleImages/{id}", articleImage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ArticleImage> articleImages = articleImageRepository.findAll();
        assertThat(articleImages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
