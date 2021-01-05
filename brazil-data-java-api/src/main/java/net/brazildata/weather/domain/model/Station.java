package net.brazildata.weather.domain.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.locationtech.jts.geom.Point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    indexes = {
      @Index(name = "idx_region", columnList = "region"),
      @Index(name = "idx_state", columnList = "state"),
      @Index(name = "idx_wmo_code", columnList = "wmoCode", unique = true)
    })
public class Station {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 2)
  private String region;

  @Column(length = 2)
  private String state;

  private String wmoCode;

  @Column(columnDefinition = "POINT")
  private Point position;

  private Float altitude;
  private LocalDate foundationDate;
  private String name;
}
