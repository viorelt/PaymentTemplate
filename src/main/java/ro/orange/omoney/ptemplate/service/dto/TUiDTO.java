package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TUi entity.
 */
public class TUiDTO implements Serializable {

    private Long id;

    private String icon;

    private String name;

    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postCommandId) {
        this.postId = postCommandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TUiDTO tUiDTO = (TUiDTO) o;
        if(tUiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tUiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TUiDTO{" +
            "id=" + getId() +
            ", icon='" + getIcon() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
