import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAudioFeatures } from 'app/shared/model/audio-features.model';

@Component({
  selector: 'jhi-audio-features-detail',
  templateUrl: './audio-features-detail.component.html',
})
export class AudioFeaturesDetailComponent implements OnInit {
  audioFeatures: IAudioFeatures | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ audioFeatures }) => (this.audioFeatures = audioFeatures));
  }

  previousState(): void {
    window.history.back();
  }
}
