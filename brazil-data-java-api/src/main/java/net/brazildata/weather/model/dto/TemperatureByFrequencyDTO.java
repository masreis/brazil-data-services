package net.brazildata.weather.model.dto;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Data;
import net.brazildata.weather.model.util.PointSerializer;

@Data
@Builder
public class TemperatureByFrequencyDTO {
  private Frequency frequency;
  private Float temperatureAvg;
  private Float temperatureMax;
  private Float temperatureMin;

  private Integer year;
  private Integer month;
  private String region;
  private String state;
  private String wmoCode;

  @JsonSerialize(using = PointSerializer.class)
  private Point position;

  private String name;
}
