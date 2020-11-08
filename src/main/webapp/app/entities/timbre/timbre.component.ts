import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITimbre } from 'app/shared/model/timbre.model';
import { TimbreService } from './timbre.service';
import { TimbreDeleteDialogComponent } from './timbre-delete-dialog.component';

@Component({
  selector: 'jhi-timbre',
  templateUrl: './timbre.component.html',
})
export class TimbreComponent implements OnInit, OnDestroy {
  timbres?: ITimbre[];
  eventSubscriber?: Subscription;

  constructor(protected timbreService: TimbreService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.timbreService.query().subscribe((res: HttpResponse<ITimbre[]>) => (this.timbres = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTimbres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITimbre): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTimbres(): void {
    this.eventSubscriber = this.eventManager.subscribe('timbreListModification', () => this.loadAll());
  }

  delete(timbre: ITimbre): void {
    const modalRef = this.modalService.open(TimbreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.timbre = timbre;
  }
}
