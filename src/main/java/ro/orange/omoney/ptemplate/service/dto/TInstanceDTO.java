package ro.orange.omoney.ptemplate.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TInstance entity.
 */
public class TInstanceDTO implements Serializable {

    private Long id;

    private String createdBy;

    private Instant createdDate;

    private Set<TVersionDTO> templates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<TVersionDTO> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<TVersionDTO> tVersions) {
        this.templates = tVersions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TInstanceDTO tInstanceDTO = (TInstanceDTO) o;
        if(tInstanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tInstanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TInstanceDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
