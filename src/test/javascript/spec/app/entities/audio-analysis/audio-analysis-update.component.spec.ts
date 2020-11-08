import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { AudioAnalysisUpdateComponent } from 'app/entities/audio-analysis/audio-analysis-update.component';
import { AudioAnalysisService } from 'app/entities/audio-analysis/audio-analysis.service';
import { AudioAnalysis } from 'app/shared/model/audio-analysis.model';

describe('Component Tests', () => {
  describe('AudioAnalysis Management Update Component', () => {
    let comp: AudioAnalysisUpdateComponent;
    let fixture: ComponentFixture<AudioAnalysisUpdateComponent>;
    let service: AudioAnalysisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [AudioAnalysisUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AudioAnalysisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AudioAnalysisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AudioAnalysisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AudioAnalysis(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AudioAnalysis();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
