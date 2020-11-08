import { IArtist } from 'app/shared/model/artist.model';
import { IAlbum } from 'app/shared/model/album.model';

export interface IGenre {
  id?: number;
  genre?: string;
  artist?: IArtist;
  album?: IAlbum;
}

export class Genre implements IGenre {
  constructor(public id?: number, public genre?: string, public artist?: IArtist, public album?: IAlbum) {}
}
