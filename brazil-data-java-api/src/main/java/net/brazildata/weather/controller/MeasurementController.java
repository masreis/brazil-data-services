package net.brazildata.weather.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import net.brazildata.weather.model.Measurement;
import net.brazildata.weather.model.dto.Frequency;
import net.brazildata.weather.model.dto.MeasurementDTO;
import net.brazildata.weather.model.dto.TemperatureByFrequencyDTO;
import net.brazildata.weather.repository.MeasurementRepository;

@RestController
@RequestMapping("/v1/weather/measurements")
@CrossOrigin("*")
public class MeasurementController {

  private final MeasurementRepository measurementRepository;

  @Value("${page.size}")
  private int pageSize;

  public MeasurementController(MeasurementRepository medidaRepository) {
    this.measurementRepository = medidaRepository;
  }

  @GetMapping("/{year}/{state}")
  @ApiOperation(value = "Find measurements by year and state")
  public ResponseEntity<List<MeasurementDTO>> findByYearAndStationState(
      @PathVariable Integer year,
      @PathVariable String state,
      @RequestParam(defaultValue = "0") int page) {
    Sort sort = Sort.by("station", "collectedOn");
    PageRequest pg = PageRequest.of(page, pageSize, sort);
    Page<Measurement> medidas =
        this.measurementRepository.findByYearAndStationState(year, state, pg);
    List<MeasurementDTO> dtos = medidas.stream().map(this::convert).collect(Collectors.toList());
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
            ano -> {
              Arrays.asList(states.split(","))
                  .stream()
                  .forEach(
                      state -> {
                        List<Object> list =
                            this.measurementRepository.findTemperatureByFrequencyAndYearAndState(
                                frequency, ano, state);
                        List<TemperatureByFrequencyDTO> collected =
                            list.stream()
                                .map(this::convertTemperatureByFrequency)
                                .collect(Collectors.toList());
                        collected.sort(
                            Comparator.comparing(TemperatureByFrequencyDTO::getMonth)
                                .thenComparing(TemperatureByFrequencyDTO::getYear)
                                .thenComparing(TemperatureByFrequencyDTO::getState));
                        dtos.add(collected);
                      });
            });

    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/years")
  @ApiOperation("Find available years")
  public ResponseEntity<List<Integer>> findYears() {
    List<Integer> years = this.measurementRepository.findYears();
    return ResponseEntity.ok(years);
  }

  private MeasurementDTO convert(Measurement medida) {
    ModelMapper mapper = new ModelMapper();
    return mapper.map(medida, MeasurementDTO.class);
  }

  private TemperatureByFrequencyDTO convertTemperatureByFrequency(Object object) {
    Object[] arr = (Object[]) object;
    int i = 0;
    return TemperatureByFrequencyDTO.builder()
        .temperatureAvg(Float.valueOf(arr[i++].toString()))
        .temperatureMax(Float.valueOf(arr[i++].toString()))
        .temperatureMin(Float.valueOf(arr[i++].toString()))
        .year(Integer.valueOf(arr[i++].toString()))
        .month(Integer.valueOf(arr[i++].toString()))
        .state(arr[i++].toString())
        .build();
  }
}
