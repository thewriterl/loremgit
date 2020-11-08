import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExternalURL } from 'app/shared/model/external-url.model';
import { ExternalURLService } from './external-url.service';

@Component({
  templateUrl: './external-url-delete-dialog.component.html',
})
export class ExternalURLDeleteDialogComponent {
  externalURL?: IExternalURL;

  constructor(
    protected externalURLService: ExternalURLService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.externalURLService.delete(id).subscribe(() => {
      this.eventManager.broadcast('externalURLListModification');
      this.activeModal.close();
    });
  }
}
