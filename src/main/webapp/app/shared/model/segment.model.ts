import { IPitch } from 'app/shared/model/pitch.model';
import { ITimbre } from 'app/shared/model/timbre.model';
import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';

export interface ISegment {
  id?: number;
  start?: number;
  duration?: number;
  confidence?: number;
  loudnessStart?: number;
  loudnessMax?: number;
  loudnessMaxTime?: number;
  loudnessEnd?: number;
  pitches?: IPitch[];
  timbres?: ITimbre[];
  audioAnalysis?: IAudioAnalysis;
}

export class Segment implements ISegment {
  constructor(
    public id?: number,
    public start?: number,
    public duration?: number,
    public confidence?: number,
    public loudnessStart?: number,
    public loudnessMax?: number,
    public loudnessMaxTime?: number,
    public loudnessEnd?: number,
    public pitches?: IPitch[],
    public timbres?: ITimbre[],
    public audioAnalysis?: IAudioAnalysis
  ) {}
}
