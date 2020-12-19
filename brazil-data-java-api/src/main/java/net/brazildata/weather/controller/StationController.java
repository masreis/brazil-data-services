package net.brazildata.weather.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.brazildata.weather.model.Station;
import net.brazildata.weather.model.dto.StationDTO;
import net.brazildata.weather.repository.StationRepository;

@RestController
@RequestMapping("/v1/weather/stations")
@CrossOrigin("*")
public class StationController {

  private StationRepository stationRepository;

  @Value("${page.size}")
  private int pageSize;

  public StationController(StationRepository estacaoRepository) {
    this.stationRepository = estacaoRepository;
  }

  @GetMapping
  @ApiOperation(value = "Find all stations")
  public ResponseEntity<List<StationDTO>> findAll(
      @RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String states) {
    Sort sort = Sort.by("state", "name");
    PageRequest pg = PageRequest.of(page, pageSize, sort);
    Page<Station> estacoes = null;
    if (states == null) {
      estacoes = this.stationRepository.findAll(pg);
    } else {
      List<String> listStates =
          Arrays.asList(states.split(","))
              .stream()
              .map(st -> st.toString())
              .collect(Collectors.toList());
      estacoes = this.stationRepository.findByStateIn(pg, listStates);
    }
    List<StationDTO> dtos = estacoes.stream().map(this::convert).collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  //  @GetMapping
  //  @ApiOperation(value = "Find all stations by state")
  //  public ResponseEntity<List<StationDTO>> findAllByState(
  //      @RequestParam(required = false) String state) {
  //    List<Station> estacoes = this.estacaoRepository.findAllByState(state);
  //    List<StationDTO> dtos = estacoes.stream().map(this::convert).collect(Collectors.toList());
  //    return ResponseEntity.ok(dtos);
  //  }

  @GetMapping("/states")
  @ApiOperation("Find all available states")
  public ResponseEntity<List<String>> findAllStates() {
    List<String> states = this.stationRepository.findStates();
    return ResponseEntity.ok(states);
  }

  // FIXME Refatorar esse mẽtodo
  private StationDTO convert(Station estacao) {
    ModelMapper mapper = new ModelMapper();
    return mapper.map(estacao, StationDTO.class);
  }
}
