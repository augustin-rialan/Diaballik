package diaballik;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;

public class StartingAI extends AIStrategy {

	@Override
	public Action decide(final Game game) {
		final ArrayList<Action> actions = new ArrayList<>();
		player.getPieces().stream().forEach(p -> actions.addAll(game.doableActions(p)));
		Collections.shuffle(actions);
		return actions.get(0);
	}

	@JsonCreator
	public StartingAI(@JsonProperty("player") final BotPlayer player) {
		super(player);
	}
}
