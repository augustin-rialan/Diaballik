package diaballik;

public abstract class BoardBuilder {

	protected Piece[][] pieces;

	public abstract void placePieces(final Player p1, final Player p2);

    /**
     * La méthode build de BoardBuilder crée la board avec la méthode placePieces qui diverge entre chaque builder
     * @param p1
     * @param p2
     * @return Board
     */
	public Board build(final Player p1, final Player p2) {
		this.placePieces(p1, p2);
		return new Board(pieces);
	}

}
