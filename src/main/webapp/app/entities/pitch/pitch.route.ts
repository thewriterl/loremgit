import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPitch, Pitch } from 'app/shared/model/pitch.model';
import { PitchService } from './pitch.service';
import { PitchComponent } from './pitch.component';
import { PitchDetailComponent } from './pitch-detail.component';
import { PitchUpdateComponent } from './pitch-update.component';

@Injectable({ providedIn: 'root' })
export class PitchResolve implements Resolve<IPitch> {
  constructor(private service: PitchService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPitch> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pitch: HttpResponse<Pitch>) => {
          if (pitch.body) {
            return of(pitch.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pitch());
  }
}

export const pitchRoute: Routes = [
  {
    path: '',
    component: PitchComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.pitch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PitchDetailComponent,
    resolve: {
      pitch: PitchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.pitch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PitchUpdateComponent,
    resolve: {
      pitch: PitchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.pitch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PitchUpdateComponent,
    resolve: {
      pitch: PitchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.pitch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
