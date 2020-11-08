package co.luizao.corporation.web.rest;

import co.luizao.corporation.domain.AudioFeatures;
import co.luizao.corporation.repository.AudioFeaturesRepository;
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
 * REST controller for managing {@link co.luizao.corporation.domain.AudioFeatures}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AudioFeaturesResource {
    private final Logger log = LoggerFactory.getLogger(AudioFeaturesResource.class);

    private static final String ENTITY_NAME = "audioFeatures";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AudioFeaturesRepository audioFeaturesRepository;

    public AudioFeaturesResource(AudioFeaturesRepository audioFeaturesRepository) {
        this.audioFeaturesRepository = audioFeaturesRepository;
    }

    /**
     * {@code POST  /audio-features} : Create a new audioFeatures.
     *
     * @param audioFeatures the audioFeatures to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new audioFeatures, or with status {@code 400 (Bad Request)} if the audioFeatures has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audio-features")
    public ResponseEntity<AudioFeatures> createAudioFeatures(@RequestBody AudioFeatures audioFeatures) throws URISyntaxException {
        log.debug("REST request to save AudioFeatures : {}", audioFeatures);
        if (audioFeatures.getId() != null) {
            throw new BadRequestAlertException("A new audioFeatures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AudioFeatures result = audioFeaturesRepository.save(audioFeatures);
        return ResponseEntity
            .created(new URI("/api/audio-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audio-features} : Updates an existing audioFeatures.
     *
     * @param audioFeatures the audioFeatures to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated audioFeatures,
     * or with status {@code 400 (Bad Request)} if the audioFeatures is not valid,
     * or with status {@code 500 (Internal Server Error)} if the audioFeatures couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audio-features")
    public ResponseEntity<AudioFeatures> updateAudioFeatures(@RequestBody AudioFeatures audioFeatures) throws URISyntaxException {
        log.debug("REST request to update AudioFeatures : {}", audioFeatures);
        if (audioFeatures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AudioFeatures result = audioFeaturesRepository.save(audioFeatures);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, audioFeatures.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /audio-features} : get all the audioFeatures.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of audioFeatures in body.
     */
    @GetMapping("/audio-features")
    public List<AudioFeatures> getAllAudioFeatures(@RequestParam(required = false) String filter) {
        if ("track-is-null".equals(filter)) {
            log.debug("REST request to get all AudioFeaturess where track is null");
            return StreamSupport
                .stream(audioFeaturesRepository.findAll().spliterator(), false)
                .filter(audioFeatures -> audioFeatures.getTrack() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all AudioFeatures");
        return audioFeaturesRepository.findAll();
    }

    /**
     * {@code GET  /audio-features/:id} : get the "id" audioFeatures.
     *
     * @param id the id of the audioFeatures to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the audioFeatures, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audio-features/{id}")
    public ResponseEntity<AudioFeatures> getAudioFeatures(@PathVariable Long id) {
        log.debug("REST request to get AudioFeatures : {}", id);
        Optional<AudioFeatures> audioFeatures = audioFeaturesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(audioFeatures);
    }

    /**
     * {@code DELETE  /audio-features/:id} : delete the "id" audioFeatures.
     *
     * @param id the id of the audioFeatures to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audio-features/{id}")
    public ResponseEntity<Void> deleteAudioFeatures(@PathVariable Long id) {
        log.debug("REST request to delete AudioFeatures : {}", id);
        audioFeaturesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
