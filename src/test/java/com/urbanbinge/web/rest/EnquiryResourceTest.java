package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.Enquiry;
import com.urbanbinge.repository.EnquiryRepository;

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
 * Test class for the EnquiryResource REST controller.
 *
 * @see EnquiryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EnquiryResourceTest {


    private static final Long DEFAULT_EVENT_ID = 0L;
    private static final Long UPDATED_EVENT_ID = 1L;
    private static final String DEFAULT_CUSTOMER_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_CUSTOMER_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_ENQUIRY_MESSAGE = "SAMPLE_TEXT";
    private static final String UPDATED_ENQUIRY_MESSAGE = "UPDATED_TEXT";
    private static final String DEFAULT_MOBILE_NO = "SAMPLE_TEXT";
    private static final String UPDATED_MOBILE_NO = "UPDATED_TEXT";
    private static final String DEFAULT_TICKE_NAMES = "SAMPLE_TEXT";
    private static final String UPDATED_TICKE_NAMES = "UPDATED_TEXT";

    @Inject
    private EnquiryRepository enquiryRepository;

    private MockMvc restEnquiryMockMvc;

    private Enquiry enquiry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnquiryResource enquiryResource = new EnquiryResource();
        ReflectionTestUtils.setField(enquiryResource, "enquiryRepository", enquiryRepository);
        this.restEnquiryMockMvc = MockMvcBuilders.standaloneSetup(enquiryResource).build();
    }

    @Before
    public void initTest() {
        enquiry = new Enquiry();
        enquiry.setEventId(DEFAULT_EVENT_ID);
        enquiry.setCustomerName(DEFAULT_CUSTOMER_NAME);
        enquiry.setEmail(DEFAULT_EMAIL);
        enquiry.setEnquiry_message(DEFAULT_ENQUIRY_MESSAGE);
        enquiry.setMobileNo(DEFAULT_MOBILE_NO);
        enquiry.setTickeNames(DEFAULT_TICKE_NAMES);
    }

    @Test
    @Transactional
    public void createEnquiry() throws Exception {
        int databaseSizeBeforeCreate = enquiryRepository.findAll().size();

        // Create the Enquiry
        restEnquiryMockMvc.perform(post("/api/enquirys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquiry)))
                .andExpect(status().isCreated());

        // Validate the Enquiry in the database
        List<Enquiry> enquirys = enquiryRepository.findAll();
        assertThat(enquirys).hasSize(databaseSizeBeforeCreate + 1);
        Enquiry testEnquiry = enquirys.get(enquirys.size() - 1);
        assertThat(testEnquiry.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testEnquiry.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testEnquiry.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEnquiry.getEnquiry_message()).isEqualTo(DEFAULT_ENQUIRY_MESSAGE);
        assertThat(testEnquiry.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testEnquiry.getTickeNames()).isEqualTo(DEFAULT_TICKE_NAMES);
    }

    @Test
    @Transactional
    public void getAllEnquirys() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquirys
        restEnquiryMockMvc.perform(get("/api/enquirys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(enquiry.getId().intValue())))
                .andExpect(jsonPath("$.[*].eventId").value(hasItem(DEFAULT_EVENT_ID.intValue())))
                .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].enquiry_message").value(hasItem(DEFAULT_ENQUIRY_MESSAGE.toString())))
                .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
                .andExpect(jsonPath("$.[*].tickeNames").value(hasItem(DEFAULT_TICKE_NAMES.toString())));
    }

    @Test
    @Transactional
    public void getEnquiry() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get the enquiry
        restEnquiryMockMvc.perform(get("/api/enquirys/{id}", enquiry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(enquiry.getId().intValue()))
            .andExpect(jsonPath("$.eventId").value(DEFAULT_EVENT_ID.intValue()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.enquiry_message").value(DEFAULT_ENQUIRY_MESSAGE.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.tickeNames").value(DEFAULT_TICKE_NAMES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnquiry() throws Exception {
        // Get the enquiry
        restEnquiryMockMvc.perform(get("/api/enquirys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquiry() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);
		
		int databaseSizeBeforeUpdate = enquiryRepository.findAll().size();

        // Update the enquiry
        enquiry.setEventId(UPDATED_EVENT_ID);
        enquiry.setCustomerName(UPDATED_CUSTOMER_NAME);
        enquiry.setEmail(UPDATED_EMAIL);
        enquiry.setEnquiry_message(UPDATED_ENQUIRY_MESSAGE);
        enquiry.setMobileNo(UPDATED_MOBILE_NO);
        enquiry.setTickeNames(UPDATED_TICKE_NAMES);
        restEnquiryMockMvc.perform(put("/api/enquirys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquiry)))
                .andExpect(status().isOk());

        // Validate the Enquiry in the database
        List<Enquiry> enquirys = enquiryRepository.findAll();
        assertThat(enquirys).hasSize(databaseSizeBeforeUpdate);
        Enquiry testEnquiry = enquirys.get(enquirys.size() - 1);
        assertThat(testEnquiry.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testEnquiry.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testEnquiry.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEnquiry.getEnquiry_message()).isEqualTo(UPDATED_ENQUIRY_MESSAGE);
        assertThat(testEnquiry.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testEnquiry.getTickeNames()).isEqualTo(UPDATED_TICKE_NAMES);
    }

    @Test
    @Transactional
    public void deleteEnquiry() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);
		
		int databaseSizeBeforeDelete = enquiryRepository.findAll().size();

        // Get the enquiry
        restEnquiryMockMvc.perform(delete("/api/enquirys/{id}", enquiry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Enquiry> enquirys = enquiryRepository.findAll();
        assertThat(enquirys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
