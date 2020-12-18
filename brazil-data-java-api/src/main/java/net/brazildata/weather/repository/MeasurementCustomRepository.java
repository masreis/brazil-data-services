package net.brazildata.weather.repository;

import java.util.List;

import net.brazildata.weather.model.dto.Frequency;

public interface MeasurementCustomRepository {

  List<Object> findTemperatureByFrequencyAndYearAndStateIn(
      Frequency frequency, List<Integer> years, List<String> states);

  List<Object> findTemperatureByFrequencyAndYearAndState(
      Frequency frequency, Integer year, String state);
}
