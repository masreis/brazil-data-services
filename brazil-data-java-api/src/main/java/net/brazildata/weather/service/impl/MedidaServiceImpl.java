package net.brazildata.weather.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import net.brazildata.weather.model.Measurement;
import net.brazildata.weather.repository.MeasurementRepository;
import net.brazildata.weather.service.MeasurementService;

@Service
public class MedidaServiceImpl implements MeasurementService {

  private MeasurementRepository measurementRepository;

  public MedidaServiceImpl(MeasurementRepository medidaRepository) {
    this.measurementRepository = medidaRepository;
  }

  @Override
  public Measurement save(Measurement medida) {
    return measurementRepository.save(medida);
  }

  @Override
  public Measurement findByEstacaoIdAndColetadoEm(Long stationId, LocalDateTime collectedOn) {
    return measurementRepository.findByStationIdAndCollectedOn(stationId, collectedOn);
  }
}
