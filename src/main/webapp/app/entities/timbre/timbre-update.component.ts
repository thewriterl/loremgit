import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITimbre, Timbre } from 'app/shared/model/timbre.model';
import { TimbreService } from './timbre.service';

@Component({
  selector: 'jhi-timbre-update',
  templateUrl: './timbre-update.component.html',
})
export class TimbreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    timbre: [],
  });

  constructor(protected timbreService: TimbreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timbre }) => {
      this.updateForm(timbre);
    });
  }

  updateForm(timbre: ITimbre): void {
    this.editForm.patchValue({
      id: timbre.id,
      timbre: timbre.timbre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timbre = this.createFromForm();
    if (timbre.id !== undefined) {
      this.subscribeToSaveResponse(this.timbreService.update(timbre));
    } else {
      this.subscribeToSaveResponse(this.timbreService.create(timbre));
    }
  }

  private createFromForm(): ITimbre {
    return {
      ...new Timbre(),
      id: this.editForm.get(['id'])!.value,
      timbre: this.editForm.get(['timbre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimbre>>): void {
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
