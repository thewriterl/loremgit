package co.luizao.corporation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ExternalURL.
 */
@Entity
@Table(name = "external_url")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExternalURL implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "href")
    private String href;

    @OneToOne(mappedBy = "externalURL")
    @JsonIgnore
    private Artist artist;

    @OneToOne(mappedBy = "externalURL")
    @JsonIgnore
    private Album album;

    @OneToOne(mappedBy = "externalURL")
    @JsonIgnore
    private Track track;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public ExternalURL source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHref() {
        return href;
    }

    public ExternalURL href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Artist getArtist() {
        return artist;
    }

    public ExternalURL artist(Artist artist) {
        this.artist = artist;
        return this;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public ExternalURL album(Album album) {
        this.album = album;
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Track getTrack() {
        return track;
    }

    public ExternalURL track(Track track) {
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
        if (!(o instanceof ExternalURL)) {
            return false;
        }
        return id != null && id.equals(((ExternalURL) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExternalURL{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", href='" + getHref() + "'" +
            "}";
    }
}
