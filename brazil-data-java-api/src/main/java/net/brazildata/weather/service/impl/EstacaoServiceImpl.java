package net.brazildata.weather.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.brazildata.weather.model.Station;
import net.brazildata.weather.repository.StationRepository;
import net.brazildata.weather.service.StationService;

@Service
public class EstacaoServiceImpl implements StationService {

  private StationRepository estacaoRepository;

  public EstacaoServiceImpl(StationRepository estacaoRepository) {
    this.estacaoRepository = estacaoRepository;
  }

  public Station save(Station estacao) {
    return this.estacaoRepository.save(estacao);
  }

  @Override
  public Station findByCodigoWmo(String codigoWmo) {
    return estacaoRepository.findByWmoCode(codigoWmo);
  }

  public List<Station> findAllByState(String state) {
    return this.estacaoRepository.findAllByState(state);
  }
}
