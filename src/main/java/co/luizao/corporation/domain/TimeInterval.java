package co.luizao.corporation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TimeInterval.
 */
@Entity
@Table(name = "time_interval")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TimeInterval implements Serializable {
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

    @ManyToOne
    @JsonIgnoreProperties(value = "bars", allowSetters = true)
    private AudioAnalysis audioAnalysis;

    @ManyToOne
    @JsonIgnoreProperties(value = "bars", allowSetters = true)
    private AudioAnalysis audioAnalysis;

    @ManyToOne
    @JsonIgnoreProperties(value = "bars", allowSetters = true)
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

    public TimeInterval start(Float start) {
        this.start = start;
        return this;
    }

    public void setStart(Float start) {
        this.start = start;
    }

    public Float getDuration() {
        return duration;
    }

    public TimeInterval duration(Float duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Float getConfidence() {
        return confidence;
    }

    public TimeInterval confidence(Float confidence) {
        this.confidence = confidence;
        return this;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    public TimeInterval audioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
        return this;
    }

    public void setAudioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
    }

    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    public TimeInterval audioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
        return this;
    }

    public void setAudioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
    }

    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    public TimeInterval audioAnalysis(AudioAnalysis audioAnalysis) {
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
        if (!(o instanceof TimeInterval)) {
            return false;
        }
        return id != null && id.equals(((TimeInterval) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeInterval{" +
            "id=" + getId() +
            ", start=" + getStart() +
            ", duration=" + getDuration() +
            ", confidence=" + getConfidence() +
            "}";
    }
}
