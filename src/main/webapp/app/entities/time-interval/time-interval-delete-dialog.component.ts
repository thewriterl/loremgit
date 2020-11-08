import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimeInterval } from 'app/shared/model/time-interval.model';
import { TimeIntervalService } from './time-interval.service';

@Component({
  templateUrl: './time-interval-delete-dialog.component.html',
})
export class TimeIntervalDeleteDialogComponent {
  timeInterval?: ITimeInterval;

  constructor(
    protected timeIntervalService: TimeIntervalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.timeIntervalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('timeIntervalListModification');
      this.activeModal.close();
    });
  }
}
