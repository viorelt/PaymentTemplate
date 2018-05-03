package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.EUiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EUi and its DTO EUiDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EUiMapper extends EntityMapper<EUiDTO, EUi> {


    @Mapping(target = "options", ignore = true)
    EUi toEntity(EUiDTO eUiDTO);

    default EUi fromId(Long id) {
        if (id == null) {
            return null;
        }
        EUi eUi = new EUi();
        eUi.setId(id);
        return eUi;
    }
}
