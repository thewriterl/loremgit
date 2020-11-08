package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.TimeInterval;
import co.luizao.corporation.repository.TimeIntervalRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TimeIntervalResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TimeIntervalResourceIT {
    private static final Float DEFAULT_START = 1F;
    private static final Float UPDATED_START = 2F;

    private static final Float DEFAULT_DURATION = 1F;
    private static final Float UPDATED_DURATION = 2F;

    private static final Float DEFAULT_CONFIDENCE = 1F;
    private static final Float UPDATED_CONFIDENCE = 2F;

    @Autowired
    private TimeIntervalRepository timeIntervalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeIntervalMockMvc;

    private TimeInterval timeInterval;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeInterval createEntity(EntityManager em) {
        TimeInterval timeInterval = new TimeInterval().start(DEFAULT_START).duration(DEFAULT_DURATION).confidence(DEFAULT_CONFIDENCE);
        return timeInterval;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeInterval createUpdatedEntity(EntityManager em) {
        TimeInterval timeInterval = new TimeInterval().start(UPDATED_START).duration(UPDATED_DURATION).confidence(UPDATED_CONFIDENCE);
        return timeInterval;
    }

    @BeforeEach
    public void initTest() {
        timeInterval = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeInterval() throws Exception {
        int databaseSizeBeforeCreate = timeIntervalRepository.findAll().size();
        // Create the TimeInterval
        restTimeIntervalMockMvc
            .perform(
                post("/api/time-intervals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeInterval))
            )
            .andExpect(status().isCreated());

        // Validate the TimeInterval in the database
        List<TimeInterval> timeIntervalList = timeIntervalRepository.findAll();
        assertThat(timeIntervalList).hasSize(databaseSizeBeforeCreate + 1);
        TimeInterval testTimeInterval = timeIntervalList.get(timeIntervalList.size() - 1);
        assertThat(testTimeInterval.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testTimeInterval.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTimeInterval.getConfidence()).isEqualTo(DEFAULT_CONFIDENCE);
    }

    @Test
    @Transactional
    public void createTimeIntervalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeIntervalRepository.findAll().size();

        // Create the TimeInterval with an existing ID
        timeInterval.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeIntervalMockMvc
            .perform(
                post("/api/time-intervals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeInterval))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeInterval in the database
        List<TimeInterval> timeIntervalList = timeIntervalRepository.findAll();
        assertThat(timeIntervalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimeIntervals() throws Exception {
        // Initialize the database
        timeIntervalRepository.saveAndFlush(timeInterval);

        // Get all the timeIntervalList
        restTimeIntervalMockMvc
            .perform(get("/api/time-intervals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeInterval.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].confidence").value(hasItem(DEFAULT_CONFIDENCE.doubleValue())));
    }

    @Test
    @Transactional
    public void getTimeInterval() throws Exception {
        // Initialize the database
        timeIntervalRepository.saveAndFlush(timeInterval);

        // Get the timeInterval
        restTimeIntervalMockMvc
            .perform(get("/api/time-intervals/{id}", timeInterval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeInterval.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.doubleValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.doubleValue()))
            .andExpect(jsonPath("$.confidence").value(DEFAULT_CONFIDENCE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeInterval() throws Exception {
        // Get the timeInterval
        restTimeIntervalMockMvc.perform(get("/api/time-intervals/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeInterval() throws Exception {
        // Initialize the database
        timeIntervalRepository.saveAndFlush(timeInterval);

        int databaseSizeBeforeUpdate = timeIntervalRepository.findAll().size();

        // Update the timeInterval
        TimeInterval updatedTimeInterval = timeIntervalRepository.findById(timeInterval.getId()).get();
        // Disconnect from session so that the updates on updatedTimeInterval are not directly saved in db
        em.detach(updatedTimeInterval);
        updatedTimeInterval.start(UPDATED_START).duration(UPDATED_DURATION).confidence(UPDATED_CONFIDENCE);

        restTimeIntervalMockMvc
            .perform(
                put("/api/time-intervals")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTimeInterval))
            )
            .andExpect(status().isOk());

        // Validate the TimeInterval in the database
        List<TimeInterval> timeIntervalList = timeIntervalRepository.findAll();
        assertThat(timeIntervalList).hasSize(databaseSizeBeforeUpdate);
        TimeInterval testTimeInterval = timeIntervalList.get(timeIntervalList.size() - 1);
        assertThat(testTimeInterval.getStart()).isEqualTo(UPDATED_START);
        assertThat(testTimeInterval.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTimeInterval.getConfidence()).isEqualTo(UPDATED_CONFIDENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeInterval() throws Exception {
        int databaseSizeBeforeUpdate = timeIntervalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeIntervalMockMvc
            .perform(
                put("/api/time-intervals").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeInterval))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeInterval in the database
        List<TimeInterval> timeIntervalList = timeIntervalRepository.findAll();
        assertThat(timeIntervalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeInterval() throws Exception {
        // Initialize the database
        timeIntervalRepository.saveAndFlush(timeInterval);

        int databaseSizeBeforeDelete = timeIntervalRepository.findAll().size();

        // Delete the timeInterval
        restTimeIntervalMockMvc
            .perform(delete("/api/time-intervals/{id}", timeInterval.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeInterval> timeIntervalList = timeIntervalRepository.findAll();
        assertThat(timeIntervalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
