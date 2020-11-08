package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.ExternalURL;
import co.luizao.corporation.repository.ExternalURLRepository;
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
 * Integration tests for the {@link ExternalURLResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExternalURLResourceIT {
    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    @Autowired
    private ExternalURLRepository externalURLRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExternalURLMockMvc;

    private ExternalURL externalURL;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalURL createEntity(EntityManager em) {
        ExternalURL externalURL = new ExternalURL().source(DEFAULT_SOURCE).href(DEFAULT_HREF);
        return externalURL;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalURL createUpdatedEntity(EntityManager em) {
        ExternalURL externalURL = new ExternalURL().source(UPDATED_SOURCE).href(UPDATED_HREF);
        return externalURL;
    }

    @BeforeEach
    public void initTest() {
        externalURL = createEntity(em);
    }

    @Test
    @Transactional
    public void createExternalURL() throws Exception {
        int databaseSizeBeforeCreate = externalURLRepository.findAll().size();
        // Create the ExternalURL
        restExternalURLMockMvc
            .perform(
                post("/api/external-urls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(externalURL))
            )
            .andExpect(status().isCreated());

        // Validate the ExternalURL in the database
        List<ExternalURL> externalURLList = externalURLRepository.findAll();
        assertThat(externalURLList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalURL testExternalURL = externalURLList.get(externalURLList.size() - 1);
        assertThat(testExternalURL.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testExternalURL.getHref()).isEqualTo(DEFAULT_HREF);
    }

    @Test
    @Transactional
    public void createExternalURLWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = externalURLRepository.findAll().size();

        // Create the ExternalURL with an existing ID
        externalURL.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternalURLMockMvc
            .perform(
                post("/api/external-urls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(externalURL))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalURL in the database
        List<ExternalURL> externalURLList = externalURLRepository.findAll();
        assertThat(externalURLList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExternalURLS() throws Exception {
        // Initialize the database
        externalURLRepository.saveAndFlush(externalURL);

        // Get all the externalURLList
        restExternalURLMockMvc
            .perform(get("/api/external-urls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalURL.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)));
    }

    @Test
    @Transactional
    public void getExternalURL() throws Exception {
        // Initialize the database
        externalURLRepository.saveAndFlush(externalURL);

        // Get the externalURL
        restExternalURLMockMvc
            .perform(get("/api/external-urls/{id}", externalURL.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(externalURL.getId().intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF));
    }

    @Test
    @Transactional
    public void getNonExistingExternalURL() throws Exception {
        // Get the externalURL
        restExternalURLMockMvc.perform(get("/api/external-urls/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExternalURL() throws Exception {
        // Initialize the database
        externalURLRepository.saveAndFlush(externalURL);

        int databaseSizeBeforeUpdate = externalURLRepository.findAll().size();

        // Update the externalURL
        ExternalURL updatedExternalURL = externalURLRepository.findById(externalURL.getId()).get();
        // Disconnect from session so that the updates on updatedExternalURL are not directly saved in db
        em.detach(updatedExternalURL);
        updatedExternalURL.source(UPDATED_SOURCE).href(UPDATED_HREF);

        restExternalURLMockMvc
            .perform(
                put("/api/external-urls")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedExternalURL))
            )
            .andExpect(status().isOk());

        // Validate the ExternalURL in the database
        List<ExternalURL> externalURLList = externalURLRepository.findAll();
        assertThat(externalURLList).hasSize(databaseSizeBeforeUpdate);
        ExternalURL testExternalURL = externalURLList.get(externalURLList.size() - 1);
        assertThat(testExternalURL.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testExternalURL.getHref()).isEqualTo(UPDATED_HREF);
    }

    @Test
    @Transactional
    public void updateNonExistingExternalURL() throws Exception {
        int databaseSizeBeforeUpdate = externalURLRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternalURLMockMvc
            .perform(
                put("/api/external-urls").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(externalURL))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExternalURL in the database
        List<ExternalURL> externalURLList = externalURLRepository.findAll();
        assertThat(externalURLList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExternalURL() throws Exception {
        // Initialize the database
        externalURLRepository.saveAndFlush(externalURL);

        int databaseSizeBeforeDelete = externalURLRepository.findAll().size();

        // Delete the externalURL
        restExternalURLMockMvc
            .perform(delete("/api/external-urls/{id}", externalURL.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExternalURL> externalURLList = externalURLRepository.findAll();
        assertThat(externalURLList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
