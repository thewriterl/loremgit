import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISegment } from 'app/shared/model/segment.model';

type EntityResponseType = HttpResponse<ISegment>;
type EntityArrayResponseType = HttpResponse<ISegment[]>;

@Injectable({ providedIn: 'root' })
export class SegmentService {
  public resourceUrl = SERVER_API_URL + 'api/segments';

  constructor(protected http: HttpClient) {}

  create(segment: ISegment): Observable<EntityResponseType> {
    return this.http.post<ISegment>(this.resourceUrl, segment, { observe: 'response' });
  }

  update(segment: ISegment): Observable<EntityResponseType> {
    return this.http.put<ISegment>(this.resourceUrl, segment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISegment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISegment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
