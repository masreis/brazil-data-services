import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Station } from './station.model';
import { API_URL_STATIONS } from "../../app.constants";

@Injectable({ providedIn: 'root' })
export class StationService {
  private stations: Station[] = [];

  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get<Station[]>(API_URL_STATIONS);
  }

}

