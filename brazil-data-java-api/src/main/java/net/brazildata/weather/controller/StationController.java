package net.brazildata.weather.controller;

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
import net.brazildata.weather.model.Station;
import net.brazildata.weather.model.dto.StationDTO;
import net.brazildata.weather.repository.StationRepository;

@RestController
@RequestMapping("/v1/weather/stations")
@CrossOrigin("*")
public class StationController {

  private StationRepository estacaoRepository;

  @Value("${page.size}")
  private int pageSize;

  public StationController(StationRepository estacaoRepository) {
    this.estacaoRepository = estacaoRepository;
  }

  @GetMapping
  @ApiOperation(value = "Find all stations")
  public ResponseEntity<List<StationDTO>> findAll(@RequestParam(defaultValue = "0") int page) {
    Sort sort = Sort.by("state", "name");
    PageRequest pg = PageRequest.of(page, pageSize, sort);
    Page<Station> estacoes = this.estacaoRepository.findAll(pg);
    List<StationDTO> dtos = estacoes.stream().map(this::convert).collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{state}")
  @ApiOperation(value = "Find all stations by state")
  public ResponseEntity<List<StationDTO>> findAllByState(@PathVariable String state) {
    List<Station> estacoes = this.estacaoRepository.findAllByState(state);
    List<StationDTO> dtos = estacoes.stream().map(this::convert).collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/states")
  @ApiOperation("Find all available states")
  public ResponseEntity<List<String>> findAllStates() {
    List<String> states = this.estacaoRepository.findStates();
    return ResponseEntity.ok(states);
  }

//  @GetMapping("/names")
//  @ApiOperation("Find all available station names")
//  public ResponseEntity<List<String>> findAllNames() {
//    List<String> names =
//        this.estacaoRepository
//            .findNames()
//            .stream()
//            .map(
//                arr -> {
//                  Object[] val = (Object[]) arr;
//                  return val[0] + "/" + val[1];
//                })
//            .collect(Collectors.toList());
//    return ResponseEntity.ok(names);
//  }
//
//  @GetMapping("/{state}/names")
//  @ApiOperation("Find all available station names")
//  public ResponseEntity<List<String>> findAllNamesByState(String state) {
//    List<String> names =
//        this.estacaoRepository
//            .findNamesByState(state)
//            .stream()
//            .map(
//                arr -> {
//                  Object[] val = (Object[]) arr;
//                  return val[0] + "/" + val[1];
//                })
//            .collect(Collectors.toList());
//    return ResponseEntity.ok(names);
//  }

  private StationDTO convert(Station estacao) {
    ModelMapper mapper = new ModelMapper();
    return mapper.map(estacao, StationDTO.class);
  }
}
