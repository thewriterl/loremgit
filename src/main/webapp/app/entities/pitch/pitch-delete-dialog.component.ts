import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPitch } from 'app/shared/model/pitch.model';
import { PitchService } from './pitch.service';

@Component({
  templateUrl: './pitch-delete-dialog.component.html',
})
export class PitchDeleteDialogComponent {
  pitch?: IPitch;

  constructor(protected pitchService: PitchService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pitchService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pitchListModification');
      this.activeModal.close();
    });
  }
}
