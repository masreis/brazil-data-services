package net.brazildata.weather.application;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.brazildata.weather.domain.StationDTO;
import net.brazildata.weather.infrastructure.persistence.StationService;

@RestController
@RequestMapping("/v1/weather/stations")
@CrossOrigin("*")
public class StationController {

  private StationService stationService;

  @Value("${page.size}")
  private int pageSize;

  public StationController(StationService stationService) {
    this.stationService = stationService;
  }

  @GetMapping
  @ApiOperation(value = "Find all stations")
  public ResponseEntity<List<StationDTO>> findAll(
      @RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String states) {
    Sort sort = Sort.by("state", "name");
    PageRequest pg = PageRequest.of(page, pageSize, sort);
    List<StationDTO> stations = null;
    if (states == null) {
      stations = this.stationService.findAll(pg);
    } else {
      List<String> listStates =
          Arrays.asList(states.split(","))
              .stream()
              .map(st -> st.toString())
              .collect(Collectors.toList());
      stations = this.stationService.findByStateIn(pg, listStates);
    }
    return ResponseEntity.ok(stations);
  }

  @GetMapping("/states")
  @ApiOperation("Find all available states")
  public ResponseEntity<List<String>> findAllStates() {
    List<String> states = this.stationService.findStates();
    return ResponseEntity.ok(states);
  }
}
