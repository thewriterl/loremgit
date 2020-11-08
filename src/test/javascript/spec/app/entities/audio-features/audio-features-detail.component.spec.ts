import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MusicIntelligenceTestModule } from '../../../test.module';
import { AudioFeaturesDetailComponent } from 'app/entities/audio-features/audio-features-detail.component';
import { AudioFeatures } from 'app/shared/model/audio-features.model';

describe('Component Tests', () => {
  describe('AudioFeatures Management Detail Component', () => {
    let comp: AudioFeaturesDetailComponent;
    let fixture: ComponentFixture<AudioFeaturesDetailComponent>;
    const route = ({ data: of({ audioFeatures: new AudioFeatures(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MusicIntelligenceTestModule],
        declarations: [AudioFeaturesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AudioFeaturesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AudioFeaturesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load audioFeatures on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.audioFeatures).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
