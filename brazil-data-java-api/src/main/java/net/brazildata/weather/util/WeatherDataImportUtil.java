package net.brazildata.weather.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import net.brazildata.weather.model.Measurement;
import net.brazildata.weather.model.Station;
import net.brazildata.weather.service.MeasurementService;
import net.brazildata.weather.service.StationService;

@Component
@Profile("load")
public class WeatherDataImportUtil implements CommandLineRunner {

  private StationService stationService;
  private MeasurementService measurementService;

  public WeatherDataImportUtil(StationService estacaoService, MeasurementService medidaService) {
    this.stationService = estacaoService;
    this.measurementService = medidaService;
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  @Override
  public void run(String... args) throws Exception {
    process();
    System.out.println("End of processing");
  }

  public void process() {
    File destDir = new File(System.getProperty("java.io.tmpdir") + "/weather");
    String file = "/media/marco/disk-cloud/dados/inmet/2003.zip";
    try {
      destDir.mkdir();
      byte[] buffer = new byte[1024];
      ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
      ZipEntry zipEntry = zis.getNextEntry();
      ExecutorService executor = Executors.newWorkStealingPool();
      Collection<Callable<Void>> tasks = new ArrayList<>();
      while (zipEntry != null) {
        if (zipEntry.isDirectory()) {
          new File(destDir + "/" + zipEntry.getName()).mkdir();
          zipEntry = zis.getNextEntry();
          continue;
        }
        File newFile = new File(destDir, zipEntry.getName());
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }
        fos.close();

        String fileName = zipEntry.getName();

        tasks.add(() -> new DataInjector().processFile(destDir, fileName));

        zipEntry = zis.getNextEntry();
      }

      executor.invokeAll(tasks);

      executor.shutdown();
      zis.closeEntry();
      zis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  class DataInjector {
    private DateTimeFormatter formatterReduced;
    private DateTimeFormatter dateTimeFormatter;

    private Station station;
    private Double latitude;
    private Double longitude;
    private String ano;

    public Void processFile(File destDir, String fileName) {
      System.out.println("=>" + fileName);
      try {
        Path path = Paths.get(destDir + "/" + fileName);
        extractYear(fileName);

        Files.readAllLines(path, Charset.forName("iso-8859-1")).stream().forEach(this::processLine);

      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    private void extractYear(String fileName) {
      fileName = fileName.substring(0, fileName.indexOf('.'));
      ano = fileName.substring(fileName.lastIndexOf("-") + 1, fileName.length());
    }

    public void processLine(String line) {

      try {
        if (line.startsWith(ano)) {
          insertMeasure(line);
        } else {
          processStationData(line);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private void insertStation() {
      try {
        Station found = stationService.findByCodigoWmo(station.getWmoCode());
        if (found == null) {
          stationService.save(station);
        } else {
          station = found;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private void processStationData(String line) {
      String[] date = line.split("\\:\\;");
      switch (date[0]) {
        case "REGIAO":
          station = new Station();
          station.setRegion(date[1]);
          break;
        case "REGI?O":
          station = new Station();
          station.setRegion(date[1]);
          break;
        case "REGIÃO":
          station = new Station();
          station.setRegion(date[1]);
          break;
        case "UF":
          station.setState(date[1]);
          break;
        case "ESTACAO":
          station.setName(date[1]);
          break;
        case "ESTAÇÃO":
          station.setName(date[1]);
          break;
        case "ESTAC?O":
          station.setName(date[1]);
          break;
        case "CODIGO (WMO)":
          station.setWmoCode(date[1]);
          break;
        case "ALTITUDE":
          station.setAltitude(floatFromString(date[1]));
          break;
        case "LATITUDE":
          latitude = Double.valueOf(date[1].replaceAll(",", "."));
          break;
        case "LONGITUDE":
          longitude = Double.valueOf(date[1].replaceAll(",", "."));
          Point geom = new GeometryFactory().createPoint(new Coordinate(latitude, longitude));
          station.setPosition(geom);
          break;
        case "DATA DE FUNDACAO":
          initializeDateFormatter(date);
          station.setFoundationDate(LocalDate.parse(date[1], formatterReduced));
          break;
        case "DATA DE FUNDAC?O":
          initializeDateFormatter(date);
          station.setFoundationDate(LocalDate.parse(date[1], formatterReduced));
          break;
        case "DATA DE FUNDAÇÃO (YYYY-MM-DD)":
          initializeDateFormatter(date);
          station.setFoundationDate(LocalDate.parse(date[1], formatterReduced));
          break;
        default:
          insertStation();
          break;
      }
    }

    private void initializeDateFormatter(String[] dados) {
      if (formatterReduced == null && dados[1].contains("-")) {
        formatterReduced = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      } else if (formatterReduced == null) {
        formatterReduced = DateTimeFormatter.ofPattern("dd/MM/yy");
      }
    }

    private Float floatFromString(String dados) {
      try {
        return Float.valueOf(dados.replaceAll(",", "."));
      } catch (Exception e) {
        return null;
      }
    }

    // List of fields in the file
    // Data;Hora UTC;PRECIPITA��O TOTAL, HOR�RIO (mm);PRESSAO ATMOSFERICA AO NIVEL DA ESTACAO,
    // HORARIA (mB);PRESS�O ATMOSFERICA MAX.NA HORA ANT. (AUT) (mB);PRESS�O ATMOSFERICA MIN. NA HORA
    // ANT. (AUT) (mB);RADIACAO GLOBAL (Kj/m�);TEMPERATURA DO AR - BULBO SECO, HORARIA (�C);
    // TEMPERATURA DO PONTO DE
    // ORVALHO (�C);TEMPERATURA M�XIMA NA HORA ANT. (AUT) (�C);TEMPERATURA M�NIMA NA HORA ANT. (AUT)
    // (�C);TEMPERATURA ORVALHO MAX. NA HORA ANT. (AUT)
    // (�C);TEMPERATURA ORVALHO MIN. NA HORA ANT. (AUT) (�C);UMIDADE REL. MAX. NA HORA ANT. (AUT)
    // (%);UMIDADE REL. MIN. NA HORA ANT. (AUT) (%);UMIDADE RELATIVA
    // DO AR, HORARIA (%);VENTO, DIRE��O HORARIA (gr) (� (gr));VENTO, RAJADA MAXIMA (m/s);VENTO,
    // VELOCIDADE HORARIA (m/s);

    private void insertMeasure(String line) {
      String[] dados = line.split(";", -1);
      Measurement medida = null;
      try {
        int i = 0;
        String dateTime = dados[i++] + " " + dados[i++].replaceAll(" UTC", "");
        initializeDateTimeFormatterMeasure(dateTime);

        LocalDateTime collectedOn = LocalDateTime.parse(dateTime, dateTimeFormatter);

        medida =
            Measurement.builder()
                .collectedOn(collectedOn)
                .year(collectedOn.getYear())
                .station(this.station)
                .month(collectedOn.getMonthValue())
                .state(this.station.getState())
                .totalPrecipitation(floatFromString(dados[i++]))
                .atmosphericPressure(floatFromString(dados[i++]))
                .atmosphericPressureMaxPrevHour(floatFromString(dados[i++]))
                .atmosphericPressureMinPrevHour(floatFromString(dados[i++]))
                .globalRadiation(floatFromString(dados[i++]))
                .airDryBulbTemperature(floatFromString(dados[i++]))
                .dewPointTemperature(floatFromString(dados[i++]))
                .maxTemperaturePrevHour(floatFromString(dados[i++]))
                .minTemperaturePrevHour(floatFromString(dados[i++]))
                .dewPointTemperatureMaxPrevHour(floatFromString(dados[i++]))
                .dewPointTemperatureMinPrevHour(floatFromString(dados[i++]))
                .relativeAirHumidityMaxPrevHour(floatFromString(dados[i++]))
                .relativeAirHumidityMinPrevHour(floatFromString(dados[i++]))
                .relativeAirHumidity(floatFromString(dados[i++]))
                .windTimeDirection(floatFromString(dados[i++]))
                .windMaxGust(floatFromString(dados[i++]))
                .windTimeSpeed(floatFromString(dados[i++]))
                .build();

        measurementService.save(medida);

      } catch (Exception e) {
        System.out.println(medida);
        System.out.println(e.getMessage());
        System.out.println(e.getLocalizedMessage());
      }
    }

    private void initializeDateTimeFormatterMeasure(String dateTime) {
      if (dateTimeFormatter == null && dateTime.contains("-")) {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      } else if (dateTimeFormatter == null) {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm");
      }
    }
  }
}
