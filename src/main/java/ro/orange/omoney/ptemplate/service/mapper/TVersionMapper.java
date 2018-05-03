package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.TVersionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TVersion and its DTO TVersionDTO.
 */
@Mapper(componentModel = "spring", uses = {TemplateMapper.class, TUiMapper.class, TBackendMapper.class})
public interface TVersionMapper extends EntityMapper<TVersionDTO, TVersion> {

    @Mapping(source = "template.id", target = "templateId")
    @Mapping(source = "ui.id", target = "uiId")
    @Mapping(source = "backend.id", target = "backendId")
    TVersionDTO toDto(TVersion tVersion);

    @Mapping(source = "templateId", target = "template")
    @Mapping(source = "uiId", target = "ui")
    @Mapping(source = "backendId", target = "backend")
    @Mapping(target = "elements", ignore = true)
    TVersion toEntity(TVersionDTO tVersionDTO);

    default TVersion fromId(Long id) {
        if (id == null) {
            return null;
        }
        TVersion tVersion = new TVersion();
        tVersion.setId(id);
        return tVersion;
    }
}
