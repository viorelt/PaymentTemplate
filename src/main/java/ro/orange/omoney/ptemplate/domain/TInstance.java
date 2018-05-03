package ro.orange.omoney.ptemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TInstance.
 */
@Entity
@Table(name = "t_instance")
public class TInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToMany(mappedBy = "tInstance")
    @JsonIgnore
    private Set<EValue> properties = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tinstance_template",
               joinColumns = @JoinColumn(name="tinstances_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="templates_id", referencedColumnName="id"))
    private Set<TVersion> templates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TInstance createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public TInstance createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<EValue> getProperties() {
        return properties;
    }

    public TInstance properties(Set<EValue> eValues) {
        this.properties = eValues;
        return this;
    }

    public TInstance addProperties(EValue eValue) {
        this.properties.add(eValue);
        eValue.setTInstance(this);
        return this;
    }

    public TInstance removeProperties(EValue eValue) {
        this.properties.remove(eValue);
        eValue.setTInstance(null);
        return this;
    }

    public void setProperties(Set<EValue> eValues) {
        this.properties = eValues;
    }

    public Set<TVersion> getTemplates() {
        return templates;
    }

    public TInstance templates(Set<TVersion> tVersions) {
        this.templates = tVersions;
        return this;
    }

    public TInstance addTemplate(TVersion tVersion) {
        this.templates.add(tVersion);
        return this;
    }

    public TInstance removeTemplate(TVersion tVersion) {
        this.templates.remove(tVersion);
        return this;
    }

    public void setTemplates(Set<TVersion> tVersions) {
        this.templates = tVersions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TInstance tInstance = (TInstance) o;
        if (tInstance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tInstance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TInstance{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
