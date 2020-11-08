import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExternalURL } from 'app/shared/model/external-url.model';
import { ExternalURLService } from './external-url.service';
import { ExternalURLDeleteDialogComponent } from './external-url-delete-dialog.component';

@Component({
  selector: 'jhi-external-url',
  templateUrl: './external-url.component.html',
})
export class ExternalURLComponent implements OnInit, OnDestroy {
  externalURLS?: IExternalURL[];
  eventSubscriber?: Subscription;

  constructor(
    protected externalURLService: ExternalURLService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.externalURLService.query().subscribe((res: HttpResponse<IExternalURL[]>) => (this.externalURLS = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExternalURLS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExternalURL): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExternalURLS(): void {
    this.eventSubscriber = this.eventManager.subscribe('externalURLListModification', () => this.loadAll());
  }

  delete(externalURL: IExternalURL): void {
    const modalRef = this.modalService.open(ExternalURLDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.externalURL = externalURL;
  }
}
