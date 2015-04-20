package com.urbanbinge.web.rest;

import com.urbanbinge.Application;
import com.urbanbinge.domain.Cities;
import com.urbanbinge.repository.CitiesRepository;

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
 * Test class for the CitiesResource REST controller.
 *
 * @see CitiesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CitiesResourceTest {

    private static final String DEFAULT_CITY_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_CITY_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CITY_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CITY_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_STATE = "SAMPLE_TEXT";
    private static final String UPDATED_STATE = "UPDATED_TEXT";
    private static final String DEFAULT_COUNTRY = "SAMPLE_TEXT";
    private static final String UPDATED_COUNTRY = "UPDATED_TEXT";

    @Inject
    private CitiesRepository citiesRepository;

    private MockMvc restCitiesMockMvc;

    private Cities cities;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CitiesResource citiesResource = new CitiesResource();
        ReflectionTestUtils.setField(citiesResource, "citiesRepository", citiesRepository);
        this.restCitiesMockMvc = MockMvcBuilders.standaloneSetup(citiesResource).build();
    }

    @Before
    public void initTest() {
        cities = new Cities();
        cities.setCityName(DEFAULT_CITY_NAME);
        cities.setCityCode(DEFAULT_CITY_CODE);
        cities.setState(DEFAULT_STATE);
        cities.setCountry(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createCities() throws Exception {
        int databaseSizeBeforeCreate = citiesRepository.findAll().size();

        // Create the Cities
        restCitiesMockMvc.perform(post("/api/citiess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cities)))
                .andExpect(status().isCreated());

        // Validate the Cities in the database
        List<Cities> citiess = citiesRepository.findAll();
        assertThat(citiess).hasSize(databaseSizeBeforeCreate + 1);
        Cities testCities = citiess.get(citiess.size() - 1);
        assertThat(testCities.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testCities.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
        assertThat(testCities.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCities.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllCitiess() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiess
        restCitiesMockMvc.perform(get("/api/citiess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
                .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME.toString())))
                .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get the cities
        restCitiesMockMvc.perform(get("/api/citiess/{id}", cities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cities.getId().intValue()))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME.toString()))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCities() throws Exception {
        // Get the cities
        restCitiesMockMvc.perform(get("/api/citiess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
		
		int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities
        cities.setCityName(UPDATED_CITY_NAME);
        cities.setCityCode(UPDATED_CITY_CODE);
        cities.setState(UPDATED_STATE);
        cities.setCountry(UPDATED_COUNTRY);
        restCitiesMockMvc.perform(put("/api/citiess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cities)))
                .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiess = citiesRepository.findAll();
        assertThat(citiess).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiess.get(citiess.size() - 1);
        assertThat(testCities.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testCities.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
        assertThat(testCities.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCities.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void deleteCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
		
		int databaseSizeBeforeDelete = citiesRepository.findAll().size();

        // Get the cities
        restCitiesMockMvc.perform(delete("/api/citiess/{id}", cities.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cities> citiess = citiesRepository.findAll();
        assertThat(citiess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
