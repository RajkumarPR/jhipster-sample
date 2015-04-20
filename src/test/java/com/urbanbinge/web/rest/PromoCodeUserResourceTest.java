package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.PromoCodeUser;
import com.urbanbinge.repository.PromoCodeUserRepository;

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
 * Test class for the PromoCodeUserResource REST controller.
 *
 * @see PromoCodeUserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PromoCodeUserResourceTest {

    private static final String DEFAULT_EMAIL_USED = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL_USED = "UPDATED_TEXT";
    private static final String DEFAULT_PROMO_CODE_USED = "SAMPLE_TEXT";
    private static final String UPDATED_PROMO_CODE_USED = "UPDATED_TEXT";

    @Inject
    private PromoCodeUserRepository promoCodeUserRepository;

    private MockMvc restPromoCodeUserMockMvc;

    private PromoCodeUser promoCodeUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromoCodeUserResource promoCodeUserResource = new PromoCodeUserResource();
        ReflectionTestUtils.setField(promoCodeUserResource, "promoCodeUserRepository", promoCodeUserRepository);
        this.restPromoCodeUserMockMvc = MockMvcBuilders.standaloneSetup(promoCodeUserResource).build();
    }

    @Before
    public void initTest() {
        promoCodeUser = new PromoCodeUser();
        promoCodeUser.setEmailUsed(DEFAULT_EMAIL_USED);
        promoCodeUser.setPromoCodeUsed(DEFAULT_PROMO_CODE_USED);
    }

    @Test
    @Transactional
    public void createPromoCodeUser() throws Exception {
        int databaseSizeBeforeCreate = promoCodeUserRepository.findAll().size();

        // Create the PromoCodeUser
        restPromoCodeUserMockMvc.perform(post("/api/promoCodeUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promoCodeUser)))
                .andExpect(status().isCreated());

        // Validate the PromoCodeUser in the database
        List<PromoCodeUser> promoCodeUsers = promoCodeUserRepository.findAll();
        assertThat(promoCodeUsers).hasSize(databaseSizeBeforeCreate + 1);
        PromoCodeUser testPromoCodeUser = promoCodeUsers.get(promoCodeUsers.size() - 1);
        assertThat(testPromoCodeUser.getEmailUsed()).isEqualTo(DEFAULT_EMAIL_USED);
        assertThat(testPromoCodeUser.getPromoCodeUsed()).isEqualTo(DEFAULT_PROMO_CODE_USED);
    }

    @Test
    @Transactional
    public void getAllPromoCodeUsers() throws Exception {
        // Initialize the database
        promoCodeUserRepository.saveAndFlush(promoCodeUser);

        // Get all the promoCodeUsers
        restPromoCodeUserMockMvc.perform(get("/api/promoCodeUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(promoCodeUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].emailUsed").value(hasItem(DEFAULT_EMAIL_USED.toString())))
                .andExpect(jsonPath("$.[*].promoCodeUsed").value(hasItem(DEFAULT_PROMO_CODE_USED.toString())));
    }

    @Test
    @Transactional
    public void getPromoCodeUser() throws Exception {
        // Initialize the database
        promoCodeUserRepository.saveAndFlush(promoCodeUser);

        // Get the promoCodeUser
        restPromoCodeUserMockMvc.perform(get("/api/promoCodeUsers/{id}", promoCodeUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(promoCodeUser.getId().intValue()))
            .andExpect(jsonPath("$.emailUsed").value(DEFAULT_EMAIL_USED.toString()))
            .andExpect(jsonPath("$.promoCodeUsed").value(DEFAULT_PROMO_CODE_USED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPromoCodeUser() throws Exception {
        // Get the promoCodeUser
        restPromoCodeUserMockMvc.perform(get("/api/promoCodeUsers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromoCodeUser() throws Exception {
        // Initialize the database
        promoCodeUserRepository.saveAndFlush(promoCodeUser);
		
		int databaseSizeBeforeUpdate = promoCodeUserRepository.findAll().size();

        // Update the promoCodeUser
        promoCodeUser.setEmailUsed(UPDATED_EMAIL_USED);
        promoCodeUser.setPromoCodeUsed(UPDATED_PROMO_CODE_USED);
        restPromoCodeUserMockMvc.perform(put("/api/promoCodeUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promoCodeUser)))
                .andExpect(status().isOk());

        // Validate the PromoCodeUser in the database
        List<PromoCodeUser> promoCodeUsers = promoCodeUserRepository.findAll();
        assertThat(promoCodeUsers).hasSize(databaseSizeBeforeUpdate);
        PromoCodeUser testPromoCodeUser = promoCodeUsers.get(promoCodeUsers.size() - 1);
        assertThat(testPromoCodeUser.getEmailUsed()).isEqualTo(UPDATED_EMAIL_USED);
        assertThat(testPromoCodeUser.getPromoCodeUsed()).isEqualTo(UPDATED_PROMO_CODE_USED);
    }

    @Test
    @Transactional
    public void deletePromoCodeUser() throws Exception {
        // Initialize the database
        promoCodeUserRepository.saveAndFlush(promoCodeUser);
		
		int databaseSizeBeforeDelete = promoCodeUserRepository.findAll().size();

        // Get the promoCodeUser
        restPromoCodeUserMockMvc.perform(delete("/api/promoCodeUsers/{id}", promoCodeUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PromoCodeUser> promoCodeUsers = promoCodeUserRepository.findAll();
        assertThat(promoCodeUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
