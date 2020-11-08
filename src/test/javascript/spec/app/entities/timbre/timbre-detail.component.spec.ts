import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { TimbreDetailComponent } from 'app/entities/timbre/timbre-detail.component';
import { Timbre } from 'app/shared/model/timbre.model';

describe('Component Tests', () => {
  describe('Timbre Management Detail Component', () => {
    let comp: TimbreDetailComponent;
    let fixture: ComponentFixture<TimbreDetailComponent>;
    const route = ({ data: of({ timbre: new Timbre(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [TimbreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TimbreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TimbreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load timbre on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.timbre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
