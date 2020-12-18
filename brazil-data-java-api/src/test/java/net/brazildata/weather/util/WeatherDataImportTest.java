package net.brazildata.weather.util;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class WeatherDataImportTest {

    @Autowired
    WeatherDataImport dataImport;

    @Test
    final void test() {
        this.dataImport.process();
    }

}
