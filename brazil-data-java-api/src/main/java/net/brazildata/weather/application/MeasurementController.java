package net.brazildata.weather.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.brazildata.weather.domain.Frequency;
import net.brazildata.weather.domain.MeasurementDTO;
import net.brazildata.weather.domain.TemperatureByFrequencyDTO;
import net.brazildata.weather.infrastructure.persistence.MeasurementService;

@RestController
@RequestMapping("/v1/weather/measurements")
@CrossOrigin("*")
public class MeasurementController {

  @Value("${page.size}")
  private int pageSize;

  private final MeasurementService measurementService;

  public MeasurementController(MeasurementService measurementService) {
    this.measurementService = measurementService;
  }

  @GetMapping("/{year}/{state}")
  @ApiOperation(value = "Find measurements by year and state")
  public ResponseEntity<List<MeasurementDTO>> findByYearAndStationState(
      @PathVariable Integer year,
      @PathVariable String state,
      @RequestParam(defaultValue = "0") int page) {
    Sort sort = Sort.by("station", "collectedOn");
    PageRequest pg = PageRequest.of(page, pageSize, sort);
    List<MeasurementDTO> dtos = this.measurementService.findByYearAndStationState(year, state, pg);
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{frequency}")
  @ApiOperation(
      "Find measurements by frequency. Filter by request param 'years', 'states' and 'idStations'. "
          + "Values separated by comma, i.e. 'states=2001,2002', 'states='AA,BB,CC' and 'stationIds=1,2,3'")
  public ResponseEntity<List<List<TemperatureByFrequencyDTO>>> findTemperatureByFrequency(
      @PathVariable Frequency frequency,
      @RequestParam(required = false) String years,
      @RequestParam(required = false) String states,
      @RequestParam(required = false) String stationIds) {
    return ResponseEntity.ok(
        this.measurementService.findTemperatureByFrequency(frequency, years, states, stationIds));
  }

  @GetMapping("/years")
  @ApiOperation("Find available years")
  public ResponseEntity<List<Integer>> findYears() {
    List<Integer> years = this.measurementService.findYears();
    return ResponseEntity.ok(years);
  }
}
