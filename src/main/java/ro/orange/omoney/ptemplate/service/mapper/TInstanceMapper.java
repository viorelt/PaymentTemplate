package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.TInstanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TInstance and its DTO TInstanceDTO.
 */
@Mapper(componentModel = "spring", uses = {TVersionMapper.class})
public interface TInstanceMapper extends EntityMapper<TInstanceDTO, TInstance> {


    @Mapping(target = "properties", ignore = true)
    TInstance toEntity(TInstanceDTO tInstanceDTO);

    default TInstance fromId(Long id) {
        if (id == null) {
            return null;
        }
        TInstance tInstance = new TInstance();
        tInstance.setId(id);
        return tInstance;
    }
}
