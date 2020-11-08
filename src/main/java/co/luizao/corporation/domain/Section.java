package co.luizao.corporation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Section.
 */
@Entity
@Table(name = "section")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Section implements Serializable {
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

    @Column(name = "loudness")
    private Float loudness;

    @Column(name = "tempo")
    private Float tempo;

    @Column(name = "tempo_confidence")
    private Float tempoConfidence;

    @Column(name = "key")
    private Integer key;

    @Column(name = "key_confidence")
    private Float keyConfidence;

    @Column(name = "mode")
    private Integer mode;

    @Column(name = "mode_confidence")
    private Float modeConfidence;

    @Column(name = "time_signature")
    private Integer timeSignature;

    @Column(name = "time_signature_confidence")
    private Float timeSignatureConfidence;

    @ManyToOne
    @JsonIgnoreProperties(value = "sections", allowSetters = true)
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

    public Section start(Float start) {
        this.start = start;
        return this;
    }

    public void setStart(Float start) {
        this.start = start;
    }

    public Float getDuration() {
        return duration;
    }

    public Section duration(Float duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Float getConfidence() {
        return confidence;
    }

    public Section confidence(Float confidence) {
        this.confidence = confidence;
        return this;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public Float getLoudness() {
        return loudness;
    }

    public Section loudness(Float loudness) {
        this.loudness = loudness;
        return this;
    }

    public void setLoudness(Float loudness) {
        this.loudness = loudness;
    }

    public Float getTempo() {
        return tempo;
    }

    public Section tempo(Float tempo) {
        this.tempo = tempo;
        return this;
    }

    public void setTempo(Float tempo) {
        this.tempo = tempo;
    }

    public Float getTempoConfidence() {
        return tempoConfidence;
    }

    public Section tempoConfidence(Float tempoConfidence) {
        this.tempoConfidence = tempoConfidence;
        return this;
    }

    public void setTempoConfidence(Float tempoConfidence) {
        this.tempoConfidence = tempoConfidence;
    }

    public Integer getKey() {
        return key;
    }

    public Section key(Integer key) {
        this.key = key;
        return this;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Float getKeyConfidence() {
        return keyConfidence;
    }

    public Section keyConfidence(Float keyConfidence) {
        this.keyConfidence = keyConfidence;
        return this;
    }

    public void setKeyConfidence(Float keyConfidence) {
        this.keyConfidence = keyConfidence;
    }

    public Integer getMode() {
        return mode;
    }

    public Section mode(Integer mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Float getModeConfidence() {
        return modeConfidence;
    }

    public Section modeConfidence(Float modeConfidence) {
        this.modeConfidence = modeConfidence;
        return this;
    }

    public void setModeConfidence(Float modeConfidence) {
        this.modeConfidence = modeConfidence;
    }

    public Integer getTimeSignature() {
        return timeSignature;
    }

    public Section timeSignature(Integer timeSignature) {
        this.timeSignature = timeSignature;
        return this;
    }

    public void setTimeSignature(Integer timeSignature) {
        this.timeSignature = timeSignature;
    }

    public Float getTimeSignatureConfidence() {
        return timeSignatureConfidence;
    }

    public Section timeSignatureConfidence(Float timeSignatureConfidence) {
        this.timeSignatureConfidence = timeSignatureConfidence;
        return this;
    }

    public void setTimeSignatureConfidence(Float timeSignatureConfidence) {
        this.timeSignatureConfidence = timeSignatureConfidence;
    }

    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    public Section audioAnalysis(AudioAnalysis audioAnalysis) {
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
        if (!(o instanceof Section)) {
            return false;
        }
        return id != null && id.equals(((Section) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Section{" +
            "id=" + getId() +
            ", start=" + getStart() +
            ", duration=" + getDuration() +
            ", confidence=" + getConfidence() +
            ", loudness=" + getLoudness() +
            ", tempo=" + getTempo() +
            ", tempoConfidence=" + getTempoConfidence() +
            ", key=" + getKey() +
            ", keyConfidence=" + getKeyConfidence() +
            ", mode=" + getMode() +
            ", modeConfidence=" + getModeConfidence() +
            ", timeSignature=" + getTimeSignature() +
            ", timeSignatureConfidence=" + getTimeSignatureConfidence() +
            "}";
    }
}
