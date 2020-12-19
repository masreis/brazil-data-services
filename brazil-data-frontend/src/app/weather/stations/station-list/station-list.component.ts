import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { Router } from '@angular/router';
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
  selectedStates: string[] = [];

  displayedColumns: string[] = ['name', 'region', 'state', 'wmoCode', 'position', 'altitude', 'foundationDate'];

  constructor(
    public stationService: StationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.stationService.findAll().subscribe(
      (response) => {
        this.stations = response
      }
    );

    this.stationService.findAllStates().subscribe(response => this.states = response);

  }

  modelChange() {
    console.log(this.selectedStates);
  }

  onChange(event: MatCheckboxChange, value: string) {
    if (event.checked) {
      this.selectedStates.push(value);
    } else {
      this.selectedStates.splice(this.selectedStates.indexOf(value), 1);
    }

    this.loadStations();

  }

  edit(id: number) {
    this.router.navigate(['expense-edit', id])
  }


  loadStations() {
    let listObservable = null;
    console.log(this.selectedStates);
    if (this.selectedStates.length == 0) {
      listObservable = this.stationService.findAll();
    } else {
      listObservable = this.stationService.findStationsByState(this.selectedStates);
    }

    listObservable.subscribe(response => {
      this.stations = response;
    });

  }
}
