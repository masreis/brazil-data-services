package net.brazildata.weather.model.util;

import java.io.IOException;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PointSerializer extends JsonSerializer<Point> {

  @Override
  public void serialize(Point p, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartArray();

    gen.writeNumber(p.getCoordinate().x);
    gen.writeNumber(p.getCoordinate().y);

    if (!Double.isNaN(p.getCoordinate().getZ())) {
      gen.writeNumber(p.getCoordinate().getZ());
    }
    gen.writeEndArray();
  }
}
