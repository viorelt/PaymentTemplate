package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.TInstanceService;
import ro.orange.omoney.ptemplate.domain.TInstance;
import ro.orange.omoney.ptemplate.repository.TInstanceRepository;
import ro.orange.omoney.ptemplate.service.dto.TInstanceDTO;
import ro.orange.omoney.ptemplate.service.mapper.TInstanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TInstance.
 */
@Service
@Transactional
public class TInstanceServiceImpl implements TInstanceService {

    private final Logger log = LoggerFactory.getLogger(TInstanceServiceImpl.class);

    private final TInstanceRepository tInstanceRepository;

    private final TInstanceMapper tInstanceMapper;

    public TInstanceServiceImpl(TInstanceRepository tInstanceRepository, TInstanceMapper tInstanceMapper) {
        this.tInstanceRepository = tInstanceRepository;
        this.tInstanceMapper = tInstanceMapper;
    }

    /**
     * Save a tInstance.
     *
     * @param tInstanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TInstanceDTO save(TInstanceDTO tInstanceDTO) {
        log.debug("Request to save TInstance : {}", tInstanceDTO);
        TInstance tInstance = tInstanceMapper.toEntity(tInstanceDTO);
        tInstance = tInstanceRepository.save(tInstance);
        return tInstanceMapper.toDto(tInstance);
    }

    /**
     * Get all the tInstances.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TInstanceDTO> findAll() {
        log.debug("Request to get all TInstances");
        return tInstanceRepository.findAllWithEagerRelationships().stream()
            .map(tInstanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tInstance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TInstanceDTO findOne(Long id) {
        log.debug("Request to get TInstance : {}", id);
        TInstance tInstance = tInstanceRepository.findOneWithEagerRelationships(id);
        return tInstanceMapper.toDto(tInstance);
    }

    /**
     * Delete the tInstance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TInstance : {}", id);
        tInstanceRepository.delete(id);
    }
}
