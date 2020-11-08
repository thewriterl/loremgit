import { ISegment } from 'app/shared/model/segment.model';

export interface IPitch {
  id?: number;
  pitch?: number;
  segments?: ISegment[];
}

export class Pitch implements IPitch {
  constructor(public id?: number, public pitch?: number, public segments?: ISegment[]) {}
}
