package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.Album;
import co.luizao.corporation.domain.enumeration.AlbumType;
import co.luizao.corporation.repository.AlbumRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AlbumResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlbumResourceIT {
    private static final AlbumType DEFAULT_ALBUM_TYPE = AlbumType.ALBUM;
    private static final AlbumType UPDATED_ALBUM_TYPE = AlbumType.SINGLE;

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_ID_SPOTIFY = "AAAAAAAAAA";
    private static final String UPDATED_ID_SPOTIFY = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POPULARITY = 1;
    private static final Integer UPDATED_POPULARITY = 2;

    private static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlbumMockMvc;

    private Album album;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createEntity(EntityManager em) {
        Album album = new Album()
            .albumType(DEFAULT_ALBUM_TYPE)
            .href(DEFAULT_HREF)
            .idSpotify(DEFAULT_ID_SPOTIFY)
            .label(DEFAULT_LABEL)
            .name(DEFAULT_NAME)
            .popularity(DEFAULT_POPULARITY)
            .releaseDate(DEFAULT_RELEASE_DATE)
            .uri(DEFAULT_URI);
        return album;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createUpdatedEntity(EntityManager em) {
        Album album = new Album()
            .albumType(UPDATED_ALBUM_TYPE)
            .href(UPDATED_HREF)
            .idSpotify(UPDATED_ID_SPOTIFY)
            .label(UPDATED_LABEL)
            .name(UPDATED_NAME)
            .popularity(UPDATED_POPULARITY)
            .releaseDate(UPDATED_RELEASE_DATE)
            .uri(UPDATED_URI);
        return album;
    }

    @BeforeEach
    public void initTest() {
        album = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlbum() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();
        // Create the Album
        restAlbumMockMvc
            .perform(post("/api/albums").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(album)))
            .andExpect(status().isCreated());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate + 1);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getAlbumType()).isEqualTo(DEFAULT_ALBUM_TYPE);
        assertThat(testAlbum.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testAlbum.getIdSpotify()).isEqualTo(DEFAULT_ID_SPOTIFY);
        assertThat(testAlbum.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAlbum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlbum.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
        assertThat(testAlbum.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testAlbum.getUri()).isEqualTo(DEFAULT_URI);
    }

    @Test
    @Transactional
    public void createAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album with an existing ID
        album.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlbumMockMvc
            .perform(post("/api/albums").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(album)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlbums() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList
        restAlbumMockMvc
            .perform(get("/api/albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].albumType").value(hasItem(DEFAULT_ALBUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].idSpotify").value(hasItem(DEFAULT_ID_SPOTIFY)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY)))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI)));
    }

    @Test
    @Transactional
    public void getAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get the album
        restAlbumMockMvc
            .perform(get("/api/albums/{id}", album.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(album.getId().intValue()))
            .andExpect(jsonPath("$.albumType").value(DEFAULT_ALBUM_TYPE.toString()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.idSpotify").value(DEFAULT_ID_SPOTIFY))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI));
    }

    @Test
    @Transactional
    public void getNonExistingAlbum() throws Exception {
        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Update the album
        Album updatedAlbum = albumRepository.findById(album.getId()).get();
        // Disconnect from session so that the updates on updatedAlbum are not directly saved in db
        em.detach(updatedAlbum);
        updatedAlbum
            .albumType(UPDATED_ALBUM_TYPE)
            .href(UPDATED_HREF)
            .idSpotify(UPDATED_ID_SPOTIFY)
            .label(UPDATED_LABEL)
            .name(UPDATED_NAME)
            .popularity(UPDATED_POPULARITY)
            .releaseDate(UPDATED_RELEASE_DATE)
            .uri(UPDATED_URI);

        restAlbumMockMvc
            .perform(put("/api/albums").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedAlbum)))
            .andExpect(status().isOk());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getAlbumType()).isEqualTo(UPDATED_ALBUM_TYPE);
        assertThat(testAlbum.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testAlbum.getIdSpotify()).isEqualTo(UPDATED_ID_SPOTIFY);
        assertThat(testAlbum.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAlbum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlbum.getPopularity()).isEqualTo(UPDATED_POPULARITY);
        assertThat(testAlbum.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testAlbum.getUri()).isEqualTo(UPDATED_URI);
    }

    @Test
    @Transactional
    public void updateNonExistingAlbum() throws Exception {
        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlbumMockMvc
            .perform(put("/api/albums").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(album)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeDelete = albumRepository.findAll().size();

        // Delete the album
        restAlbumMockMvc
            .perform(delete("/api/albums/{id}", album.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
