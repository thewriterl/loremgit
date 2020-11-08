import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MusicIntelligenceSharedModule } from 'app/shared/shared.module';
import { AudioAnalysisComponent } from './audio-analysis.component';
import { AudioAnalysisDetailComponent } from './audio-analysis-detail.component';
import { AudioAnalysisUpdateComponent } from './audio-analysis-update.component';
import { AudioAnalysisDeleteDialogComponent } from './audio-analysis-delete-dialog.component';
import { audioAnalysisRoute } from './audio-analysis.route';

@NgModule({
  imports: [MusicIntelligenceSharedModule, RouterModule.forChild(audioAnalysisRoute)],
  declarations: [AudioAnalysisComponent, AudioAnalysisDetailComponent, AudioAnalysisUpdateComponent, AudioAnalysisDeleteDialogComponent],
  entryComponents: [AudioAnalysisDeleteDialogComponent],
})
export class MusicIntelligenceAudioAnalysisModule {}
