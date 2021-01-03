import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MeasurementLineChartComponent } from './weather/measurements/measurement-linechart/measurement-linechart.component';
import { MeasurementListComponent } from './weather/measurements/measurement-list/measurement-list.component';
import { StationListComponent } from './weather/stations/station-list/station-list.component';
import { StationMapComponent } from './weather/stations/station-map/station-map.component';

const routes: Routes = [
  { path: '', component: StationListComponent },
  { path: 'station-list', component: StationListComponent },
  { path: 'station-map', component: StationMapComponent },
  { path: 'measurement-list', component: MeasurementListComponent },
  { path: 'measurement-linechart', component: MeasurementLineChartComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
