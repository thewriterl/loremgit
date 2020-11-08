import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { AudioAnalysisComponent } from 'app/entities/audio-analysis/audio-analysis.component';
import { AudioAnalysisService } from 'app/entities/audio-analysis/audio-analysis.service';
import { AudioAnalysis } from 'app/shared/model/audio-analysis.model';

describe('Component Tests', () => {
  describe('AudioAnalysis Management Component', () => {
    let comp: AudioAnalysisComponent;
    let fixture: ComponentFixture<AudioAnalysisComponent>;
    let service: AudioAnalysisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [AudioAnalysisComponent],
      })
        .overrideTemplate(AudioAnalysisComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AudioAnalysisComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AudioAnalysisService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AudioAnalysis(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.audioAnalyses && comp.audioAnalyses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
