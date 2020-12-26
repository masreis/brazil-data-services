package net.brazildata.weather.repository;

import java.util.List;

import net.brazildata.weather.model.dto.Frequency;

public interface MeasurementCustomRepository {

  List<Object> findTemperatureByFrequency(
      Frequency frequency, Integer year, String state, Long idStation);
}
