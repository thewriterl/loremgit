import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAudioAnalysis, AudioAnalysis } from 'app/shared/model/audio-analysis.model';
import { AudioAnalysisService } from './audio-analysis.service';

@Component({
  selector: 'jhi-audio-analysis-update',
  templateUrl: './audio-analysis-update.component.html',
})
export class AudioAnalysisUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected audioAnalysisService: AudioAnalysisService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ audioAnalysis }) => {
      this.updateForm(audioAnalysis);
    });
  }

  updateForm(audioAnalysis: IAudioAnalysis): void {
    this.editForm.patchValue({
      id: audioAnalysis.id,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const audioAnalysis = this.createFromForm();
    if (audioAnalysis.id !== undefined) {
      this.subscribeToSaveResponse(this.audioAnalysisService.update(audioAnalysis));
    } else {
      this.subscribeToSaveResponse(this.audioAnalysisService.create(audioAnalysis));
    }
  }

  private createFromForm(): IAudioAnalysis {
    return {
      ...new AudioAnalysis(),
      id: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAudioAnalysis>>): void {
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
