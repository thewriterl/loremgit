import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAudioAnalysis, AudioAnalysis } from 'app/shared/model/audio-analysis.model';
import { AudioAnalysisService } from './audio-analysis.service';
import { AudioAnalysisComponent } from './audio-analysis.component';
import { AudioAnalysisDetailComponent } from './audio-analysis-detail.component';
import { AudioAnalysisUpdateComponent } from './audio-analysis-update.component';

@Injectable({ providedIn: 'root' })
export class AudioAnalysisResolve implements Resolve<IAudioAnalysis> {
  constructor(private service: AudioAnalysisService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAudioAnalysis> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((audioAnalysis: HttpResponse<AudioAnalysis>) => {
          if (audioAnalysis.body) {
            return of(audioAnalysis.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AudioAnalysis());
  }
}

export const audioAnalysisRoute: Routes = [
  {
    path: '',
    component: AudioAnalysisComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.audioAnalysis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AudioAnalysisDetailComponent,
    resolve: {
      audioAnalysis: AudioAnalysisResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.audioAnalysis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AudioAnalysisUpdateComponent,
    resolve: {
      audioAnalysis: AudioAnalysisResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.audioAnalysis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AudioAnalysisUpdateComponent,
    resolve: {
      audioAnalysis: AudioAnalysisResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'musicIntelligenceApp.audioAnalysis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
