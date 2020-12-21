import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { Router } from '@angular/router';
import { StationService } from '../../stations/station.service';
import { MeasurementService } from '../measurement.service';
import { TemperatureByFrequency } from '../temperature-by-frequency.model';

@Component({
  selector: 'app-measurement-list',
  templateUrl: './measurement-list.component.html',
  styleUrls: ['./measurement-list.component.css']
})
export class MeasurementListComponent implements OnInit {

  temperaturesByFrequency: TemperatureByFrequency[] = [];
  selectedYears: number[] = [2019];
  selectedStates: string[] = ['DF'];
  years: number[] = [];
  states: string[] = [];

  displayedColumns: string[] = ['temperatureAvg', 'temperatureMax', 'temperatureMin', 'referenceDate', 'year', 'month', 'state', 'frequency'];

  constructor(
    public measurementService: MeasurementService,
    public stationService: StationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadTemperatures();
    this.loadYears();
    this.loadStates();
  }

  private loadStates() {
    this.stationService.findAllStates().subscribe(response => this.states = response);
  }

  private loadYears() {
    this.measurementService.findYears().subscribe(response => {
      this.years = response;
    });
  }

  private loadTemperatures() {
    this.measurementService.findAllTemperatures(this.selectedYears, this.selectedStates).subscribe(
      (response) => {
        this.temperaturesByFrequency = [];
        response.forEach(arr => {
          this.temperaturesByFrequency = this.temperaturesByFrequency.concat(arr);
        });
      }
    );
  }

  edit(id: number) {
    this.router.navigate(['expense-edit', id])
  }

  onChangeState(event: MatCheckboxChange, value: string) {
    if (event.checked) {
      this.selectedStates.push(value);
    } else {
      this.selectedStates.splice(this.selectedStates.indexOf(value), 1);
    }

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

}
