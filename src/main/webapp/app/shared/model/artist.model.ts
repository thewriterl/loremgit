import { IImage } from 'app/shared/model/image.model';
import { IExternalURL } from 'app/shared/model/external-url.model';
import { IGenre } from 'app/shared/model/genre.model';
import { IAlbum } from 'app/shared/model/album.model';
import { ITrack } from 'app/shared/model/track.model';

export interface IArtist {
  id?: number;
  name?: string;
  popularity?: number;
  idSpotify?: string;
  href?: string;
  followers?: number;
  image?: IImage;
  externalURL?: IExternalURL;
  genres?: IGenre[];
  album?: IAlbum;
  track?: ITrack;
}

export class Artist implements IArtist {
  constructor(
    public id?: number,
    public name?: string,
    public popularity?: number,
    public idSpotify?: string,
    public href?: string,
    public followers?: number,
    public image?: IImage,
    public externalURL?: IExternalURL,
    public genres?: IGenre[],
    public album?: IAlbum,
    public track?: ITrack
  ) {}
}
