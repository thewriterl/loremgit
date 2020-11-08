package co.luizao.corporation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.luizao.corporation.MusicIntelligenceApp;
import co.luizao.corporation.domain.Segment;
import co.luizao.corporation.repository.SegmentRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SegmentResource} REST controller.
 */
@SpringBootTest(classes = MusicIntelligenceApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SegmentResourceIT {
    private static final Float DEFAULT_START = 1F;
    private static final Float UPDATED_START = 2F;

    private static final Float DEFAULT_DURATION = 1F;
    private static final Float UPDATED_DURATION = 2F;

    private static final Float DEFAULT_CONFIDENCE = 1F;
    private static final Float UPDATED_CONFIDENCE = 2F;

    private static final Float DEFAULT_LOUDNESS_START = 1F;
    private static final Float UPDATED_LOUDNESS_START = 2F;

    private static final Float DEFAULT_LOUDNESS_MAX = 1F;
    private static final Float UPDATED_LOUDNESS_MAX = 2F;

    private static final Float DEFAULT_LOUDNESS_MAX_TIME = 1F;
    private static final Float UPDATED_LOUDNESS_MAX_TIME = 2F;

    private static final Float DEFAULT_LOUDNESS_END = 1F;
    private static final Float UPDATED_LOUDNESS_END = 2F;

    @Autowired
    private SegmentRepository segmentRepository;

    @Mock
    private SegmentRepository segmentRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSegmentMockMvc;

    private Segment segment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segment createEntity(EntityManager em) {
        Segment segment = new Segment()
            .start(DEFAULT_START)
            .duration(DEFAULT_DURATION)
            .confidence(DEFAULT_CONFIDENCE)
            .loudnessStart(DEFAULT_LOUDNESS_START)
            .loudnessMax(DEFAULT_LOUDNESS_MAX)
            .loudnessMaxTime(DEFAULT_LOUDNESS_MAX_TIME)
            .loudnessEnd(DEFAULT_LOUDNESS_END);
        return segment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segment createUpdatedEntity(EntityManager em) {
        Segment segment = new Segment()
            .start(UPDATED_START)
            .duration(UPDATED_DURATION)
            .confidence(UPDATED_CONFIDENCE)
            .loudnessStart(UPDATED_LOUDNESS_START)
            .loudnessMax(UPDATED_LOUDNESS_MAX)
            .loudnessMaxTime(UPDATED_LOUDNESS_MAX_TIME)
            .loudnessEnd(UPDATED_LOUDNESS_END);
        return segment;
    }

    @BeforeEach
    public void initTest() {
        segment = createEntity(em);
    }

    @Test
    @Transactional
    public void createSegment() throws Exception {
        int databaseSizeBeforeCreate = segmentRepository.findAll().size();
        // Create the Segment
        restSegmentMockMvc
            .perform(post("/api/segments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segment)))
            .andExpect(status().isCreated());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeCreate + 1);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testSegment.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testSegment.getConfidence()).isEqualTo(DEFAULT_CONFIDENCE);
        assertThat(testSegment.getLoudnessStart()).isEqualTo(DEFAULT_LOUDNESS_START);
        assertThat(testSegment.getLoudnessMax()).isEqualTo(DEFAULT_LOUDNESS_MAX);
        assertThat(testSegment.getLoudnessMaxTime()).isEqualTo(DEFAULT_LOUDNESS_MAX_TIME);
        assertThat(testSegment.getLoudnessEnd()).isEqualTo(DEFAULT_LOUDNESS_END);
    }

    @Test
    @Transactional
    public void createSegmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = segmentRepository.findAll().size();

        // Create the Segment with an existing ID
        segment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentMockMvc
            .perform(post("/api/segments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segment)))
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSegments() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        // Get all the segmentList
        restSegmentMockMvc
            .perform(get("/api/segments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segment.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].confidence").value(hasItem(DEFAULT_CONFIDENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].loudnessStart").value(hasItem(DEFAULT_LOUDNESS_START.doubleValue())))
            .andExpect(jsonPath("$.[*].loudnessMax").value(hasItem(DEFAULT_LOUDNESS_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].loudnessMaxTime").value(hasItem(DEFAULT_LOUDNESS_MAX_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].loudnessEnd").value(hasItem(DEFAULT_LOUDNESS_END.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllSegmentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(segmentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSegmentMockMvc.perform(get("/api/segments?eagerload=true")).andExpect(status().isOk());

        verify(segmentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllSegmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(segmentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSegmentMockMvc.perform(get("/api/segments?eagerload=true")).andExpect(status().isOk());

        verify(segmentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        // Get the segment
        restSegmentMockMvc
            .perform(get("/api/segments/{id}", segment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segment.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.doubleValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.doubleValue()))
            .andExpect(jsonPath("$.confidence").value(DEFAULT_CONFIDENCE.doubleValue()))
            .andExpect(jsonPath("$.loudnessStart").value(DEFAULT_LOUDNESS_START.doubleValue()))
            .andExpect(jsonPath("$.loudnessMax").value(DEFAULT_LOUDNESS_MAX.doubleValue()))
            .andExpect(jsonPath("$.loudnessMaxTime").value(DEFAULT_LOUDNESS_MAX_TIME.doubleValue()))
            .andExpect(jsonPath("$.loudnessEnd").value(DEFAULT_LOUDNESS_END.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSegment() throws Exception {
        // Get the segment
        restSegmentMockMvc.perform(get("/api/segments/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();

        // Update the segment
        Segment updatedSegment = segmentRepository.findById(segment.getId()).get();
        // Disconnect from session so that the updates on updatedSegment are not directly saved in db
        em.detach(updatedSegment);
        updatedSegment
            .start(UPDATED_START)
            .duration(UPDATED_DURATION)
            .confidence(UPDATED_CONFIDENCE)
            .loudnessStart(UPDATED_LOUDNESS_START)
            .loudnessMax(UPDATED_LOUDNESS_MAX)
            .loudnessMaxTime(UPDATED_LOUDNESS_MAX_TIME)
            .loudnessEnd(UPDATED_LOUDNESS_END);

        restSegmentMockMvc
            .perform(
                put("/api/segments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedSegment))
            )
            .andExpect(status().isOk());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getStart()).isEqualTo(UPDATED_START);
        assertThat(testSegment.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testSegment.getConfidence()).isEqualTo(UPDATED_CONFIDENCE);
        assertThat(testSegment.getLoudnessStart()).isEqualTo(UPDATED_LOUDNESS_START);
        assertThat(testSegment.getLoudnessMax()).isEqualTo(UPDATED_LOUDNESS_MAX);
        assertThat(testSegment.getLoudnessMaxTime()).isEqualTo(UPDATED_LOUDNESS_MAX_TIME);
        assertThat(testSegment.getLoudnessEnd()).isEqualTo(UPDATED_LOUDNESS_END);
    }

    @Test
    @Transactional
    public void updateNonExistingSegment() throws Exception {
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentMockMvc
            .perform(put("/api/segments").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segment)))
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        int databaseSizeBeforeDelete = segmentRepository.findAll().size();

        // Delete the segment
        restSegmentMockMvc
            .perform(delete("/api/segments/{id}", segment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
