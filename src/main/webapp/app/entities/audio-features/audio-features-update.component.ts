import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAudioFeatures, AudioFeatures } from 'app/shared/model/audio-features.model';
import { AudioFeaturesService } from './audio-features.service';

@Component({
  selector: 'jhi-audio-features-update',
  templateUrl: './audio-features-update.component.html',
})
export class AudioFeaturesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    durationMs: [],
    key: [],
    mode: [],
    timeSignature: [],
    acousticness: [],
    daceability: [],
    energy: [],
    instrumentalness: [],
    liveness: [],
    loudness: [],
    speechiness: [],
    valence: [],
    tempo: [],
    uri: [],
    trackHref: [],
    analysisUrl: [],
  });

  constructor(protected audioFeaturesService: AudioFeaturesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ audioFeatures }) => {
      this.updateForm(audioFeatures);
    });
  }

  updateForm(audioFeatures: IAudioFeatures): void {
    this.editForm.patchValue({
      id: audioFeatures.id,
      durationMs: audioFeatures.durationMs,
      key: audioFeatures.key,
      mode: audioFeatures.mode,
      timeSignature: audioFeatures.timeSignature,
      acousticness: audioFeatures.acousticness,
      daceability: audioFeatures.daceability,
      energy: audioFeatures.energy,
      instrumentalness: audioFeatures.instrumentalness,
      liveness: audioFeatures.liveness,
      loudness: audioFeatures.loudness,
      speechiness: audioFeatures.speechiness,
      valence: audioFeatures.valence,
      tempo: audioFeatures.tempo,
      uri: audioFeatures.uri,
      trackHref: audioFeatures.trackHref,
      analysisUrl: audioFeatures.analysisUrl,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const audioFeatures = this.createFromForm();
    if (audioFeatures.id !== undefined) {
      this.subscribeToSaveResponse(this.audioFeaturesService.update(audioFeatures));
    } else {
      this.subscribeToSaveResponse(this.audioFeaturesService.create(audioFeatures));
    }
  }

  private createFromForm(): IAudioFeatures {
    return {
      ...new AudioFeatures(),
      id: this.editForm.get(['id'])!.value,
      durationMs: this.editForm.get(['durationMs'])!.value,
      key: this.editForm.get(['key'])!.value,
      mode: this.editForm.get(['mode'])!.value,
      timeSignature: this.editForm.get(['timeSignature'])!.value,
      acousticness: this.editForm.get(['acousticness'])!.value,
      daceability: this.editForm.get(['daceability'])!.value,
      energy: this.editForm.get(['energy'])!.value,
      instrumentalness: this.editForm.get(['instrumentalness'])!.value,
      liveness: this.editForm.get(['liveness'])!.value,
      loudness: this.editForm.get(['loudness'])!.value,
      speechiness: this.editForm.get(['speechiness'])!.value,
      valence: this.editForm.get(['valence'])!.value,
      tempo: this.editForm.get(['tempo'])!.value,
      uri: this.editForm.get(['uri'])!.value,
      trackHref: this.editForm.get(['trackHref'])!.value,
      analysisUrl: this.editForm.get(['analysisUrl'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAudioFeatures>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
