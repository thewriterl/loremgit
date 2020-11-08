package co.luizao.corporation.web.rest;

import co.luizao.corporation.domain.Timbre;
import co.luizao.corporation.repository.TimbreRepository;
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
 * REST controller for managing {@link co.luizao.corporation.domain.Timbre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TimbreResource {
    private final Logger log = LoggerFactory.getLogger(TimbreResource.class);

    private static final String ENTITY_NAME = "timbre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimbreRepository timbreRepository;

    public TimbreResource(TimbreRepository timbreRepository) {
        this.timbreRepository = timbreRepository;
    }

    /**
     * {@code POST  /timbres} : Create a new timbre.
     *
     * @param timbre the timbre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timbre, or with status {@code 400 (Bad Request)} if the timbre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/timbres")
    public ResponseEntity<Timbre> createTimbre(@RequestBody Timbre timbre) throws URISyntaxException {
        log.debug("REST request to save Timbre : {}", timbre);
        if (timbre.getId() != null) {
            throw new BadRequestAlertException("A new timbre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Timbre result = timbreRepository.save(timbre);
        return ResponseEntity
            .created(new URI("/api/timbres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /timbres} : Updates an existing timbre.
     *
     * @param timbre the timbre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timbre,
     * or with status {@code 400 (Bad Request)} if the timbre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timbre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/timbres")
    public ResponseEntity<Timbre> updateTimbre(@RequestBody Timbre timbre) throws URISyntaxException {
        log.debug("REST request to update Timbre : {}", timbre);
        if (timbre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Timbre result = timbreRepository.save(timbre);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timbre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /timbres} : get all the timbres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timbres in body.
     */
    @GetMapping("/timbres")
    public List<Timbre> getAllTimbres() {
        log.debug("REST request to get all Timbres");
        return timbreRepository.findAll();
    }

    /**
     * {@code GET  /timbres/:id} : get the "id" timbre.
     *
     * @param id the id of the timbre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timbre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/timbres/{id}")
    public ResponseEntity<Timbre> getTimbre(@PathVariable Long id) {
        log.debug("REST request to get Timbre : {}", id);
        Optional<Timbre> timbre = timbreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(timbre);
    }

    /**
     * {@code DELETE  /timbres/:id} : delete the "id" timbre.
     *
     * @param id the id of the timbre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/timbres/{id}")
    public ResponseEntity<Void> deleteTimbre(@PathVariable Long id) {
        log.debug("REST request to delete Timbre : {}", id);
        timbreRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
