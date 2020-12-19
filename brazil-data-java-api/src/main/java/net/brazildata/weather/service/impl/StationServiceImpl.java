package net.brazildata.weather.service.impl;

import org.springframework.stereotype.Service;

import net.brazildata.weather.model.Station;
import net.brazildata.weather.repository.StationRepository;
import net.brazildata.weather.service.StationService;

@Service
public class StationServiceImpl implements StationService {

  private StationRepository estacaoRepository;

  public StationServiceImpl(StationRepository estacaoRepository) {
    this.estacaoRepository = estacaoRepository;
  }

  public Station save(Station estacao) {
    return this.estacaoRepository.save(estacao);
  }

  @Override
  public Station findByCodigoWmo(String codigoWmo) {
    return estacaoRepository.findByWmoCode(codigoWmo);
  }

//  public List<Station> findAllByState(List<String> states) {
//    return this.estacaoRepository.findAllByState(states);
//  }
}
