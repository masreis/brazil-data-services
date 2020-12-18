package net.brazildata.weather.model.dto;

import java.time.LocalDate;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.ToString;
import net.brazildata.weather.model.util.PointSerializer;

@Data
@ToString
public class StationDTO {
  private Long id;
  private String region;
  private String state;
  private String wmoCode;

  @JsonSerialize(using = PointSerializer.class)
  private Point position;

  private Float altitude;
  private LocalDate foundationDate;
  private String name;
}
