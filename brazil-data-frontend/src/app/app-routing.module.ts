import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MeasurementListComponent } from './weather/measurements/measurement-list/measurement-list.component';
import { StationListComponent } from './weather/stations/station-list/station-list.component';

const routes: Routes = [
  { path: '', component: StationListComponent },
  { path: 'station-list', component: StationListComponent },
  { path: 'measurement-list', component: MeasurementListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
