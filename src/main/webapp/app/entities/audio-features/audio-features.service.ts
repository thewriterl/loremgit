import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAudioFeatures } from 'app/shared/model/audio-features.model';

type EntityResponseType = HttpResponse<IAudioFeatures>;
type EntityArrayResponseType = HttpResponse<IAudioFeatures[]>;

@Injectable({ providedIn: 'root' })
export class AudioFeaturesService {
  public resourceUrl = SERVER_API_URL + 'api/audio-features';

  constructor(protected http: HttpClient) {}

  create(audioFeatures: IAudioFeatures): Observable<EntityResponseType> {
    return this.http.post<IAudioFeatures>(this.resourceUrl, audioFeatures, { observe: 'response' });
  }

  update(audioFeatures: IAudioFeatures): Observable<EntityResponseType> {
    return this.http.put<IAudioFeatures>(this.resourceUrl, audioFeatures, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAudioFeatures>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAudioFeatures[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
