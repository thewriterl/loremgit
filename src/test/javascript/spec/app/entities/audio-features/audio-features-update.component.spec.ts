import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { AudioFeaturesUpdateComponent } from 'app/entities/audio-features/audio-features-update.component';
import { AudioFeaturesService } from 'app/entities/audio-features/audio-features.service';
import { AudioFeatures } from 'app/shared/model/audio-features.model';

describe('Component Tests', () => {
  describe('AudioFeatures Management Update Component', () => {
    let comp: AudioFeaturesUpdateComponent;
    let fixture: ComponentFixture<AudioFeaturesUpdateComponent>;
    let service: AudioFeaturesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [AudioFeaturesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AudioFeaturesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AudioFeaturesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AudioFeaturesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AudioFeatures(123);
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
        const entity = new AudioFeatures();
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
