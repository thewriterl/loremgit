import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimeInterval } from 'app/shared/model/time-interval.model';

@Component({
  selector: 'jhi-time-interval-detail',
  templateUrl: './time-interval-detail.component.html',
})
export class TimeIntervalDetailComponent implements OnInit {
  timeInterval: ITimeInterval | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeInterval }) => (this.timeInterval = timeInterval));
  }

  previousState(): void {
    window.history.back();
  }
}
