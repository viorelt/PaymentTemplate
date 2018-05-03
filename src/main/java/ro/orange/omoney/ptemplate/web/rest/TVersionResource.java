package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.TVersionService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.TVersionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TVersion.
 */
@RestController
@RequestMapping("/api")
public class TVersionResource {

    private final Logger log = LoggerFactory.getLogger(TVersionResource.class);

    private static final String ENTITY_NAME = "tVersion";

    private final TVersionService tVersionService;

    public TVersionResource(TVersionService tVersionService) {
        this.tVersionService = tVersionService;
    }

    /**
     * POST  /t-versions : Create a new tVersion.
     *
     * @param tVersionDTO the tVersionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tVersionDTO, or with status 400 (Bad Request) if the tVersion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-versions")
    @Timed
    public ResponseEntity<TVersionDTO> createTVersion(@RequestBody TVersionDTO tVersionDTO) throws URISyntaxException {
        log.debug("REST request to save TVersion : {}", tVersionDTO);
        if (tVersionDTO.getId() != null) {
            throw new BadRequestAlertException("A new tVersion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TVersionDTO result = tVersionService.save(tVersionDTO);
        return ResponseEntity.created(new URI("/api/t-versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-versions : Updates an existing tVersion.
     *
     * @param tVersionDTO the tVersionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tVersionDTO,
     * or with status 400 (Bad Request) if the tVersionDTO is not valid,
     * or with status 500 (Internal Server Error) if the tVersionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-versions")
    @Timed
    public ResponseEntity<TVersionDTO> updateTVersion(@RequestBody TVersionDTO tVersionDTO) throws URISyntaxException {
        log.debug("REST request to update TVersion : {}", tVersionDTO);
        if (tVersionDTO.getId() == null) {
            return createTVersion(tVersionDTO);
        }
        TVersionDTO result = tVersionService.save(tVersionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tVersionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-versions : get all the tVersions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tVersions in body
     */
    @GetMapping("/t-versions")
    @Timed
    public List<TVersionDTO> getAllTVersions() {
        log.debug("REST request to get all TVersions");
        return tVersionService.findAll();
        }

    /**
     * GET  /t-versions/:id : get the "id" tVersion.
     *
     * @param id the id of the tVersionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tVersionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-versions/{id}")
    @Timed
    public ResponseEntity<TVersionDTO> getTVersion(@PathVariable Long id) {
        log.debug("REST request to get TVersion : {}", id);
        TVersionDTO tVersionDTO = tVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tVersionDTO));
    }

    /**
     * DELETE  /t-versions/:id : delete the "id" tVersion.
     *
     * @param id the id of the tVersionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-versions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTVersion(@PathVariable Long id) {
        log.debug("REST request to delete TVersion : {}", id);
        tVersionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
