package net.brazildata.weather.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.brazildata.weather.model.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

  Station findByWmoCode(String wmoCode);

  Page<Station> findByStateIn(Pageable pageable, List<String> states);

  @Query(value = "select distinct state from Station s")
  List<String> findStates();

  //  @Query("select distinct name, state from Station s")
  //  List<Object> findNames();
  //
  //  @Query("select distinct name, state from Station s where s.state = :state")
  //  List<Object> findNamesByState(String state);
}
