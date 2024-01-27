import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sud } from '../models/sud';
import { SUD_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class SudService {

  constructor(private httpClient: HttpClient) { }

  public getAllSudovi(): Observable<any> {
    return this.httpClient.get(SUD_URL);
  }

  public addSud(sud: Sud): Observable<any> {
    return this.httpClient.post(SUD_URL, sud);
  }

  public updateSud(sud: Sud): Observable<any> {
    return this.httpClient.put(`${SUD_URL}/${sud.id}`, sud);
  }

  public deleteSud(sudId: number): Observable<any> {
    return this.httpClient.delete(`${SUD_URL}/${sudId}`);
  }
}
