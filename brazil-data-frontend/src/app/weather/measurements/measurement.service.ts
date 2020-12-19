import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TemperatureByFrequency } from './temperature-by-frequency.model';
import { API_URL_MEASUREMENTS } from "../../app.constants";

@Injectable({ providedIn: 'root' })
export class MeasurementService {

  constructor(private http: HttpClient) { }

  findAllTemperatures(years: string[], states: string[]) {
    const url = API_URL_MEASUREMENTS + "/MONTHLY?years=" + years + "&states=" + states
    console.log(url);
    return this.http.get<TemperatureByFrequency[][]>(url);
  }

}

