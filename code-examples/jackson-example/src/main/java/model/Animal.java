package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Objects;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We have to identify the sub-classes to help the marshalling
@JsonSubTypes({
	@JsonSubTypes.Type(value = Cow.class)
})
// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Animal {
	// why not
	protected String name;

	// JsonCreator identifies the constructors to call when an object is unmarshalled.
	// all the attributes of this constructor must be tagged with @JsonProperty that
	// match to Json values of the unmarshalled object
	@JsonCreator
	public Animal(@JsonProperty("name") final String animalName) {
		super();
		name = animalName;
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Animal)) {
			return false;
		}
		final Animal animal = (Animal) o;
		return Objects.equals(name, animal.name);
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
