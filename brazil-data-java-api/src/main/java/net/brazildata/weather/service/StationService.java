package net.brazildata.weather.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import net.brazildata.weather.model.Station;
import net.brazildata.weather.model.dto.StationDTO;

public interface StationService {

  Station save(Station estacao);

  Station findByCodigoWmo(String codigoWmo);

  List<StationDTO> findAll(PageRequest pg);

  List<StationDTO> findByStateIn(PageRequest pg, List<String> listStates);

  List<String> findStates();
}
