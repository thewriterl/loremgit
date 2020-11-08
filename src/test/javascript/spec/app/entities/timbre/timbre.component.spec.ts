import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { TimbreComponent } from 'app/entities/timbre/timbre.component';
import { TimbreService } from 'app/entities/timbre/timbre.service';
import { Timbre } from 'app/shared/model/timbre.model';

describe('Component Tests', () => {
  describe('Timbre Management Component', () => {
    let comp: TimbreComponent;
    let fixture: ComponentFixture<TimbreComponent>;
    let service: TimbreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [TimbreComponent],
      })
        .overrideTemplate(TimbreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TimbreComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TimbreService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Timbre(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.timbres && comp.timbres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
