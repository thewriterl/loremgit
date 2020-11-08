import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { TimeIntervalDetailComponent } from 'app/entities/time-interval/time-interval-detail.component';
import { TimeInterval } from 'app/shared/model/time-interval.model';

describe('Component Tests', () => {
  describe('TimeInterval Management Detail Component', () => {
    let comp: TimeIntervalDetailComponent;
    let fixture: ComponentFixture<TimeIntervalDetailComponent>;
    const route = ({ data: of({ timeInterval: new TimeInterval(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [TimeIntervalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TimeIntervalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TimeIntervalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load timeInterval on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.timeInterval).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
