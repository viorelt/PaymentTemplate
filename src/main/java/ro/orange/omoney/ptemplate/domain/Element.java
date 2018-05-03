package ro.orange.omoney.ptemplate.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Element entity
 */
@ApiModel(description = "The Element entity")
@Entity
@Table(name = "element")
public class Element implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private TVersion tVersion;

    @OneToOne
    @JoinColumn(unique = true)
    private EUi ui;

    @OneToOne
    @JoinColumn(unique = true)
    private EBackend backend;

    @OneToOne
    @JoinColumn(unique = true)
    private EValue init;

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

    public Element code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Element name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TVersion getTVersion() {
        return tVersion;
    }

    public Element tVersion(TVersion tVersion) {
        this.tVersion = tVersion;
        return this;
    }

    public void setTVersion(TVersion tVersion) {
        this.tVersion = tVersion;
    }

    public EUi getUi() {
        return ui;
    }

    public Element ui(EUi eUi) {
        this.ui = eUi;
        return this;
    }

    public void setUi(EUi eUi) {
        this.ui = eUi;
    }

    public EBackend getBackend() {
        return backend;
    }

    public Element backend(EBackend eBackend) {
        this.backend = eBackend;
        return this;
    }

    public void setBackend(EBackend eBackend) {
        this.backend = eBackend;
    }

    public EValue getInit() {
        return init;
    }

    public Element init(EValue eValue) {
        this.init = eValue;
        return this;
    }

    public void setInit(EValue eValue) {
        this.init = eValue;
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
        Element element = (Element) o;
        if (element.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), element.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Element{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
