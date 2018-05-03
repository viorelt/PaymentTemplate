package ro.orange.omoney.ptemplate.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TUi.
 */
@Entity
@Table(name = "t_ui")
public class TUi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "icon")
    private String icon;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private PostCommand post;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public TUi icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public TUi name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PostCommand getPost() {
        return post;
    }

    public TUi post(PostCommand postCommand) {
        this.post = postCommand;
        return this;
    }

    public void setPost(PostCommand postCommand) {
        this.post = postCommand;
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
        TUi tUi = (TUi) o;
        if (tUi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tUi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TUi{" +
            "id=" + getId() +
            ", icon='" + getIcon() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
