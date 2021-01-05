package net.brazildata.weather.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import net.brazildata.weather.domain.model.Station;
import net.brazildata.weather.domain.model.repository.StationRepository;

// @RunWith(SpringRunner.class)
// @SpringBootTest
class EstacaoRepositoryTest {

  //    @Autowired
  private StationRepository estacaoRepository;

  //    @Test
  final void testFindAll() {}

  //    @Test
  final void testSave() {
    try {
      Point geom = new GeometryFactory().createPoint(new Coordinate(-15.789343, -47.925756));
      Station estacao =
          Station.builder()
              .altitude(1100.0f)
              .wmoCode("cod")
              .foundationDate(LocalDate.now())
              .position(geom)
              .region("RE")
              .state("UF")
              .build();
      estacaoRepository.save(estacao);
      assertTrue(estacao.getId() > 0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //    @Test
  final void testFindById() {}

  //    @Test
  final void testDeleteById() {}
}
