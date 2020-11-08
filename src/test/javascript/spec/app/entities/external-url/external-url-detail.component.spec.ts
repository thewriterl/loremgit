import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { ExternalURLDetailComponent } from 'app/entities/external-url/external-url-detail.component';
import { ExternalURL } from 'app/shared/model/external-url.model';

describe('Component Tests', () => {
  describe('ExternalURL Management Detail Component', () => {
    let comp: ExternalURLDetailComponent;
    let fixture: ComponentFixture<ExternalURLDetailComponent>;
    const route = ({ data: of({ externalURL: new ExternalURL(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [ExternalURLDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExternalURLDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExternalURLDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load externalURL on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.externalURL).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
