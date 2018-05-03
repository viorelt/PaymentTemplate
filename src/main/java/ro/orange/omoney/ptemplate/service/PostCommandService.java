package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.PostCommandDTO;
import java.util.List;

/**
 * Service Interface for managing PostCommand.
 */
public interface PostCommandService {

    /**
     * Save a postCommand.
     *
     * @param postCommandDTO the entity to save
     * @return the persisted entity
     */
    PostCommandDTO save(PostCommandDTO postCommandDTO);

    /**
     * Get all the postCommands.
     *
     * @return the list of entities
     */
    List<PostCommandDTO> findAll();

    /**
     * Get the "id" postCommand.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PostCommandDTO findOne(Long id);

    /**
     * Delete the "id" postCommand.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
