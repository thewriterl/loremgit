import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MusicIntelligenceSharedModule } from 'app/shared/shared.module';
import { ExternalURLComponent } from './external-url.component';
import { ExternalURLDetailComponent } from './external-url-detail.component';
import { ExternalURLUpdateComponent } from './external-url-update.component';
import { ExternalURLDeleteDialogComponent } from './external-url-delete-dialog.component';
import { externalURLRoute } from './external-url.route';

@NgModule({
  imports: [MusicIntelligenceSharedModule, RouterModule.forChild(externalURLRoute)],
  declarations: [ExternalURLComponent, ExternalURLDetailComponent, ExternalURLUpdateComponent, ExternalURLDeleteDialogComponent],
  entryComponents: [ExternalURLDeleteDialogComponent],
})
export class MusicIntelligenceExternalURLModule {}
