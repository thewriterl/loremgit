import { Moment } from 'moment';
import { IImage } from 'app/shared/model/image.model';
import { IExternalURL } from 'app/shared/model/external-url.model';
import { IGenre } from 'app/shared/model/genre.model';
import { IArtist } from 'app/shared/model/artist.model';
import { AlbumType } from 'app/shared/model/enumerations/album-type.model';

export interface IAlbum {
  id?: number;
  albumType?: AlbumType;
  href?: string;
  idSpotify?: string;
  label?: string;
  name?: string;
  popularity?: number;
  releaseDate?: Moment;
  uri?: string;
  image?: IImage;
  externalURL?: IExternalURL;
  genres?: IGenre[];
  artists?: IArtist[];
}

export class Album implements IAlbum {
  constructor(
    public id?: number,
    public albumType?: AlbumType,
    public href?: string,
    public idSpotify?: string,
    public label?: string,
    public name?: string,
    public popularity?: number,
    public releaseDate?: Moment,
    public uri?: string,
    public image?: IImage,
    public externalURL?: IExternalURL,
    public genres?: IGenre[],
    public artists?: IArtist[]
  ) {}
}
