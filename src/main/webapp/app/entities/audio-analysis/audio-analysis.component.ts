import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';
import { AudioAnalysisService } from './audio-analysis.service';
import { AudioAnalysisDeleteDialogComponent } from './audio-analysis-delete-dialog.component';

@Component({
  selector: 'jhi-audio-analysis',
  templateUrl: './audio-analysis.component.html',
})
export class AudioAnalysisComponent implements OnInit, OnDestroy {
  audioAnalyses?: IAudioAnalysis[];
  eventSubscriber?: Subscription;

  constructor(
    protected audioAnalysisService: AudioAnalysisService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.audioAnalysisService.query().subscribe((res: HttpResponse<IAudioAnalysis[]>) => (this.audioAnalyses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAudioAnalyses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAudioAnalysis): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAudioAnalyses(): void {
    this.eventSubscriber = this.eventManager.subscribe('audioAnalysisListModification', () => this.loadAll());
  }

  delete(audioAnalysis: IAudioAnalysis): void {
    const modalRef = this.modalService.open(AudioAnalysisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.audioAnalysis = audioAnalysis;
  }
}
