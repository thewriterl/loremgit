import { IArtist } from 'app/shared/model/artist.model';
import { IAlbum } from 'app/shared/model/album.model';

export interface IImage {
  id?: number;
  height?: number;
  url?: string;
  width?: number;
  artist?: IArtist;
  album?: IAlbum;
}

export class Image implements IImage {
  constructor(
    public id?: number,
    public height?: number,
    public url?: string,
    public width?: number,
    public artist?: IArtist,
    public album?: IAlbum
  ) {}
}
