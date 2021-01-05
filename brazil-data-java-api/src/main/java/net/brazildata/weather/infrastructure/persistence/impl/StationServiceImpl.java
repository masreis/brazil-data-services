package net.brazildata.weather.infrastructure.persistence.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import net.brazildata.weather.domain.StationDTO;
import net.brazildata.weather.domain.model.Station;
import net.brazildata.weather.domain.model.repository.StationRepository;
import net.brazildata.weather.infrastructure.persistence.StationService;

@Service
public class StationServiceImpl implements StationService {

  private final StationRepository stationRepository;
  private final ModelMapper mapper;

  public StationServiceImpl(final StationRepository estacaoRepository, final ModelMapper mapper) {
    this.stationRepository = estacaoRepository;
    this.mapper = mapper;
  }

  public Station save(Station estacao) {
    return this.stationRepository.save(estacao);
  }

  @Override
  public Station findByCodigoWmo(String codigoWmo) {
    return stationRepository.findByWmoCode(codigoWmo);
  }

  @Override
  public List<StationDTO> findAll(PageRequest pg) {
    return this.stationRepository
        .findAll(pg)
        .stream()
        .map(this::convert)
        .collect(Collectors.toList());
  }

  @Override
  public List<StationDTO> findByStateIn(PageRequest pg, List<String> listStates) {
    return this.stationRepository
        .findByStateIn(pg, listStates)
        .stream()
        .map(this::convert)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> findStates() {
    return this.stationRepository.findStates();
  }

  private StationDTO convert(Station estacao) {
    return mapper.map(estacao, StationDTO.class);
  }
}
