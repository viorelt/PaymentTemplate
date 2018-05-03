package ro.orange.omoney.ptemplate.service.impl;

import ro.orange.omoney.ptemplate.service.PostCommandService;
import ro.orange.omoney.ptemplate.domain.PostCommand;
import ro.orange.omoney.ptemplate.repository.PostCommandRepository;
import ro.orange.omoney.ptemplate.service.dto.PostCommandDTO;
import ro.orange.omoney.ptemplate.service.mapper.PostCommandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PostCommand.
 */
@Service
@Transactional
public class PostCommandServiceImpl implements PostCommandService {

    private final Logger log = LoggerFactory.getLogger(PostCommandServiceImpl.class);

    private final PostCommandRepository postCommandRepository;

    private final PostCommandMapper postCommandMapper;

    public PostCommandServiceImpl(PostCommandRepository postCommandRepository, PostCommandMapper postCommandMapper) {
        this.postCommandRepository = postCommandRepository;
        this.postCommandMapper = postCommandMapper;
    }

    /**
     * Save a postCommand.
     *
     * @param postCommandDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PostCommandDTO save(PostCommandDTO postCommandDTO) {
        log.debug("Request to save PostCommand : {}", postCommandDTO);
        PostCommand postCommand = postCommandMapper.toEntity(postCommandDTO);
        postCommand = postCommandRepository.save(postCommand);
        return postCommandMapper.toDto(postCommand);
    }

    /**
     * Get all the postCommands.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PostCommandDTO> findAll() {
        log.debug("Request to get all PostCommands");
        return postCommandRepository.findAll().stream()
            .map(postCommandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one postCommand by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PostCommandDTO findOne(Long id) {
        log.debug("Request to get PostCommand : {}", id);
        PostCommand postCommand = postCommandRepository.findOne(id);
        return postCommandMapper.toDto(postCommand);
    }

    /**
     * Delete the postCommand by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostCommand : {}", id);
        postCommandRepository.delete(id);
    }
}
