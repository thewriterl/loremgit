import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAudioFeatures } from 'app/shared/model/audio-features.model';
import { AudioFeaturesService } from './audio-features.service';
import { AudioFeaturesDeleteDialogComponent } from './audio-features-delete-dialog.component';

@Component({
  selector: 'jhi-audio-features',
  templateUrl: './audio-features.component.html',
})
export class AudioFeaturesComponent implements OnInit, OnDestroy {
  audioFeatures?: IAudioFeatures[];
  eventSubscriber?: Subscription;

  constructor(
    protected audioFeaturesService: AudioFeaturesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.audioFeaturesService.query().subscribe((res: HttpResponse<IAudioFeatures[]>) => (this.audioFeatures = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAudioFeatures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAudioFeatures): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAudioFeatures(): void {
    this.eventSubscriber = this.eventManager.subscribe('audioFeaturesListModification', () => this.loadAll());
  }

  delete(audioFeatures: IAudioFeatures): void {
    const modalRef = this.modalService.open(AudioFeaturesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.audioFeatures = audioFeatures;
  }
}
