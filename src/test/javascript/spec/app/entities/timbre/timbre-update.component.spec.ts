import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { TimbreUpdateComponent } from 'app/entities/timbre/timbre-update.component';
import { TimbreService } from 'app/entities/timbre/timbre.service';
import { Timbre } from 'app/shared/model/timbre.model';

describe('Component Tests', () => {
  describe('Timbre Management Update Component', () => {
    let comp: TimbreUpdateComponent;
    let fixture: ComponentFixture<TimbreUpdateComponent>;
    let service: TimbreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [TimbreUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TimbreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TimbreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TimbreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Timbre(123);
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
        const entity = new Timbre();
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
