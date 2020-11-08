import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MusicIntelligenceSharedModule } from 'app/shared/shared.module';
import { TimeIntervalComponent } from './time-interval.component';
import { TimeIntervalDetailComponent } from './time-interval-detail.component';
import { TimeIntervalUpdateComponent } from './time-interval-update.component';
import { TimeIntervalDeleteDialogComponent } from './time-interval-delete-dialog.component';
import { timeIntervalRoute } from './time-interval.route';

@NgModule({
  imports: [MusicIntelligenceSharedModule, RouterModule.forChild(timeIntervalRoute)],
  declarations: [TimeIntervalComponent, TimeIntervalDetailComponent, TimeIntervalUpdateComponent, TimeIntervalDeleteDialogComponent],
  entryComponents: [TimeIntervalDeleteDialogComponent],
})
export class MusicIntelligenceTimeIntervalModule {}
