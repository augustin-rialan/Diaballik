package diaballik;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = ThrowBall.class),
		@JsonSubTypes.Type(value = MovePiece.class)
})

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Action {

	protected Position oldPos;

	protected Position newPos;

	@JsonCreator
	/**
	 * Une action est composé de la position précédente et de la nouvelle position
	 */
	public Action(@JsonProperty("oldpos") final Position oldPos,
				  @JsonProperty("newpos") final Position newPos) {
		this.oldPos = oldPos;
		this.newPos = newPos;
	}

	public abstract void execute(Board board);

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Action action = (Action) o;
		return Objects.equals(oldPos, action.oldPos) &&
				Objects.equals(newPos, action.newPos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(oldPos, newPos);
	}
}
