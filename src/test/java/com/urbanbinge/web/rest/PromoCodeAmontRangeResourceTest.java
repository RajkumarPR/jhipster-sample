package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.PromoCodeAmontRange;
import com.urbanbinge.repository.PromoCodeAmontRangeRepository;

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
 * Test class for the PromoCodeAmontRangeResource REST controller.
 *
 * @see PromoCodeAmontRangeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PromoCodeAmontRangeResourceTest {


    private static final Integer DEFAULT_LOWER = 0;
    private static final Integer UPDATED_LOWER = 1;

    private static final Integer DEFAULT_HIGHER = 0;
    private static final Integer UPDATED_HIGHER = 1;

    private static final Integer DEFAULT_THRESHOLD = 0;
    private static final Integer UPDATED_THRESHOLD = 1;

    @Inject
    private PromoCodeAmontRangeRepository promoCodeAmontRangeRepository;

    private MockMvc restPromoCodeAmontRangeMockMvc;

    private PromoCodeAmontRange promoCodeAmontRange;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromoCodeAmontRangeResource promoCodeAmontRangeResource = new PromoCodeAmontRangeResource();
        ReflectionTestUtils.setField(promoCodeAmontRangeResource, "promoCodeAmontRangeRepository", promoCodeAmontRangeRepository);
        this.restPromoCodeAmontRangeMockMvc = MockMvcBuilders.standaloneSetup(promoCodeAmontRangeResource).build();
    }

    @Before
    public void initTest() {
        promoCodeAmontRange = new PromoCodeAmontRange();
        promoCodeAmontRange.setLower(DEFAULT_LOWER);
        promoCodeAmontRange.setHigher(DEFAULT_HIGHER);
        promoCodeAmontRange.setThreshold(DEFAULT_THRESHOLD);
    }

    @Test
    @Transactional
    public void createPromoCodeAmontRange() throws Exception {
        int databaseSizeBeforeCreate = promoCodeAmontRangeRepository.findAll().size();

        // Create the PromoCodeAmontRange
        restPromoCodeAmontRangeMockMvc.perform(post("/api/promoCodeAmontRanges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promoCodeAmontRange)))
                .andExpect(status().isCreated());

        // Validate the PromoCodeAmontRange in the database
        List<PromoCodeAmontRange> promoCodeAmontRanges = promoCodeAmontRangeRepository.findAll();
        assertThat(promoCodeAmontRanges).hasSize(databaseSizeBeforeCreate + 1);
        PromoCodeAmontRange testPromoCodeAmontRange = promoCodeAmontRanges.get(promoCodeAmontRanges.size() - 1);
        assertThat(testPromoCodeAmontRange.getLower()).isEqualTo(DEFAULT_LOWER);
        assertThat(testPromoCodeAmontRange.getHigher()).isEqualTo(DEFAULT_HIGHER);
        assertThat(testPromoCodeAmontRange.getThreshold()).isEqualTo(DEFAULT_THRESHOLD);
    }

    @Test
    @Transactional
    public void getAllPromoCodeAmontRanges() throws Exception {
        // Initialize the database
        promoCodeAmontRangeRepository.saveAndFlush(promoCodeAmontRange);

        // Get all the promoCodeAmontRanges
        restPromoCodeAmontRangeMockMvc.perform(get("/api/promoCodeAmontRanges"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(promoCodeAmontRange.getId().intValue())))
                .andExpect(jsonPath("$.[*].lower").value(hasItem(DEFAULT_LOWER)))
                .andExpect(jsonPath("$.[*].higher").value(hasItem(DEFAULT_HIGHER)))
                .andExpect(jsonPath("$.[*].threshold").value(hasItem(DEFAULT_THRESHOLD)));
    }

    @Test
    @Transactional
    public void getPromoCodeAmontRange() throws Exception {
        // Initialize the database
        promoCodeAmontRangeRepository.saveAndFlush(promoCodeAmontRange);

        // Get the promoCodeAmontRange
        restPromoCodeAmontRangeMockMvc.perform(get("/api/promoCodeAmontRanges/{id}", promoCodeAmontRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(promoCodeAmontRange.getId().intValue()))
            .andExpect(jsonPath("$.lower").value(DEFAULT_LOWER))
            .andExpect(jsonPath("$.higher").value(DEFAULT_HIGHER))
            .andExpect(jsonPath("$.threshold").value(DEFAULT_THRESHOLD));
    }

    @Test
    @Transactional
    public void getNonExistingPromoCodeAmontRange() throws Exception {
        // Get the promoCodeAmontRange
        restPromoCodeAmontRangeMockMvc.perform(get("/api/promoCodeAmontRanges/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromoCodeAmontRange() throws Exception {
        // Initialize the database
        promoCodeAmontRangeRepository.saveAndFlush(promoCodeAmontRange);
		
		int databaseSizeBeforeUpdate = promoCodeAmontRangeRepository.findAll().size();

        // Update the promoCodeAmontRange
        promoCodeAmontRange.setLower(UPDATED_LOWER);
        promoCodeAmontRange.setHigher(UPDATED_HIGHER);
        promoCodeAmontRange.setThreshold(UPDATED_THRESHOLD);
        restPromoCodeAmontRangeMockMvc.perform(put("/api/promoCodeAmontRanges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promoCodeAmontRange)))
                .andExpect(status().isOk());

        // Validate the PromoCodeAmontRange in the database
        List<PromoCodeAmontRange> promoCodeAmontRanges = promoCodeAmontRangeRepository.findAll();
        assertThat(promoCodeAmontRanges).hasSize(databaseSizeBeforeUpdate);
        PromoCodeAmontRange testPromoCodeAmontRange = promoCodeAmontRanges.get(promoCodeAmontRanges.size() - 1);
        assertThat(testPromoCodeAmontRange.getLower()).isEqualTo(UPDATED_LOWER);
        assertThat(testPromoCodeAmontRange.getHigher()).isEqualTo(UPDATED_HIGHER);
        assertThat(testPromoCodeAmontRange.getThreshold()).isEqualTo(UPDATED_THRESHOLD);
    }

    @Test
    @Transactional
    public void deletePromoCodeAmontRange() throws Exception {
        // Initialize the database
        promoCodeAmontRangeRepository.saveAndFlush(promoCodeAmontRange);
		
		int databaseSizeBeforeDelete = promoCodeAmontRangeRepository.findAll().size();

        // Get the promoCodeAmontRange
        restPromoCodeAmontRangeMockMvc.perform(delete("/api/promoCodeAmontRanges/{id}", promoCodeAmontRange.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PromoCodeAmontRange> promoCodeAmontRanges = promoCodeAmontRangeRepository.findAll();
        assertThat(promoCodeAmontRanges).hasSize(databaseSizeBeforeDelete - 1);
    }
}
