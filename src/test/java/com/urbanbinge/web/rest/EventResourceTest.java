package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.Event;
import com.urbanbinge.repository.EventRepository;

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
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_EVENT_VENUE = "SAMPLE_TEXT";
    private static final String UPDATED_EVENT_VENUE = "UPDATED_TEXT";
    private static final String DEFAULT_COST_INCLUDES = "SAMPLE_TEXT";
    private static final String UPDATED_COST_INCLUDES = "UPDATED_TEXT";
    private static final String DEFAULT_SPECIAL_INSTRUCTIONS = "SAMPLE_TEXT";
    private static final String UPDATED_SPECIAL_INSTRUCTIONS = "UPDATED_TEXT";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_BOOKABLE = false;
    private static final Boolean UPDATED_IS_BOOKABLE = true;

    private static final Boolean DEFAULT_IS_CLOSED = false;
    private static final Boolean UPDATED_IS_CLOSED = true;

    private static final Boolean DEFAULT_IS_ONLY_ENQUIRY = false;
    private static final Boolean UPDATED_IS_ONLY_ENQUIRY = true;

    private static final DateTime DEFAULT_START_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_START_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.print(DEFAULT_START_DATE);

    private static final DateTime DEFAULT_END_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_END_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.print(DEFAULT_END_DATE);
    private static final String DEFAULT_EVENT_CONTACT = "SAMPLE_TEXT";
    private static final String UPDATED_EVENT_CONTACT = "UPDATED_TEXT";

    private static final Integer DEFAULT_EVENT_TYPE = 0;
    private static final Integer UPDATED_EVENT_TYPE = 1;
    private static final String DEFAULT_VIDEO_LINK = "SAMPLE_TEXT";
    private static final String UPDATED_VIDEO_LINK = "UPDATED_TEXT";

    @Inject
    private EventRepository eventRepository;

    private MockMvc restEventMockMvc;

    private Event event;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventResource eventResource = new EventResource();
        ReflectionTestUtils.setField(eventResource, "eventRepository", eventRepository);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource).build();
    }

    @Before
    public void initTest() {
        event = new Event();
        event.setTitle(DEFAULT_TITLE);
        event.setDescription(DEFAULT_DESCRIPTION);
        event.setEventVenue(DEFAULT_EVENT_VENUE);
        event.setCostIncludes(DEFAULT_COST_INCLUDES);
        event.setSpecialInstructions(DEFAULT_SPECIAL_INSTRUCTIONS);
        event.setIsActive(DEFAULT_IS_ACTIVE);
        event.setIsApproved(DEFAULT_IS_APPROVED);
        event.setIsBookable(DEFAULT_IS_BOOKABLE);
        event.setIsClosed(DEFAULT_IS_CLOSED);
        event.setIsOnlyEnquiry(DEFAULT_IS_ONLY_ENQUIRY);
        event.setStartDate(DEFAULT_START_DATE);
        event.setEndDate(DEFAULT_END_DATE);
        event.setEventContact(DEFAULT_EVENT_CONTACT);
        event.setEventType(DEFAULT_EVENT_TYPE);
        event.setVideoLink(DEFAULT_VIDEO_LINK);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        restEventMockMvc.perform(post("/api/events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(event)))
                .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = events.get(events.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getEventVenue()).isEqualTo(DEFAULT_EVENT_VENUE);
        assertThat(testEvent.getCostIncludes()).isEqualTo(DEFAULT_COST_INCLUDES);
        assertThat(testEvent.getSpecialInstructions()).isEqualTo(DEFAULT_SPECIAL_INSTRUCTIONS);
        assertThat(testEvent.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testEvent.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testEvent.getIsBookable()).isEqualTo(DEFAULT_IS_BOOKABLE);
        assertThat(testEvent.getIsClosed()).isEqualTo(DEFAULT_IS_CLOSED);
        assertThat(testEvent.getIsOnlyEnquiry()).isEqualTo(DEFAULT_IS_ONLY_ENQUIRY);
        assertThat(testEvent.getStartDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEvent.getEndDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEvent.getEventContact()).isEqualTo(DEFAULT_EVENT_CONTACT);
        assertThat(testEvent.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testEvent.getVideoLink()).isEqualTo(DEFAULT_VIDEO_LINK);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the events
        restEventMockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].eventVenue").value(hasItem(DEFAULT_EVENT_VENUE.toString())))
                .andExpect(jsonPath("$.[*].costIncludes").value(hasItem(DEFAULT_COST_INCLUDES.toString())))
                .andExpect(jsonPath("$.[*].specialInstructions").value(hasItem(DEFAULT_SPECIAL_INSTRUCTIONS.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
                .andExpect(jsonPath("$.[*].isBookable").value(hasItem(DEFAULT_IS_BOOKABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].isClosed").value(hasItem(DEFAULT_IS_CLOSED.booleanValue())))
                .andExpect(jsonPath("$.[*].isOnlyEnquiry").value(hasItem(DEFAULT_IS_ONLY_ENQUIRY.booleanValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].eventContact").value(hasItem(DEFAULT_EVENT_CONTACT.toString())))
                .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
                .andExpect(jsonPath("$.[*].videoLink").value(hasItem(DEFAULT_VIDEO_LINK.toString())));
    }

    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.eventVenue").value(DEFAULT_EVENT_VENUE.toString()))
            .andExpect(jsonPath("$.costIncludes").value(DEFAULT_COST_INCLUDES.toString()))
            .andExpect(jsonPath("$.specialInstructions").value(DEFAULT_SPECIAL_INSTRUCTIONS.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isBookable").value(DEFAULT_IS_BOOKABLE.booleanValue()))
            .andExpect(jsonPath("$.isClosed").value(DEFAULT_IS_CLOSED.booleanValue()))
            .andExpect(jsonPath("$.isOnlyEnquiry").value(DEFAULT_IS_ONLY_ENQUIRY.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.eventContact").value(DEFAULT_EVENT_CONTACT.toString()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE))
            .andExpect(jsonPath("$.videoLink").value(DEFAULT_VIDEO_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);
		
		int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        event.setTitle(UPDATED_TITLE);
        event.setDescription(UPDATED_DESCRIPTION);
        event.setEventVenue(UPDATED_EVENT_VENUE);
        event.setCostIncludes(UPDATED_COST_INCLUDES);
        event.setSpecialInstructions(UPDATED_SPECIAL_INSTRUCTIONS);
        event.setIsActive(UPDATED_IS_ACTIVE);
        event.setIsApproved(UPDATED_IS_APPROVED);
        event.setIsBookable(UPDATED_IS_BOOKABLE);
        event.setIsClosed(UPDATED_IS_CLOSED);
        event.setIsOnlyEnquiry(UPDATED_IS_ONLY_ENQUIRY);
        event.setStartDate(UPDATED_START_DATE);
        event.setEndDate(UPDATED_END_DATE);
        event.setEventContact(UPDATED_EVENT_CONTACT);
        event.setEventType(UPDATED_EVENT_TYPE);
        event.setVideoLink(UPDATED_VIDEO_LINK);
        restEventMockMvc.perform(put("/api/events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(event)))
                .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = events.get(events.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getEventVenue()).isEqualTo(UPDATED_EVENT_VENUE);
        assertThat(testEvent.getCostIncludes()).isEqualTo(UPDATED_COST_INCLUDES);
        assertThat(testEvent.getSpecialInstructions()).isEqualTo(UPDATED_SPECIAL_INSTRUCTIONS);
        assertThat(testEvent.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEvent.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testEvent.getIsBookable()).isEqualTo(UPDATED_IS_BOOKABLE);
        assertThat(testEvent.getIsClosed()).isEqualTo(UPDATED_IS_CLOSED);
        assertThat(testEvent.getIsOnlyEnquiry()).isEqualTo(UPDATED_IS_ONLY_ENQUIRY);
        assertThat(testEvent.getStartDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_START_DATE);
        assertThat(testEvent.getEndDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_END_DATE);
        assertThat(testEvent.getEventContact()).isEqualTo(UPDATED_EVENT_CONTACT);
        assertThat(testEvent.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testEvent.getVideoLink()).isEqualTo(UPDATED_VIDEO_LINK);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);
		
		int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Get the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(databaseSizeBeforeDelete - 1);
    }
}
