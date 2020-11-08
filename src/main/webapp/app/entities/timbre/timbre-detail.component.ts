import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimbre } from 'app/shared/model/timbre.model';

@Component({
  selector: 'jhi-timbre-detail',
  templateUrl: './timbre-detail.component.html',
})
export class TimbreDetailComponent implements OnInit {
  timbre: ITimbre | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timbre }) => (this.timbre = timbre));
  }

  previousState(): void {
    window.history.back();
  }
}
