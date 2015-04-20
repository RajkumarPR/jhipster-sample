package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.Rssfeeds;
import com.urbanbinge.repository.RssfeedsRepository;

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
 * Test class for the RssfeedsResource REST controller.
 *
 * @see RssfeedsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RssfeedsResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_EVENT_URL = "SAMPLE_TEXT";
    private static final String UPDATED_EVENT_URL = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final DateTime DEFAULT_PUB_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_PUB_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_PUB_DATE_STR = dateTimeFormatter.print(DEFAULT_PUB_DATE);
    private static final String DEFAULT_GUID = "SAMPLE_TEXT";
    private static final String UPDATED_GUID = "UPDATED_TEXT";

    @Inject
    private RssfeedsRepository rssfeedsRepository;

    private MockMvc restRssfeedsMockMvc;

    private Rssfeeds rssfeeds;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RssfeedsResource rssfeedsResource = new RssfeedsResource();
        ReflectionTestUtils.setField(rssfeedsResource, "rssfeedsRepository", rssfeedsRepository);
        this.restRssfeedsMockMvc = MockMvcBuilders.standaloneSetup(rssfeedsResource).build();
    }

    @Before
    public void initTest() {
        rssfeeds = new Rssfeeds();
        rssfeeds.setTitle(DEFAULT_TITLE);
        rssfeeds.setEventUrl(DEFAULT_EVENT_URL);
        rssfeeds.setDescription(DEFAULT_DESCRIPTION);
        rssfeeds.setPubDate(DEFAULT_PUB_DATE);
        rssfeeds.setGuid(DEFAULT_GUID);
    }

    @Test
    @Transactional
    public void createRssfeeds() throws Exception {
        int databaseSizeBeforeCreate = rssfeedsRepository.findAll().size();

        // Create the Rssfeeds
        restRssfeedsMockMvc.perform(post("/api/rssfeedss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rssfeeds)))
                .andExpect(status().isCreated());

        // Validate the Rssfeeds in the database
        List<Rssfeeds> rssfeedss = rssfeedsRepository.findAll();
        assertThat(rssfeedss).hasSize(databaseSizeBeforeCreate + 1);
        Rssfeeds testRssfeeds = rssfeedss.get(rssfeedss.size() - 1);
        assertThat(testRssfeeds.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRssfeeds.getEventUrl()).isEqualTo(DEFAULT_EVENT_URL);
        assertThat(testRssfeeds.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRssfeeds.getPubDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_PUB_DATE);
        assertThat(testRssfeeds.getGuid()).isEqualTo(DEFAULT_GUID);
    }

    @Test
    @Transactional
    public void getAllRssfeedss() throws Exception {
        // Initialize the database
        rssfeedsRepository.saveAndFlush(rssfeeds);

        // Get all the rssfeedss
        restRssfeedsMockMvc.perform(get("/api/rssfeedss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rssfeeds.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].eventUrl").value(hasItem(DEFAULT_EVENT_URL.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].pubDate").value(hasItem(DEFAULT_PUB_DATE_STR)))
                .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())));
    }

    @Test
    @Transactional
    public void getRssfeeds() throws Exception {
        // Initialize the database
        rssfeedsRepository.saveAndFlush(rssfeeds);

        // Get the rssfeeds
        restRssfeedsMockMvc.perform(get("/api/rssfeedss/{id}", rssfeeds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rssfeeds.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.eventUrl").value(DEFAULT_EVENT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pubDate").value(DEFAULT_PUB_DATE_STR))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRssfeeds() throws Exception {
        // Get the rssfeeds
        restRssfeedsMockMvc.perform(get("/api/rssfeedss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRssfeeds() throws Exception {
        // Initialize the database
        rssfeedsRepository.saveAndFlush(rssfeeds);
		
		int databaseSizeBeforeUpdate = rssfeedsRepository.findAll().size();

        // Update the rssfeeds
        rssfeeds.setTitle(UPDATED_TITLE);
        rssfeeds.setEventUrl(UPDATED_EVENT_URL);
        rssfeeds.setDescription(UPDATED_DESCRIPTION);
        rssfeeds.setPubDate(UPDATED_PUB_DATE);
        rssfeeds.setGuid(UPDATED_GUID);
        restRssfeedsMockMvc.perform(put("/api/rssfeedss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rssfeeds)))
                .andExpect(status().isOk());

        // Validate the Rssfeeds in the database
        List<Rssfeeds> rssfeedss = rssfeedsRepository.findAll();
        assertThat(rssfeedss).hasSize(databaseSizeBeforeUpdate);
        Rssfeeds testRssfeeds = rssfeedss.get(rssfeedss.size() - 1);
        assertThat(testRssfeeds.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRssfeeds.getEventUrl()).isEqualTo(UPDATED_EVENT_URL);
        assertThat(testRssfeeds.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRssfeeds.getPubDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_PUB_DATE);
        assertThat(testRssfeeds.getGuid()).isEqualTo(UPDATED_GUID);
    }

    @Test
    @Transactional
    public void deleteRssfeeds() throws Exception {
        // Initialize the database
        rssfeedsRepository.saveAndFlush(rssfeeds);
		
		int databaseSizeBeforeDelete = rssfeedsRepository.findAll().size();

        // Get the rssfeeds
        restRssfeedsMockMvc.perform(delete("/api/rssfeedss/{id}", rssfeeds.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Rssfeeds> rssfeedss = rssfeedsRepository.findAll();
        assertThat(rssfeedss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
