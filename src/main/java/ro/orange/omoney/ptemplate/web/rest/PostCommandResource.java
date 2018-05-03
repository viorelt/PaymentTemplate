package ro.orange.omoney.ptemplate.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.orange.omoney.ptemplate.service.PostCommandService;
import ro.orange.omoney.ptemplate.web.rest.errors.BadRequestAlertException;
import ro.orange.omoney.ptemplate.web.rest.util.HeaderUtil;
import ro.orange.omoney.ptemplate.service.dto.PostCommandDTO;
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
 * REST controller for managing PostCommand.
 */
@RestController
@RequestMapping("/api")
public class PostCommandResource {

    private final Logger log = LoggerFactory.getLogger(PostCommandResource.class);

    private static final String ENTITY_NAME = "postCommand";

    private final PostCommandService postCommandService;

    public PostCommandResource(PostCommandService postCommandService) {
        this.postCommandService = postCommandService;
    }

    /**
     * POST  /post-commands : Create a new postCommand.
     *
     * @param postCommandDTO the postCommandDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new postCommandDTO, or with status 400 (Bad Request) if the postCommand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/post-commands")
    @Timed
    public ResponseEntity<PostCommandDTO> createPostCommand(@RequestBody PostCommandDTO postCommandDTO) throws URISyntaxException {
        log.debug("REST request to save PostCommand : {}", postCommandDTO);
        if (postCommandDTO.getId() != null) {
            throw new BadRequestAlertException("A new postCommand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostCommandDTO result = postCommandService.save(postCommandDTO);
        return ResponseEntity.created(new URI("/api/post-commands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /post-commands : Updates an existing postCommand.
     *
     * @param postCommandDTO the postCommandDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated postCommandDTO,
     * or with status 400 (Bad Request) if the postCommandDTO is not valid,
     * or with status 500 (Internal Server Error) if the postCommandDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/post-commands")
    @Timed
    public ResponseEntity<PostCommandDTO> updatePostCommand(@RequestBody PostCommandDTO postCommandDTO) throws URISyntaxException {
        log.debug("REST request to update PostCommand : {}", postCommandDTO);
        if (postCommandDTO.getId() == null) {
            return createPostCommand(postCommandDTO);
        }
        PostCommandDTO result = postCommandService.save(postCommandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, postCommandDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /post-commands : get all the postCommands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of postCommands in body
     */
    @GetMapping("/post-commands")
    @Timed
    public List<PostCommandDTO> getAllPostCommands() {
        log.debug("REST request to get all PostCommands");
        return postCommandService.findAll();
        }

    /**
     * GET  /post-commands/:id : get the "id" postCommand.
     *
     * @param id the id of the postCommandDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the postCommandDTO, or with status 404 (Not Found)
     */
    @GetMapping("/post-commands/{id}")
    @Timed
    public ResponseEntity<PostCommandDTO> getPostCommand(@PathVariable Long id) {
        log.debug("REST request to get PostCommand : {}", id);
        PostCommandDTO postCommandDTO = postCommandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(postCommandDTO));
    }

    /**
     * DELETE  /post-commands/:id : delete the "id" postCommand.
     *
     * @param id the id of the postCommandDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/post-commands/{id}")
    @Timed
    public ResponseEntity<Void> deletePostCommand(@PathVariable Long id) {
        log.debug("REST request to delete PostCommand : {}", id);
        postCommandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
