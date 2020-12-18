import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MeasurementService } from '../measurement.service';
import { TemperatureByFrequency } from '../temperature-by-frequency.model';

@Component({
  selector: 'app-measurement-list',
  templateUrl: './measurement-list.component.html',
  styleUrls: ['./measurement-list.component.css']
})
export class MeasurementListComponent implements OnInit {

  temperaturesByFrequency: TemperatureByFrequency[][] = [];
  selectedYears: string[] = [];
  selectedStates: string[] = [];
  years: string[] = [];
  states: string[] = [];

  displayedColumns: string[] = ['temperatureAvg', 'temperatureMax', 'temperatureMin', 'referenceDate', 'year', 'month', 'state', 'frequency'];

  constructor(
    public measurementService: MeasurementService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.measurementService.findAllTemperatures(this.selectedYears, this.selectedStates).subscribe(
      (response) => {
        console.log(response);
        this.temperaturesByFrequency = response
      }
    );

  }

  edit(id: number) {
    this.router.navigate(['expense-edit', id])
  }

}
