package co.luizao.corporation.web.rest;

import co.luizao.corporation.domain.Pitch;
import co.luizao.corporation.repository.PitchRepository;
import co.luizao.corporation.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link co.luizao.corporation.domain.Pitch}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PitchResource {
    private final Logger log = LoggerFactory.getLogger(PitchResource.class);

    private static final String ENTITY_NAME = "pitch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PitchRepository pitchRepository;

    public PitchResource(PitchRepository pitchRepository) {
        this.pitchRepository = pitchRepository;
    }

    /**
     * {@code POST  /pitches} : Create a new pitch.
     *
     * @param pitch the pitch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pitch, or with status {@code 400 (Bad Request)} if the pitch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pitches")
    public ResponseEntity<Pitch> createPitch(@RequestBody Pitch pitch) throws URISyntaxException {
        log.debug("REST request to save Pitch : {}", pitch);
        if (pitch.getId() != null) {
            throw new BadRequestAlertException("A new pitch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pitch result = pitchRepository.save(pitch);
        return ResponseEntity
            .created(new URI("/api/pitches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pitches} : Updates an existing pitch.
     *
     * @param pitch the pitch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pitch,
     * or with status {@code 400 (Bad Request)} if the pitch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pitch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pitches")
    public ResponseEntity<Pitch> updatePitch(@RequestBody Pitch pitch) throws URISyntaxException {
        log.debug("REST request to update Pitch : {}", pitch);
        if (pitch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pitch result = pitchRepository.save(pitch);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pitch.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pitches} : get all the pitches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pitches in body.
     */
    @GetMapping("/pitches")
    public List<Pitch> getAllPitches() {
        log.debug("REST request to get all Pitches");
        return pitchRepository.findAll();
    }

    /**
     * {@code GET  /pitches/:id} : get the "id" pitch.
     *
     * @param id the id of the pitch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pitch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pitches/{id}")
    public ResponseEntity<Pitch> getPitch(@PathVariable Long id) {
        log.debug("REST request to get Pitch : {}", id);
        Optional<Pitch> pitch = pitchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pitch);
    }

    /**
     * {@code DELETE  /pitches/:id} : delete the "id" pitch.
     *
     * @param id the id of the pitch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pitches/{id}")
    public ResponseEntity<Void> deletePitch(@PathVariable Long id) {
        log.debug("REST request to delete Pitch : {}", id);
        pitchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
