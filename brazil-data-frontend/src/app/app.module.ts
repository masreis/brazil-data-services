import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StationListComponent } from './weather/stations/station-list/station-list.component';
import { HeaderComponent } from './header/header.components';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { StationService } from './weather/stations/station.service';
import { HttpClientModule } from '@angular/common/http';


import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from "@angular/material/table";
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatExpansionModule } from "@angular/material/expansion";
import { MatIconModule } from "@angular/material/icon";
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatSidenavModule } from '@angular/material/sidenav';
import { ChartsModule } from 'ng2-charts';

import { MeasurementService } from './weather/measurements/measurement.service';
import { MeasurementListComponent } from './weather/measurements/measurement-list/measurement-list.component';
import { MeasurementLineChartComponent } from "./weather/measurements/measurement-linechart/measurement-linechart.component";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    StationListComponent,
    MeasurementListComponent,
    MeasurementLineChartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,

    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatToolbarModule,
    MatExpansionModule,
    MatTableModule,
    MatIconModule,
    MatSidenavModule,
    MatSelectModule,
    MatCheckboxModule,

    ChartsModule,

    HttpClientModule
  ],
  providers: [StationService, MeasurementService],
  bootstrap: [AppComponent]
})
export class AppModule { }
