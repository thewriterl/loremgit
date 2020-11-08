import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MusicIntelligenceSharedModule } from 'app/shared/shared.module';
import { TimbreComponent } from './timbre.component';
import { TimbreDetailComponent } from './timbre-detail.component';
import { TimbreUpdateComponent } from './timbre-update.component';
import { TimbreDeleteDialogComponent } from './timbre-delete-dialog.component';
import { timbreRoute } from './timbre.route';

@NgModule({
  imports: [MusicIntelligenceSharedModule, RouterModule.forChild(timbreRoute)],
  declarations: [TimbreComponent, TimbreDetailComponent, TimbreUpdateComponent, TimbreDeleteDialogComponent],
  entryComponents: [TimbreDeleteDialogComponent],
})
export class MusicIntelligenceTimbreModule {}
