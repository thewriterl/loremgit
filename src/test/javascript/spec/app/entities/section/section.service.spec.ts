import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SectionService } from 'app/entities/section/section.service';
import { ISection, Section } from 'app/shared/model/section.model';

describe('Service Tests', () => {
  describe('Section Service', () => {
    let injector: TestBed;
    let service: SectionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISection;
    let expectedResult: ISection | ISection[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SectionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Section(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Section', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Section()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Section', () => {
        const returnedFromService = Object.assign(
          {
            start: 1,
            duration: 1,
            confidence: 1,
            loudness: 1,
            tempo: 1,
            tempoConfidence: 1,
            key: 1,
            keyConfidence: 1,
            mode: 1,
            modeConfidence: 1,
            timeSignature: 1,
            timeSignatureConfidence: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Section', () => {
        const returnedFromService = Object.assign(
          {
            start: 1,
            duration: 1,
            confidence: 1,
            loudness: 1,
            tempo: 1,
            tempoConfidence: 1,
            key: 1,
            keyConfidence: 1,
            mode: 1,
            modeConfidence: 1,
            timeSignature: 1,
            timeSignatureConfidence: 1,
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

      it('should delete a Section', () => {
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
