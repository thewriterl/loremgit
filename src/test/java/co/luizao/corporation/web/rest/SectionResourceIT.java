package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.Section;
import co.luizao.corporation.repository.SectionRepository;
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
 * Integration tests for the {@link SectionResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SectionResourceIT {
    private static final Float DEFAULT_START = 1F;
    private static final Float UPDATED_START = 2F;

    private static final Float DEFAULT_DURATION = 1F;
    private static final Float UPDATED_DURATION = 2F;

    private static final Float DEFAULT_CONFIDENCE = 1F;
    private static final Float UPDATED_CONFIDENCE = 2F;

    private static final Float DEFAULT_LOUDNESS = 1F;
    private static final Float UPDATED_LOUDNESS = 2F;

    private static final Float DEFAULT_TEMPO = 1F;
    private static final Float UPDATED_TEMPO = 2F;

    private static final Float DEFAULT_TEMPO_CONFIDENCE = 1F;
    private static final Float UPDATED_TEMPO_CONFIDENCE = 2F;

    private static final Integer DEFAULT_KEY = 1;
    private static final Integer UPDATED_KEY = 2;

    private static final Float DEFAULT_KEY_CONFIDENCE = 1F;
    private static final Float UPDATED_KEY_CONFIDENCE = 2F;

    private static final Integer DEFAULT_MODE = 1;
    private static final Integer UPDATED_MODE = 2;

    private static final Float DEFAULT_MODE_CONFIDENCE = 1F;
    private static final Float UPDATED_MODE_CONFIDENCE = 2F;

    private static final Integer DEFAULT_TIME_SIGNATURE = 1;
    private static final Integer UPDATED_TIME_SIGNATURE = 2;

    private static final Float DEFAULT_TIME_SIGNATURE_CONFIDENCE = 1F;
    private static final Float UPDATED_TIME_SIGNATURE_CONFIDENCE = 2F;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSectionMockMvc;

    private Section section;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Section createEntity(EntityManager em) {
        Section section = new Section()
            .start(DEFAULT_START)
            .duration(DEFAULT_DURATION)
            .confidence(DEFAULT_CONFIDENCE)
            .loudness(DEFAULT_LOUDNESS)
            .tempo(DEFAULT_TEMPO)
            .tempoConfidence(DEFAULT_TEMPO_CONFIDENCE)
            .key(DEFAULT_KEY)
            .keyConfidence(DEFAULT_KEY_CONFIDENCE)
            .mode(DEFAULT_MODE)
            .modeConfidence(DEFAULT_MODE_CONFIDENCE)
            .timeSignature(DEFAULT_TIME_SIGNATURE)
            .timeSignatureConfidence(DEFAULT_TIME_SIGNATURE_CONFIDENCE);
        return section;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Section createUpdatedEntity(EntityManager em) {
        Section section = new Section()
            .start(UPDATED_START)
            .duration(UPDATED_DURATION)
            .confidence(UPDATED_CONFIDENCE)
            .loudness(UPDATED_LOUDNESS)
            .tempo(UPDATED_TEMPO)
            .tempoConfidence(UPDATED_TEMPO_CONFIDENCE)
            .key(UPDATED_KEY)
            .keyConfidence(UPDATED_KEY_CONFIDENCE)
            .mode(UPDATED_MODE)
            .modeConfidence(UPDATED_MODE_CONFIDENCE)
            .timeSignature(UPDATED_TIME_SIGNATURE)
            .timeSignatureConfidence(UPDATED_TIME_SIGNATURE_CONFIDENCE);
        return section;
    }

    @BeforeEach
    public void initTest() {
        section = createEntity(em);
    }

    @Test
    @Transactional
    public void createSection() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();
        // Create the Section
        restSectionMockMvc
            .perform(post("/api/sections").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isCreated());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate + 1);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testSection.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testSection.getConfidence()).isEqualTo(DEFAULT_CONFIDENCE);
        assertThat(testSection.getLoudness()).isEqualTo(DEFAULT_LOUDNESS);
        assertThat(testSection.getTempo()).isEqualTo(DEFAULT_TEMPO);
        assertThat(testSection.getTempoConfidence()).isEqualTo(DEFAULT_TEMPO_CONFIDENCE);
        assertThat(testSection.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testSection.getKeyConfidence()).isEqualTo(DEFAULT_KEY_CONFIDENCE);
        assertThat(testSection.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testSection.getModeConfidence()).isEqualTo(DEFAULT_MODE_CONFIDENCE);
        assertThat(testSection.getTimeSignature()).isEqualTo(DEFAULT_TIME_SIGNATURE);
        assertThat(testSection.getTimeSignatureConfidence()).isEqualTo(DEFAULT_TIME_SIGNATURE_CONFIDENCE);
    }

    @Test
    @Transactional
    public void createSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // Create the Section with an existing ID
        section.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionMockMvc
            .perform(post("/api/sections").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSections() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList
        restSectionMockMvc
            .perform(get("/api/sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(section.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].confidence").value(hasItem(DEFAULT_CONFIDENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].loudness").value(hasItem(DEFAULT_LOUDNESS.doubleValue())))
            .andExpect(jsonPath("$.[*].tempo").value(hasItem(DEFAULT_TEMPO.doubleValue())))
            .andExpect(jsonPath("$.[*].tempoConfidence").value(hasItem(DEFAULT_TEMPO_CONFIDENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].keyConfidence").value(hasItem(DEFAULT_KEY_CONFIDENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE)))
            .andExpect(jsonPath("$.[*].modeConfidence").value(hasItem(DEFAULT_MODE_CONFIDENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].timeSignature").value(hasItem(DEFAULT_TIME_SIGNATURE)))
            .andExpect(jsonPath("$.[*].timeSignatureConfidence").value(hasItem(DEFAULT_TIME_SIGNATURE_CONFIDENCE.doubleValue())));
    }

    @Test
    @Transactional
    public void getSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get the section
        restSectionMockMvc
            .perform(get("/api/sections/{id}", section.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(section.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.doubleValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.doubleValue()))
            .andExpect(jsonPath("$.confidence").value(DEFAULT_CONFIDENCE.doubleValue()))
            .andExpect(jsonPath("$.loudness").value(DEFAULT_LOUDNESS.doubleValue()))
            .andExpect(jsonPath("$.tempo").value(DEFAULT_TEMPO.doubleValue()))
            .andExpect(jsonPath("$.tempoConfidence").value(DEFAULT_TEMPO_CONFIDENCE.doubleValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.keyConfidence").value(DEFAULT_KEY_CONFIDENCE.doubleValue()))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE))
            .andExpect(jsonPath("$.modeConfidence").value(DEFAULT_MODE_CONFIDENCE.doubleValue()))
            .andExpect(jsonPath("$.timeSignature").value(DEFAULT_TIME_SIGNATURE))
            .andExpect(jsonPath("$.timeSignatureConfidence").value(DEFAULT_TIME_SIGNATURE_CONFIDENCE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSection() throws Exception {
        // Get the section
        restSectionMockMvc.perform(get("/api/sections/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Update the section
        Section updatedSection = sectionRepository.findById(section.getId()).get();
        // Disconnect from session so that the updates on updatedSection are not directly saved in db
        em.detach(updatedSection);
        updatedSection
            .start(UPDATED_START)
            .duration(UPDATED_DURATION)
            .confidence(UPDATED_CONFIDENCE)
            .loudness(UPDATED_LOUDNESS)
            .tempo(UPDATED_TEMPO)
            .tempoConfidence(UPDATED_TEMPO_CONFIDENCE)
            .key(UPDATED_KEY)
            .keyConfidence(UPDATED_KEY_CONFIDENCE)
            .mode(UPDATED_MODE)
            .modeConfidence(UPDATED_MODE_CONFIDENCE)
            .timeSignature(UPDATED_TIME_SIGNATURE)
            .timeSignatureConfidence(UPDATED_TIME_SIGNATURE_CONFIDENCE);

        restSectionMockMvc
            .perform(
                put("/api/sections").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedSection))
            )
            .andExpect(status().isOk());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getStart()).isEqualTo(UPDATED_START);
        assertThat(testSection.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testSection.getConfidence()).isEqualTo(UPDATED_CONFIDENCE);
        assertThat(testSection.getLoudness()).isEqualTo(UPDATED_LOUDNESS);
        assertThat(testSection.getTempo()).isEqualTo(UPDATED_TEMPO);
        assertThat(testSection.getTempoConfidence()).isEqualTo(UPDATED_TEMPO_CONFIDENCE);
        assertThat(testSection.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testSection.getKeyConfidence()).isEqualTo(UPDATED_KEY_CONFIDENCE);
        assertThat(testSection.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testSection.getModeConfidence()).isEqualTo(UPDATED_MODE_CONFIDENCE);
        assertThat(testSection.getTimeSignature()).isEqualTo(UPDATED_TIME_SIGNATURE);
        assertThat(testSection.getTimeSignatureConfidence()).isEqualTo(UPDATED_TIME_SIGNATURE_CONFIDENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectionMockMvc
            .perform(put("/api/sections").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeDelete = sectionRepository.findAll().size();

        // Delete the section
        restSectionMockMvc
            .perform(delete("/api/sections/{id}", section.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
