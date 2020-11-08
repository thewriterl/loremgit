package co.luizao.corporation.web.rest;

import co.luizao.corporation.domain.ExternalURL;
import co.luizao.corporation.repository.ExternalURLRepository;
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
 * REST controller for managing {@link co.luizao.corporation.domain.ExternalURL}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExternalURLResource {
    private final Logger log = LoggerFactory.getLogger(ExternalURLResource.class);

    private static final String ENTITY_NAME = "externalURL";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExternalURLRepository externalURLRepository;

    public ExternalURLResource(ExternalURLRepository externalURLRepository) {
        this.externalURLRepository = externalURLRepository;
    }

    /**
     * {@code POST  /external-urls} : Create a new externalURL.
     *
     * @param externalURL the externalURL to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new externalURL, or with status {@code 400 (Bad Request)} if the externalURL has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/external-urls")
    public ResponseEntity<ExternalURL> createExternalURL(@RequestBody ExternalURL externalURL) throws URISyntaxException {
        log.debug("REST request to save ExternalURL : {}", externalURL);
        if (externalURL.getId() != null) {
            throw new BadRequestAlertException("A new externalURL cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExternalURL result = externalURLRepository.save(externalURL);
        return ResponseEntity
            .created(new URI("/api/external-urls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /external-urls} : Updates an existing externalURL.
     *
     * @param externalURL the externalURL to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated externalURL,
     * or with status {@code 400 (Bad Request)} if the externalURL is not valid,
     * or with status {@code 500 (Internal Server Error)} if the externalURL couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/external-urls")
    public ResponseEntity<ExternalURL> updateExternalURL(@RequestBody ExternalURL externalURL) throws URISyntaxException {
        log.debug("REST request to update ExternalURL : {}", externalURL);
        if (externalURL.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExternalURL result = externalURLRepository.save(externalURL);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, externalURL.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /external-urls} : get all the externalURLS.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of externalURLS in body.
     */
    @GetMapping("/external-urls")
    public List<ExternalURL> getAllExternalURLS(@RequestParam(required = false) String filter) {
        if ("artist-is-null".equals(filter)) {
            log.debug("REST request to get all ExternalURLs where artist is null");
            return StreamSupport
                .stream(externalURLRepository.findAll().spliterator(), false)
                .filter(externalURL -> externalURL.getArtist() == null)
                .collect(Collectors.toList());
        }
        if ("album-is-null".equals(filter)) {
            log.debug("REST request to get all ExternalURLs where album is null");
            return StreamSupport
                .stream(externalURLRepository.findAll().spliterator(), false)
                .filter(externalURL -> externalURL.getAlbum() == null)
                .collect(Collectors.toList());
        }
        if ("track-is-null".equals(filter)) {
            log.debug("REST request to get all ExternalURLs where track is null");
            return StreamSupport
                .stream(externalURLRepository.findAll().spliterator(), false)
                .filter(externalURL -> externalURL.getTrack() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ExternalURLS");
        return externalURLRepository.findAll();
    }

    /**
     * {@code GET  /external-urls/:id} : get the "id" externalURL.
     *
     * @param id the id of the externalURL to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the externalURL, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/external-urls/{id}")
    public ResponseEntity<ExternalURL> getExternalURL(@PathVariable Long id) {
        log.debug("REST request to get ExternalURL : {}", id);
        Optional<ExternalURL> externalURL = externalURLRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(externalURL);
    }

    /**
     * {@code DELETE  /external-urls/:id} : delete the "id" externalURL.
     *
     * @param id the id of the externalURL to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/external-urls/{id}")
    public ResponseEntity<Void> deleteExternalURL(@PathVariable Long id) {
        log.debug("REST request to delete ExternalURL : {}", id);
        externalURLRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
