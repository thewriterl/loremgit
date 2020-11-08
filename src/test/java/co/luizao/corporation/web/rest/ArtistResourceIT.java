package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.Artist;
import co.luizao.corporation.repository.ArtistRepository;
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
 * Integration tests for the {@link ArtistResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArtistResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_POPULARITY = 1F;
    private static final Float UPDATED_POPULARITY = 2F;

    private static final String DEFAULT_ID_SPOTIFY = "AAAAAAAAAA";
    private static final String UPDATED_ID_SPOTIFY = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final Integer DEFAULT_FOLLOWERS = 1;
    private static final Integer UPDATED_FOLLOWERS = 2;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtistMockMvc;

    private Artist artist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artist createEntity(EntityManager em) {
        Artist artist = new Artist()
            .name(DEFAULT_NAME)
            .popularity(DEFAULT_POPULARITY)
            .idSpotify(DEFAULT_ID_SPOTIFY)
            .href(DEFAULT_HREF)
            .followers(DEFAULT_FOLLOWERS);
        return artist;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artist createUpdatedEntity(EntityManager em) {
        Artist artist = new Artist()
            .name(UPDATED_NAME)
            .popularity(UPDATED_POPULARITY)
            .idSpotify(UPDATED_ID_SPOTIFY)
            .href(UPDATED_HREF)
            .followers(UPDATED_FOLLOWERS);
        return artist;
    }

    @BeforeEach
    public void initTest() {
        artist = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtist() throws Exception {
        int databaseSizeBeforeCreate = artistRepository.findAll().size();
        // Create the Artist
        restArtistMockMvc
            .perform(post("/api/artists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artist)))
            .andExpect(status().isCreated());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeCreate + 1);
        Artist testArtist = artistList.get(artistList.size() - 1);
        assertThat(testArtist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArtist.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
        assertThat(testArtist.getIdSpotify()).isEqualTo(DEFAULT_ID_SPOTIFY);
        assertThat(testArtist.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testArtist.getFollowers()).isEqualTo(DEFAULT_FOLLOWERS);
    }

    @Test
    @Transactional
    public void createArtistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = artistRepository.findAll().size();

        // Create the Artist with an existing ID
        artist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistMockMvc
            .perform(post("/api/artists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artist)))
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArtists() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get all the artistList
        restArtistMockMvc
            .perform(get("/api/artists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artist.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.doubleValue())))
            .andExpect(jsonPath("$.[*].idSpotify").value(hasItem(DEFAULT_ID_SPOTIFY)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].followers").value(hasItem(DEFAULT_FOLLOWERS)));
    }

    @Test
    @Transactional
    public void getArtist() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get the artist
        restArtistMockMvc
            .perform(get("/api/artists/{id}", artist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artist.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY.doubleValue()))
            .andExpect(jsonPath("$.idSpotify").value(DEFAULT_ID_SPOTIFY))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.followers").value(DEFAULT_FOLLOWERS));
    }

    @Test
    @Transactional
    public void getNonExistingArtist() throws Exception {
        // Get the artist
        restArtistMockMvc.perform(get("/api/artists/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtist() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        int databaseSizeBeforeUpdate = artistRepository.findAll().size();

        // Update the artist
        Artist updatedArtist = artistRepository.findById(artist.getId()).get();
        // Disconnect from session so that the updates on updatedArtist are not directly saved in db
        em.detach(updatedArtist);
        updatedArtist
            .name(UPDATED_NAME)
            .popularity(UPDATED_POPULARITY)
            .idSpotify(UPDATED_ID_SPOTIFY)
            .href(UPDATED_HREF)
            .followers(UPDATED_FOLLOWERS);

        restArtistMockMvc
            .perform(put("/api/artists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedArtist)))
            .andExpect(status().isOk());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
        Artist testArtist = artistList.get(artistList.size() - 1);
        assertThat(testArtist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArtist.getPopularity()).isEqualTo(UPDATED_POPULARITY);
        assertThat(testArtist.getIdSpotify()).isEqualTo(UPDATED_ID_SPOTIFY);
        assertThat(testArtist.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testArtist.getFollowers()).isEqualTo(UPDATED_FOLLOWERS);
    }

    @Test
    @Transactional
    public void updateNonExistingArtist() throws Exception {
        int databaseSizeBeforeUpdate = artistRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(put("/api/artists").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artist)))
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArtist() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        int databaseSizeBeforeDelete = artistRepository.findAll().size();

        // Delete the artist
        restArtistMockMvc
            .perform(delete("/api/artists/{id}", artist.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
