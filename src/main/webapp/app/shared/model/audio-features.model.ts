import { ITrack } from 'app/shared/model/track.model';

export interface IAudioFeatures {
  id?: number;
  durationMs?: number;
  key?: number;
  mode?: number;
  timeSignature?: number;
  acousticness?: number;
  daceability?: number;
  energy?: number;
  instrumentalness?: number;
  liveness?: number;
  loudness?: number;
  speechiness?: number;
  valence?: number;
  tempo?: number;
  uri?: string;
  trackHref?: string;
  analysisUrl?: string;
  track?: ITrack;
}

export class AudioFeatures implements IAudioFeatures {
  constructor(
    public id?: number,
    public durationMs?: number,
    public key?: number,
    public mode?: number,
    public timeSignature?: number,
    public acousticness?: number,
    public daceability?: number,
    public energy?: number,
    public instrumentalness?: number,
    public liveness?: number,
    public loudness?: number,
    public speechiness?: number,
    public valence?: number,
    public tempo?: number,
    public uri?: string,
    public trackHref?: string,
    public analysisUrl?: string,
    public track?: ITrack
  ) {}
}
