import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPitch } from 'app/shared/model/pitch.model';

type EntityResponseType = HttpResponse<IPitch>;
type EntityArrayResponseType = HttpResponse<IPitch[]>;

@Injectable({ providedIn: 'root' })
export class PitchService {
  public resourceUrl = SERVER_API_URL + 'api/pitches';

  constructor(protected http: HttpClient) {}

  create(pitch: IPitch): Observable<EntityResponseType> {
    return this.http.post<IPitch>(this.resourceUrl, pitch, { observe: 'response' });
  }

  update(pitch: IPitch): Observable<EntityResponseType> {
    return this.http.put<IPitch>(this.resourceUrl, pitch, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPitch>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPitch[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
