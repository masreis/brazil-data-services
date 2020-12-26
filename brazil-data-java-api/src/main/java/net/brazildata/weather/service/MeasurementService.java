package net.brazildata.weather.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import net.brazildata.weather.model.Measurement;
import net.brazildata.weather.model.dto.Frequency;
import net.brazildata.weather.model.dto.MeasurementDTO;
import net.brazildata.weather.model.dto.TemperatureByFrequencyDTO;

public interface MeasurementService {

  Measurement save(Measurement medida);

  Measurement findByStationIdAndCollectedOn(Long idEstacao, LocalDateTime coletadoEm);

  List<MeasurementDTO> findByYearAndStationState(Integer year, String state, PageRequest pg);

  List<List<TemperatureByFrequencyDTO>> findTemperatureByFrequency(
      Frequency frequency, String years, String states, String stationIds);

  List<Integer> findYears();
}
