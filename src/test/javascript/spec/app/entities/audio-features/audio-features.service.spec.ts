import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AudioFeaturesService } from 'app/entities/audio-features/audio-features.service';
import { IAudioFeatures, AudioFeatures } from 'app/shared/model/audio-features.model';

describe('Service Tests', () => {
  describe('AudioFeatures Service', () => {
    let injector: TestBed;
    let service: AudioFeaturesService;
    let httpMock: HttpTestingController;
    let elemDefault: IAudioFeatures;
    let expectedResult: IAudioFeatures | IAudioFeatures[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AudioFeaturesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AudioFeatures(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AudioFeatures', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AudioFeatures()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AudioFeatures', () => {
        const returnedFromService = Object.assign(
          {
            durationMs: 1,
            key: 1,
            mode: 1,
            timeSignature: 1,
            acousticness: 1,
            daceability: 1,
            energy: 1,
            instrumentalness: 1,
            liveness: 1,
            loudness: 1,
            speechiness: 1,
            valence: 1,
            tempo: 1,
            uri: 'BBBBBB',
            trackHref: 'BBBBBB',
            analysisUrl: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AudioFeatures', () => {
        const returnedFromService = Object.assign(
          {
            durationMs: 1,
            key: 1,
            mode: 1,
            timeSignature: 1,
            acousticness: 1,
            daceability: 1,
            energy: 1,
            instrumentalness: 1,
            liveness: 1,
            loudness: 1,
            speechiness: 1,
            valence: 1,
            tempo: 1,
            uri: 'BBBBBB',
            trackHref: 'BBBBBB',
            analysisUrl: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AudioFeatures', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
