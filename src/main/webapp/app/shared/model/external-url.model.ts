import { IArtist } from 'app/shared/model/artist.model';
import { IAlbum } from 'app/shared/model/album.model';
import { ITrack } from 'app/shared/model/track.model';

export interface IExternalURL {
  id?: number;
  source?: string;
  href?: string;
  artist?: IArtist;
  album?: IAlbum;
  track?: ITrack;
}

export class ExternalURL implements IExternalURL {
  constructor(
    public id?: number,
    public source?: string,
    public href?: string,
    public artist?: IArtist,
    public album?: IAlbum,
    public track?: ITrack
  ) {}
}
