package co.luizao.corporation.web.rest;

import co.luizao.corporation.domain.AudioAnalysis;
import co.luizao.corporation.repository.AudioAnalysisRepository;
import co.luizao.corporation.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link co.luizao.corporation.domain.AudioAnalysis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AudioAnalysisResource {
    private final Logger log = LoggerFactory.getLogger(AudioAnalysisResource.class);

    private static final String ENTITY_NAME = "audioAnalysis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AudioAnalysisRepository audioAnalysisRepository;

    public AudioAnalysisResource(AudioAnalysisRepository audioAnalysisRepository) {
        this.audioAnalysisRepository = audioAnalysisRepository;
    }

    /**
     * {@code POST  /audio-analyses} : Create a new audioAnalysis.
     *
     * @param audioAnalysis the audioAnalysis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new audioAnalysis, or with status {@code 400 (Bad Request)} if the audioAnalysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audio-analyses")
    public ResponseEntity<AudioAnalysis> createAudioAnalysis(@RequestBody AudioAnalysis audioAnalysis) throws URISyntaxException {
        log.debug("REST request to save AudioAnalysis : {}", audioAnalysis);
        if (audioAnalysis.getId() != null) {
            throw new BadRequestAlertException("A new audioAnalysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AudioAnalysis result = audioAnalysisRepository.save(audioAnalysis);
        return ResponseEntity
            .created(new URI("/api/audio-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audio-analyses} : Updates an existing audioAnalysis.
     *
     * @param audioAnalysis the audioAnalysis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated audioAnalysis,
     * or with status {@code 400 (Bad Request)} if the audioAnalysis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the audioAnalysis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audio-analyses")
    public ResponseEntity<AudioAnalysis> updateAudioAnalysis(@RequestBody AudioAnalysis audioAnalysis) throws URISyntaxException {
        log.debug("REST request to update AudioAnalysis : {}", audioAnalysis);
        if (audioAnalysis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AudioAnalysis result = audioAnalysisRepository.save(audioAnalysis);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, audioAnalysis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /audio-analyses} : get all the audioAnalyses.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of audioAnalyses in body.
     */
    @GetMapping("/audio-analyses")
    public List<AudioAnalysis> getAllAudioAnalyses(@RequestParam(required = false) String filter) {
        if ("track-is-null".equals(filter)) {
            log.debug("REST request to get all AudioAnalysiss where track is null");
            return StreamSupport
                .stream(audioAnalysisRepository.findAll().spliterator(), false)
                .filter(audioAnalysis -> audioAnalysis.getTrack() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all AudioAnalyses");
        return audioAnalysisRepository.findAll();
    }

    /**
     * {@code GET  /audio-analyses/:id} : get the "id" audioAnalysis.
     *
     * @param id the id of the audioAnalysis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the audioAnalysis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audio-analyses/{id}")
    public ResponseEntity<AudioAnalysis> getAudioAnalysis(@PathVariable Long id) {
        log.debug("REST request to get AudioAnalysis : {}", id);
        Optional<AudioAnalysis> audioAnalysis = audioAnalysisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(audioAnalysis);
    }

    /**
     * {@code DELETE  /audio-analyses/:id} : delete the "id" audioAnalysis.
     *
     * @param id the id of the audioAnalysis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audio-analyses/{id}")
    public ResponseEntity<Void> deleteAudioAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete AudioAnalysis : {}", id);
        audioAnalysisRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
