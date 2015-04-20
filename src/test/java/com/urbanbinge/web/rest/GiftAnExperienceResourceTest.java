package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.GiftAnExperience;
import com.urbanbinge.repository.GiftAnExperienceRepository;

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
 * Test class for the GiftAnExperienceResource REST controller.
 *
 * @see GiftAnExperienceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GiftAnExperienceResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_RECEIVER_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_RECEIVER_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_RECEIVER_MAIL = "SAMPLE_TEXT";
    private static final String UPDATED_RECEIVER_MAIL = "UPDATED_TEXT";
    private static final String DEFAULT_SENDER_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_SENDER_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SENDER_MAIL = "SAMPLE_TEXT";
    private static final String UPDATED_SENDER_MAIL = "UPDATED_TEXT";
    private static final String DEFAULT_SENDER_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_SENDER_PHONE = "UPDATED_TEXT";
    private static final String DEFAULT_GIFT_MESSAGE = "SAMPLE_TEXT";
    private static final String UPDATED_GIFT_MESSAGE = "UPDATED_TEXT";
    private static final String DEFAULT_VOUCHER_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_VOUCHER_CODE = "UPDATED_TEXT";

    private static final Integer DEFAULT_VOUCHER_AMOUNT = 0;
    private static final Integer UPDATED_VOUCHER_AMOUNT = 1;
    private static final String DEFAULT_TRANSACTION_ID = "SAMPLE_TEXT";
    private static final String UPDATED_TRANSACTION_ID = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE_OF_TRANSACTION = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE_OF_TRANSACTION = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_OF_TRANSACTION_STR = dateTimeFormatter.print(DEFAULT_DATE_OF_TRANSACTION);

    private static final Boolean DEFAULT_IS_VOUCHER_VALID = false;
    private static final Boolean UPDATED_IS_VOUCHER_VALID = true;

    private static final DateTime DEFAULT_VOUCHER_VALIDITY = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_VOUCHER_VALIDITY = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_VOUCHER_VALIDITY_STR = dateTimeFormatter.print(DEFAULT_VOUCHER_VALIDITY);

    @Inject
    private GiftAnExperienceRepository giftAnExperienceRepository;

    private MockMvc restGiftAnExperienceMockMvc;

    private GiftAnExperience giftAnExperience;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GiftAnExperienceResource giftAnExperienceResource = new GiftAnExperienceResource();
        ReflectionTestUtils.setField(giftAnExperienceResource, "giftAnExperienceRepository", giftAnExperienceRepository);
        this.restGiftAnExperienceMockMvc = MockMvcBuilders.standaloneSetup(giftAnExperienceResource).build();
    }

    @Before
    public void initTest() {
        giftAnExperience = new GiftAnExperience();
        giftAnExperience.setReceiverName(DEFAULT_RECEIVER_NAME);
        giftAnExperience.setReceiverMail(DEFAULT_RECEIVER_MAIL);
        giftAnExperience.setSenderName(DEFAULT_SENDER_NAME);
        giftAnExperience.setSenderMail(DEFAULT_SENDER_MAIL);
        giftAnExperience.setSenderPhone(DEFAULT_SENDER_PHONE);
        giftAnExperience.setGiftMessage(DEFAULT_GIFT_MESSAGE);
        giftAnExperience.setVoucherCode(DEFAULT_VOUCHER_CODE);
        giftAnExperience.setVoucherAmount(DEFAULT_VOUCHER_AMOUNT);
        giftAnExperience.setTransactionId(DEFAULT_TRANSACTION_ID);
        giftAnExperience.setDateOfTransaction(DEFAULT_DATE_OF_TRANSACTION);
        giftAnExperience.setIsVoucherValid(DEFAULT_IS_VOUCHER_VALID);
        giftAnExperience.setVoucherValidity(DEFAULT_VOUCHER_VALIDITY);
    }

    @Test
    @Transactional
    public void createGiftAnExperience() throws Exception {
        int databaseSizeBeforeCreate = giftAnExperienceRepository.findAll().size();

        // Create the GiftAnExperience
        restGiftAnExperienceMockMvc.perform(post("/api/giftAnExperiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(giftAnExperience)))
                .andExpect(status().isCreated());

        // Validate the GiftAnExperience in the database
        List<GiftAnExperience> giftAnExperiences = giftAnExperienceRepository.findAll();
        assertThat(giftAnExperiences).hasSize(databaseSizeBeforeCreate + 1);
        GiftAnExperience testGiftAnExperience = giftAnExperiences.get(giftAnExperiences.size() - 1);
        assertThat(testGiftAnExperience.getReceiverName()).isEqualTo(DEFAULT_RECEIVER_NAME);
        assertThat(testGiftAnExperience.getReceiverMail()).isEqualTo(DEFAULT_RECEIVER_MAIL);
        assertThat(testGiftAnExperience.getSenderName()).isEqualTo(DEFAULT_SENDER_NAME);
        assertThat(testGiftAnExperience.getSenderMail()).isEqualTo(DEFAULT_SENDER_MAIL);
        assertThat(testGiftAnExperience.getSenderPhone()).isEqualTo(DEFAULT_SENDER_PHONE);
        assertThat(testGiftAnExperience.getGiftMessage()).isEqualTo(DEFAULT_GIFT_MESSAGE);
        assertThat(testGiftAnExperience.getVoucherCode()).isEqualTo(DEFAULT_VOUCHER_CODE);
        assertThat(testGiftAnExperience.getVoucherAmount()).isEqualTo(DEFAULT_VOUCHER_AMOUNT);
        assertThat(testGiftAnExperience.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testGiftAnExperience.getDateOfTransaction().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE_OF_TRANSACTION);
        assertThat(testGiftAnExperience.getIsVoucherValid()).isEqualTo(DEFAULT_IS_VOUCHER_VALID);
        assertThat(testGiftAnExperience.getVoucherValidity().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_VOUCHER_VALIDITY);
    }

    @Test
    @Transactional
    public void getAllGiftAnExperiences() throws Exception {
        // Initialize the database
        giftAnExperienceRepository.saveAndFlush(giftAnExperience);

        // Get all the giftAnExperiences
        restGiftAnExperienceMockMvc.perform(get("/api/giftAnExperiences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(giftAnExperience.getId().intValue())))
                .andExpect(jsonPath("$.[*].receiverName").value(hasItem(DEFAULT_RECEIVER_NAME.toString())))
                .andExpect(jsonPath("$.[*].receiverMail").value(hasItem(DEFAULT_RECEIVER_MAIL.toString())))
                .andExpect(jsonPath("$.[*].senderName").value(hasItem(DEFAULT_SENDER_NAME.toString())))
                .andExpect(jsonPath("$.[*].senderMail").value(hasItem(DEFAULT_SENDER_MAIL.toString())))
                .andExpect(jsonPath("$.[*].senderPhone").value(hasItem(DEFAULT_SENDER_PHONE.toString())))
                .andExpect(jsonPath("$.[*].giftMessage").value(hasItem(DEFAULT_GIFT_MESSAGE.toString())))
                .andExpect(jsonPath("$.[*].voucherCode").value(hasItem(DEFAULT_VOUCHER_CODE.toString())))
                .andExpect(jsonPath("$.[*].voucherAmount").value(hasItem(DEFAULT_VOUCHER_AMOUNT)))
                .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.toString())))
                .andExpect(jsonPath("$.[*].dateOfTransaction").value(hasItem(DEFAULT_DATE_OF_TRANSACTION_STR)))
                .andExpect(jsonPath("$.[*].isVoucherValid").value(hasItem(DEFAULT_IS_VOUCHER_VALID.booleanValue())))
                .andExpect(jsonPath("$.[*].voucherValidity").value(hasItem(DEFAULT_VOUCHER_VALIDITY_STR)));
    }

    @Test
    @Transactional
    public void getGiftAnExperience() throws Exception {
        // Initialize the database
        giftAnExperienceRepository.saveAndFlush(giftAnExperience);

        // Get the giftAnExperience
        restGiftAnExperienceMockMvc.perform(get("/api/giftAnExperiences/{id}", giftAnExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(giftAnExperience.getId().intValue()))
            .andExpect(jsonPath("$.receiverName").value(DEFAULT_RECEIVER_NAME.toString()))
            .andExpect(jsonPath("$.receiverMail").value(DEFAULT_RECEIVER_MAIL.toString()))
            .andExpect(jsonPath("$.senderName").value(DEFAULT_SENDER_NAME.toString()))
            .andExpect(jsonPath("$.senderMail").value(DEFAULT_SENDER_MAIL.toString()))
            .andExpect(jsonPath("$.senderPhone").value(DEFAULT_SENDER_PHONE.toString()))
            .andExpect(jsonPath("$.giftMessage").value(DEFAULT_GIFT_MESSAGE.toString()))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_VOUCHER_CODE.toString()))
            .andExpect(jsonPath("$.voucherAmount").value(DEFAULT_VOUCHER_AMOUNT))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.toString()))
            .andExpect(jsonPath("$.dateOfTransaction").value(DEFAULT_DATE_OF_TRANSACTION_STR))
            .andExpect(jsonPath("$.isVoucherValid").value(DEFAULT_IS_VOUCHER_VALID.booleanValue()))
            .andExpect(jsonPath("$.voucherValidity").value(DEFAULT_VOUCHER_VALIDITY_STR));
    }

    @Test
    @Transactional
    public void getNonExistingGiftAnExperience() throws Exception {
        // Get the giftAnExperience
        restGiftAnExperienceMockMvc.perform(get("/api/giftAnExperiences/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiftAnExperience() throws Exception {
        // Initialize the database
        giftAnExperienceRepository.saveAndFlush(giftAnExperience);
		
		int databaseSizeBeforeUpdate = giftAnExperienceRepository.findAll().size();

        // Update the giftAnExperience
        giftAnExperience.setReceiverName(UPDATED_RECEIVER_NAME);
        giftAnExperience.setReceiverMail(UPDATED_RECEIVER_MAIL);
        giftAnExperience.setSenderName(UPDATED_SENDER_NAME);
        giftAnExperience.setSenderMail(UPDATED_SENDER_MAIL);
        giftAnExperience.setSenderPhone(UPDATED_SENDER_PHONE);
        giftAnExperience.setGiftMessage(UPDATED_GIFT_MESSAGE);
        giftAnExperience.setVoucherCode(UPDATED_VOUCHER_CODE);
        giftAnExperience.setVoucherAmount(UPDATED_VOUCHER_AMOUNT);
        giftAnExperience.setTransactionId(UPDATED_TRANSACTION_ID);
        giftAnExperience.setDateOfTransaction(UPDATED_DATE_OF_TRANSACTION);
        giftAnExperience.setIsVoucherValid(UPDATED_IS_VOUCHER_VALID);
        giftAnExperience.setVoucherValidity(UPDATED_VOUCHER_VALIDITY);
        restGiftAnExperienceMockMvc.perform(put("/api/giftAnExperiences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(giftAnExperience)))
                .andExpect(status().isOk());

        // Validate the GiftAnExperience in the database
        List<GiftAnExperience> giftAnExperiences = giftAnExperienceRepository.findAll();
        assertThat(giftAnExperiences).hasSize(databaseSizeBeforeUpdate);
        GiftAnExperience testGiftAnExperience = giftAnExperiences.get(giftAnExperiences.size() - 1);
        assertThat(testGiftAnExperience.getReceiverName()).isEqualTo(UPDATED_RECEIVER_NAME);
        assertThat(testGiftAnExperience.getReceiverMail()).isEqualTo(UPDATED_RECEIVER_MAIL);
        assertThat(testGiftAnExperience.getSenderName()).isEqualTo(UPDATED_SENDER_NAME);
        assertThat(testGiftAnExperience.getSenderMail()).isEqualTo(UPDATED_SENDER_MAIL);
        assertThat(testGiftAnExperience.getSenderPhone()).isEqualTo(UPDATED_SENDER_PHONE);
        assertThat(testGiftAnExperience.getGiftMessage()).isEqualTo(UPDATED_GIFT_MESSAGE);
        assertThat(testGiftAnExperience.getVoucherCode()).isEqualTo(UPDATED_VOUCHER_CODE);
        assertThat(testGiftAnExperience.getVoucherAmount()).isEqualTo(UPDATED_VOUCHER_AMOUNT);
        assertThat(testGiftAnExperience.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testGiftAnExperience.getDateOfTransaction().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE_OF_TRANSACTION);
        assertThat(testGiftAnExperience.getIsVoucherValid()).isEqualTo(UPDATED_IS_VOUCHER_VALID);
        assertThat(testGiftAnExperience.getVoucherValidity().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_VOUCHER_VALIDITY);
    }

    @Test
    @Transactional
    public void deleteGiftAnExperience() throws Exception {
        // Initialize the database
        giftAnExperienceRepository.saveAndFlush(giftAnExperience);
		
		int databaseSizeBeforeDelete = giftAnExperienceRepository.findAll().size();

        // Get the giftAnExperience
        restGiftAnExperienceMockMvc.perform(delete("/api/giftAnExperiences/{id}", giftAnExperience.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GiftAnExperience> giftAnExperiences = giftAnExperienceRepository.findAll();
        assertThat(giftAnExperiences).hasSize(databaseSizeBeforeDelete - 1);
    }
}
