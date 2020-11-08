import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { TimeIntervalUpdateComponent } from 'app/entities/time-interval/time-interval-update.component';
import { TimeIntervalService } from 'app/entities/time-interval/time-interval.service';
import { TimeInterval } from 'app/shared/model/time-interval.model';

describe('Component Tests', () => {
  describe('TimeInterval Management Update Component', () => {
    let comp: TimeIntervalUpdateComponent;
    let fixture: ComponentFixture<TimeIntervalUpdateComponent>;
    let service: TimeIntervalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [TimeIntervalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TimeIntervalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TimeIntervalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TimeIntervalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TimeInterval(123);
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
        const entity = new TimeInterval();
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
