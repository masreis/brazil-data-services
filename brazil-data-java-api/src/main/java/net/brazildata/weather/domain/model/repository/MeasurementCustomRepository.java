package net.brazildata.weather.domain.model.repository;

import java.util.List;

import net.brazildata.weather.domain.Frequency;

public interface MeasurementCustomRepository {

  List<Object> findTemperatureByFrequency(
      Frequency frequency, Integer year, String state, Long idStation);
}
