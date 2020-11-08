import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { ExternalURLComponent } from 'app/entities/external-url/external-url.component';
import { ExternalURLService } from 'app/entities/external-url/external-url.service';
import { ExternalURL } from 'app/shared/model/external-url.model';

describe('Component Tests', () => {
  describe('ExternalURL Management Component', () => {
    let comp: ExternalURLComponent;
    let fixture: ComponentFixture<ExternalURLComponent>;
    let service: ExternalURLService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [ExternalURLComponent],
      })
        .overrideTemplate(ExternalURLComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExternalURLComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExternalURLService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ExternalURL(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.externalURLS && comp.externalURLS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
