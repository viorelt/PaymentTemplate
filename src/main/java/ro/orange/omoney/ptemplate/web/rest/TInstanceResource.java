package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.TInstanceService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.TInstanceDTO;
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
 * REST controller for managing TInstance.
 */
@RestController
@RequestMapping("/api")
public class TInstanceResource {

    private final Logger log = LoggerFactory.getLogger(TInstanceResource.class);

    private static final String ENTITY_NAME = "tInstance";

    private final TInstanceService tInstanceService;

    public TInstanceResource(TInstanceService tInstanceService) {
        this.tInstanceService = tInstanceService;
    }

    /**
     * POST  /t-instances : Create a new tInstance.
     *
     * @param tInstanceDTO the tInstanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tInstanceDTO, or with status 400 (Bad Request) if the tInstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-instances")
    @Timed
    public ResponseEntity<TInstanceDTO> createTInstance(@RequestBody TInstanceDTO tInstanceDTO) throws URISyntaxException {
        log.debug("REST request to save TInstance : {}", tInstanceDTO);
        if (tInstanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new tInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TInstanceDTO result = tInstanceService.save(tInstanceDTO);
        return ResponseEntity.created(new URI("/api/t-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-instances : Updates an existing tInstance.
     *
     * @param tInstanceDTO the tInstanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tInstanceDTO,
     * or with status 400 (Bad Request) if the tInstanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the tInstanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-instances")
    @Timed
    public ResponseEntity<TInstanceDTO> updateTInstance(@RequestBody TInstanceDTO tInstanceDTO) throws URISyntaxException {
        log.debug("REST request to update TInstance : {}", tInstanceDTO);
        if (tInstanceDTO.getId() == null) {
            return createTInstance(tInstanceDTO);
        }
        TInstanceDTO result = tInstanceService.save(tInstanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tInstanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-instances : get all the tInstances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tInstances in body
     */
    @GetMapping("/t-instances")
    @Timed
    public List<TInstanceDTO> getAllTInstances() {
        log.debug("REST request to get all TInstances");
        return tInstanceService.findAll();
        }

    /**
     * GET  /t-instances/:id : get the "id" tInstance.
     *
     * @param id the id of the tInstanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tInstanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/t-instances/{id}")
    @Timed
    public ResponseEntity<TInstanceDTO> getTInstance(@PathVariable Long id) {
        log.debug("REST request to get TInstance : {}", id);
        TInstanceDTO tInstanceDTO = tInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tInstanceDTO));
    }

    /**
     * DELETE  /t-instances/:id : delete the "id" tInstance.
     *
     * @param id the id of the tInstanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-instances/{id}")
    @Timed
    public ResponseEntity<Void> deleteTInstance(@PathVariable Long id) {
        log.debug("REST request to delete TInstance : {}", id);
        tInstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
