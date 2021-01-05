package net.brazildata.weather.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/data-import")
public class DataImportController {

  @GetMapping("/{year}")
  public ResponseEntity<?> importData(@PathVariable String year) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
