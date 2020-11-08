import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { PitchDetailComponent } from 'app/entities/pitch/pitch-detail.component';
import { Pitch } from 'app/shared/model/pitch.model';

describe('Component Tests', () => {
  describe('Pitch Management Detail Component', () => {
    let comp: PitchDetailComponent;
    let fixture: ComponentFixture<PitchDetailComponent>;
    const route = ({ data: of({ pitch: new Pitch(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [PitchDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PitchDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PitchDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pitch on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pitch).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
