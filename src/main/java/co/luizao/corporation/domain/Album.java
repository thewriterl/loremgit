package co.luizao.corporation.domain;

import co.luizao.corporation.domain.enumeration.AlbumType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Album.
 */
@Entity
@Table(name = "album")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Album implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "album_type")
    private AlbumType albumType;

    @Column(name = "href")
    private String href;

    @Column(name = "id_spotify")
    private String idSpotify;

    @Column(name = "label")
    private String label;

    @Column(name = "name")
    private String name;

    @Column(name = "popularity")
    private Integer popularity;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "uri")
    private String uri;

    @OneToOne
    @JoinColumn(unique = true)
    private Image image;

    @OneToOne
    @JoinColumn(unique = true)
    private ExternalURL externalURL;

    @OneToMany(mappedBy = "album")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "album")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Artist> artists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlbumType getAlbumType() {
        return albumType;
    }

    public Album albumType(AlbumType albumType) {
        this.albumType = albumType;
        return this;
    }

    public void setAlbumType(AlbumType albumType) {
        this.albumType = albumType;
    }

    public String getHref() {
        return href;
    }

    public Album href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIdSpotify() {
        return idSpotify;
    }

    public Album idSpotify(String idSpotify) {
        this.idSpotify = idSpotify;
        return this;
    }

    public void setIdSpotify(String idSpotify) {
        this.idSpotify = idSpotify;
    }

    public String getLabel() {
        return label;
    }

    public Album label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public Album name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public Album popularity(Integer popularity) {
        this.popularity = popularity;
        return this;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Album releaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUri() {
        return uri;
    }

    public Album uri(String uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Image getImage() {
        return image;
    }

    public Album image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ExternalURL getExternalURL() {
        return externalURL;
    }

    public Album externalURL(ExternalURL externalURL) {
        this.externalURL = externalURL;
        return this;
    }

    public void setExternalURL(ExternalURL externalURL) {
        this.externalURL = externalURL;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Album genres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public Album addGenre(Genre genre) {
        this.genres.add(genre);
        genre.setAlbum(this);
        return this;
    }

    public Album removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.setAlbum(null);
        return this;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public Album artists(Set<Artist> artists) {
        this.artists = artists;
        return this;
    }

    public Album addArtist(Artist artist) {
        this.artists.add(artist);
        artist.setAlbum(this);
        return this;
    }

    public Album removeArtist(Artist artist) {
        this.artists.remove(artist);
        artist.setAlbum(null);
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
        if (!(o instanceof Album)) {
            return false;
        }
        return id != null && id.equals(((Album) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Album{" +
            "id=" + getId() +
            ", albumType='" + getAlbumType() + "'" +
            ", href='" + getHref() + "'" +
            ", idSpotify='" + getIdSpotify() + "'" +
            ", label='" + getLabel() + "'" +
            ", name='" + getName() + "'" +
            ", popularity=" + getPopularity() +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", uri='" + getUri() + "'" +
            "}";
    }
}
