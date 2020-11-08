import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MusicIntelligenceSharedModule } from 'app/shared/shared.module';
import { PitchComponent } from './pitch.component';
import { PitchDetailComponent } from './pitch-detail.component';
import { PitchUpdateComponent } from './pitch-update.component';
import { PitchDeleteDialogComponent } from './pitch-delete-dialog.component';
import { pitchRoute } from './pitch.route';

@NgModule({
  imports: [MusicIntelligenceSharedModule, RouterModule.forChild(pitchRoute)],
  declarations: [PitchComponent, PitchDetailComponent, PitchUpdateComponent, PitchDeleteDialogComponent],
  entryComponents: [PitchDeleteDialogComponent],
})
export class MusicIntelligencePitchModule {}
