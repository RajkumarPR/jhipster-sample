package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.EventTicket;
import com.urbanbinge.repository.EventTicketRepository;

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
 * Test class for the EventTicketResource REST controller.
 *
 * @see EventTicketResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventTicketResourceTest {


    private static final Integer DEFAULT_TICKET_COST = 0;
    private static final Integer UPDATED_TICKET_COST = 1;

    private static final Integer DEFAULT_TOTAL_TICKETS = 0;
    private static final Integer UPDATED_TOTAL_TICKETS = 1;

    private static final Integer DEFAULT_DISCOUNT = 0;
    private static final Integer UPDATED_DISCOUNT = 1;

    private static final Boolean DEFAULT_HAS_OFFER = false;
    private static final Boolean UPDATED_HAS_OFFER = true;

    private static final Integer DEFAULT_MINIMUN_TICKET = 0;
    private static final Integer UPDATED_MINIMUN_TICKET = 1;

    private static final Integer DEFAULT_TICKET_TYPE = 0;
    private static final Integer UPDATED_TICKET_TYPE = 1;
    private static final String DEFAULT_TICKET_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_TICKET_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_START_TIME = "SAMPLE_TEXT";
    private static final String UPDATED_START_TIME = "UPDATED_TEXT";
    private static final String DEFAULT_END_TIME = "SAMPLE_TEXT";
    private static final String UPDATED_END_TIME = "UPDATED_TEXT";
    private static final String DEFAULT_DURATION = "SAMPLE_TEXT";
    private static final String UPDATED_DURATION = "UPDATED_TEXT";

    @Inject
    private EventTicketRepository eventTicketRepository;

    private MockMvc restEventTicketMockMvc;

    private EventTicket eventTicket;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventTicketResource eventTicketResource = new EventTicketResource();
        ReflectionTestUtils.setField(eventTicketResource, "eventTicketRepository", eventTicketRepository);
        this.restEventTicketMockMvc = MockMvcBuilders.standaloneSetup(eventTicketResource).build();
    }

    @Before
    public void initTest() {
        eventTicket = new EventTicket();
        eventTicket.setTicketCost(DEFAULT_TICKET_COST);
        eventTicket.setTotalTickets(DEFAULT_TOTAL_TICKETS);
        eventTicket.setDiscount(DEFAULT_DISCOUNT);
        eventTicket.setHasOffer(DEFAULT_HAS_OFFER);
        eventTicket.setMinimunTicket(DEFAULT_MINIMUN_TICKET);
        eventTicket.setTicketType(DEFAULT_TICKET_TYPE);
        eventTicket.setTicketName(DEFAULT_TICKET_NAME);
        eventTicket.setStartTime(DEFAULT_START_TIME);
        eventTicket.setEndTime(DEFAULT_END_TIME);
        eventTicket.setDuration(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createEventTicket() throws Exception {
        int databaseSizeBeforeCreate = eventTicketRepository.findAll().size();

        // Create the EventTicket
        restEventTicketMockMvc.perform(post("/api/eventTickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventTicket)))
                .andExpect(status().isCreated());

        // Validate the EventTicket in the database
        List<EventTicket> eventTickets = eventTicketRepository.findAll();
        assertThat(eventTickets).hasSize(databaseSizeBeforeCreate + 1);
        EventTicket testEventTicket = eventTickets.get(eventTickets.size() - 1);
        assertThat(testEventTicket.getTicketCost()).isEqualTo(DEFAULT_TICKET_COST);
        assertThat(testEventTicket.getTotalTickets()).isEqualTo(DEFAULT_TOTAL_TICKETS);
        assertThat(testEventTicket.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testEventTicket.getHasOffer()).isEqualTo(DEFAULT_HAS_OFFER);
        assertThat(testEventTicket.getMinimunTicket()).isEqualTo(DEFAULT_MINIMUN_TICKET);
        assertThat(testEventTicket.getTicketType()).isEqualTo(DEFAULT_TICKET_TYPE);
        assertThat(testEventTicket.getTicketName()).isEqualTo(DEFAULT_TICKET_NAME);
        assertThat(testEventTicket.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testEventTicket.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testEventTicket.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void getAllEventTickets() throws Exception {
        // Initialize the database
        eventTicketRepository.saveAndFlush(eventTicket);

        // Get all the eventTickets
        restEventTicketMockMvc.perform(get("/api/eventTickets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eventTicket.getId().intValue())))
                .andExpect(jsonPath("$.[*].ticketCost").value(hasItem(DEFAULT_TICKET_COST)))
                .andExpect(jsonPath("$.[*].totalTickets").value(hasItem(DEFAULT_TOTAL_TICKETS)))
                .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
                .andExpect(jsonPath("$.[*].hasOffer").value(hasItem(DEFAULT_HAS_OFFER.booleanValue())))
                .andExpect(jsonPath("$.[*].minimunTicket").value(hasItem(DEFAULT_MINIMUN_TICKET)))
                .andExpect(jsonPath("$.[*].ticketType").value(hasItem(DEFAULT_TICKET_TYPE)))
                .andExpect(jsonPath("$.[*].ticketName").value(hasItem(DEFAULT_TICKET_NAME.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())));
    }

    @Test
    @Transactional
    public void getEventTicket() throws Exception {
        // Initialize the database
        eventTicketRepository.saveAndFlush(eventTicket);

        // Get the eventTicket
        restEventTicketMockMvc.perform(get("/api/eventTickets/{id}", eventTicket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(eventTicket.getId().intValue()))
            .andExpect(jsonPath("$.ticketCost").value(DEFAULT_TICKET_COST))
            .andExpect(jsonPath("$.totalTickets").value(DEFAULT_TOTAL_TICKETS))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT))
            .andExpect(jsonPath("$.hasOffer").value(DEFAULT_HAS_OFFER.booleanValue()))
            .andExpect(jsonPath("$.minimunTicket").value(DEFAULT_MINIMUN_TICKET))
            .andExpect(jsonPath("$.ticketType").value(DEFAULT_TICKET_TYPE))
            .andExpect(jsonPath("$.ticketName").value(DEFAULT_TICKET_NAME.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEventTicket() throws Exception {
        // Get the eventTicket
        restEventTicketMockMvc.perform(get("/api/eventTickets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventTicket() throws Exception {
        // Initialize the database
        eventTicketRepository.saveAndFlush(eventTicket);
		
		int databaseSizeBeforeUpdate = eventTicketRepository.findAll().size();

        // Update the eventTicket
        eventTicket.setTicketCost(UPDATED_TICKET_COST);
        eventTicket.setTotalTickets(UPDATED_TOTAL_TICKETS);
        eventTicket.setDiscount(UPDATED_DISCOUNT);
        eventTicket.setHasOffer(UPDATED_HAS_OFFER);
        eventTicket.setMinimunTicket(UPDATED_MINIMUN_TICKET);
        eventTicket.setTicketType(UPDATED_TICKET_TYPE);
        eventTicket.setTicketName(UPDATED_TICKET_NAME);
        eventTicket.setStartTime(UPDATED_START_TIME);
        eventTicket.setEndTime(UPDATED_END_TIME);
        eventTicket.setDuration(UPDATED_DURATION);
        restEventTicketMockMvc.perform(put("/api/eventTickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventTicket)))
                .andExpect(status().isOk());

        // Validate the EventTicket in the database
        List<EventTicket> eventTickets = eventTicketRepository.findAll();
        assertThat(eventTickets).hasSize(databaseSizeBeforeUpdate);
        EventTicket testEventTicket = eventTickets.get(eventTickets.size() - 1);
        assertThat(testEventTicket.getTicketCost()).isEqualTo(UPDATED_TICKET_COST);
        assertThat(testEventTicket.getTotalTickets()).isEqualTo(UPDATED_TOTAL_TICKETS);
        assertThat(testEventTicket.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testEventTicket.getHasOffer()).isEqualTo(UPDATED_HAS_OFFER);
        assertThat(testEventTicket.getMinimunTicket()).isEqualTo(UPDATED_MINIMUN_TICKET);
        assertThat(testEventTicket.getTicketType()).isEqualTo(UPDATED_TICKET_TYPE);
        assertThat(testEventTicket.getTicketName()).isEqualTo(UPDATED_TICKET_NAME);
        assertThat(testEventTicket.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testEventTicket.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testEventTicket.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void deleteEventTicket() throws Exception {
        // Initialize the database
        eventTicketRepository.saveAndFlush(eventTicket);
		
		int databaseSizeBeforeDelete = eventTicketRepository.findAll().size();

        // Get the eventTicket
        restEventTicketMockMvc.perform(delete("/api/eventTickets/{id}", eventTicket.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EventTicket> eventTickets = eventTicketRepository.findAll();
        assertThat(eventTickets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
