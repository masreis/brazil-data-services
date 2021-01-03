import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Station } from '../station.model';
import { StationService } from '../station.service';

@Component({
  selector: 'app-station-list',
  templateUrl: './station-list.component.html',
  styleUrls: ['./station-list.component.css']
})
export class StationListComponent implements OnInit {

  stations: Station[] = [];
  states: string[] = [];
  selectedStates: string[] = ['DF'];

  displayedColumns: string[] = ['name', 'region', 'state', 'wmoCode', 'position', 'altitude', 'foundationDate'];

  constructor(
    public stationService: StationService,
    private router: Router
  ) {
    console.log("constructor");
    this.loadStates();
  }

  ngOnInit(): void {
    console.log("ngOnInit");
    this.loadStations();
  }

  loadStates() {
    console.log("loadStates")
    this.stationService.findAllStates().subscribe(response => this.states = response);
  }

  onChange(event: MatCheckboxChange, value: string) {
    console.log("onChance");
    if (event.checked) {
      this.selectedStates.push(value);
    } else {
      this.selectedStates.splice(this.selectedStates.indexOf(value), 1);
    }

    this.loadStations();

  }

  loadStations() {
    console.log("loadStations");
    this.stations = [];
    this.stationService.findStationsByState(this.selectedStates).subscribe(response => {
      this.stations = response;
    });

  }

}
