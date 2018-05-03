package ro.orange.omoney.ptemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The entity is a payment template
 */
@ApiModel(description = "The entity is a payment template")
@Entity
@Table(name = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The code that uniquely identifies a template
     */
    @ApiModelProperty(value = "The code that uniquely identifies a template")
    @Column(name = "code")
    private String code;

    /**
     * The user account identifier
     */
    @ApiModelProperty(value = "The user account identifier")
    @Column(name = "account_id")
    private Long accountId;

    @OneToMany(mappedBy = "template")
    @JsonIgnore
    private Set<TVersion> versions = new HashSet<>();

    @ManyToOne
    private TVersion lastVersion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Template code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Template accountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Set<TVersion> getVersions() {
        return versions;
    }

    public Template versions(Set<TVersion> tVersions) {
        this.versions = tVersions;
        return this;
    }

    public Template addVersions(TVersion tVersion) {
        this.versions.add(tVersion);
        tVersion.setTemplate(this);
        return this;
    }

    public Template removeVersions(TVersion tVersion) {
        this.versions.remove(tVersion);
        tVersion.setTemplate(null);
        return this;
    }

    public void setVersions(Set<TVersion> tVersions) {
        this.versions = tVersions;
    }

    public TVersion getLastVersion() {
        return lastVersion;
    }

    public Template lastVersion(TVersion tVersion) {
        this.lastVersion = tVersion;
        return this;
    }

    public void setLastVersion(TVersion tVersion) {
        this.lastVersion = tVersion;
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
        Template template = (Template) o;
        if (template.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), template.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", accountId=" + getAccountId() +
            "}";
    }
}
