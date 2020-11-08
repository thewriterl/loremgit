import { ISegment } from 'app/shared/model/segment.model';

export interface ITimbre {
  id?: number;
  timbre?: number;
  segments?: ISegment[];
}

export class Timbre implements ITimbre {
  constructor(public id?: number, public timbre?: number, public segments?: ISegment[]) {}
}
