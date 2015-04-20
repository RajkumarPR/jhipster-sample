package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.EventCategory;
import com.urbanbinge.repository.EventCategoryRepository;

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
 * Test class for the EventCategoryResource REST controller.
 *
 * @see EventCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventCategoryResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_CATEGORYNAME = "SAMPLE_TEXT";
    private static final String UPDATED_CATEGORYNAME = "UPDATED_TEXT";
    private static final String DEFAULT_CATEGORYDESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_CATEGORYDESCRIPTION = "UPDATED_TEXT";

    private static final DateTime DEFAULT_LAST_MODIFIED_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_LAST_MODIFIED_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.print(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private EventCategoryRepository eventCategoryRepository;

    private MockMvc restEventCategoryMockMvc;

    private EventCategory eventCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventCategoryResource eventCategoryResource = new EventCategoryResource();
        ReflectionTestUtils.setField(eventCategoryResource, "eventCategoryRepository", eventCategoryRepository);
        this.restEventCategoryMockMvc = MockMvcBuilders.standaloneSetup(eventCategoryResource).build();
    }

    @Before
    public void initTest() {
        eventCategory = new EventCategory();
        eventCategory.setCategoryname(DEFAULT_CATEGORYNAME);
        eventCategory.setCategorydescription(DEFAULT_CATEGORYDESCRIPTION);
        eventCategory.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createEventCategory() throws Exception {
        int databaseSizeBeforeCreate = eventCategoryRepository.findAll().size();

        // Create the EventCategory
        restEventCategoryMockMvc.perform(post("/api/eventCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventCategory)))
                .andExpect(status().isCreated());

        // Validate the EventCategory in the database
        List<EventCategory> eventCategorys = eventCategoryRepository.findAll();
        assertThat(eventCategorys).hasSize(databaseSizeBeforeCreate + 1);
        EventCategory testEventCategory = eventCategorys.get(eventCategorys.size() - 1);
        assertThat(testEventCategory.getCategoryname()).isEqualTo(DEFAULT_CATEGORYNAME);
        assertThat(testEventCategory.getCategorydescription()).isEqualTo(DEFAULT_CATEGORYDESCRIPTION);
        assertThat(testEventCategory.getLastModifiedDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllEventCategorys() throws Exception {
        // Initialize the database
        eventCategoryRepository.saveAndFlush(eventCategory);

        // Get all the eventCategorys
        restEventCategoryMockMvc.perform(get("/api/eventCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eventCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].categoryname").value(hasItem(DEFAULT_CATEGORYNAME.toString())))
                .andExpect(jsonPath("$.[*].categorydescription").value(hasItem(DEFAULT_CATEGORYDESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getEventCategory() throws Exception {
        // Initialize the database
        eventCategoryRepository.saveAndFlush(eventCategory);

        // Get the eventCategory
        restEventCategoryMockMvc.perform(get("/api/eventCategorys/{id}", eventCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(eventCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryname").value(DEFAULT_CATEGORYNAME.toString()))
            .andExpect(jsonPath("$.categorydescription").value(DEFAULT_CATEGORYDESCRIPTION.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingEventCategory() throws Exception {
        // Get the eventCategory
        restEventCategoryMockMvc.perform(get("/api/eventCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventCategory() throws Exception {
        // Initialize the database
        eventCategoryRepository.saveAndFlush(eventCategory);
		
		int databaseSizeBeforeUpdate = eventCategoryRepository.findAll().size();

        // Update the eventCategory
        eventCategory.setCategoryname(UPDATED_CATEGORYNAME);
        eventCategory.setCategorydescription(UPDATED_CATEGORYDESCRIPTION);
        eventCategory.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restEventCategoryMockMvc.perform(put("/api/eventCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventCategory)))
                .andExpect(status().isOk());

        // Validate the EventCategory in the database
        List<EventCategory> eventCategorys = eventCategoryRepository.findAll();
        assertThat(eventCategorys).hasSize(databaseSizeBeforeUpdate);
        EventCategory testEventCategory = eventCategorys.get(eventCategorys.size() - 1);
        assertThat(testEventCategory.getCategoryname()).isEqualTo(UPDATED_CATEGORYNAME);
        assertThat(testEventCategory.getCategorydescription()).isEqualTo(UPDATED_CATEGORYDESCRIPTION);
        assertThat(testEventCategory.getLastModifiedDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteEventCategory() throws Exception {
        // Initialize the database
        eventCategoryRepository.saveAndFlush(eventCategory);
		
		int databaseSizeBeforeDelete = eventCategoryRepository.findAll().size();

        // Get the eventCategory
        restEventCategoryMockMvc.perform(delete("/api/eventCategorys/{id}", eventCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EventCategory> eventCategorys = eventCategoryRepository.findAll();
        assertThat(eventCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
