package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.PromoCode;
import com.urbanbinge.repository.PromoCodeRepository;

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
 * Test class for the PromoCodeResource REST controller.
 *
 * @see PromoCodeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PromoCodeResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_PROMO_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_PROMO_CODE = "UPDATED_TEXT";

    private static final Integer DEFAULT_PROMO_AMOUNT = 0;
    private static final Integer UPDATED_PROMO_AMOUNT = 1;

    private static final Boolean DEFAULT_IS_PROMO_VALID = false;
    private static final Boolean UPDATED_IS_PROMO_VALID = true;

    private static final DateTime DEFAULT_PROMO_VALIDITY = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_PROMO_VALIDITY = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_PROMO_VALIDITY_STR = dateTimeFormatter.print(DEFAULT_PROMO_VALIDITY);

    @Inject
    private PromoCodeRepository promoCodeRepository;

    private MockMvc restPromoCodeMockMvc;

    private PromoCode promoCode;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromoCodeResource promoCodeResource = new PromoCodeResource();
        ReflectionTestUtils.setField(promoCodeResource, "promoCodeRepository", promoCodeRepository);
        this.restPromoCodeMockMvc = MockMvcBuilders.standaloneSetup(promoCodeResource).build();
    }

    @Before
    public void initTest() {
        promoCode = new PromoCode();
        promoCode.setPromoCode(DEFAULT_PROMO_CODE);
        promoCode.setPromoAmount(DEFAULT_PROMO_AMOUNT);
        promoCode.setIsPromoValid(DEFAULT_IS_PROMO_VALID);
        promoCode.setPromoValidity(DEFAULT_PROMO_VALIDITY);
    }

    @Test
    @Transactional
    public void createPromoCode() throws Exception {
        int databaseSizeBeforeCreate = promoCodeRepository.findAll().size();

        // Create the PromoCode
        restPromoCodeMockMvc.perform(post("/api/promoCodes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promoCode)))
                .andExpect(status().isCreated());

        // Validate the PromoCode in the database
        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        assertThat(promoCodes).hasSize(databaseSizeBeforeCreate + 1);
        PromoCode testPromoCode = promoCodes.get(promoCodes.size() - 1);
        assertThat(testPromoCode.getPromoCode()).isEqualTo(DEFAULT_PROMO_CODE);
        assertThat(testPromoCode.getPromoAmount()).isEqualTo(DEFAULT_PROMO_AMOUNT);
        assertThat(testPromoCode.getIsPromoValid()).isEqualTo(DEFAULT_IS_PROMO_VALID);
        assertThat(testPromoCode.getPromoValidity().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_PROMO_VALIDITY);
    }

    @Test
    @Transactional
    public void getAllPromoCodes() throws Exception {
        // Initialize the database
        promoCodeRepository.saveAndFlush(promoCode);

        // Get all the promoCodes
        restPromoCodeMockMvc.perform(get("/api/promoCodes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(promoCode.getId().intValue())))
                .andExpect(jsonPath("$.[*].promoCode").value(hasItem(DEFAULT_PROMO_CODE.toString())))
                .andExpect(jsonPath("$.[*].promoAmount").value(hasItem(DEFAULT_PROMO_AMOUNT)))
                .andExpect(jsonPath("$.[*].isPromoValid").value(hasItem(DEFAULT_IS_PROMO_VALID.booleanValue())))
                .andExpect(jsonPath("$.[*].promoValidity").value(hasItem(DEFAULT_PROMO_VALIDITY_STR)));
    }

    @Test
    @Transactional
    public void getPromoCode() throws Exception {
        // Initialize the database
        promoCodeRepository.saveAndFlush(promoCode);

        // Get the promoCode
        restPromoCodeMockMvc.perform(get("/api/promoCodes/{id}", promoCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(promoCode.getId().intValue()))
            .andExpect(jsonPath("$.promoCode").value(DEFAULT_PROMO_CODE.toString()))
            .andExpect(jsonPath("$.promoAmount").value(DEFAULT_PROMO_AMOUNT))
            .andExpect(jsonPath("$.isPromoValid").value(DEFAULT_IS_PROMO_VALID.booleanValue()))
            .andExpect(jsonPath("$.promoValidity").value(DEFAULT_PROMO_VALIDITY_STR));
    }

    @Test
    @Transactional
    public void getNonExistingPromoCode() throws Exception {
        // Get the promoCode
        restPromoCodeMockMvc.perform(get("/api/promoCodes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromoCode() throws Exception {
        // Initialize the database
        promoCodeRepository.saveAndFlush(promoCode);
		
		int databaseSizeBeforeUpdate = promoCodeRepository.findAll().size();

        // Update the promoCode
        promoCode.setPromoCode(UPDATED_PROMO_CODE);
        promoCode.setPromoAmount(UPDATED_PROMO_AMOUNT);
        promoCode.setIsPromoValid(UPDATED_IS_PROMO_VALID);
        promoCode.setPromoValidity(UPDATED_PROMO_VALIDITY);
        restPromoCodeMockMvc.perform(put("/api/promoCodes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promoCode)))
                .andExpect(status().isOk());

        // Validate the PromoCode in the database
        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        assertThat(promoCodes).hasSize(databaseSizeBeforeUpdate);
        PromoCode testPromoCode = promoCodes.get(promoCodes.size() - 1);
        assertThat(testPromoCode.getPromoCode()).isEqualTo(UPDATED_PROMO_CODE);
        assertThat(testPromoCode.getPromoAmount()).isEqualTo(UPDATED_PROMO_AMOUNT);
        assertThat(testPromoCode.getIsPromoValid()).isEqualTo(UPDATED_IS_PROMO_VALID);
        assertThat(testPromoCode.getPromoValidity().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_PROMO_VALIDITY);
    }

    @Test
    @Transactional
    public void deletePromoCode() throws Exception {
        // Initialize the database
        promoCodeRepository.saveAndFlush(promoCode);
		
		int databaseSizeBeforeDelete = promoCodeRepository.findAll().size();

        // Get the promoCode
        restPromoCodeMockMvc.perform(delete("/api/promoCodes/{id}", promoCode.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        assertThat(promoCodes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
