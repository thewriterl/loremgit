import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExternalURL, ExternalURL } from 'app/shared/model/external-url.model';
import { ExternalURLService } from './external-url.service';

@Component({
  selector: 'jhi-external-url-update',
  templateUrl: './external-url-update.component.html',
})
export class ExternalURLUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    source: [],
    href: [],
  });

  constructor(protected externalURLService: ExternalURLService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ externalURL }) => {
      this.updateForm(externalURL);
    });
  }

  updateForm(externalURL: IExternalURL): void {
    this.editForm.patchValue({
      id: externalURL.id,
      source: externalURL.source,
      href: externalURL.href,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const externalURL = this.createFromForm();
    if (externalURL.id !== undefined) {
      this.subscribeToSaveResponse(this.externalURLService.update(externalURL));
    } else {
      this.subscribeToSaveResponse(this.externalURLService.create(externalURL));
    }
  }

  private createFromForm(): IExternalURL {
    return {
      ...new ExternalURL(),
      id: this.editForm.get(['id'])!.value,
      source: this.editForm.get(['source'])!.value,
      href: this.editForm.get(['href'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExternalURL>>): void {
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
