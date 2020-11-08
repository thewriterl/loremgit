import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AlbumService } from 'app/entities/album/album.service';
import { IAlbum, Album } from 'app/shared/model/album.model';
import { AlbumType } from 'app/shared/model/enumerations/album-type.model';

describe('Service Tests', () => {
  describe('Album Service', () => {
    let injector: TestBed;
    let service: AlbumService;
    let httpMock: HttpTestingController;
    let elemDefault: IAlbum;
    let expectedResult: IAlbum | IAlbum[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AlbumService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Album(0, AlbumType.ALBUM, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            releaseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Album', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            releaseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            releaseDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Album()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Album', () => {
        const returnedFromService = Object.assign(
          {
            albumType: 'BBBBBB',
            href: 'BBBBBB',
            idSpotify: 'BBBBBB',
            label: 'BBBBBB',
            name: 'BBBBBB',
            popularity: 1,
            releaseDate: currentDate.format(DATE_FORMAT),
            uri: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            releaseDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Album', () => {
        const returnedFromService = Object.assign(
          {
            albumType: 'BBBBBB',
            href: 'BBBBBB',
            idSpotify: 'BBBBBB',
            label: 'BBBBBB',
            name: 'BBBBBB',
            popularity: 1,
            releaseDate: currentDate.format(DATE_FORMAT),
            uri: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            releaseDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Album', () => {
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