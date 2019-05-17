package diaballik;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Piece {

	private boolean hasBall;

	private int x;

	private int y;

    @JsonIdentityReference
	private Player player;

	public void setBall(final boolean bool) {
	    this.hasBall = bool;
    }

	public boolean hasBall() {
		return hasBall;
	}

    @JsonIgnore
	public int getX() {
		return x;
	}

    @JsonIgnore
	public int getY() {
		return y;
	}

    @JsonIdentityReference
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(final Player p) {
		this.player = p;
	}

    /**
     * Constructeur de pièce qui a un x, un y, un player associé et qui possède ou non une balle (hasball).
     * @param position
     * @param player
     */
	public Piece(final Position position, final Player player) {
		this.x = position.getX();
        this.y = position.getY();
		this.player = player;
		this.hasBall = false;
	}

    @JsonCreator
    public Piece(@JsonProperty("x") final int x,
                 @JsonProperty("y") final int y,
                 @JsonProperty("player") final Player player,
                 @JsonProperty("hasball") final boolean hasBall) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.hasBall = hasBall;
    }

	public void setPosition(final Position position) {
	    x = position.getX();
	    y = position.getY();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece piece = (Piece) o;
        return hasBall == piece.hasBall &&
                x == piece.x &&
                y == piece.y &&
                Objects.equals(player, piece.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasBall, x, y, player);
    }

    @Override
    public String toString() {
        return "Piece " + x + ", " + y;
	    /*return "Player: " + this.getPlayer().getName() + "\n"
                + "x : " + this.getX() + "y : " + this.getY() + "\n"
	            +"HasBall: " + this.hasBall() + " \n";*/
    }

}
