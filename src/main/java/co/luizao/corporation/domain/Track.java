package co.luizao.corporation.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Track.
 */
@Entity
@Table(name = "track")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Track implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "disc_number")
    private Integer discNumber;

    @Column(name = "duration_ms")
    private Integer durationMS;

    @Column(name = "explicit")
    private Boolean explicit;

    @Column(name = "href")
    private String href;

    @Column(name = "id_spotify")
    private String idSpotify;

    @Column(name = "is_playable")
    private Boolean isPlayable;

    @Column(name = "name")
    private String name;

    @Column(name = "preview_url")
    private String previewUrl;

    @Column(name = "track_number")
    private Integer trackNumber;

    @Column(name = "uri")
    private String uri;

    @Column(name = "is_local")
    private Boolean isLocal;

    @OneToOne
    @JoinColumn(unique = true)
    private ExternalURL externalURL;

    @OneToOne
    @JoinColumn(unique = true)
    private AudioAnalysis audioAnalysis;

    @OneToOne
    @JoinColumn(unique = true)
    private AudioFeatures audioFeatures;

    @OneToMany(mappedBy = "track")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Artist> artists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiscNumber() {
        return discNumber;
    }

    public Track discNumber(Integer discNumber) {
        this.discNumber = discNumber;
        return this;
    }

    public void setDiscNumber(Integer discNumber) {
        this.discNumber = discNumber;
    }

    public Integer getDurationMS() {
        return durationMS;
    }

    public Track durationMS(Integer durationMS) {
        this.durationMS = durationMS;
        return this;
    }

    public void setDurationMS(Integer durationMS) {
        this.durationMS = durationMS;
    }

    public Boolean isExplicit() {
        return explicit;
    }

    public Track explicit(Boolean explicit) {
        this.explicit = explicit;
        return this;
    }

    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    public String getHref() {
        return href;
    }

    public Track href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIdSpotify() {
        return idSpotify;
    }

    public Track idSpotify(String idSpotify) {
        this.idSpotify = idSpotify;
        return this;
    }

    public void setIdSpotify(String idSpotify) {
        this.idSpotify = idSpotify;
    }

    public Boolean isIsPlayable() {
        return isPlayable;
    }

    public Track isPlayable(Boolean isPlayable) {
        this.isPlayable = isPlayable;
        return this;
    }

    public void setIsPlayable(Boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    public String getName() {
        return name;
    }

    public Track name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public Track previewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
        return this;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public Track trackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
        return this;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getUri() {
        return uri;
    }

    public Track uri(String uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Boolean isIsLocal() {
        return isLocal;
    }

    public Track isLocal(Boolean isLocal) {
        this.isLocal = isLocal;
        return this;
    }

    public void setIsLocal(Boolean isLocal) {
        this.isLocal = isLocal;
    }

    public ExternalURL getExternalURL() {
        return externalURL;
    }

    public Track externalURL(ExternalURL externalURL) {
        this.externalURL = externalURL;
        return this;
    }

    public void setExternalURL(ExternalURL externalURL) {
        this.externalURL = externalURL;
    }

    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    public Track audioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
        return this;
    }

    public void setAudioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
    }

    public AudioFeatures getAudioFeatures() {
        return audioFeatures;
    }

    public Track audioFeatures(AudioFeatures audioFeatures) {
        this.audioFeatures = audioFeatures;
        return this;
    }

    public void setAudioFeatures(AudioFeatures audioFeatures) {
        this.audioFeatures = audioFeatures;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public Track artists(Set<Artist> artists) {
        this.artists = artists;
        return this;
    }

    public Track addArtist(Artist artist) {
        this.artists.add(artist);
        artist.setTrack(this);
        return this;
    }

    public Track removeArtist(Artist artist) {
        this.artists.remove(artist);
        artist.setTrack(null);
        return this;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Track)) {
            return false;
        }
        return id != null && id.equals(((Track) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Track{" +
            "id=" + getId() +
            ", discNumber=" + getDiscNumber() +
            ", durationMS=" + getDurationMS() +
            ", explicit='" + isExplicit() + "'" +
            ", href='" + getHref() + "'" +
            ", idSpotify='" + getIdSpotify() + "'" +
            ", isPlayable='" + isIsPlayable() + "'" +
            ", name='" + getName() + "'" +
            ", previewUrl='" + getPreviewUrl() + "'" +
            ", trackNumber=" + getTrackNumber() +
            ", uri='" + getUri() + "'" +
            ", isLocal='" + isIsLocal() + "'" +
            "}";
    }
}
