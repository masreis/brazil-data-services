package net.brazildata.weather.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import net.brazildata.weather.domain.Frequency;
import net.brazildata.weather.domain.MeasurementDTO;
import net.brazildata.weather.domain.TemperatureByFrequencyDTO;
import net.brazildata.weather.domain.model.Measurement;

public interface MeasurementService {

  Measurement save(Measurement medida);

  Measurement findByStationIdAndCollectedOn(Long idEstacao, LocalDateTime coletadoEm);

  List<MeasurementDTO> findByYearAndStationState(Integer year, String state, PageRequest pg);

  List<List<TemperatureByFrequencyDTO>> findTemperatureByFrequency(
      Frequency frequency, String years, String states, String stationIds);

  List<Integer> findYears();
}
