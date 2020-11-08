import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';

export interface ISection {
  id?: number;
  start?: number;
  duration?: number;
  confidence?: number;
  loudness?: number;
  tempo?: number;
  tempoConfidence?: number;
  key?: number;
  keyConfidence?: number;
  mode?: number;
  modeConfidence?: number;
  timeSignature?: number;
  timeSignatureConfidence?: number;
  audioAnalysis?: IAudioAnalysis;
}

export class Section implements ISection {
  constructor(
    public id?: number,
    public start?: number,
    public duration?: number,
    public confidence?: number,
    public loudness?: number,
    public tempo?: number,
    public tempoConfidence?: number,
    public key?: number,
    public keyConfidence?: number,
    public mode?: number,
    public modeConfidence?: number,
    public timeSignature?: number,
    public timeSignatureConfidence?: number,
    public audioAnalysis?: IAudioAnalysis
  ) {}
}
