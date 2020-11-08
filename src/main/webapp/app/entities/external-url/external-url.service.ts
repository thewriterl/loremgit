import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExternalURL } from 'app/shared/model/external-url.model';

type EntityResponseType = HttpResponse<IExternalURL>;
type EntityArrayResponseType = HttpResponse<IExternalURL[]>;

@Injectable({ providedIn: 'root' })
export class ExternalURLService {
  public resourceUrl = SERVER_API_URL + 'api/external-urls';

  constructor(protected http: HttpClient) {}

  create(externalURL: IExternalURL): Observable<EntityResponseType> {
    return this.http.post<IExternalURL>(this.resourceUrl, externalURL, { observe: 'response' });
  }

  update(externalURL: IExternalURL): Observable<EntityResponseType> {
    return this.http.put<IExternalURL>(this.resourceUrl, externalURL, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExternalURL>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExternalURL[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
