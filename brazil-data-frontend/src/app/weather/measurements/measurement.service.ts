import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TemperatureByFrequency } from './temperature-by-frequency.model';
import { API_URL_MEASUREMENTS } from "../../app.constants";
import { API_URL_MEASUREMENTS_YEARS } from "../../app.constants";

@Injectable({ providedIn: 'root' })
export class MeasurementService {

  findYears() {
    return this.http.get<number[]>(API_URL_MEASUREMENTS_YEARS);
  }

  constructor(private http: HttpClient) { }

  findAllTemperatures(years: number[], states: string[]) {
    const url = API_URL_MEASUREMENTS + "/MONTHLY?years=" + years + "&states=" + states
    return this.http.get<TemperatureByFrequency[][]>(url);
  }

}

