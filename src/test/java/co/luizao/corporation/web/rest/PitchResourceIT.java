package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.Pitch;
import co.luizao.corporation.repository.PitchRepository;
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
 * Integration tests for the {@link PitchResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PitchResourceIT {
    private static final Float DEFAULT_PITCH = 1F;
    private static final Float UPDATED_PITCH = 2F;

    @Autowired
    private PitchRepository pitchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPitchMockMvc;

    private Pitch pitch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pitch createEntity(EntityManager em) {
        Pitch pitch = new Pitch().pitch(DEFAULT_PITCH);
        return pitch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pitch createUpdatedEntity(EntityManager em) {
        Pitch pitch = new Pitch().pitch(UPDATED_PITCH);
        return pitch;
    }

    @BeforeEach
    public void initTest() {
        pitch = createEntity(em);
    }

    @Test
    @Transactional
    public void createPitch() throws Exception {
        int databaseSizeBeforeCreate = pitchRepository.findAll().size();
        // Create the Pitch
        restPitchMockMvc
            .perform(post("/api/pitches").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pitch)))
            .andExpect(status().isCreated());

        // Validate the Pitch in the database
        List<Pitch> pitchList = pitchRepository.findAll();
        assertThat(pitchList).hasSize(databaseSizeBeforeCreate + 1);
        Pitch testPitch = pitchList.get(pitchList.size() - 1);
        assertThat(testPitch.getPitch()).isEqualTo(DEFAULT_PITCH);
    }

    @Test
    @Transactional
    public void createPitchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pitchRepository.findAll().size();

        // Create the Pitch with an existing ID
        pitch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPitchMockMvc
            .perform(post("/api/pitches").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pitch)))
            .andExpect(status().isBadRequest());

        // Validate the Pitch in the database
        List<Pitch> pitchList = pitchRepository.findAll();
        assertThat(pitchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPitches() throws Exception {
        // Initialize the database
        pitchRepository.saveAndFlush(pitch);

        // Get all the pitchList
        restPitchMockMvc
            .perform(get("/api/pitches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pitch.getId().intValue())))
            .andExpect(jsonPath("$.[*].pitch").value(hasItem(DEFAULT_PITCH.doubleValue())));
    }

    @Test
    @Transactional
    public void getPitch() throws Exception {
        // Initialize the database
        pitchRepository.saveAndFlush(pitch);

        // Get the pitch
        restPitchMockMvc
            .perform(get("/api/pitches/{id}", pitch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pitch.getId().intValue()))
            .andExpect(jsonPath("$.pitch").value(DEFAULT_PITCH.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPitch() throws Exception {
        // Get the pitch
        restPitchMockMvc.perform(get("/api/pitches/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePitch() throws Exception {
        // Initialize the database
        pitchRepository.saveAndFlush(pitch);

        int databaseSizeBeforeUpdate = pitchRepository.findAll().size();

        // Update the pitch
        Pitch updatedPitch = pitchRepository.findById(pitch.getId()).get();
        // Disconnect from session so that the updates on updatedPitch are not directly saved in db
        em.detach(updatedPitch);
        updatedPitch.pitch(UPDATED_PITCH);

        restPitchMockMvc
            .perform(put("/api/pitches").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedPitch)))
            .andExpect(status().isOk());

        // Validate the Pitch in the database
        List<Pitch> pitchList = pitchRepository.findAll();
        assertThat(pitchList).hasSize(databaseSizeBeforeUpdate);
        Pitch testPitch = pitchList.get(pitchList.size() - 1);
        assertThat(testPitch.getPitch()).isEqualTo(UPDATED_PITCH);
    }

    @Test
    @Transactional
    public void updateNonExistingPitch() throws Exception {
        int databaseSizeBeforeUpdate = pitchRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPitchMockMvc
            .perform(put("/api/pitches").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pitch)))
            .andExpect(status().isBadRequest());

        // Validate the Pitch in the database
        List<Pitch> pitchList = pitchRepository.findAll();
        assertThat(pitchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePitch() throws Exception {
        // Initialize the database
        pitchRepository.saveAndFlush(pitch);

        int databaseSizeBeforeDelete = pitchRepository.findAll().size();

        // Delete the pitch
        restPitchMockMvc
            .perform(delete("/api/pitches/{id}", pitch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pitch> pitchList = pitchRepository.findAll();
        assertThat(pitchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
