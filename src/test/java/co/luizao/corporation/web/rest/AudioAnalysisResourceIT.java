package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.AudioAnalysis;
import co.luizao.corporation.repository.AudioAnalysisRepository;
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
 * Integration tests for the {@link AudioAnalysisResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AudioAnalysisResourceIT {
    @Autowired
    private AudioAnalysisRepository audioAnalysisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAudioAnalysisMockMvc;

    private AudioAnalysis audioAnalysis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AudioAnalysis createEntity(EntityManager em) {
        AudioAnalysis audioAnalysis = new AudioAnalysis();
        return audioAnalysis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AudioAnalysis createUpdatedEntity(EntityManager em) {
        AudioAnalysis audioAnalysis = new AudioAnalysis();
        return audioAnalysis;
    }

    @BeforeEach
    public void initTest() {
        audioAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudioAnalysis() throws Exception {
        int databaseSizeBeforeCreate = audioAnalysisRepository.findAll().size();
        // Create the AudioAnalysis
        restAudioAnalysisMockMvc
            .perform(
                post("/api/audio-analyses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(audioAnalysis))
            )
            .andExpect(status().isCreated());

        // Validate the AudioAnalysis in the database
        List<AudioAnalysis> audioAnalysisList = audioAnalysisRepository.findAll();
        assertThat(audioAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        AudioAnalysis testAudioAnalysis = audioAnalysisList.get(audioAnalysisList.size() - 1);
    }

    @Test
    @Transactional
    public void createAudioAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = audioAnalysisRepository.findAll().size();

        // Create the AudioAnalysis with an existing ID
        audioAnalysis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudioAnalysisMockMvc
            .perform(
                post("/api/audio-analyses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(audioAnalysis))
            )
            .andExpect(status().isBadRequest());

        // Validate the AudioAnalysis in the database
        List<AudioAnalysis> audioAnalysisList = audioAnalysisRepository.findAll();
        assertThat(audioAnalysisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAudioAnalyses() throws Exception {
        // Initialize the database
        audioAnalysisRepository.saveAndFlush(audioAnalysis);

        // Get all the audioAnalysisList
        restAudioAnalysisMockMvc
            .perform(get("/api/audio-analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audioAnalysis.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAudioAnalysis() throws Exception {
        // Initialize the database
        audioAnalysisRepository.saveAndFlush(audioAnalysis);

        // Get the audioAnalysis
        restAudioAnalysisMockMvc
            .perform(get("/api/audio-analyses/{id}", audioAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(audioAnalysis.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAudioAnalysis() throws Exception {
        // Get the audioAnalysis
        restAudioAnalysisMockMvc.perform(get("/api/audio-analyses/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudioAnalysis() throws Exception {
        // Initialize the database
        audioAnalysisRepository.saveAndFlush(audioAnalysis);

        int databaseSizeBeforeUpdate = audioAnalysisRepository.findAll().size();

        // Update the audioAnalysis
        AudioAnalysis updatedAudioAnalysis = audioAnalysisRepository.findById(audioAnalysis.getId()).get();
        // Disconnect from session so that the updates on updatedAudioAnalysis are not directly saved in db
        em.detach(updatedAudioAnalysis);

        restAudioAnalysisMockMvc
            .perform(
                put("/api/audio-analyses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAudioAnalysis))
            )
            .andExpect(status().isOk());

        // Validate the AudioAnalysis in the database
        List<AudioAnalysis> audioAnalysisList = audioAnalysisRepository.findAll();
        assertThat(audioAnalysisList).hasSize(databaseSizeBeforeUpdate);
        AudioAnalysis testAudioAnalysis = audioAnalysisList.get(audioAnalysisList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAudioAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = audioAnalysisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAudioAnalysisMockMvc
            .perform(
                put("/api/audio-analyses").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(audioAnalysis))
            )
            .andExpect(status().isBadRequest());

        // Validate the AudioAnalysis in the database
        List<AudioAnalysis> audioAnalysisList = audioAnalysisRepository.findAll();
        assertThat(audioAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAudioAnalysis() throws Exception {
        // Initialize the database
        audioAnalysisRepository.saveAndFlush(audioAnalysis);

        int databaseSizeBeforeDelete = audioAnalysisRepository.findAll().size();

        // Delete the audioAnalysis
        restAudioAnalysisMockMvc
            .perform(delete("/api/audio-analyses/{id}", audioAnalysis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AudioAnalysis> audioAnalysisList = audioAnalysisRepository.findAll();
        assertThat(audioAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
