package diaballik;

import java.util.ArrayList;
import java.util.Arrays;

public class NewGameBuilder extends GameBuilder {

	@Override
	public Game build() {
		final ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
		return new Game(players, board);
	}

	public NewGameBuilder(final Player p1,
						  final Player p2,
						  final Board board) {
		this.player1 = p1;
		this.player2 = p2;
		this.board = board;
	}
}
