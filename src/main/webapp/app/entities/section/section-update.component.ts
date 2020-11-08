import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISection, Section } from 'app/shared/model/section.model';
import { SectionService } from './section.service';
import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';
import { AudioAnalysisService } from 'app/entities/audio-analysis/audio-analysis.service';

@Component({
  selector: 'jhi-section-update',
  templateUrl: './section-update.component.html',
})
export class SectionUpdateComponent implements OnInit {
  isSaving = false;
  audioanalyses: IAudioAnalysis[] = [];

  editForm = this.fb.group({
    id: [],
    start: [],
    duration: [],
    confidence: [],
    loudness: [],
    tempo: [],
    tempoConfidence: [],
    key: [],
    keyConfidence: [],
    mode: [],
    modeConfidence: [],
    timeSignature: [],
    timeSignatureConfidence: [],
    audioAnalysis: [],
  });

  constructor(
    protected sectionService: SectionService,
    protected audioAnalysisService: AudioAnalysisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ section }) => {
      this.updateForm(section);

      this.audioAnalysisService.query().subscribe((res: HttpResponse<IAudioAnalysis[]>) => (this.audioanalyses = res.body || []));
    });
  }

  updateForm(section: ISection): void {
    this.editForm.patchValue({
      id: section.id,
      start: section.start,
      duration: section.duration,
      confidence: section.confidence,
      loudness: section.loudness,
      tempo: section.tempo,
      tempoConfidence: section.tempoConfidence,
      key: section.key,
      keyConfidence: section.keyConfidence,
      mode: section.mode,
      modeConfidence: section.modeConfidence,
      timeSignature: section.timeSignature,
      timeSignatureConfidence: section.timeSignatureConfidence,
      audioAnalysis: section.audioAnalysis,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const section = this.createFromForm();
    if (section.id !== undefined) {
      this.subscribeToSaveResponse(this.sectionService.update(section));
    } else {
      this.subscribeToSaveResponse(this.sectionService.create(section));
    }
  }

  private createFromForm(): ISection {
    return {
      ...new Section(),
      id: this.editForm.get(['id'])!.value,
      start: this.editForm.get(['start'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      confidence: this.editForm.get(['confidence'])!.value,
      loudness: this.editForm.get(['loudness'])!.value,
      tempo: this.editForm.get(['tempo'])!.value,
      tempoConfidence: this.editForm.get(['tempoConfidence'])!.value,
      key: this.editForm.get(['key'])!.value,
      keyConfidence: this.editForm.get(['keyConfidence'])!.value,
      mode: this.editForm.get(['mode'])!.value,
      modeConfidence: this.editForm.get(['modeConfidence'])!.value,
      timeSignature: this.editForm.get(['timeSignature'])!.value,
      timeSignatureConfidence: this.editForm.get(['timeSignatureConfidence'])!.value,
      audioAnalysis: this.editForm.get(['audioAnalysis'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISection>>): void {
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
