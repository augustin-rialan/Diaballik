package diaballik;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadGameBuilder extends GameBuilder {

    @Override
	public Game build() {
        final ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
        return new Game(players, board);
	}
}
