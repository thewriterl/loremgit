import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAudioAnalysis } from 'app/shared/model/audio-analysis.model';

type EntityResponseType = HttpResponse<IAudioAnalysis>;
type EntityArrayResponseType = HttpResponse<IAudioAnalysis[]>;

@Injectable({ providedIn: 'root' })
export class AudioAnalysisService {
  public resourceUrl = SERVER_API_URL + 'api/audio-analyses';

  constructor(protected http: HttpClient) {}

  create(audioAnalysis: IAudioAnalysis): Observable<EntityResponseType> {
    return this.http.post<IAudioAnalysis>(this.resourceUrl, audioAnalysis, { observe: 'response' });
  }

  update(audioAnalysis: IAudioAnalysis): Observable<EntityResponseType> {
    return this.http.put<IAudioAnalysis>(this.resourceUrl, audioAnalysis, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAudioAnalysis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAudioAnalysis[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
