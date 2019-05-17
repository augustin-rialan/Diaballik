package diaballik;

import java.util.stream.IntStream;

public class StandardBuilder extends BoardBuilder {

	@Override
	/**
	 * La méthode placePieces de StandardBuilder place les pièces de chaque joueur de chaque coté du plateau tout en
	 * positionant la balle sur la pièce du milieu
	 * @param p1
	 * @param p2
	 */
	public void placePieces(final Player p1, final Player p2) {
		pieces[0] = IntStream.range(0, 7).mapToObj(n -> {
			final Piece p = new Piece(new Position(0, n), p1);
			p1.addPiece(p);
			return p;
		}).toArray(Piece[]::new);
		pieces[0][3].setBall(true);

		pieces[6] = IntStream.range(0, 7).mapToObj(n -> {
			final Piece p = new Piece(new Position(6, n), p2);
			p2.addPiece(p);
			return p;
		}).toArray(Piece[]::new);
		pieces[6][3].setBall(true);
	}

	public StandardBuilder() {
		pieces = new Piece[7][7];
	}

}
