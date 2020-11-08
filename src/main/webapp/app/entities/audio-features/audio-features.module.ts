import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MusicIntelligenceSharedModule } from 'app/shared/shared.module';
import { AudioFeaturesComponent } from './audio-features.component';
import { AudioFeaturesDetailComponent } from './audio-features-detail.component';
import { AudioFeaturesUpdateComponent } from './audio-features-update.component';
import { AudioFeaturesDeleteDialogComponent } from './audio-features-delete-dialog.component';
import { audioFeaturesRoute } from './audio-features.route';

@NgModule({
  imports: [MusicIntelligenceSharedModule, RouterModule.forChild(audioFeaturesRoute)],
  declarations: [AudioFeaturesComponent, AudioFeaturesDetailComponent, AudioFeaturesUpdateComponent, AudioFeaturesDeleteDialogComponent],
  entryComponents: [AudioFeaturesDeleteDialogComponent],
})
export class MusicIntelligenceAudioFeaturesModule {}
