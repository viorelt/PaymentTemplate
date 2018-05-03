package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PostCommand entity.
 */
public class PostCommandDTO implements Serializable {

    private Long id;

    private String labelKey;

    private String endpoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PostCommandDTO postCommandDTO = (PostCommandDTO) o;
        if(postCommandDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postCommandDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostCommandDTO{" +
            "id=" + getId() +
            ", labelKey='" + getLabelKey() + "'" +
            ", endpoint='" + getEndpoint() + "'" +
            "}";
    }
}
