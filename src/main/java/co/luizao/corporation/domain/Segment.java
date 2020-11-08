package co.luizao.corporation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Segment.
 */
@Entity
@Table(name = "segment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Segment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start")
    private Float start;

    @Column(name = "duration")
    private Float duration;

    @Column(name = "confidence")
    private Float confidence;

    @Column(name = "loudness_start")
    private Float loudnessStart;

    @Column(name = "loudness_max")
    private Float loudnessMax;

    @Column(name = "loudness_max_time")
    private Float loudnessMaxTime;

    @Column(name = "loudness_end")
    private Float loudnessEnd;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "segment_pitches",
        joinColumns = @JoinColumn(name = "segment_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "pitches_id", referencedColumnName = "id")
    )
    private Set<Pitch> pitches = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "segment_timbre",
        joinColumns = @JoinColumn(name = "segment_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "timbre_id", referencedColumnName = "id")
    )
    private Set<Timbre> timbres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "segments", allowSetters = true)
    private AudioAnalysis audioAnalysis;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getStart() {
        return start;
    }

    public Segment start(Float start) {
        this.start = start;
        return this;
    }

    public void setStart(Float start) {
        this.start = start;
    }

    public Float getDuration() {
        return duration;
    }

    public Segment duration(Float duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Float getConfidence() {
        return confidence;
    }

    public Segment confidence(Float confidence) {
        this.confidence = confidence;
        return this;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public Float getLoudnessStart() {
        return loudnessStart;
    }

    public Segment loudnessStart(Float loudnessStart) {
        this.loudnessStart = loudnessStart;
        return this;
    }

    public void setLoudnessStart(Float loudnessStart) {
        this.loudnessStart = loudnessStart;
    }

    public Float getLoudnessMax() {
        return loudnessMax;
    }

    public Segment loudnessMax(Float loudnessMax) {
        this.loudnessMax = loudnessMax;
        return this;
    }

    public void setLoudnessMax(Float loudnessMax) {
        this.loudnessMax = loudnessMax;
    }

    public Float getLoudnessMaxTime() {
        return loudnessMaxTime;
    }

    public Segment loudnessMaxTime(Float loudnessMaxTime) {
        this.loudnessMaxTime = loudnessMaxTime;
        return this;
    }

    public void setLoudnessMaxTime(Float loudnessMaxTime) {
        this.loudnessMaxTime = loudnessMaxTime;
    }

    public Float getLoudnessEnd() {
        return loudnessEnd;
    }

    public Segment loudnessEnd(Float loudnessEnd) {
        this.loudnessEnd = loudnessEnd;
        return this;
    }

    public void setLoudnessEnd(Float loudnessEnd) {
        this.loudnessEnd = loudnessEnd;
    }

    public Set<Pitch> getPitches() {
        return pitches;
    }

    public Segment pitches(Set<Pitch> pitches) {
        this.pitches = pitches;
        return this;
    }

    public Segment addPitches(Pitch pitch) {
        this.pitches.add(pitch);
        pitch.getSegments().add(this);
        return this;
    }

    public Segment removePitches(Pitch pitch) {
        this.pitches.remove(pitch);
        pitch.getSegments().remove(this);
        return this;
    }

    public void setPitches(Set<Pitch> pitches) {
        this.pitches = pitches;
    }

    public Set<Timbre> getTimbres() {
        return timbres;
    }

    public Segment timbres(Set<Timbre> timbres) {
        this.timbres = timbres;
        return this;
    }

    public Segment addTimbre(Timbre timbre) {
        this.timbres.add(timbre);
        timbre.getSegments().add(this);
        return this;
    }

    public Segment removeTimbre(Timbre timbre) {
        this.timbres.remove(timbre);
        timbre.getSegments().remove(this);
        return this;
    }

    public void setTimbres(Set<Timbre> timbres) {
        this.timbres = timbres;
    }

    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    public Segment audioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
        return this;
    }

    public void setAudioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Segment)) {
            return false;
        }
        return id != null && id.equals(((Segment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Segment{" +
            "id=" + getId() +
            ", start=" + getStart() +
            ", duration=" + getDuration() +
            ", confidence=" + getConfidence() +
            ", loudnessStart=" + getLoudnessStart() +
            ", loudnessMax=" + getLoudnessMax() +
            ", loudnessMaxTime=" + getLoudnessMaxTime() +
            ", loudnessEnd=" + getLoudnessEnd() +
            "}";
    }
}
