import { Component, OnInit } from "@angular/core";
import { StationListComponent } from "../station-list/station-list.component";
import { Station } from "../station.model";

@Component({
  selector: 'app-station-map',
  templateUrl: './station-map.component.html',
  styleUrls: ['./station-map.component.css']
})
export class StationMapComponent extends StationListComponent implements OnInit {

  zoom = 10
  center: google.maps.LatLngLiteral

  ngOnInit() {
    super.ngOnInit();
    navigator.geolocation.getCurrentPosition((position) => {
      this.center = {
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      }
    })
  }

  getPosition(station: Station) {
    let position = { lat: station.position[0], lng: station.position[1] };
    return position;
  }

  getLabel(station: Station) {
    let label = { text: station.name };
    return label;
  }

}
