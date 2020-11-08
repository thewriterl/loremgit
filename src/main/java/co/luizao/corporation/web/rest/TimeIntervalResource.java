package co.luizao.corporation.web.rest;

import co.luizao.corporation.domain.TimeInterval;
import co.luizao.corporation.repository.TimeIntervalRepository;
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
 * REST controller for managing {@link co.luizao.corporation.domain.TimeInterval}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TimeIntervalResource {
    private final Logger log = LoggerFactory.getLogger(TimeIntervalResource.class);

    private static final String ENTITY_NAME = "timeInterval";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeIntervalRepository timeIntervalRepository;

    public TimeIntervalResource(TimeIntervalRepository timeIntervalRepository) {
        this.timeIntervalRepository = timeIntervalRepository;
    }

    /**
     * {@code POST  /time-intervals} : Create a new timeInterval.
     *
     * @param timeInterval the timeInterval to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeInterval, or with status {@code 400 (Bad Request)} if the timeInterval has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-intervals")
    public ResponseEntity<TimeInterval> createTimeInterval(@RequestBody TimeInterval timeInterval) throws URISyntaxException {
        log.debug("REST request to save TimeInterval : {}", timeInterval);
        if (timeInterval.getId() != null) {
            throw new BadRequestAlertException("A new timeInterval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeInterval result = timeIntervalRepository.save(timeInterval);
        return ResponseEntity
            .created(new URI("/api/time-intervals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-intervals} : Updates an existing timeInterval.
     *
     * @param timeInterval the timeInterval to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeInterval,
     * or with status {@code 400 (Bad Request)} if the timeInterval is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeInterval couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-intervals")
    public ResponseEntity<TimeInterval> updateTimeInterval(@RequestBody TimeInterval timeInterval) throws URISyntaxException {
        log.debug("REST request to update TimeInterval : {}", timeInterval);
        if (timeInterval.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeInterval result = timeIntervalRepository.save(timeInterval);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeInterval.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /time-intervals} : get all the timeIntervals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeIntervals in body.
     */
    @GetMapping("/time-intervals")
    public List<TimeInterval> getAllTimeIntervals() {
        log.debug("REST request to get all TimeIntervals");
        return timeIntervalRepository.findAll();
    }

    /**
     * {@code GET  /time-intervals/:id} : get the "id" timeInterval.
     *
     * @param id the id of the timeInterval to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeInterval, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-intervals/{id}")
    public ResponseEntity<TimeInterval> getTimeInterval(@PathVariable Long id) {
        log.debug("REST request to get TimeInterval : {}", id);
        Optional<TimeInterval> timeInterval = timeIntervalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(timeInterval);
    }

    /**
     * {@code DELETE  /time-intervals/:id} : delete the "id" timeInterval.
     *
     * @param id the id of the timeInterval to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-intervals/{id}")
    public ResponseEntity<Void> deleteTimeInterval(@PathVariable Long id) {
        log.debug("REST request to delete TimeInterval : {}", id);
        timeIntervalRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
