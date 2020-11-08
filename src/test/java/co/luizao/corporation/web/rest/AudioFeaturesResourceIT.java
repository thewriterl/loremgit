package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.AudioFeatures;
import co.luizao.corporation.repository.AudioFeaturesRepository;
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
 * Integration tests for the {@link AudioFeaturesResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AudioFeaturesResourceIT {
    private static final Integer DEFAULT_DURATION_MS = 1;
    private static final Integer UPDATED_DURATION_MS = 2;

    private static final Integer DEFAULT_KEY = 1;
    private static final Integer UPDATED_KEY = 2;

    private static final Integer DEFAULT_MODE = 1;
    private static final Integer UPDATED_MODE = 2;

    private static final Integer DEFAULT_TIME_SIGNATURE = 1;
    private static final Integer UPDATED_TIME_SIGNATURE = 2;

    private static final Float DEFAULT_ACOUSTICNESS = 1F;
    private static final Float UPDATED_ACOUSTICNESS = 2F;

    private static final Float DEFAULT_DACEABILITY = 1F;
    private static final Float UPDATED_DACEABILITY = 2F;

    private static final Float DEFAULT_ENERGY = 1F;
    private static final Float UPDATED_ENERGY = 2F;

    private static final Float DEFAULT_INSTRUMENTALNESS = 1F;
    private static final Float UPDATED_INSTRUMENTALNESS = 2F;

    private static final Float DEFAULT_LIVENESS = 1F;
    private static final Float UPDATED_LIVENESS = 2F;

    private static final Float DEFAULT_LOUDNESS = 1F;
    private static final Float UPDATED_LOUDNESS = 2F;

    private static final Float DEFAULT_SPEECHINESS = 1F;
    private static final Float UPDATED_SPEECHINESS = 2F;

    private static final Float DEFAULT_VALENCE = 1F;
    private static final Float UPDATED_VALENCE = 2F;

    private static final Float DEFAULT_TEMPO = 1F;
    private static final Float UPDATED_TEMPO = 2F;

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    private static final String DEFAULT_TRACK_HREF = "AAAAAAAAAA";
    private static final String UPDATED_TRACK_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYSIS_URL = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS_URL = "BBBBBBBBBB";

    @Autowired
    private AudioFeaturesRepository audioFeaturesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAudioFeaturesMockMvc;

    private AudioFeatures audioFeatures;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AudioFeatures createEntity(EntityManager em) {
        AudioFeatures audioFeatures = new AudioFeatures()
            .durationMs(DEFAULT_DURATION_MS)
            .key(DEFAULT_KEY)
            .mode(DEFAULT_MODE)
            .timeSignature(DEFAULT_TIME_SIGNATURE)
            .acousticness(DEFAULT_ACOUSTICNESS)
            .daceability(DEFAULT_DACEABILITY)
            .energy(DEFAULT_ENERGY)
            .instrumentalness(DEFAULT_INSTRUMENTALNESS)
            .liveness(DEFAULT_LIVENESS)
            .loudness(DEFAULT_LOUDNESS)
            .speechiness(DEFAULT_SPEECHINESS)
            .valence(DEFAULT_VALENCE)
            .tempo(DEFAULT_TEMPO)
            .uri(DEFAULT_URI)
            .trackHref(DEFAULT_TRACK_HREF)
            .analysisUrl(DEFAULT_ANALYSIS_URL);
        return audioFeatures;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AudioFeatures createUpdatedEntity(EntityManager em) {
        AudioFeatures audioFeatures = new AudioFeatures()
            .durationMs(UPDATED_DURATION_MS)
            .key(UPDATED_KEY)
            .mode(UPDATED_MODE)
            .timeSignature(UPDATED_TIME_SIGNATURE)
            .acousticness(UPDATED_ACOUSTICNESS)
            .daceability(UPDATED_DACEABILITY)
            .energy(UPDATED_ENERGY)
            .instrumentalness(UPDATED_INSTRUMENTALNESS)
            .liveness(UPDATED_LIVENESS)
            .loudness(UPDATED_LOUDNESS)
            .speechiness(UPDATED_SPEECHINESS)
            .valence(UPDATED_VALENCE)
            .tempo(UPDATED_TEMPO)
            .uri(UPDATED_URI)
            .trackHref(UPDATED_TRACK_HREF)
            .analysisUrl(UPDATED_ANALYSIS_URL);
        return audioFeatures;
    }

    @BeforeEach
    public void initTest() {
        audioFeatures = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudioFeatures() throws Exception {
        int databaseSizeBeforeCreate = audioFeaturesRepository.findAll().size();
        // Create the AudioFeatures
        restAudioFeaturesMockMvc
            .perform(
                post("/api/audio-features")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(audioFeatures))
            )
            .andExpect(status().isCreated());

        // Validate the AudioFeatures in the database
        List<AudioFeatures> audioFeaturesList = audioFeaturesRepository.findAll();
        assertThat(audioFeaturesList).hasSize(databaseSizeBeforeCreate + 1);
        AudioFeatures testAudioFeatures = audioFeaturesList.get(audioFeaturesList.size() - 1);
        assertThat(testAudioFeatures.getDurationMs()).isEqualTo(DEFAULT_DURATION_MS);
        assertThat(testAudioFeatures.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testAudioFeatures.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testAudioFeatures.getTimeSignature()).isEqualTo(DEFAULT_TIME_SIGNATURE);
        assertThat(testAudioFeatures.getAcousticness()).isEqualTo(DEFAULT_ACOUSTICNESS);
        assertThat(testAudioFeatures.getDaceability()).isEqualTo(DEFAULT_DACEABILITY);
        assertThat(testAudioFeatures.getEnergy()).isEqualTo(DEFAULT_ENERGY);
        assertThat(testAudioFeatures.getInstrumentalness()).isEqualTo(DEFAULT_INSTRUMENTALNESS);
        assertThat(testAudioFeatures.getLiveness()).isEqualTo(DEFAULT_LIVENESS);
        assertThat(testAudioFeatures.getLoudness()).isEqualTo(DEFAULT_LOUDNESS);
        assertThat(testAudioFeatures.getSpeechiness()).isEqualTo(DEFAULT_SPEECHINESS);
        assertThat(testAudioFeatures.getValence()).isEqualTo(DEFAULT_VALENCE);
        assertThat(testAudioFeatures.getTempo()).isEqualTo(DEFAULT_TEMPO);
        assertThat(testAudioFeatures.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testAudioFeatures.getTrackHref()).isEqualTo(DEFAULT_TRACK_HREF);
        assertThat(testAudioFeatures.getAnalysisUrl()).isEqualTo(DEFAULT_ANALYSIS_URL);
    }

    @Test
    @Transactional
    public void createAudioFeaturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = audioFeaturesRepository.findAll().size();

        // Create the AudioFeatures with an existing ID
        audioFeatures.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudioFeaturesMockMvc
            .perform(
                post("/api/audio-features")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(audioFeatures))
            )
            .andExpect(status().isBadRequest());

        // Validate the AudioFeatures in the database
        List<AudioFeatures> audioFeaturesList = audioFeaturesRepository.findAll();
        assertThat(audioFeaturesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAudioFeatures() throws Exception {
        // Initialize the database
        audioFeaturesRepository.saveAndFlush(audioFeatures);

        // Get all the audioFeaturesList
        restAudioFeaturesMockMvc
            .perform(get("/api/audio-features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audioFeatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].durationMs").value(hasItem(DEFAULT_DURATION_MS)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE)))
            .andExpect(jsonPath("$.[*].timeSignature").value(hasItem(DEFAULT_TIME_SIGNATURE)))
            .andExpect(jsonPath("$.[*].acousticness").value(hasItem(DEFAULT_ACOUSTICNESS.doubleValue())))
            .andExpect(jsonPath("$.[*].daceability").value(hasItem(DEFAULT_DACEABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].energy").value(hasItem(DEFAULT_ENERGY.doubleValue())))
            .andExpect(jsonPath("$.[*].instrumentalness").value(hasItem(DEFAULT_INSTRUMENTALNESS.doubleValue())))
            .andExpect(jsonPath("$.[*].liveness").value(hasItem(DEFAULT_LIVENESS.doubleValue())))
            .andExpect(jsonPath("$.[*].loudness").value(hasItem(DEFAULT_LOUDNESS.doubleValue())))
            .andExpect(jsonPath("$.[*].speechiness").value(hasItem(DEFAULT_SPEECHINESS.doubleValue())))
            .andExpect(jsonPath("$.[*].valence").value(hasItem(DEFAULT_VALENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].tempo").value(hasItem(DEFAULT_TEMPO.doubleValue())))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].trackHref").value(hasItem(DEFAULT_TRACK_HREF)))
            .andExpect(jsonPath("$.[*].analysisUrl").value(hasItem(DEFAULT_ANALYSIS_URL)));
    }

    @Test
    @Transactional
    public void getAudioFeatures() throws Exception {
        // Initialize the database
        audioFeaturesRepository.saveAndFlush(audioFeatures);

        // Get the audioFeatures
        restAudioFeaturesMockMvc
            .perform(get("/api/audio-features/{id}", audioFeatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(audioFeatures.getId().intValue()))
            .andExpect(jsonPath("$.durationMs").value(DEFAULT_DURATION_MS))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE))
            .andExpect(jsonPath("$.timeSignature").value(DEFAULT_TIME_SIGNATURE))
            .andExpect(jsonPath("$.acousticness").value(DEFAULT_ACOUSTICNESS.doubleValue()))
            .andExpect(jsonPath("$.daceability").value(DEFAULT_DACEABILITY.doubleValue()))
            .andExpect(jsonPath("$.energy").value(DEFAULT_ENERGY.doubleValue()))
            .andExpect(jsonPath("$.instrumentalness").value(DEFAULT_INSTRUMENTALNESS.doubleValue()))
            .andExpect(jsonPath("$.liveness").value(DEFAULT_LIVENESS.doubleValue()))
            .andExpect(jsonPath("$.loudness").value(DEFAULT_LOUDNESS.doubleValue()))
            .andExpect(jsonPath("$.speechiness").value(DEFAULT_SPEECHINESS.doubleValue()))
            .andExpect(jsonPath("$.valence").value(DEFAULT_VALENCE.doubleValue()))
            .andExpect(jsonPath("$.tempo").value(DEFAULT_TEMPO.doubleValue()))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI))
            .andExpect(jsonPath("$.trackHref").value(DEFAULT_TRACK_HREF))
            .andExpect(jsonPath("$.analysisUrl").value(DEFAULT_ANALYSIS_URL));
    }

    @Test
    @Transactional
    public void getNonExistingAudioFeatures() throws Exception {
        // Get the audioFeatures
        restAudioFeaturesMockMvc.perform(get("/api/audio-features/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudioFeatures() throws Exception {
        // Initialize the database
        audioFeaturesRepository.saveAndFlush(audioFeatures);

        int databaseSizeBeforeUpdate = audioFeaturesRepository.findAll().size();

        // Update the audioFeatures
        AudioFeatures updatedAudioFeatures = audioFeaturesRepository.findById(audioFeatures.getId()).get();
        // Disconnect from session so that the updates on updatedAudioFeatures are not directly saved in db
        em.detach(updatedAudioFeatures);
        updatedAudioFeatures
            .durationMs(UPDATED_DURATION_MS)
            .key(UPDATED_KEY)
            .mode(UPDATED_MODE)
            .timeSignature(UPDATED_TIME_SIGNATURE)
            .acousticness(UPDATED_ACOUSTICNESS)
            .daceability(UPDATED_DACEABILITY)
            .energy(UPDATED_ENERGY)
            .instrumentalness(UPDATED_INSTRUMENTALNESS)
            .liveness(UPDATED_LIVENESS)
            .loudness(UPDATED_LOUDNESS)
            .speechiness(UPDATED_SPEECHINESS)
            .valence(UPDATED_VALENCE)
            .tempo(UPDATED_TEMPO)
            .uri(UPDATED_URI)
            .trackHref(UPDATED_TRACK_HREF)
            .analysisUrl(UPDATED_ANALYSIS_URL);

        restAudioFeaturesMockMvc
            .perform(
                put("/api/audio-features")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAudioFeatures))
            )
            .andExpect(status().isOk());

        // Validate the AudioFeatures in the database
        List<AudioFeatures> audioFeaturesList = audioFeaturesRepository.findAll();
        assertThat(audioFeaturesList).hasSize(databaseSizeBeforeUpdate);
        AudioFeatures testAudioFeatures = audioFeaturesList.get(audioFeaturesList.size() - 1);
        assertThat(testAudioFeatures.getDurationMs()).isEqualTo(UPDATED_DURATION_MS);
        assertThat(testAudioFeatures.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAudioFeatures.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testAudioFeatures.getTimeSignature()).isEqualTo(UPDATED_TIME_SIGNATURE);
        assertThat(testAudioFeatures.getAcousticness()).isEqualTo(UPDATED_ACOUSTICNESS);
        assertThat(testAudioFeatures.getDaceability()).isEqualTo(UPDATED_DACEABILITY);
        assertThat(testAudioFeatures.getEnergy()).isEqualTo(UPDATED_ENERGY);
        assertThat(testAudioFeatures.getInstrumentalness()).isEqualTo(UPDATED_INSTRUMENTALNESS);
        assertThat(testAudioFeatures.getLiveness()).isEqualTo(UPDATED_LIVENESS);
        assertThat(testAudioFeatures.getLoudness()).isEqualTo(UPDATED_LOUDNESS);
        assertThat(testAudioFeatures.getSpeechiness()).isEqualTo(UPDATED_SPEECHINESS);
        assertThat(testAudioFeatures.getValence()).isEqualTo(UPDATED_VALENCE);
        assertThat(testAudioFeatures.getTempo()).isEqualTo(UPDATED_TEMPO);
        assertThat(testAudioFeatures.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testAudioFeatures.getTrackHref()).isEqualTo(UPDATED_TRACK_HREF);
        assertThat(testAudioFeatures.getAnalysisUrl()).isEqualTo(UPDATED_ANALYSIS_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingAudioFeatures() throws Exception {
        int databaseSizeBeforeUpdate = audioFeaturesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAudioFeaturesMockMvc
            .perform(
                put("/api/audio-features").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(audioFeatures))
            )
            .andExpect(status().isBadRequest());

        // Validate the AudioFeatures in the database
        List<AudioFeatures> audioFeaturesList = audioFeaturesRepository.findAll();
        assertThat(audioFeaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAudioFeatures() throws Exception {
        // Initialize the database
        audioFeaturesRepository.saveAndFlush(audioFeatures);

        int databaseSizeBeforeDelete = audioFeaturesRepository.findAll().size();

        // Delete the audioFeatures
        restAudioFeaturesMockMvc
            .perform(delete("/api/audio-features/{id}", audioFeatures.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AudioFeatures> audioFeaturesList = audioFeaturesRepository.findAll();
        assertThat(audioFeaturesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
