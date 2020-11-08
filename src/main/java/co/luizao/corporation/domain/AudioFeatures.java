package co.luizao.corporation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AudioFeatures.
 */
@Entity
@Table(name = "audio_features")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AudioFeatures implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "duration_ms")
    private Integer durationMs;

    @Column(name = "key")
    private Integer key;

    @Column(name = "mode")
    private Integer mode;

    @Column(name = "time_signature")
    private Integer timeSignature;

    @Column(name = "acousticness")
    private Float acousticness;

    @Column(name = "daceability")
    private Float daceability;

    @Column(name = "energy")
    private Float energy;

    @Column(name = "instrumentalness")
    private Float instrumentalness;

    @Column(name = "liveness")
    private Float liveness;

    @Column(name = "loudness")
    private Float loudness;

    @Column(name = "speechiness")
    private Float speechiness;

    @Column(name = "valence")
    private Float valence;

    @Column(name = "tempo")
    private Float tempo;

    @Column(name = "uri")
    private String uri;

    @Column(name = "track_href")
    private String trackHref;

    @Column(name = "analysis_url")
    private String analysisUrl;

    @OneToOne(mappedBy = "audioFeatures")
    @JsonIgnore
    private Track track;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDurationMs() {
        return durationMs;
    }

    public AudioFeatures durationMs(Integer durationMs) {
        this.durationMs = durationMs;
        return this;
    }

    public void setDurationMs(Integer durationMs) {
        this.durationMs = durationMs;
    }

    public Integer getKey() {
        return key;
    }

    public AudioFeatures key(Integer key) {
        this.key = key;
        return this;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getMode() {
        return mode;
    }

    public AudioFeatures mode(Integer mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getTimeSignature() {
        return timeSignature;
    }

    public AudioFeatures timeSignature(Integer timeSignature) {
        this.timeSignature = timeSignature;
        return this;
    }

    public void setTimeSignature(Integer timeSignature) {
        this.timeSignature = timeSignature;
    }

    public Float getAcousticness() {
        return acousticness;
    }

    public AudioFeatures acousticness(Float acousticness) {
        this.acousticness = acousticness;
        return this;
    }

    public void setAcousticness(Float acousticness) {
        this.acousticness = acousticness;
    }

    public Float getDaceability() {
        return daceability;
    }

    public AudioFeatures daceability(Float daceability) {
        this.daceability = daceability;
        return this;
    }

    public void setDaceability(Float daceability) {
        this.daceability = daceability;
    }

    public Float getEnergy() {
        return energy;
    }

    public AudioFeatures energy(Float energy) {
        this.energy = energy;
        return this;
    }

    public void setEnergy(Float energy) {
        this.energy = energy;
    }

    public Float getInstrumentalness() {
        return instrumentalness;
    }

    public AudioFeatures instrumentalness(Float instrumentalness) {
        this.instrumentalness = instrumentalness;
        return this;
    }

    public void setInstrumentalness(Float instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public Float getLiveness() {
        return liveness;
    }

    public AudioFeatures liveness(Float liveness) {
        this.liveness = liveness;
        return this;
    }

    public void setLiveness(Float liveness) {
        this.liveness = liveness;
    }

    public Float getLoudness() {
        return loudness;
    }

    public AudioFeatures loudness(Float loudness) {
        this.loudness = loudness;
        return this;
    }

    public void setLoudness(Float loudness) {
        this.loudness = loudness;
    }

    public Float getSpeechiness() {
        return speechiness;
    }

    public AudioFeatures speechiness(Float speechiness) {
        this.speechiness = speechiness;
        return this;
    }

    public void setSpeechiness(Float speechiness) {
        this.speechiness = speechiness;
    }

    public Float getValence() {
        return valence;
    }

    public AudioFeatures valence(Float valence) {
        this.valence = valence;
        return this;
    }

    public void setValence(Float valence) {
        this.valence = valence;
    }

    public Float getTempo() {
        return tempo;
    }

    public AudioFeatures tempo(Float tempo) {
        this.tempo = tempo;
        return this;
    }

    public void setTempo(Float tempo) {
        this.tempo = tempo;
    }

    public String getUri() {
        return uri;
    }

    public AudioFeatures uri(String uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTrackHref() {
        return trackHref;
    }

    public AudioFeatures trackHref(String trackHref) {
        this.trackHref = trackHref;
        return this;
    }

    public void setTrackHref(String trackHref) {
        this.trackHref = trackHref;
    }

    public String getAnalysisUrl() {
        return analysisUrl;
    }

    public AudioFeatures analysisUrl(String analysisUrl) {
        this.analysisUrl = analysisUrl;
        return this;
    }

    public void setAnalysisUrl(String analysisUrl) {
        this.analysisUrl = analysisUrl;
    }

    public Track getTrack() {
        return track;
    }

    public AudioFeatures track(Track track) {
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
        if (!(o instanceof AudioFeatures)) {
            return false;
        }
        return id != null && id.equals(((AudioFeatures) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AudioFeatures{" +
            "id=" + getId() +
            ", durationMs=" + getDurationMs() +
            ", key=" + getKey() +
            ", mode=" + getMode() +
            ", timeSignature=" + getTimeSignature() +
            ", acousticness=" + getAcousticness() +
            ", daceability=" + getDaceability() +
            ", energy=" + getEnergy() +
            ", instrumentalness=" + getInstrumentalness() +
            ", liveness=" + getLiveness() +
            ", loudness=" + getLoudness() +
            ", speechiness=" + getSpeechiness() +
            ", valence=" + getValence() +
            ", tempo=" + getTempo() +
            ", uri='" + getUri() + "'" +
            ", trackHref='" + getTrackHref() + "'" +
            ", analysisUrl='" + getAnalysisUrl() + "'" +
            "}";
    }
}
