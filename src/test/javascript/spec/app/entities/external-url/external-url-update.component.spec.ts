import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { ExternalURLUpdateComponent } from 'app/entities/external-url/external-url-update.component';
import { ExternalURLService } from 'app/entities/external-url/external-url.service';
import { ExternalURL } from 'app/shared/model/external-url.model';

describe('Component Tests', () => {
  describe('ExternalURL Management Update Component', () => {
    let comp: ExternalURLUpdateComponent;
    let fixture: ComponentFixture<ExternalURLUpdateComponent>;
    let service: ExternalURLService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [ExternalURLUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExternalURLUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExternalURLUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExternalURLService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExternalURL(123);
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
        const entity = new ExternalURL();
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
