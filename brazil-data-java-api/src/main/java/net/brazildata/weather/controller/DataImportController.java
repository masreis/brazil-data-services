package net.brazildata.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brazildata.weather.util.WeatherDataImport;

@RestController
@RequestMapping("/v1/data-import")
public class DataImportController {

  private WeatherDataImport dataImport;

  public DataImportController(WeatherDataImport dataImport) {
    this.dataImport = dataImport;
  }

  @GetMapping("/{year}")
  public ResponseEntity<?> importData(@PathVariable String year) {
    this.dataImport.process();
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
