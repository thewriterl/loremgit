import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITrack, Track } from 'app/shared/model/track.model';
import { TrackService } from './track.service';
import { IExternalURL } from 'app/shared/model/external-url.model';
import { ExternalURLService } from 'app/entities/external-url/external-url.service';
import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';
import { AudioAnalysisService } from 'app/entities/audio-analysis/audio-analysis.service';
import { IAudioFeatures } from 'app/shared/model/audio-features.model';
import { AudioFeaturesService } from 'app/entities/audio-features/audio-features.service';

type SelectableEntity = IExternalURL | IAudioAnalysis | IAudioFeatures;

@Component({
  selector: 'jhi-track-update',
  templateUrl: './track-update.component.html',
})
export class TrackUpdateComponent implements OnInit {
  isSaving = false;
  externalurls: IExternalURL[] = [];
  audioanalyses: IAudioAnalysis[] = [];
  audiofeatures: IAudioFeatures[] = [];

  editForm = this.fb.group({
    id: [],
    discNumber: [],
    durationMS: [],
    explicit: [],
    href: [],
    idSpotify: [],
    isPlayable: [],
    name: [],
    previewUrl: [],
    trackNumber: [],
    uri: [],
    isLocal: [],
    externalURL: [],
    audioAnalysis: [],
    audioFeatures: [],
  });

  constructor(
    protected trackService: TrackService,
    protected externalURLService: ExternalURLService,
    protected audioAnalysisService: AudioAnalysisService,
    protected audioFeaturesService: AudioFeaturesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ track }) => {
      this.updateForm(track);

      this.externalURLService
        .query({ filter: 'track-is-null' })
        .pipe(
          map((res: HttpResponse<IExternalURL[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IExternalURL[]) => {
          if (!track.externalURL || !track.externalURL.id) {
            this.externalurls = resBody;
          } else {
            this.externalURLService
              .find(track.externalURL.id)
              .pipe(
                map((subRes: HttpResponse<IExternalURL>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IExternalURL[]) => (this.externalurls = concatRes));
          }
        });

      this.audioAnalysisService
        .query({ filter: 'track-is-null' })
        .pipe(
          map((res: HttpResponse<IAudioAnalysis[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAudioAnalysis[]) => {
          if (!track.audioAnalysis || !track.audioAnalysis.id) {
            this.audioanalyses = resBody;
          } else {
            this.audioAnalysisService
              .find(track.audioAnalysis.id)
              .pipe(
                map((subRes: HttpResponse<IAudioAnalysis>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAudioAnalysis[]) => (this.audioanalyses = concatRes));
          }
        });

      this.audioFeaturesService
        .query({ filter: 'track-is-null' })
        .pipe(
          map((res: HttpResponse<IAudioFeatures[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAudioFeatures[]) => {
          if (!track.audioFeatures || !track.audioFeatures.id) {
            this.audiofeatures = resBody;
          } else {
            this.audioFeaturesService
              .find(track.audioFeatures.id)
              .pipe(
                map((subRes: HttpResponse<IAudioFeatures>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAudioFeatures[]) => (this.audiofeatures = concatRes));
          }
        });
    });
  }

  updateForm(track: ITrack): void {
    this.editForm.patchValue({
      id: track.id,
      discNumber: track.discNumber,
      durationMS: track.durationMS,
      explicit: track.explicit,
      href: track.href,
      idSpotify: track.idSpotify,
      isPlayable: track.isPlayable,
      name: track.name,
      previewUrl: track.previewUrl,
      trackNumber: track.trackNumber,
      uri: track.uri,
      isLocal: track.isLocal,
      externalURL: track.externalURL,
      audioAnalysis: track.audioAnalysis,
      audioFeatures: track.audioFeatures,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const track = this.createFromForm();
    if (track.id !== undefined) {
      this.subscribeToSaveResponse(this.trackService.update(track));
    } else {
      this.subscribeToSaveResponse(this.trackService.create(track));
    }
  }

  private createFromForm(): ITrack {
    return {
      ...new Track(),
      id: this.editForm.get(['id'])!.value,
      discNumber: this.editForm.get(['discNumber'])!.value,
      durationMS: this.editForm.get(['durationMS'])!.value,
      explicit: this.editForm.get(['explicit'])!.value,
      href: this.editForm.get(['href'])!.value,
      idSpotify: this.editForm.get(['idSpotify'])!.value,
      isPlayable: this.editForm.get(['isPlayable'])!.value,
      name: this.editForm.get(['name'])!.value,
      previewUrl: this.editForm.get(['previewUrl'])!.value,
      trackNumber: this.editForm.get(['trackNumber'])!.value,
      uri: this.editForm.get(['uri'])!.value,
      isLocal: this.editForm.get(['isLocal'])!.value,
      externalURL: this.editForm.get(['externalURL'])!.value,
      audioAnalysis: this.editForm.get(['audioAnalysis'])!.value,
      audioFeatures: this.editForm.get(['audioFeatures'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrack>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
