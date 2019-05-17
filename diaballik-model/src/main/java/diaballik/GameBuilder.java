package diaballik;

public abstract class GameBuilder {

	protected Player player1;

	protected Player player2;

	protected Board board;

	/**
	 * Méthode abstraite build rédéfinie dans les deux instances de GameBuilder
	 */
	public abstract Game build();

}
