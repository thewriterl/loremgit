import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPitch, Pitch } from 'app/shared/model/pitch.model';
import { PitchService } from './pitch.service';

@Component({
  selector: 'jhi-pitch-update',
  templateUrl: './pitch-update.component.html',
})
export class PitchUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    pitch: [],
  });

  constructor(protected pitchService: PitchService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pitch }) => {
      this.updateForm(pitch);
    });
  }

  updateForm(pitch: IPitch): void {
    this.editForm.patchValue({
      id: pitch.id,
      pitch: pitch.pitch,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pitch = this.createFromForm();
    if (pitch.id !== undefined) {
      this.subscribeToSaveResponse(this.pitchService.update(pitch));
    } else {
      this.subscribeToSaveResponse(this.pitchService.create(pitch));
    }
  }

  private createFromForm(): IPitch {
    return {
      ...new Pitch(),
      id: this.editForm.get(['id'])!.value,
      pitch: this.editForm.get(['pitch'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPitch>>): void {
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
