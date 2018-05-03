package ro.orange.omoney.ptemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The class will keep versions of a specific template
 * It is necessary to version a template to have
 * strong references from instances based on a particular version
 */
@ApiModel(description = "The class will keep versions of a specific template It is necessary to version a template to have strong references from instances based on a particular version")
@Entity
@Table(name = "t_version")
public class TVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The template version
     */
    @ApiModelProperty(value = "The template version")
    @Column(name = "version")
    private Long version;

    /**
     * Who created the version
     */
    @ApiModelProperty(value = "Who created the version")
    @Column(name = "created_by")
    private String createdBy;

    /**
     * When the version was created
     */
    @ApiModelProperty(value = "When the version was created")
    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne
    private Template template;

    @OneToOne
    @JoinColumn(unique = true)
    private TUi ui;

    @OneToOne
    @JoinColumn(unique = true)
    private TBackend backend;

    @OneToMany(mappedBy = "tVersion")
    @JsonIgnore
    private Set<Element> elements = new HashSet<>();

    @ManyToOne
    private TVersion parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public TVersion version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TVersion createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public TVersion createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Template getTemplate() {
        return template;
    }

    public TVersion template(Template template) {
        this.template = template;
        return this;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public TUi getUi() {
        return ui;
    }

    public TVersion ui(TUi tUi) {
        this.ui = tUi;
        return this;
    }

    public void setUi(TUi tUi) {
        this.ui = tUi;
    }

    public TBackend getBackend() {
        return backend;
    }

    public TVersion backend(TBackend tBackend) {
        this.backend = tBackend;
        return this;
    }

    public void setBackend(TBackend tBackend) {
        this.backend = tBackend;
    }

    public Set<Element> getElements() {
        return elements;
    }

    public TVersion elements(Set<Element> elements) {
        this.elements = elements;
        return this;
    }

    public TVersion addElements(Element element) {
        this.elements.add(element);
        element.setTVersion(this);
        return this;
    }

    public TVersion removeElements(Element element) {
        this.elements.remove(element);
        element.setTVersion(null);
        return this;
    }

    public void setElements(Set<Element> elements) {
        this.elements = elements;
    }

    public TVersion getParent() {
        return parent;
    }

    public TVersion parent(TVersion tVersion) {
        this.parent = tVersion;
        return this;
    }

    public void setParent(TVersion tVersion) {
        this.parent = tVersion;
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
        TVersion tVersion = (TVersion) o;
        if (tVersion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tVersion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TVersion{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
