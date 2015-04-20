package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.EventLocation;
import com.urbanbinge.repository.EventLocationRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EventLocationResource REST controller.
 *
 * @see EventLocationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventLocationResourceTest {

    private static final String DEFAULT_LOCATION = "SAMPLE_TEXT";
    private static final String UPDATED_LOCATION = "UPDATED_TEXT";
    private static final String DEFAULT_EVENT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_EVENT_ADDRESS = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_LATITUDE = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_LATITUDE = BigDecimal.ONE;

    private static final BigDecimal DEFAULT_LONGITUDE = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_LONGITUDE = BigDecimal.ONE;

    @Inject
    private EventLocationRepository eventLocationRepository;

    private MockMvc restEventLocationMockMvc;

    private EventLocation eventLocation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventLocationResource eventLocationResource = new EventLocationResource();
        ReflectionTestUtils.setField(eventLocationResource, "eventLocationRepository", eventLocationRepository);
        this.restEventLocationMockMvc = MockMvcBuilders.standaloneSetup(eventLocationResource).build();
    }

    @Before
    public void initTest() {
        eventLocation = new EventLocation();
        eventLocation.setLocation(DEFAULT_LOCATION);
        eventLocation.setEventAddress(DEFAULT_EVENT_ADDRESS);
        eventLocation.setLatitude(DEFAULT_LATITUDE);
        eventLocation.setLongitude(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createEventLocation() throws Exception {
        int databaseSizeBeforeCreate = eventLocationRepository.findAll().size();

        // Create the EventLocation
        restEventLocationMockMvc.perform(post("/api/eventLocations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventLocation)))
                .andExpect(status().isCreated());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocations = eventLocationRepository.findAll();
        assertThat(eventLocations).hasSize(databaseSizeBeforeCreate + 1);
        EventLocation testEventLocation = eventLocations.get(eventLocations.size() - 1);
        assertThat(testEventLocation.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testEventLocation.getEventAddress()).isEqualTo(DEFAULT_EVENT_ADDRESS);
        assertThat(testEventLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testEventLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllEventLocations() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        // Get all the eventLocations
        restEventLocationMockMvc.perform(get("/api/eventLocations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eventLocation.getId().intValue())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].eventAddress").value(hasItem(DEFAULT_EVENT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())));
    }

    @Test
    @Transactional
    public void getEventLocation() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        // Get the eventLocation
        restEventLocationMockMvc.perform(get("/api/eventLocations/{id}", eventLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(eventLocation.getId().intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.eventAddress").value(DEFAULT_EVENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEventLocation() throws Exception {
        // Get the eventLocation
        restEventLocationMockMvc.perform(get("/api/eventLocations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventLocation() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);
		
		int databaseSizeBeforeUpdate = eventLocationRepository.findAll().size();

        // Update the eventLocation
        eventLocation.setLocation(UPDATED_LOCATION);
        eventLocation.setEventAddress(UPDATED_EVENT_ADDRESS);
        eventLocation.setLatitude(UPDATED_LATITUDE);
        eventLocation.setLongitude(UPDATED_LONGITUDE);
        restEventLocationMockMvc.perform(put("/api/eventLocations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventLocation)))
                .andExpect(status().isOk());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocations = eventLocationRepository.findAll();
        assertThat(eventLocations).hasSize(databaseSizeBeforeUpdate);
        EventLocation testEventLocation = eventLocations.get(eventLocations.size() - 1);
        assertThat(testEventLocation.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testEventLocation.getEventAddress()).isEqualTo(UPDATED_EVENT_ADDRESS);
        assertThat(testEventLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEventLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void deleteEventLocation() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);
		
		int databaseSizeBeforeDelete = eventLocationRepository.findAll().size();

        // Get the eventLocation
        restEventLocationMockMvc.perform(delete("/api/eventLocations/{id}", eventLocation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EventLocation> eventLocations = eventLocationRepository.findAll();
        assertThat(eventLocations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
