package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.Timbre;
import co.luizao.corporation.repository.TimbreRepository;
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
 * Integration tests for the {@link TimbreResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TimbreResourceIT {
    private static final Float DEFAULT_TIMBRE = 1F;
    private static final Float UPDATED_TIMBRE = 2F;

    @Autowired
    private TimbreRepository timbreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimbreMockMvc;

    private Timbre timbre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timbre createEntity(EntityManager em) {
        Timbre timbre = new Timbre().timbre(DEFAULT_TIMBRE);
        return timbre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timbre createUpdatedEntity(EntityManager em) {
        Timbre timbre = new Timbre().timbre(UPDATED_TIMBRE);
        return timbre;
    }

    @BeforeEach
    public void initTest() {
        timbre = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimbre() throws Exception {
        int databaseSizeBeforeCreate = timbreRepository.findAll().size();
        // Create the Timbre
        restTimbreMockMvc
            .perform(post("/api/timbres").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timbre)))
            .andExpect(status().isCreated());

        // Validate the Timbre in the database
        List<Timbre> timbreList = timbreRepository.findAll();
        assertThat(timbreList).hasSize(databaseSizeBeforeCreate + 1);
        Timbre testTimbre = timbreList.get(timbreList.size() - 1);
        assertThat(testTimbre.getTimbre()).isEqualTo(DEFAULT_TIMBRE);
    }

    @Test
    @Transactional
    public void createTimbreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timbreRepository.findAll().size();

        // Create the Timbre with an existing ID
        timbre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimbreMockMvc
            .perform(post("/api/timbres").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timbre)))
            .andExpect(status().isBadRequest());

        // Validate the Timbre in the database
        List<Timbre> timbreList = timbreRepository.findAll();
        assertThat(timbreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimbres() throws Exception {
        // Initialize the database
        timbreRepository.saveAndFlush(timbre);

        // Get all the timbreList
        restTimbreMockMvc
            .perform(get("/api/timbres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timbre.getId().intValue())))
            .andExpect(jsonPath("$.[*].timbre").value(hasItem(DEFAULT_TIMBRE.doubleValue())));
    }

    @Test
    @Transactional
    public void getTimbre() throws Exception {
        // Initialize the database
        timbreRepository.saveAndFlush(timbre);

        // Get the timbre
        restTimbreMockMvc
            .perform(get("/api/timbres/{id}", timbre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timbre.getId().intValue()))
            .andExpect(jsonPath("$.timbre").value(DEFAULT_TIMBRE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimbre() throws Exception {
        // Get the timbre
        restTimbreMockMvc.perform(get("/api/timbres/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimbre() throws Exception {
        // Initialize the database
        timbreRepository.saveAndFlush(timbre);

        int databaseSizeBeforeUpdate = timbreRepository.findAll().size();

        // Update the timbre
        Timbre updatedTimbre = timbreRepository.findById(timbre.getId()).get();
        // Disconnect from session so that the updates on updatedTimbre are not directly saved in db
        em.detach(updatedTimbre);
        updatedTimbre.timbre(UPDATED_TIMBRE);

        restTimbreMockMvc
            .perform(put("/api/timbres").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedTimbre)))
            .andExpect(status().isOk());

        // Validate the Timbre in the database
        List<Timbre> timbreList = timbreRepository.findAll();
        assertThat(timbreList).hasSize(databaseSizeBeforeUpdate);
        Timbre testTimbre = timbreList.get(timbreList.size() - 1);
        assertThat(testTimbre.getTimbre()).isEqualTo(UPDATED_TIMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTimbre() throws Exception {
        int databaseSizeBeforeUpdate = timbreRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimbreMockMvc
            .perform(put("/api/timbres").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timbre)))
            .andExpect(status().isBadRequest());

        // Validate the Timbre in the database
        List<Timbre> timbreList = timbreRepository.findAll();
        assertThat(timbreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimbre() throws Exception {
        // Initialize the database
        timbreRepository.saveAndFlush(timbre);

        int databaseSizeBeforeDelete = timbreRepository.findAll().size();

        // Delete the timbre
        restTimbreMockMvc
            .perform(delete("/api/timbres/{id}", timbre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Timbre> timbreList = timbreRepository.findAll();
        assertThat(timbreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
