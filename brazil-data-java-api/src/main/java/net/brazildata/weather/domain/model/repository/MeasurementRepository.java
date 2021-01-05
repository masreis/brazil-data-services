package net.brazildata.weather.domain.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.brazildata.weather.domain.model.Measurement;

@Repository
public interface MeasurementRepository
    extends JpaRepository<Measurement, Long>, MeasurementCustomRepository {

  Measurement findByStationIdAndCollectedOn(Long stationId, LocalDateTime collectedOn);

  Page<Measurement> findByYearAndStationState(Integer year, String state, Pageable page);

  @Query(value = "select distinct year from Measurement m")
  List<Integer> findYears();
}
