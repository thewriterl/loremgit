import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExternalURL, ExternalURL } from 'app/shared/model/external-url.model';
import { ExternalURLService } from './external-url.service';
import { ExternalURLComponent } from './external-url.component';
import { ExternalURLDetailComponent } from './external-url-detail.component';
import { ExternalURLUpdateComponent } from './external-url-update.component';

@Injectable({ providedIn: 'root' })
export class ExternalURLResolve implements Resolve<IExternalURL> {
  constructor(private service: ExternalURLService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExternalURL> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((externalURL: HttpResponse<ExternalURL>) => {
          if (externalURL.body) {
            return of(externalURL.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExternalURL());
  }
}

export const externalURLRoute: Routes = [
  {
    path: '',
    component: ExternalURLComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.externalURL.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExternalURLDetailComponent,
    resolve: {
      externalURL: ExternalURLResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.externalURL.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExternalURLUpdateComponent,
    resolve: {
      externalURL: ExternalURLResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.externalURL.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExternalURLUpdateComponent,
    resolve: {
      externalURL: ExternalURLResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.externalURL.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
