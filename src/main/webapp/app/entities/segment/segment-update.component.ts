import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISegment, Segment } from 'app/shared/model/segment.model';
import { SegmentService } from './segment.service';
import { IPitch } from 'app/shared/model/pitch.model';
import { PitchService } from 'app/entities/pitch/pitch.service';
import { ITimbre } from 'app/shared/model/timbre.model';
import { TimbreService } from 'app/entities/timbre/timbre.service';
import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';
import { AudioAnalysisService } from 'app/entities/audio-analysis/audio-analysis.service';

type SelectableEntity = IPitch | ITimbre | IAudioAnalysis;

type SelectableManyToManyEntity = IPitch | ITimbre;

@Component({
  selector: 'jhi-segment-update',
  templateUrl: './segment-update.component.html',
})
export class SegmentUpdateComponent implements OnInit {
  isSaving = false;
  pitches: IPitch[] = [];
  timbres: ITimbre[] = [];
  audioanalyses: IAudioAnalysis[] = [];

  editForm = this.fb.group({
    id: [],
    start: [],
    duration: [],
    confidence: [],
    loudnessStart: [],
    loudnessMax: [],
    loudnessMaxTime: [],
    loudnessEnd: [],
    pitches: [],
    timbres: [],
    audioAnalysis: [],
  });

  constructor(
    protected segmentService: SegmentService,
    protected pitchService: PitchService,
    protected timbreService: TimbreService,
    protected audioAnalysisService: AudioAnalysisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ segment }) => {
      this.updateForm(segment);

      this.pitchService.query().subscribe((res: HttpResponse<IPitch[]>) => (this.pitches = res.body || []));

      this.timbreService.query().subscribe((res: HttpResponse<ITimbre[]>) => (this.timbres = res.body || []));

      this.audioAnalysisService.query().subscribe((res: HttpResponse<IAudioAnalysis[]>) => (this.audioanalyses = res.body || []));
    });
  }

  updateForm(segment: ISegment): void {
    this.editForm.patchValue({
      id: segment.id,
      start: segment.start,
      duration: segment.duration,
      confidence: segment.confidence,
      loudnessStart: segment.loudnessStart,
      loudnessMax: segment.loudnessMax,
      loudnessMaxTime: segment.loudnessMaxTime,
      loudnessEnd: segment.loudnessEnd,
      pitches: segment.pitches,
      timbres: segment.timbres,
      audioAnalysis: segment.audioAnalysis,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const segment = this.createFromForm();
    if (segment.id !== undefined) {
      this.subscribeToSaveResponse(this.segmentService.update(segment));
    } else {
      this.subscribeToSaveResponse(this.segmentService.create(segment));
    }
  }

  private createFromForm(): ISegment {
    return {
      ...new Segment(),
      id: this.editForm.get(['id'])!.value,
      start: this.editForm.get(['start'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      confidence: this.editForm.get(['confidence'])!.value,
      loudnessStart: this.editForm.get(['loudnessStart'])!.value,
      loudnessMax: this.editForm.get(['loudnessMax'])!.value,
      loudnessMaxTime: this.editForm.get(['loudnessMaxTime'])!.value,
      loudnessEnd: this.editForm.get(['loudnessEnd'])!.value,
      pitches: this.editForm.get(['pitches'])!.value,
      timbres: this.editForm.get(['timbres'])!.value,
      audioAnalysis: this.editForm.get(['audioAnalysis'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISegment>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
