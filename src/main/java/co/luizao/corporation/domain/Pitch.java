package co.luizao.corporation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pitch.
 */
@Entity
@Table(name = "pitch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pitch implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pitch")
    private Float pitch;

    @ManyToMany(mappedBy = "pitches")
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

    public Float getPitch() {
        return pitch;
    }

    public Pitch pitch(Float pitch) {
        this.pitch = pitch;
        return this;
    }

    public void setPitch(Float pitch) {
        this.pitch = pitch;
    }

    public Set<Segment> getSegments() {
        return segments;
    }

    public Pitch segments(Set<Segment> segments) {
        this.segments = segments;
        return this;
    }

    public Pitch addSegment(Segment segment) {
        this.segments.add(segment);
        segment.getPitches().add(this);
        return this;
    }

    public Pitch removeSegment(Segment segment) {
        this.segments.remove(segment);
        segment.getPitches().remove(this);
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
        if (!(o instanceof Pitch)) {
            return false;
        }
        return id != null && id.equals(((Pitch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pitch{" +
            "id=" + getId() +
            ", pitch=" + getPitch() +
            "}";
    }
}
