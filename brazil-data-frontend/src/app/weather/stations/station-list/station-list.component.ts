import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
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
  ) { }

  ngOnInit(): void {
    this.loadStations();
    this.stationService.findAllStates().subscribe(response => this.states = response);

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
    let listObservable: Observable<Station[]>;
    if (this.selectedStates.length == 0) {
      listObservable = this.stationService.findAllStations();
    } else {
      listObservable = this.stationService.findStationsByState(this.selectedStates);
    }

    listObservable.subscribe(response => {
      this.stations = response;
    });

  }

}
