import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { TimeIntervalComponent } from 'app/entities/time-interval/time-interval.component';
import { TimeIntervalService } from 'app/entities/time-interval/time-interval.service';
import { TimeInterval } from 'app/shared/model/time-interval.model';

describe('Component Tests', () => {
  describe('TimeInterval Management Component', () => {
    let comp: TimeIntervalComponent;
    let fixture: ComponentFixture<TimeIntervalComponent>;
    let service: TimeIntervalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [TimeIntervalComponent],
      })
        .overrideTemplate(TimeIntervalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TimeIntervalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TimeIntervalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TimeInterval(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.timeIntervals && comp.timeIntervals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
