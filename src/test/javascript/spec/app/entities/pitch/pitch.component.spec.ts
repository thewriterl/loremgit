import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { PitchComponent } from 'app/entities/pitch/pitch.component';
import { PitchService } from 'app/entities/pitch/pitch.service';
import { Pitch } from 'app/shared/model/pitch.model';

describe('Component Tests', () => {
  describe('Pitch Management Component', () => {
    let comp: PitchComponent;
    let fixture: ComponentFixture<PitchComponent>;
    let service: PitchService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [PitchComponent],
      })
        .overrideTemplate(PitchComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PitchComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PitchService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pitch(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pitches && comp.pitches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
