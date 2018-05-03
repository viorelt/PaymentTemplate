package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.TemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Template and its DTO TemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {TVersionMapper.class})
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {

    @Mapping(source = "lastVersion.id", target = "lastVersionId")
    TemplateDTO toDto(Template template);

    @Mapping(target = "versions", ignore = true)
    @Mapping(source = "lastVersionId", target = "lastVersion")
    Template toEntity(TemplateDTO templateDTO);

    default Template fromId(Long id) {
        if (id == null) {
            return null;
        }
        Template template = new Template();
        template.setId(id);
        return template;
    }
}
