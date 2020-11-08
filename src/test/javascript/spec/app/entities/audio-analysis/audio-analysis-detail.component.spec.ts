import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { AudioAnalysisDetailComponent } from 'app/entities/audio-analysis/audio-analysis-detail.component';
import { AudioAnalysis } from 'app/shared/model/audio-analysis.model';

describe('Component Tests', () => {
  describe('AudioAnalysis Management Detail Component', () => {
    let comp: AudioAnalysisDetailComponent;
    let fixture: ComponentFixture<AudioAnalysisDetailComponent>;
    const route = ({ data: of({ audioAnalysis: new AudioAnalysis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [AudioAnalysisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AudioAnalysisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AudioAnalysisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load audioAnalysis on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.audioAnalysis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
