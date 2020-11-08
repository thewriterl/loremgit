import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { PitchUpdateComponent } from 'app/entities/pitch/pitch-update.component';
import { PitchService } from 'app/entities/pitch/pitch.service';
import { Pitch } from 'app/shared/model/pitch.model';

describe('Component Tests', () => {
  describe('Pitch Management Update Component', () => {
    let comp: PitchUpdateComponent;
    let fixture: ComponentFixture<PitchUpdateComponent>;
    let service: PitchService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [PitchUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PitchUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PitchUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PitchService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pitch(123);
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
        const entity = new Pitch();
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
