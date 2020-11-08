import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { AudioFeaturesComponent } from 'app/entities/audio-features/audio-features.component';
import { AudioFeaturesService } from 'app/entities/audio-features/audio-features.service';
import { AudioFeatures } from 'app/shared/model/audio-features.model';

describe('Component Tests', () => {
  describe('AudioFeatures Management Component', () => {
    let comp: AudioFeaturesComponent;
    let fixture: ComponentFixture<AudioFeaturesComponent>;
    let service: AudioFeaturesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [AudioFeaturesComponent],
      })
        .overrideTemplate(AudioFeaturesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AudioFeaturesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AudioFeaturesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AudioFeatures(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.audioFeatures && comp.audioFeatures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
