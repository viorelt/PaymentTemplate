package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.TInstanceDTO;
import java.util.List;

/**
 * Service Interface for managing TInstance.
 */
public interface TInstanceService {

    /**
     * Save a tInstance.
     *
     * @param tInstanceDTO the entity to save
     * @return the persisted entity
     */
    TInstanceDTO save(TInstanceDTO tInstanceDTO);

    /**
     * Get all the tInstances.
     *
     * @return the list of entities
     */
    List<TInstanceDTO> findAll();

    /**
     * Get the "id" tInstance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TInstanceDTO findOne(Long id);

    /**
     * Delete the "id" tInstance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
