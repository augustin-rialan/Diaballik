package diaballik.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.awt.Color;
import java.io.IOException;

/**
 * An example of a marshaller
 */
public class ColorSerializer extends StdSerializer<Color> {
	public ColorSerializer() {
		super(Color.class);
	}

	@Override
	public void serialize(final Color color, final JsonGenerator gen, final SerializerProvider serializerProvider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("code", String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));
		gen.writeEndObject();
	}
}
