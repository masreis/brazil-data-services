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
  selectedYears: string[] = ['2019'];
  selectedStates: string[] = [];
  years: string[] = [];
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
    this.stationService.findAllStates().subscribe(response => {
      this.years = response;
    });
  }

  private loadTemperatures() {
    this.measurementService.findAllTemperatures(this.selectedYears, this.selectedStates).subscribe(
      (response) => {
        this.temperaturesByFrequency = [];
        response.forEach(arr => {
          console.log(arr);
          this.temperaturesByFrequency = this.temperaturesByFrequency.concat(arr);
        });
      }
    );
  }

  edit(id: number) {
    this.router.navigate(['expense-edit', id])
  }

  onChange(event: MatCheckboxChange, value: string) {
    if (event.checked) {
      this.selectedStates.push(value);
    } else {
      this.selectedStates.splice(this.selectedStates.indexOf(value), 1);
    }

    this.loadTemperatures();

  }

}
