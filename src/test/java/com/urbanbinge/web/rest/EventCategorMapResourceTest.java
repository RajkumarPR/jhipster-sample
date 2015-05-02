package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.EventCategorMap;
import com.urbanbinge.repository.EventCategorMapRepository;

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
 * Test class for the EventCategorMapResource REST controller.
 *
 * @see EventCategorMapResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventCategorMapResourceTest {


    @Inject
    private EventCategorMapRepository eventCategorMapRepository;

    private MockMvc restEventCategorMapMockMvc;

    private EventCategorMap eventCategorMap;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventCategorMapResource eventCategorMapResource = new EventCategorMapResource();
        ReflectionTestUtils.setField(eventCategorMapResource, "eventCategorMapRepository", eventCategorMapRepository);
        this.restEventCategorMapMockMvc = MockMvcBuilders.standaloneSetup(eventCategorMapResource).build();
    }

    @Before
    public void initTest() {
        eventCategorMap = new EventCategorMap();
    }

    @Test
    @Transactional
    public void createEventCategorMap() throws Exception {
        int databaseSizeBeforeCreate = eventCategorMapRepository.findAll().size();

        // Create the EventCategorMap
        restEventCategorMapMockMvc.perform(post("/api/eventCategorMaps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventCategorMap)))
                .andExpect(status().isCreated());

        // Validate the EventCategorMap in the database
        List<EventCategorMap> eventCategorMaps = eventCategorMapRepository.findAll();
        assertThat(eventCategorMaps).hasSize(databaseSizeBeforeCreate + 1);
        EventCategorMap testEventCategorMap = eventCategorMaps.get(eventCategorMaps.size() - 1);
    }

    @Test
    @Transactional
    public void getAllEventCategorMaps() throws Exception {
        // Initialize the database
        eventCategorMapRepository.saveAndFlush(eventCategorMap);

        // Get all the eventCategorMaps
        restEventCategorMapMockMvc.perform(get("/api/eventCategorMaps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eventCategorMap.getId().intValue())));
    }

    @Test
    @Transactional
    public void getEventCategorMap() throws Exception {
        // Initialize the database
        eventCategorMapRepository.saveAndFlush(eventCategorMap);

        // Get the eventCategorMap
        restEventCategorMapMockMvc.perform(get("/api/eventCategorMaps/{id}", eventCategorMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(eventCategorMap.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEventCategorMap() throws Exception {
        // Get the eventCategorMap
        restEventCategorMapMockMvc.perform(get("/api/eventCategorMaps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventCategorMap() throws Exception {
        // Initialize the database
        eventCategorMapRepository.saveAndFlush(eventCategorMap);
		
		int databaseSizeBeforeUpdate = eventCategorMapRepository.findAll().size();

        // Update the eventCategorMap
        restEventCategorMapMockMvc.perform(put("/api/eventCategorMaps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventCategorMap)))
                .andExpect(status().isOk());

        // Validate the EventCategorMap in the database
        List<EventCategorMap> eventCategorMaps = eventCategorMapRepository.findAll();
        assertThat(eventCategorMaps).hasSize(databaseSizeBeforeUpdate);
        EventCategorMap testEventCategorMap = eventCategorMaps.get(eventCategorMaps.size() - 1);
    }

    @Test
    @Transactional
    public void deleteEventCategorMap() throws Exception {
        // Initialize the database
        eventCategorMapRepository.saveAndFlush(eventCategorMap);
		
		int databaseSizeBeforeDelete = eventCategorMapRepository.findAll().size();

        // Get the eventCategorMap
        restEventCategorMapMockMvc.perform(delete("/api/eventCategorMaps/{id}", eventCategorMap.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EventCategorMap> eventCategorMaps = eventCategorMapRepository.findAll();
        assertThat(eventCategorMaps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
