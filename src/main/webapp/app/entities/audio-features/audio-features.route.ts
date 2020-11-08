import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAudioFeatures, AudioFeatures } from 'app/shared/model/audio-features.model';
import { AudioFeaturesService } from './audio-features.service';
import { AudioFeaturesComponent } from './audio-features.component';
import { AudioFeaturesDetailComponent } from './audio-features-detail.component';
import { AudioFeaturesUpdateComponent } from './audio-features-update.component';

@Injectable({ providedIn: 'root' })
export class AudioFeaturesResolve implements Resolve<IAudioFeatures> {
  constructor(private service: AudioFeaturesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAudioFeatures> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((audioFeatures: HttpResponse<AudioFeatures>) => {
          if (audioFeatures.body) {
            return of(audioFeatures.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AudioFeatures());
  }
}

export const audioFeaturesRoute: Routes = [
  {
    path: '',
    component: AudioFeaturesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.audioFeatures.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AudioFeaturesDetailComponent,
    resolve: {
      audioFeatures: AudioFeaturesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.audioFeatures.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AudioFeaturesUpdateComponent,
    resolve: {
      audioFeatures: AudioFeaturesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.audioFeatures.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AudioFeaturesUpdateComponent,
    resolve: {
      audioFeatures: AudioFeaturesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.audioFeatures.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
