package diaballik;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;


public class NoobAI extends AIStrategy {


	/**
	 * La méthode decide de NoobAi choisit une action au hasard en choisissant la méthode doableActions de Game et la retourne
	 * @param game
	 * @return Action
	 */
	@Override
	public Action decide(final Game game) {
	    final ArrayList<Action> actions = new ArrayList<>();
	    player.getPieces().stream().forEach(p -> actions.addAll(game.doableActions(p)));
        Collections.shuffle(actions);
        return actions.get(0);
	}

	@JsonCreator
	public NoobAI(@JsonProperty("player") final BotPlayer player) {
	    super(player);
    }

}
