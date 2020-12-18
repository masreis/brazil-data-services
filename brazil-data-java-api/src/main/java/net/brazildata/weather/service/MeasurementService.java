package net.brazildata.weather.service;

import java.time.LocalDateTime;

import net.brazildata.weather.model.Measurement;

public interface MeasurementService {

  Measurement save(Measurement medida);

  Measurement findByEstacaoIdAndColetadoEm(Long idEstacao, LocalDateTime coletadoEm);
}
