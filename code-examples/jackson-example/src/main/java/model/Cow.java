package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Cow extends Animal {
	private final boolean horns;

	@JsonCreator
	public Cow(@JsonProperty("name") final String animalName, @JsonProperty("horns") final boolean hasHorns) {
		super(animalName);
		this.horns = hasHorns;
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Cow)) {
			return false;
		}
		if(!super.equals(o)) {
			return false;
		}
		final Cow cow = (Cow) o;
		return horns == cow.horns;
	}

	public boolean isHorns() {
		return horns;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), horns);
	}
}
