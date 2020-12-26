package net.brazildata.weather.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import net.brazildata.weather.model.Measurement;
import net.brazildata.weather.model.dto.Frequency;
import net.brazildata.weather.model.dto.MeasurementDTO;
import net.brazildata.weather.model.dto.TemperatureByFrequencyDTO;
import net.brazildata.weather.repository.MeasurementRepository;
import net.brazildata.weather.service.MeasurementService;

@Service
public class MedidaServiceImpl implements MeasurementService {

  private final MeasurementRepository measurementRepository;
  private final ModelMapper mapper;

  public MedidaServiceImpl(final MeasurementRepository medidaRepository, final ModelMapper mapper) {
    this.measurementRepository = medidaRepository;
    this.mapper = mapper;
  }

  public Measurement save(Measurement medida) {
    return measurementRepository.save(medida);
  }

  public Measurement findByStationIdAndCollectedOn(Long stationId, LocalDateTime collectedOn) {
    return measurementRepository.findByStationIdAndCollectedOn(stationId, collectedOn);
  }

  private TemperatureByFrequencyDTO convertTemperatureByFrequency(Object object) {

    Object[] arr = (Object[]) object;
    int i = 0;
    return TemperatureByFrequencyDTO.builder()
        .temperatureAvg(Float.valueOf(arr[i++].toString()))
        .temperatureMax(Float.valueOf(arr[i++].toString()))
        .temperatureMin(Float.valueOf(arr[i++].toString()))
        .year(Integer.valueOf(arr[i++].toString()))
        .month(Integer.valueOf(arr[i++].toString()))
        .location(arr[i++].toString())
        .frequency(Frequency.valueOf(arr[i++].toString()))
        .referenceDate(arr[i++].toString())
        .build();
  }

  @Override
  public List<MeasurementDTO> findByYearAndStationState(
      Integer year, String state, PageRequest pg) {
    return this.measurementRepository
        .findByYearAndStationState(year, state, pg)
        .stream()
        .map(this::convert)
        .collect(Collectors.toList());
  }

  //  @Override
  //  public List<TemperatureByFrequencyDTO> findTemperatureByFrequencyAndYearAndState(
  //      Frequency frequency, Integer year, String state) {
  //    List<TemperatureByFrequencyDTO> list =
  //        this.measurementRepository
  //            .findTemperatureByFrequencyAndYearAndState(frequency, year, state)
  //            .stream()
  //            .map(this::convertTemperatureByFrequency)
  //            .collect(Collectors.toList());
  //    return list;
  //  }

  @Override
  public List<Integer> findYears() {
    return this.measurementRepository.findYears();
  }

  private MeasurementDTO convert(Measurement medida) {
    return mapper.map(medida, MeasurementDTO.class);
  }

  @Override
  public List<List<TemperatureByFrequencyDTO>> findTemperatureByFrequency(
      Frequency frequency, String years, String states, String stationIds) {

    List<List<TemperatureByFrequencyDTO>> dtos = new ArrayList<>();

    List<Integer> listYears =
        Arrays.asList(years.split(","))
            .stream()
            .mapToInt(year -> Integer.valueOf(year))
            .boxed()
            .collect(Collectors.toList());

    if (StringUtils.isNotEmpty(stationIds)) {
      loadByStations(frequency, stationIds, dtos, listYears);
    } else {
      loadByStates(frequency, states, dtos, listYears);
    }
    return dtos;
  }

  private void loadByStations(
      Frequency frequency,
      String stationIds,
      List<List<TemperatureByFrequencyDTO>> dtos,
      List<Integer> listYears) {
    listYears.forEach(
        year -> {
          Arrays.asList(stationIds.split(","))
              .stream()
              .mapToLong(id -> Long.parseLong(id))
              .boxed()
              .forEach(
                  stationId -> {
                    List<TemperatureByFrequencyDTO> list =
                        this.measurementRepository
                            .findTemperatureByFrequency(frequency, year, null, stationId)
                            .stream()
                            .map(this::convertTemperatureByFrequency)
                            .collect(Collectors.toList());
                    dtos.add(list);
                  });
        });
  }

  private void loadByStates(
      Frequency frequency,
      String states,
      List<List<TemperatureByFrequencyDTO>> dtos,
      List<Integer> listYears) {
    listYears.forEach(
        year -> {
          Arrays.asList(states.split(","))
              .stream()
              .forEach(
                  state -> {
                    List<TemperatureByFrequencyDTO> list =
                        this.measurementRepository
                            .findTemperatureByFrequency(frequency, year, state, null)
                            .stream()
                            .map(this::convertTemperatureByFrequency)
                            .collect(Collectors.toList());
                    dtos.add(list);
                  });
        });
  }
}
