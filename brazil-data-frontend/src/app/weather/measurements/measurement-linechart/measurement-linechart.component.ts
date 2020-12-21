import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { Router } from '@angular/router';
import { ChartDataSets } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { StationService } from '../../stations/station.service';
import { MeasurementService } from '../measurement.service';
import { TemperatureByFrequency } from '../temperature-by-frequency.model';

@Component({
  selector: 'app-measurement-linechart',
  templateUrl: './measurement-linechart.component.html',
  styleUrls: ['./measurement-linechart.component.css']
})
export class MeasurementLineChartComponent implements OnInit {

  temperaturesByFrequency: TemperatureByFrequency[] = [];
  selectedYears: number[] = [2019];
  selectedStates: string[] = ['DF'];
  years: number[] = [];
  states: string[] = [];

  lineChartData: ChartDataSets[] = [];
  lineChartLabels: Label[] = [];

  lineChartOptions = {
    responsive: true,
    legend: { position: 'right' },
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          },
          display: true,
          scaleLabel: {
            display: true,
            labelString: "Degrees celsius",
          }
        }
      ], xAxes: [
        {
          scaleLabel: {
            display: true,
            labelString: "Month of the Year",
          },
        },
      ]
    }
  };

  lineChartColors: Color[] = [];

  lineChartLegend = true;
  lineChartPlugins = [];
  lineChartType = 'line';

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
        this.lineChartData = [];

        response.forEach(arr => {

          this.lineChartData.push(
            {
              data: arr.map(temp => temp.temperatureAvg),
              label: arr[0].state + '/' + arr[0].year,
              fill: false
            }
          );

          this.lineChartLabels = arr.map(temp => temp.month.toLocaleString());

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
      const indexOf = this.selectedStates.indexOf(value);
      this.selectedStates.splice(indexOf, 1);
    }

    this.selectedStates.sort();

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
