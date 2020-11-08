import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITimeInterval } from 'app/shared/model/time-interval.model';
import { TimeIntervalService } from './time-interval.service';
import { TimeIntervalDeleteDialogComponent } from './time-interval-delete-dialog.component';

@Component({
  selector: 'jhi-time-interval',
  templateUrl: './time-interval.component.html',
})
export class TimeIntervalComponent implements OnInit, OnDestroy {
  timeIntervals?: ITimeInterval[];
  eventSubscriber?: Subscription;

  constructor(
    protected timeIntervalService: TimeIntervalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.timeIntervalService.query().subscribe((res: HttpResponse<ITimeInterval[]>) => (this.timeIntervals = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTimeIntervals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITimeInterval): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTimeIntervals(): void {
    this.eventSubscriber = this.eventManager.subscribe('timeIntervalListModification', () => this.loadAll());
  }

  delete(timeInterval: ITimeInterval): void {
    const modalRef = this.modalService.open(TimeIntervalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.timeInterval = timeInterval;
  }
}
