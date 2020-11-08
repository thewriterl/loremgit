import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITimeInterval } from 'app/shared/model/time-interval.model';

type EntityResponseType = HttpResponse<ITimeInterval>;
type EntityArrayResponseType = HttpResponse<ITimeInterval[]>;

@Injectable({ providedIn: 'root' })
export class TimeIntervalService {
  public resourceUrl = SERVER_API_URL + 'api/time-intervals';

  constructor(protected http: HttpClient) {}

  create(timeInterval: ITimeInterval): Observable<EntityResponseType> {
    return this.http.post<ITimeInterval>(this.resourceUrl, timeInterval, { observe: 'response' });
  }

  update(timeInterval: ITimeInterval): Observable<EntityResponseType> {
    return this.http.put<ITimeInterval>(this.resourceUrl, timeInterval, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITimeInterval>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITimeInterval[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
