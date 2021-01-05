package net.brazildata.weather.infrastructure.persistence;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import net.brazildata.weather.domain.StationDTO;
import net.brazildata.weather.domain.model.Station;

public interface StationService {

  Station save(Station estacao);

  Station findByCodigoWmo(String codigoWmo);

  List<StationDTO> findAll(PageRequest pg);

  List<StationDTO> findByStateIn(PageRequest pg, List<String> listStates);

  List<String> findStates();
}
