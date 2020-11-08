import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimbre } from 'app/shared/model/timbre.model';
import { TimbreService } from './timbre.service';

@Component({
  templateUrl: './timbre-delete-dialog.component.html',
})
export class TimbreDeleteDialogComponent {
  timbre?: ITimbre;

  constructor(protected timbreService: TimbreService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.timbreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('timbreListModification');
      this.activeModal.close();
    });
  }
}
