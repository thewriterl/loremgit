package co.luizao.corporation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Artist.
 */
@Entity
@Table(name = "artist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Artist implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "popularity")
    private Float popularity;

    @Column(name = "id_spotify")
    private String idSpotify;

    @Column(name = "href")
    private String href;

    @Column(name = "followers")
    private Integer followers;

    @OneToOne
    @JoinColumn(unique = true)
    private Image image;

    @OneToOne
    @JoinColumn(unique = true)
    private ExternalURL externalURL;

    @OneToMany(mappedBy = "artist")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Genre> genres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "artists", allowSetters = true)
    private Album album;

    @ManyToOne
    @JsonIgnoreProperties(value = "artists", allowSetters = true)
    private Track track;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Artist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPopularity() {
        return popularity;
    }

    public Artist popularity(Float popularity) {
        this.popularity = popularity;
        return this;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getIdSpotify() {
        return idSpotify;
    }

    public Artist idSpotify(String idSpotify) {
        this.idSpotify = idSpotify;
        return this;
    }

    public void setIdSpotify(String idSpotify) {
        this.idSpotify = idSpotify;
    }

    public String getHref() {
        return href;
    }

    public Artist href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getFollowers() {
        return followers;
    }

    public Artist followers(Integer followers) {
        this.followers = followers;
        return this;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Image getImage() {
        return image;
    }

    public Artist image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ExternalURL getExternalURL() {
        return externalURL;
    }

    public Artist externalURL(ExternalURL externalURL) {
        this.externalURL = externalURL;
        return this;
    }

    public void setExternalURL(ExternalURL externalURL) {
        this.externalURL = externalURL;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Artist genres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public Artist addGenre(Genre genre) {
        this.genres.add(genre);
        genre.setArtist(this);
        return this;
    }

    public Artist removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.setArtist(null);
        return this;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Album getAlbum() {
        return album;
    }

    public Artist album(Album album) {
        this.album = album;
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Track getTrack() {
        return track;
    }

    public Artist track(Track track) {
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
        if (!(o instanceof Artist)) {
            return false;
        }
        return id != null && id.equals(((Artist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artist{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", popularity=" + getPopularity() +
            ", idSpotify='" + getIdSpotify() + "'" +
            ", href='" + getHref() + "'" +
            ", followers=" + getFollowers() +
            "}";
    }
}
