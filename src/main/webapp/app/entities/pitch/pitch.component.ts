import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPitch } from 'app/shared/model/pitch.model';
import { PitchService } from './pitch.service';
import { PitchDeleteDialogComponent } from './pitch-delete-dialog.component';

@Component({
  selector: 'jhi-pitch',
  templateUrl: './pitch.component.html',
})
export class PitchComponent implements OnInit, OnDestroy {
  pitches?: IPitch[];
  eventSubscriber?: Subscription;

  constructor(protected pitchService: PitchService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.pitchService.query().subscribe((res: HttpResponse<IPitch[]>) => (this.pitches = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPitches();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPitch): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPitches(): void {
    this.eventSubscriber = this.eventManager.subscribe('pitchListModification', () => this.loadAll());
  }

  delete(pitch: IPitch): void {
    const modalRef = this.modalService.open(PitchDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pitch = pitch;
  }
}
