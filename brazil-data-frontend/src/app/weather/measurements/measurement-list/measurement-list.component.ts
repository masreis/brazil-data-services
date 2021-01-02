import { Component, OnInit, ɵConsole } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { Router } from '@angular/router';
import { Station } from '../../stations/station.model';
import { StationService } from '../../stations/station.service';
import { MeasurementService } from '../measurement.service';
import { TemperatureByFrequency } from '../temperature-by-frequency.model';

@Component({
  selector: 'app-measurement-list',
  templateUrl: './measurement-list.component.html',
  styleUrls: ['./measurement-list.component.css']
})
export class MeasurementListComponent implements OnInit {

  MAXIMUM: string = 'MAX';
  MINIMUM: string = 'MIN';
  AVERAGE: string = 'AVG';

  temperaturesByFrequency: TemperatureByFrequency[] = [];
  selectedYears: number[] = [2020];
  selectedStates: string[] = ['DF'];
  selectedStations: string[] = [];
  selectedAggregations: string[] = [this.AVERAGE];

  years: number[] = [];
  states: string[] = [];
  stations: Station[] = [];
  aggregations: string[] = [];

  displayedColumns: string[] = ['temperatureAvg', 'temperatureMax', 'temperatureMin', 'referenceDate', 'year', 'month', 'location', 'frequency'];

  constructor(
    public measurementService: MeasurementService,
    public stationService: StationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadAggregations();
    this.loadYears();
    this.loadStates();
    this.loadStationNamesByState();

    this.loadTemperatures();

  }

  loadAggregations() {
    this.aggregations.push(this.MINIMUM);
    this.aggregations.push(this.AVERAGE);
    this.aggregations.push(this.MAXIMUM);
  }

  loadStationNamesByState() {
    this.stationService.findStationsByState(this.selectedStates).subscribe(response =>
      this.stations = response);
  }

  protected loadStates() {
    this.stationService.findAllStates().subscribe(response => this.states = response);
  }

  protected loadYears() {
    this.measurementService.findYears().subscribe(
      response => this.years = response,
      error => console.log("ERROR: ", error)
    );
  }

  protected loadTemperatures() {
    this.measurementService.findTemperatures(this.selectedYears, this.selectedStates, this.selectedStations)
      .subscribe(
        response => {
          this.temperaturesByFrequency = [];
          response.forEach(arr => {
            this.temperaturesByFrequency = this.temperaturesByFrequency.concat(arr);
          });
        }
      );
  }

  onChangeState(event: MatCheckboxChange, value: string) {
    this.selectedStations = [];
    if (event.checked) {
      this.selectedStates.push(value);
    } else {
      this.selectedStates.splice(this.selectedStates.indexOf(value), 1);
    }

    this.loadStationNamesByState();

    this.loadTemperatures();

  }

  onChangeYear(event: MatCheckboxChange, value: number) {
    if (event.checked) {
      this.selectedYears.push(value);
    } else {
      const indexOf = this.selectedYears.indexOf(value);
      this.selectedYears.splice(indexOf, 1);
    }

    this.selectedYears.sort();

    this.loadTemperatures();

  }

  onChangeStation(event: MatCheckboxChange, value: string) {
    if (event.checked) {
      this.selectedStations.push(value);
    } else {
      this.selectedStations.splice(this.selectedStations.indexOf(value), 1);
    }

    this.loadTemperatures();

  }

  onChangeAggregation(event: MatCheckboxChange, value: string) {
    if (event.checked) {
      this.selectedAggregations.push(value);
    } else {
      this.selectedAggregations.splice(this.selectedAggregations.indexOf(value), 1);
    }

    this.loadTemperatures();

  }

}
