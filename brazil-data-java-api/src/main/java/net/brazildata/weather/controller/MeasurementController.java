package net.brazildata.weather.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import net.brazildata.weather.model.dto.Frequency;
import net.brazildata.weather.model.dto.MeasurementDTO;
import net.brazildata.weather.model.dto.TemperatureByFrequencyDTO;
import net.brazildata.weather.service.MeasurementService;

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
  @ApiOperation("Find measurements by frequency")
  public ResponseEntity<List<List<TemperatureByFrequencyDTO>>> findTemperatureByFrequency(
      @PathVariable Frequency frequency,
      @RequestParam(required = false) String years,
      @RequestParam(required = false) String states) {

    List<List<TemperatureByFrequencyDTO>> dtos = new ArrayList<>();

    Arrays.asList(years.split(","))
        .stream()
        .mapToInt(year -> Integer.valueOf(year))
        .boxed()
        .collect(Collectors.toList())
        .forEach(
            year -> {
              Arrays.asList(states.split(","))
                  .stream()
                  .forEach(
                      state -> {
                        List<TemperatureByFrequencyDTO> list =
                            this.measurementService.findTemperatureByFrequencyAndYearAndState(
                                frequency, year, state);
                        dtos.add(list);
                      });
            });

    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/years")
  @ApiOperation("Find available years")
  public ResponseEntity<List<Integer>> findYears() {
    List<Integer> years = this.measurementService.findYears();
    return ResponseEntity.ok(years);
  }
}
