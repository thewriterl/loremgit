import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITimbre, Timbre } from 'app/shared/model/timbre.model';
import { TimbreService } from './timbre.service';
import { TimbreComponent } from './timbre.component';
import { TimbreDetailComponent } from './timbre-detail.component';
import { TimbreUpdateComponent } from './timbre-update.component';

@Injectable({ providedIn: 'root' })
export class TimbreResolve implements Resolve<ITimbre> {
  constructor(private service: TimbreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITimbre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((timbre: HttpResponse<Timbre>) => {
          if (timbre.body) {
            return of(timbre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Timbre());
  }
}

export const timbreRoute: Routes = [
  {
    path: '',
    component: TimbreComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.timbre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TimbreDetailComponent,
    resolve: {
      timbre: TimbreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.timbre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TimbreUpdateComponent,
    resolve: {
      timbre: TimbreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.timbre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TimbreUpdateComponent,
    resolve: {
      timbre: TimbreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.timbre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
