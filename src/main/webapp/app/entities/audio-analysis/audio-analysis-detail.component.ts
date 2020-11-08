import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';

@Component({
  selector: 'jhi-audio-analysis-detail',
  templateUrl: './audio-analysis-detail.component.html',
})
export class AudioAnalysisDetailComponent implements OnInit {
  audioAnalysis: IAudioAnalysis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ audioAnalysis }) => (this.audioAnalysis = audioAnalysis));
  }

  previousState(): void {
    window.history.back();
  }
}
