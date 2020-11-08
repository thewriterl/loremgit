package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.Track;
import co.luizao.corporation.repository.TrackRepository;
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
 * Integration tests for the {@link TrackResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TrackResourceIT {
    private static final Integer DEFAULT_DISC_NUMBER = 1;
    private static final Integer UPDATED_DISC_NUMBER = 2;

    private static final Integer DEFAULT_DURATION_MS = 1;
    private static final Integer UPDATED_DURATION_MS = 2;

    private static final Boolean DEFAULT_EXPLICIT = false;
    private static final Boolean UPDATED_EXPLICIT = true;

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_ID_SPOTIFY = "AAAAAAAAAA";
    private static final String UPDATED_ID_SPOTIFY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PLAYABLE = false;
    private static final Boolean UPDATED_IS_PLAYABLE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIEW_URL = "AAAAAAAAAA";
    private static final String UPDATED_PREVIEW_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRACK_NUMBER = 1;
    private static final Integer UPDATED_TRACK_NUMBER = 2;

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_LOCAL = false;
    private static final Boolean UPDATED_IS_LOCAL = true;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrackMockMvc;

    private Track track;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Track createEntity(EntityManager em) {
        Track track = new Track()
            .discNumber(DEFAULT_DISC_NUMBER)
            .durationMS(DEFAULT_DURATION_MS)
            .explicit(DEFAULT_EXPLICIT)
            .href(DEFAULT_HREF)
            .idSpotify(DEFAULT_ID_SPOTIFY)
            .isPlayable(DEFAULT_IS_PLAYABLE)
            .name(DEFAULT_NAME)
            .previewUrl(DEFAULT_PREVIEW_URL)
            .trackNumber(DEFAULT_TRACK_NUMBER)
            .uri(DEFAULT_URI)
            .isLocal(DEFAULT_IS_LOCAL);
        return track;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Track createUpdatedEntity(EntityManager em) {
        Track track = new Track()
            .discNumber(UPDATED_DISC_NUMBER)
            .durationMS(UPDATED_DURATION_MS)
            .explicit(UPDATED_EXPLICIT)
            .href(UPDATED_HREF)
            .idSpotify(UPDATED_ID_SPOTIFY)
            .isPlayable(UPDATED_IS_PLAYABLE)
            .name(UPDATED_NAME)
            .previewUrl(UPDATED_PREVIEW_URL)
            .trackNumber(UPDATED_TRACK_NUMBER)
            .uri(UPDATED_URI)
            .isLocal(UPDATED_IS_LOCAL);
        return track;
    }

    @BeforeEach
    public void initTest() {
        track = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrack() throws Exception {
        int databaseSizeBeforeCreate = trackRepository.findAll().size();
        // Create the Track
        restTrackMockMvc
            .perform(post("/api/tracks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(track)))
            .andExpect(status().isCreated());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeCreate + 1);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getDiscNumber()).isEqualTo(DEFAULT_DISC_NUMBER);
        assertThat(testTrack.getDurationMS()).isEqualTo(DEFAULT_DURATION_MS);
        assertThat(testTrack.isExplicit()).isEqualTo(DEFAULT_EXPLICIT);
        assertThat(testTrack.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testTrack.getIdSpotify()).isEqualTo(DEFAULT_ID_SPOTIFY);
        assertThat(testTrack.isIsPlayable()).isEqualTo(DEFAULT_IS_PLAYABLE);
        assertThat(testTrack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrack.getPreviewUrl()).isEqualTo(DEFAULT_PREVIEW_URL);
        assertThat(testTrack.getTrackNumber()).isEqualTo(DEFAULT_TRACK_NUMBER);
        assertThat(testTrack.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testTrack.isIsLocal()).isEqualTo(DEFAULT_IS_LOCAL);
    }

    @Test
    @Transactional
    public void createTrackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trackRepository.findAll().size();

        // Create the Track with an existing ID
        track.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackMockMvc
            .perform(post("/api/tracks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(track)))
            .andExpect(status().isBadRequest());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTracks() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        // Get all the trackList
        restTrackMockMvc
            .perform(get("/api/tracks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(track.getId().intValue())))
            .andExpect(jsonPath("$.[*].discNumber").value(hasItem(DEFAULT_DISC_NUMBER)))
            .andExpect(jsonPath("$.[*].durationMS").value(hasItem(DEFAULT_DURATION_MS)))
            .andExpect(jsonPath("$.[*].explicit").value(hasItem(DEFAULT_EXPLICIT.booleanValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].idSpotify").value(hasItem(DEFAULT_ID_SPOTIFY)))
            .andExpect(jsonPath("$.[*].isPlayable").value(hasItem(DEFAULT_IS_PLAYABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].previewUrl").value(hasItem(DEFAULT_PREVIEW_URL)))
            .andExpect(jsonPath("$.[*].trackNumber").value(hasItem(DEFAULT_TRACK_NUMBER)))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)))
            .andExpect(jsonPath("$.[*].isLocal").value(hasItem(DEFAULT_IS_LOCAL.booleanValue())));
    }

    @Test
    @Transactional
    public void getTrack() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        // Get the track
        restTrackMockMvc
            .perform(get("/api/tracks/{id}", track.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(track.getId().intValue()))
            .andExpect(jsonPath("$.discNumber").value(DEFAULT_DISC_NUMBER))
            .andExpect(jsonPath("$.durationMS").value(DEFAULT_DURATION_MS))
            .andExpect(jsonPath("$.explicit").value(DEFAULT_EXPLICIT.booleanValue()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.idSpotify").value(DEFAULT_ID_SPOTIFY))
            .andExpect(jsonPath("$.isPlayable").value(DEFAULT_IS_PLAYABLE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.previewUrl").value(DEFAULT_PREVIEW_URL))
            .andExpect(jsonPath("$.trackNumber").value(DEFAULT_TRACK_NUMBER))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI))
            .andExpect(jsonPath("$.isLocal").value(DEFAULT_IS_LOCAL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrack() throws Exception {
        // Get the track
        restTrackMockMvc.perform(get("/api/tracks/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrack() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        int databaseSizeBeforeUpdate = trackRepository.findAll().size();

        // Update the track
        Track updatedTrack = trackRepository.findById(track.getId()).get();
        // Disconnect from session so that the updates on updatedTrack are not directly saved in db
        em.detach(updatedTrack);
        updatedTrack
            .discNumber(UPDATED_DISC_NUMBER)
            .durationMS(UPDATED_DURATION_MS)
            .explicit(UPDATED_EXPLICIT)
            .href(UPDATED_HREF)
            .idSpotify(UPDATED_ID_SPOTIFY)
            .isPlayable(UPDATED_IS_PLAYABLE)
            .name(UPDATED_NAME)
            .previewUrl(UPDATED_PREVIEW_URL)
            .trackNumber(UPDATED_TRACK_NUMBER)
            .uri(UPDATED_URI)
            .isLocal(UPDATED_IS_LOCAL);

        restTrackMockMvc
            .perform(put("/api/tracks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedTrack)))
            .andExpect(status().isOk());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getDiscNumber()).isEqualTo(UPDATED_DISC_NUMBER);
        assertThat(testTrack.getDurationMS()).isEqualTo(UPDATED_DURATION_MS);
        assertThat(testTrack.isExplicit()).isEqualTo(UPDATED_EXPLICIT);
        assertThat(testTrack.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testTrack.getIdSpotify()).isEqualTo(UPDATED_ID_SPOTIFY);
        assertThat(testTrack.isIsPlayable()).isEqualTo(UPDATED_IS_PLAYABLE);
        assertThat(testTrack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrack.getPreviewUrl()).isEqualTo(UPDATED_PREVIEW_URL);
        assertThat(testTrack.getTrackNumber()).isEqualTo(UPDATED_TRACK_NUMBER);
        assertThat(testTrack.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testTrack.isIsLocal()).isEqualTo(UPDATED_IS_LOCAL);
    }

    @Test
    @Transactional
    public void updateNonExistingTrack() throws Exception {
        int databaseSizeBeforeUpdate = trackRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackMockMvc
            .perform(put("/api/tracks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(track)))
            .andExpect(status().isBadRequest());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrack() throws Exception {
        // Initialize the database
        trackRepository.saveAndFlush(track);

        int databaseSizeBeforeDelete = trackRepository.findAll().size();

        // Delete the track
        restTrackMockMvc
            .perform(delete("/api/tracks/{id}", track.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
