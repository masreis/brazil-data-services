package net.brazildata.repository.weather;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.brazildata.weather.model.Measurement;
import net.brazildata.weather.model.Station;
import net.brazildata.weather.repository.MeasurementRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest
class MedidaRepositoryTest {

    @Autowired
    MeasurementRepository medidaRepository;

//    @Test
    final void testFindAll() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    final void testSave() {
        Station estacao = Station.builder().id(1l).build();
        Measurement medida = Measurement.builder().collectedOn(LocalDateTime.now()).station(estacao)
                .totalPrecipitation(1f).atmosphericPressure(2f).atmosphericPressureMaxPrevHour(3f)
                .atmosphericPressureMinPrevHour(4f).globalRadiation(5f).airDryBulbTemperature(6f)
                .maxTemperaturePrevHour(7f).minTemperaturePrevHour(8f).dewPointTemperature(9f).build();
        medidaRepository.save(medida);
    }

//    @Test
    final void testFindById() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    final void testDeleteById() {
        fail("Not yet implemented"); // TODO
    }

}
