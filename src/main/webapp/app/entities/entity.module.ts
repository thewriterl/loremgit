import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'image',
        loadChildren: () => import('./image/image.module').then(m => m.MusicIntelligenceImageModule),
      },
      {
        path: 'genre',
        loadChildren: () => import('./genre/genre.module').then(m => m.MusicIntelligenceGenreModule),
      },
      {
        path: 'external-url',
        loadChildren: () => import('./external-url/external-url.module').then(m => m.MusicIntelligenceExternalURLModule),
      },
      {
        path: 'artist',
        loadChildren: () => import('./artist/artist.module').then(m => m.MusicIntelligenceArtistModule),
      },
      {
        path: 'album',
        loadChildren: () => import('./album/album.module').then(m => m.MusicIntelligenceAlbumModule),
      },
      {
        path: 'track',
        loadChildren: () => import('./track/track.module').then(m => m.MusicIntelligenceTrackModule),
      },
      {
        path: 'time-interval',
        loadChildren: () => import('./time-interval/time-interval.module').then(m => m.MusicIntelligenceTimeIntervalModule),
      },
      {
        path: 'audio-analysis',
        loadChildren: () => import('./audio-analysis/audio-analysis.module').then(m => m.MusicIntelligenceAudioAnalysisModule),
      },
      {
        path: 'section',
        loadChildren: () => import('./section/section.module').then(m => m.MusicIntelligenceSectionModule),
      },
      {
        path: 'segment',
        loadChildren: () => import('./segment/segment.module').then(m => m.MusicIntelligenceSegmentModule),
      },
      {
        path: 'pitch',
        loadChildren: () => import('./pitch/pitch.module').then(m => m.MusicIntelligencePitchModule),
      },
      {
        path: 'timbre',
        loadChildren: () => import('./timbre/timbre.module').then(m => m.MusicIntelligenceTimbreModule),
      },
      {
        path: 'audio-features',
        loadChildren: () => import('./audio-features/audio-features.module').then(m => m.MusicIntelligenceAudioFeaturesModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MusicIntelligenceEntityModule {}
