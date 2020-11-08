import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';
import { AudioAnalysisService } from './audio-analysis.service';

@Component({
  templateUrl: './audio-analysis-delete-dialog.component.html',
})
export class AudioAnalysisDeleteDialogComponent {
  audioAnalysis?: IAudioAnalysis;

  constructor(
    protected audioAnalysisService: AudioAnalysisService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.audioAnalysisService.delete(id).subscribe(() => {
      this.eventManager.broadcast('audioAnalysisListModification');
      this.activeModal.close();
    });
  }
}
