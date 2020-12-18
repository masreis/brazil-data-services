package net.brazildata.weather.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
    indexes = {
      @Index(name = "idx_relativeairhumidity", columnList = "relativeAirHumidity"),
      @Index(name = "idx_air_dry_ulb_temp", columnList = "airDryBulbTemperature"),
      @Index(name = "idx_collected_on", columnList = "collectedOn"),
      @Index(name = "idx_year", columnList = "year"),
      @Index(name = "idx_month", columnList = "month"),
      @Index(name = "idx_year_month", columnList = "year, month"),
      @Index(name = "idx_year_month_state", columnList = "year, month, state"),
      @Index(name = "idx_state", columnList = "state"),
      @Index(
          name = "idx_station_id_collected_on",
          columnList = "station_id, collectedOn",
          unique = true)
    })
public class Measurement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private Station station;

  @Column(columnDefinition = "smallint")
  private Integer year;

  @Column(columnDefinition = "tinyint")
  private Integer month;

  @Column(length = 2)
  private String state;

  private LocalDateTime collectedOn;
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
