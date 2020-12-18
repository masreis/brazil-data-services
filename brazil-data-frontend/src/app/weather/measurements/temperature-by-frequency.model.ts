import { Frequency } from "./frequency.model";

export interface TemperatureByFrequency {
  temperatureAvg: number;
  temperatureMax: number;
  temperatureMin: number;
  referenceDate: string;
  year: number;
  month: number;
  state: string;
  frequency: Frequency;
}
