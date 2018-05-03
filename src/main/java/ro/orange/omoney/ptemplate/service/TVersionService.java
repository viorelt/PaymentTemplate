package ro.orange.omoney.ptemplate.service;

import ro.orange.omoney.ptemplate.service.dto.TVersionDTO;
import java.util.List;

/**
 * Service Interface for managing TVersion.
 */
public interface TVersionService {

    /**
     * Save a tVersion.
     *
     * @param tVersionDTO the entity to save
     * @return the persisted entity
     */
    TVersionDTO save(TVersionDTO tVersionDTO);

    /**
     * Get all the tVersions.
     *
     * @return the list of entities
     */
    List<TVersionDTO> findAll();

    /**
     * Get the "id" tVersion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TVersionDTO findOne(Long id);

    /**
     * Delete the "id" tVersion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
