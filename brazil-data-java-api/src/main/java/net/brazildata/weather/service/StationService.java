package net.brazildata.weather.service;

import net.brazildata.weather.model.Station;

public interface StationService {

  Station save(Station estacao);

  Station findByCodigoWmo(String codigoWmo);
}
