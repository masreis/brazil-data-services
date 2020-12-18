package net.brazildata.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brazildata.weather.util.WeatherDataImport;

@RestController
@RequestMapping("/data-import-controller")
public class DataImportController {

  private WeatherDataImport dataImport;

  public DataImportController(WeatherDataImport dataImport) {
    this.dataImport = dataImport;
  }

  @GetMapping("/{ano}")
  public ResponseEntity<?> importData(@PathVariable String ano) {
    this.dataImport.process();
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
