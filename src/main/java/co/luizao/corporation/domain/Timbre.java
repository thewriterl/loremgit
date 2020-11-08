package co.luizao.corporation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Timbre.
 */
@Entity
@Table(name = "timbre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Timbre implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "timbre")
    private Float timbre;

    @ManyToMany(mappedBy = "timbres")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Segment> segments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTimbre() {
        return timbre;
    }

    public Timbre timbre(Float timbre) {
        this.timbre = timbre;
        return this;
    }

    public void setTimbre(Float timbre) {
        this.timbre = timbre;
    }

    public Set<Segment> getSegments() {
        return segments;
    }

    public Timbre segments(Set<Segment> segments) {
        this.segments = segments;
        return this;
    }

    public Timbre addSegment(Segment segment) {
        this.segments.add(segment);
        segment.getTimbres().add(this);
        return this;
    }

    public Timbre removeSegment(Segment segment) {
        this.segments.remove(segment);
        segment.getTimbres().remove(this);
        return this;
    }

    public void setSegments(Set<Segment> segments) {
        this.segments = segments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Timbre)) {
            return false;
        }
        return id != null && id.equals(((Timbre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Timbre{" +
            "id=" + getId() +
            ", timbre=" + getTimbre() +
            "}";
    }
}
