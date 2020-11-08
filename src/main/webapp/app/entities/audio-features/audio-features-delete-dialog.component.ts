import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAudioFeatures } from 'app/shared/model/audio-features.model';
import { AudioFeaturesService } from './audio-features.service';

@Component({
  templateUrl: './audio-features-delete-dialog.component.html',
})
export class AudioFeaturesDeleteDialogComponent {
  audioFeatures?: IAudioFeatures;

  constructor(
    protected audioFeaturesService: AudioFeaturesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.audioFeaturesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('audioFeaturesListModification');
      this.activeModal.close();
    });
  }
}
