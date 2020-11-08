import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TestetsetsetSharedModule } from 'app/shared/shared.module';
import { TestetsetsetCoreModule } from 'app/core/core.module';
import { TestetsetsetAppRoutingModule } from './app-routing.module';
import { TestetsetsetHomeModule } from './home/home.module';
import { TestetsetsetEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TestetsetsetSharedModule,
    TestetsetsetCoreModule,
    TestetsetsetHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TestetsetsetEntityModule,
    TestetsetsetAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class TestetsetsetAppModule {}
