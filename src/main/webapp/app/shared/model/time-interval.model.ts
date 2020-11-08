import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';

export interface ITimeInterval {
  id?: number;
  start?: number;
  duration?: number;
  confidence?: number;
  audioAnalysis?: IAudioAnalysis;
  audioAnalysis?: IAudioAnalysis;
  audioAnalysis?: IAudioAnalysis;
}

export class TimeInterval implements ITimeInterval {
  constructor(
    public id?: number,
    public start?: number,
    public duration?: number,
    public confidence?: number,
    public audioAnalysis?: IAudioAnalysis,
    public audioAnalysis?: IAudioAnalysis,
    public audioAnalysis?: IAudioAnalysis
  ) {}
}
