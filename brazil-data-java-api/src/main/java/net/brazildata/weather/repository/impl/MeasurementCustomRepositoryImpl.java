package net.brazildata.weather.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;
import net.brazildata.weather.model.dto.Frequency;
import net.brazildata.weather.repository.MeasurementCustomRepository;

@Slf4j
public class MeasurementCustomRepositoryImpl implements MeasurementCustomRepository {

  private EntityManager em;

  public MeasurementCustomRepositoryImpl(EntityManager em) {
    this.em = em;
  }

  public List<Object> findTemperatureByFrequencyAndYearAndStateIn(
      Frequency frequency, List<Integer> years, List<String> states) {
    String sql =
        "select round(avg(air_dry_bulb_temperature),1) temperatureAvg, max(max_temperature_prev_hour) temperatureMax, "
            + "min(min_temperature_prev_hour) temperatureMin, year, month, "
            + "m.state from measurement m left join station s on m.station_id = s.id "
            + "where 1 = 1 and m.year in :years and m.state in (:states) and m.air_dry_bulb_temperature != -9999 "
            + "and m.max_temperature_prev_hour != -9999 and m.min_temperature_prev_hour != -9999 "
            + "group by m.year, m.month, m.state";
    long start = System.currentTimeMillis();
    Query query = em.createNativeQuery(sql, Object.class);
    query.setParameter("years", years);
    query.setParameter("states", states);
    List<Object> resultList = query.getResultList();
    log.info("State: " + states + " - " + (System.currentTimeMillis() - start));
    return resultList;
  }

  // TODO Implement other frequencies
  public List<Object> findTemperatureByFrequencyAndYearAndState(
      Frequency frequency, Integer year, String state) {
    String sql = "";
    switch (frequency) {
      case MONTHLY:
        sql =
            "select round(avg(air_dry_bulb_temperature),1) temperatureAvg, max(max_temperature_prev_hour) temperatureMax, "
                + "min(min_temperature_prev_hour) temperatureMin, year, month, "
                + "m.state, 'MONTHLY' as frequency, date_format(collected_on, '%Y-%m') "
                + "from measurement m left join station s on m.station_id = s.id "
                + "where 1 = 1 and m.year = :year and m.state = :state and m.air_dry_bulb_temperature != -9999 "
                + "and m.max_temperature_prev_hour != -9999 and m.min_temperature_prev_hour != -9999 "
                + "group by m.year, m.month, m.state";
        break;
    }
    Query query = em.createNativeQuery(sql);
    query.setParameter("year", year);
    query.setParameter("state", state);
    List<Object> resultList = query.getResultList();
    return resultList;
  }
}
