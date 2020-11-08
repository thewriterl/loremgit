import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExternalURL } from 'app/shared/model/external-url.model';

@Component({
  selector: 'jhi-external-url-detail',
  templateUrl: './external-url-detail.component.html',
})
export class ExternalURLDetailComponent implements OnInit {
  externalURL: IExternalURL | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ externalURL }) => (this.externalURL = externalURL));
  }

  previousState(): void {
    window.history.back();
  }
}
