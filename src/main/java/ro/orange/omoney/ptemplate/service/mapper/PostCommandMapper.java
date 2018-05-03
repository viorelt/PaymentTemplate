package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.PostCommandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PostCommand and its DTO PostCommandDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostCommandMapper extends EntityMapper<PostCommandDTO, PostCommand> {



    default PostCommand fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostCommand postCommand = new PostCommand();
        postCommand.setId(id);
        return postCommand;
    }
}
