package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMarshalling {
	static Stream<Object> getInstancesToMarshall() {
		final Farm farm = new Farm();
		farm.addAnimal(new Cow("Harry", false));

		return Stream.of(
			new Cow("Holly", true),
			farm
		);
	}

	@ParameterizedTest
	@MethodSource("getInstancesToMarshall")
	void testMarshall(final Object objectToMarshall) throws IOException {
		final ObjectMapper mapper =  new ObjectMapper();
		final String serializedObject = mapper.writeValueAsString(objectToMarshall);
		System.out.println(serializedObject);
		final Object readValue = mapper.readValue(serializedObject, objectToMarshall.getClass());
		assertEquals(objectToMarshall, readValue);
	}
}
