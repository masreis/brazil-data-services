package net.brazildata.weather.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureByFrequencyDTO {
  private Float temperatureAvg;
  private Float temperatureMax;
  private Float temperatureMin;
  private String referenceDate;
  private Integer year;
  private Integer month;
  private String location;
  private Frequency frequency;
}
