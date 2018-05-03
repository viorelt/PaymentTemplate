package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.TVersionService;
import ro.orange.omoney.ptemplate.domain.TVersion;
import ro.orange.omoney.ptemplate.repository.TVersionRepository;
import ro.orange.omoney.ptemplate.service.dto.TVersionDTO;
import ro.orange.omoney.ptemplate.service.mapper.TVersionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TVersion.
 */
@Service
@Transactional
public class TVersionServiceImpl implements TVersionService {

    private final Logger log = LoggerFactory.getLogger(TVersionServiceImpl.class);

    private final TVersionRepository tVersionRepository;

    private final TVersionMapper tVersionMapper;

    public TVersionServiceImpl(TVersionRepository tVersionRepository, TVersionMapper tVersionMapper) {
        this.tVersionRepository = tVersionRepository;
        this.tVersionMapper = tVersionMapper;
    }

    /**
     * Save a tVersion.
     *
     * @param tVersionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TVersionDTO save(TVersionDTO tVersionDTO) {
        log.debug("Request to save TVersion : {}", tVersionDTO);
        TVersion tVersion = tVersionMapper.toEntity(tVersionDTO);
        tVersion = tVersionRepository.save(tVersion);
        return tVersionMapper.toDto(tVersion);
    }

    /**
     * Get all the tVersions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TVersionDTO> findAll() {
        log.debug("Request to get all TVersions");
        return tVersionRepository.findAll().stream()
            .map(tVersionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tVersion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TVersionDTO findOne(Long id) {
        log.debug("Request to get TVersion : {}", id);
        TVersion tVersion = tVersionRepository.findOne(id);
        return tVersionMapper.toDto(tVersion);
    }

    /**
     * Delete the tVersion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TVersion : {}", id);
        tVersionRepository.delete(id);
    }
}
