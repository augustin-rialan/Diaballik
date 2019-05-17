package diaballik;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = BotPlayer.class)
})

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Player {

	protected String name;

	protected Color color;

    //@JsonIdentityReference
	@JsonIgnore
	private List<Piece> pieces;

	/**
	 * Constructeur de Player qui a un nom et une couleur
	 * @param name
	 * @param
	 */
    @JsonCreator
    public Player(@JsonProperty("name") final String name,
                  @JsonProperty("color") final Color color) {
        this.name = name;
        this.color = color;
        this.pieces = new ArrayList<>();
    }

	public Color getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Player player = (Player) o;
		return Objects.equals(name, player.name) &&
				Objects.equals(color, player.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, color);
	}

	public void addPiece(final Piece piece) {
	    this.pieces.add(piece);
    }

    //@JsonIdentityReference
    //@Produces(MediaType.APPLICATION_JSON)
	public List<Piece> getPieces() {
	    return this.pieces;
    }
}
