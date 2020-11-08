import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITimeInterval, TimeInterval } from 'app/shared/model/time-interval.model';
import { TimeIntervalService } from './time-interval.service';
import { TimeIntervalComponent } from './time-interval.component';
import { TimeIntervalDetailComponent } from './time-interval-detail.component';
import { TimeIntervalUpdateComponent } from './time-interval-update.component';

@Injectable({ providedIn: 'root' })
export class TimeIntervalResolve implements Resolve<ITimeInterval> {
  constructor(private service: TimeIntervalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITimeInterval> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((timeInterval: HttpResponse<TimeInterval>) => {
          if (timeInterval.body) {
            return of(timeInterval.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TimeInterval());
  }
}

export const timeIntervalRoute: Routes = [
  {
    path: '',
    component: TimeIntervalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.timeInterval.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TimeIntervalDetailComponent,
    resolve: {
      timeInterval: TimeIntervalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.timeInterval.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TimeIntervalUpdateComponent,
    resolve: {
      timeInterval: TimeIntervalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.timeInterval.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TimeIntervalUpdateComponent,
    resolve: {
      timeInterval: TimeIntervalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.timeInterval.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
