package ro.orange.omoney.ptemplate.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The ValueOption entity
 */
@ApiModel(description = "The ValueOption entity")
@Entity
@Table(name = "value_option")
public class ValueOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_label")
    private String label;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private EUi eUi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public ValueOption label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public ValueOption value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EUi getEUi() {
        return eUi;
    }

    public ValueOption eUi(EUi eUi) {
        this.eUi = eUi;
        return this;
    }

    public void setEUi(EUi eUi) {
        this.eUi = eUi;
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
        ValueOption valueOption = (ValueOption) o;
        if (valueOption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valueOption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValueOption{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
