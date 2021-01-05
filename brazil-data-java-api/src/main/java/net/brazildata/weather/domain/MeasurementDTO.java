package net.brazildata.weather.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MeasurementDTO {
  private Long id;
  private StationDTO station;
  private LocalDateTime collectedOn;
  private Integer year;
  private Float totalPrecipitation;

  // barometric pressure
  private Float atmosphericPressure;
  private Float atmosphericPressureMaxPrevHour;
  private Float atmosphericPressureMinPrevHour;

  private Float globalRadiation;

  // temperatura bulbo seco
  private Float airDryBulbTemperature;

  private Float maxTemperaturePrevHour;
  private Float minTemperaturePrevHour;

  // temperatura ponto de orvalho;
  private Float dewPointTemperature;
  private Float dewPointTemperatureMaxPrevHour;
  private Float dewPointTemperatureMinPrevHour;

  private Float relativeAirHumidityMaxPrevHour;
  private Float relativeAirHumidityMinPrevHour;
  private Float relativeAirHumidity;

  // vento direcao horaria
  private Float windTimeDirection;
  // vento rajada maxima
  private Float windMaxGust;
  // vento velocidade horaria
  private Float windTimeSpeed;
}
