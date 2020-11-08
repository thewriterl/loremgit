import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MusicIntelligenceSharedModule } from 'app/shared/shared.module';
import { SegmentComponent } from './segment.component';
import { SegmentDetailComponent } from './segment-detail.component';
import { SegmentUpdateComponent } from './segment-update.component';
import { SegmentDeleteDialogComponent } from './segment-delete-dialog.component';
import { segmentRoute } from './segment.route';

@NgModule({
  imports: [MusicIntelligenceSharedModule, RouterModule.forChild(segmentRoute)],
  declarations: [SegmentComponent, SegmentDetailComponent, SegmentUpdateComponent, SegmentDeleteDialogComponent],
  entryComponents: [SegmentDeleteDialogComponent],
})
export class MusicIntelligenceSegmentModule {}
