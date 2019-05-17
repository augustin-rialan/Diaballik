package diaballik;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Board {

	private Piece[][] pieces;

	public final Piece getPiece(final Position pos) {
		if(pieces[pos.getX()][pos.getY()] != null) {
			return pieces[pos.getX()][pos.getY()];
		} else {
			return null;
		}
	}

	public void setPiece(final Position pos, final Piece piece) {
		pieces[pos.getX()][pos.getY()] = piece;
	}

	public void setBall(final Position pos, final boolean ball) {
		pieces[pos.getX()][pos.getY()].setBall(ball);
	}

    /**
     * Utilise le tableau de board pour créer une ArrayList
     * @return ArrayList<Piece>
     */
	public ArrayList<Piece> pieceList() {
		final ArrayList<Piece> pc = new ArrayList<>();
		Stream.iterate(0, n -> n + 1)
				.limit(7)
				.forEach(x -> {
					Stream.iterate(0, n -> n + 1)
							.limit(7)
							.forEach(y -> {
								if(this.pieces[x][y] != null) {
									pc.add(this.pieces[x][y]);
								}
							});
				});
		return pc;
	}

	/**
	 * Constructeur de Board
	 * @param pieces
	 */
	@JsonCreator
	public Board(@JsonProperty("pieces") final Piece[][] pieces) {
		this.pieces = new Piece[7][];
		Stream.iterate(0, n -> n + 1)
				.limit(7)
				.forEach(x -> {
					this.pieces[x] = new Piece[7];
					Stream.iterate(0, n -> n + 1)
							.limit(7)
							.forEach(y -> {
								this.pieces[x][y] = pieces[x][y];
							});
				});
	}

    /**
     * Autre constructeur de Board
     * @param board
     */
	public Board(final Board board) {
		this.pieces = new Piece[7][];
		Stream.iterate(0, n -> n + 1)
				.limit(7)
				.forEach(x -> {
					this.pieces[x] = new Piece[7];
					Stream.iterate(0, n -> n + 1)
							.limit(7)
							.forEach(y -> {
								this.pieces[x][y] = board.getPiece(new Position(x, y));
							});
				});
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Board board = (Board) o;
		return Arrays.deepEquals(pieces, board.pieces);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(pieces);
	}
}
