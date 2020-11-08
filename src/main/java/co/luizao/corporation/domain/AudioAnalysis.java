package co.luizao.corporation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AudioAnalysis.
 */
@Entity
@Table(name = "audio_analysis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AudioAnalysis implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "audioAnalysis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Section> sections = new HashSet<>();

    @OneToMany(mappedBy = "audioAnalysis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TimeInterval> bars = new HashSet<>();

    @OneToMany(mappedBy = "audioAnalysis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TimeInterval> beats = new HashSet<>();

    @OneToMany(mappedBy = "audioAnalysis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Segment> segments = new HashSet<>();

    @OneToMany(mappedBy = "audioAnalysis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TimeInterval> tatums = new HashSet<>();

    @OneToOne(mappedBy = "audioAnalysis")
    @JsonIgnore
    private Track track;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public AudioAnalysis sections(Set<Section> sections) {
        this.sections = sections;
        return this;
    }

    public AudioAnalysis addSections(Section section) {
        this.sections.add(section);
        section.setAudioAnalysis(this);
        return this;
    }

    public AudioAnalysis removeSections(Section section) {
        this.sections.remove(section);
        section.setAudioAnalysis(null);
        return this;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public Set<TimeInterval> getBars() {
        return bars;
    }

    public AudioAnalysis bars(Set<TimeInterval> timeIntervals) {
        this.bars = timeIntervals;
        return this;
    }

    public AudioAnalysis addBars(TimeInterval timeInterval) {
        this.bars.add(timeInterval);
        timeInterval.setAudioAnalysis(this);
        return this;
    }

    public AudioAnalysis removeBars(TimeInterval timeInterval) {
        this.bars.remove(timeInterval);
        timeInterval.setAudioAnalysis(null);
        return this;
    }

    public void setBars(Set<TimeInterval> timeIntervals) {
        this.bars = timeIntervals;
    }

    public Set<TimeInterval> getBeats() {
        return beats;
    }

    public AudioAnalysis beats(Set<TimeInterval> timeIntervals) {
        this.beats = timeIntervals;
        return this;
    }

    public AudioAnalysis addBeats(TimeInterval timeInterval) {
        this.beats.add(timeInterval);
        timeInterval.setAudioAnalysis(this);
        return this;
    }

    public AudioAnalysis removeBeats(TimeInterval timeInterval) {
        this.beats.remove(timeInterval);
        timeInterval.setAudioAnalysis(null);
        return this;
    }

    public void setBeats(Set<TimeInterval> timeIntervals) {
        this.beats = timeIntervals;
    }

    public Set<Segment> getSegments() {
        return segments;
    }

    public AudioAnalysis segments(Set<Segment> segments) {
        this.segments = segments;
        return this;
    }

    public AudioAnalysis addSegments(Segment segment) {
        this.segments.add(segment);
        segment.setAudioAnalysis(this);
        return this;
    }

    public AudioAnalysis removeSegments(Segment segment) {
        this.segments.remove(segment);
        segment.setAudioAnalysis(null);
        return this;
    }

    public void setSegments(Set<Segment> segments) {
        this.segments = segments;
    }

    public Set<TimeInterval> getTatums() {
        return tatums;
    }

    public AudioAnalysis tatums(Set<TimeInterval> timeIntervals) {
        this.tatums = timeIntervals;
        return this;
    }

    public AudioAnalysis addTatums(TimeInterval timeInterval) {
        this.tatums.add(timeInterval);
        timeInterval.setAudioAnalysis(this);
        return this;
    }

    public AudioAnalysis removeTatums(TimeInterval timeInterval) {
        this.tatums.remove(timeInterval);
        timeInterval.setAudioAnalysis(null);
        return this;
    }

    public void setTatums(Set<TimeInterval> timeIntervals) {
        this.tatums = timeIntervals;
    }

    public Track getTrack() {
        return track;
    }

    public AudioAnalysis track(Track track) {
        this.track = track;
        return this;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AudioAnalysis)) {
            return false;
        }
        return id != null && id.equals(((AudioAnalysis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AudioAnalysis{" +
            "id=" + getId() +
            "}";
    }
}
