import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
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

  displayedColumns: string[] = ['id', 'region', 'state', 'wmoCode', 'position', 'altitude', 'foundationDate', 'name'];

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

  }

  edit(id: number) {
    this.router.navigate(['expense-edit', id])
  }

}
