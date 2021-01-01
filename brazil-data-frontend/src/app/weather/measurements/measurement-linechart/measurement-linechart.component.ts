import { Component, OnInit } from '@angular/core';
import { ChartDataSets, Chart } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { MeasurementListComponent } from '../measurement-list/measurement-list.component';

@Component({
  selector: 'app-measurement-linechart',
  templateUrl: './measurement-linechart.component.html',
  styleUrls: ['./measurement-linechart.component.css']
})
export class MeasurementLineChartComponent extends MeasurementListComponent implements OnInit {

  ngOnInit() {
    super.ngOnInit();
    Chart.defaults.global.defaultFontFamily = 'Roboto';
  }

  lineChartData: ChartDataSets[] = [];
  lineChartLabels: Label[] = [];

  lineChartOptions = {
    tooltips: {
      mode: 'index',
      intersect: false,
      displayColors: false,
    },
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

  protected loadTemperatures() {
    this.measurementService.findAllTemperatures(this.selectedYears, this.selectedStates, this.selectedStations)
      .subscribe(
        (response) => {
          this.temperaturesByFrequency = [];
          this.lineChartData = [];

          response.forEach(arr => {

            if (this.selectedAggregations.indexOf(this.MINIMUM) >= 0) {
              this.lineChartData.push(
                {
                  data: arr.map(temp => temp.temperatureMin),
                  label: arr[0].location + '/' + arr[0].year + ' (min)',
                  fill: false,
                }
              );
            }

            if (this.selectedAggregations.indexOf(this.AVERAGE) >= 0) {
              this.lineChartData.push(
                {
                  data: arr.map(temp => temp.temperatureAvg),
                  label: arr[0].location + '/' + arr[0].year + ' (avg)',
                  // fill: "-1",
                  fill: false
                }
              );
            }

            if (this.selectedAggregations.indexOf(this.MAXIMUM) >= 0) {
              this.lineChartData.push(
                {
                  data: arr.map(temp => temp.temperatureMax),
                  label: arr[0].location + '/' + arr[0].year + ' (max)',
                  // fill: "-1",
                  fill: false
                }
              );
            }

            this.lineChartLabels = arr.map(temp => temp.month.toLocaleString());

          });
        }, error => {

          this.temperaturesByFrequency = [];
          this.lineChartData = [];

        }
      );
  }

}
