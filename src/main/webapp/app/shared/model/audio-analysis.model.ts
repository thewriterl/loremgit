import { ISection } from 'app/shared/model/section.model';
import { ITimeInterval } from 'app/shared/model/time-interval.model';
import { ISegment } from 'app/shared/model/segment.model';
import { ITrack } from 'app/shared/model/track.model';

export interface IAudioAnalysis {
  id?: number;
  sections?: ISection[];
  bars?: ITimeInterval[];
  beats?: ITimeInterval[];
  segments?: ISegment[];
  tatums?: ITimeInterval[];
  track?: ITrack;
}

export class AudioAnalysis implements IAudioAnalysis {
  constructor(
    public id?: number,
    public sections?: ISection[],
    public bars?: ITimeInterval[],
    public beats?: ITimeInterval[],
    public segments?: ISegment[],
    public tatums?: ITimeInterval[],
    public track?: ITrack
  ) {}
}
