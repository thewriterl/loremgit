import { IExternalURL } from 'app/shared/model/external-url.model';
import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';
import { IAudioFeatures } from 'app/shared/model/audio-features.model';
import { IArtist } from 'app/shared/model/artist.model';

export interface ITrack {
  id?: number;
  discNumber?: number;
  durationMS?: number;
  explicit?: boolean;
  href?: string;
  idSpotify?: string;
  isPlayable?: boolean;
  name?: string;
  previewUrl?: string;
  trackNumber?: number;
  uri?: string;
  isLocal?: boolean;
  externalURL?: IExternalURL;
  audioAnalysis?: IAudioAnalysis;
  audioFeatures?: IAudioFeatures;
  artists?: IArtist[];
}

export class Track implements ITrack {
  constructor(
    public id?: number,
    public discNumber?: number,
    public durationMS?: number,
    public explicit?: boolean,
    public href?: string,
    public idSpotify?: string,
    public isPlayable?: boolean,
    public name?: string,
    public previewUrl?: string,
    public trackNumber?: number,
    public uri?: string,
    public isLocal?: boolean,
    public externalURL?: IExternalURL,
    public audioAnalysis?: IAudioAnalysis,
    public audioFeatures?: IAudioFeatures,
    public artists?: IArtist[]
  ) {
    this.explicit = this.explicit || false;
    this.isPlayable = this.isPlayable || false;
    this.isLocal = this.isLocal || false;
  }
}
