import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITimeInterval, TimeInterval } from 'app/shared/model/time-interval.model';
import { TimeIntervalService } from './time-interval.service';
import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';
import { AudioAnalysisService } from 'app/entities/audio-analysis/audio-analysis.service';

@Component({
  selector: 'jhi-time-interval-update',
  templateUrl: './time-interval-update.component.html',
})
export class TimeIntervalUpdateComponent implements OnInit {
  isSaving = false;
  audioanalyses: IAudioAnalysis[] = [];

  editForm = this.fb.group({
    id: [],
    start: [],
    duration: [],
    confidence: [],
    audioAnalysis: [],
    audioAnalysis: [],
    audioAnalysis: [],
  });

  constructor(
    protected timeIntervalService: TimeIntervalService,
    protected audioAnalysisService: AudioAnalysisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeInterval }) => {
      this.updateForm(timeInterval);

      this.audioAnalysisService.query().subscribe((res: HttpResponse<IAudioAnalysis[]>) => (this.audioanalyses = res.body || []));
    });
  }

  updateForm(timeInterval: ITimeInterval): void {
    this.editForm.patchValue({
      id: timeInterval.id,
      start: timeInterval.start,
      duration: timeInterval.duration,
      confidence: timeInterval.confidence,
      audioAnalysis: timeInterval.audioAnalysis,
      audioAnalysis: timeInterval.audioAnalysis,
      audioAnalysis: timeInterval.audioAnalysis,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timeInterval = this.createFromForm();
    if (timeInterval.id !== undefined) {
      this.subscribeToSaveResponse(this.timeIntervalService.update(timeInterval));
    } else {
      this.subscribeToSaveResponse(this.timeIntervalService.create(timeInterval));
    }
  }

  private createFromForm(): ITimeInterval {
    return {
      ...new TimeInterval(),
      id: this.editForm.get(['id'])!.value,
      start: this.editForm.get(['start'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      confidence: this.editForm.get(['confidence'])!.value,
      audioAnalysis: this.editForm.get(['audioAnalysis'])!.value,
      audioAnalysis: this.editForm.get(['audioAnalysis'])!.value,
      audioAnalysis: this.editForm.get(['audioAnalysis'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeInterval>>): void {
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

  trackById(index: number, item: IAudioAnalysis): any {
    return item.id;
  }
}
