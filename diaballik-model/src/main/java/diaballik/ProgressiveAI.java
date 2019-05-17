package diaballik;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ProgressiveAI extends AIStrategy {

	@JsonIgnore
	private NoobAI noobAI;

	@JsonIgnore
	private StartingAI startingAI;

	/**
	 * La méthode ProgressiveAI appelle la méthode decide de StartingAI si le nombre de tour est supérieur à 5 et la méthode decide
	 * de NoobAi si le nombre de tour est inférieur à 5
	 * @param game
	 * @return
	 */
	@Override
	public Action decide(final Game game) {
		if(game.getNbTurns() >= 5) {
			return startingAI.decide(game);
		} else {
			return noobAI.decide(game);
		}
	}

	/**
	 * La constructeur de ProgressiveAi qui a un attribut noobAi et un attribut startingAi
	 * @param player
	 */
	@JsonCreator
	public ProgressiveAI(@JsonProperty("player") final BotPlayer player) {
		super(player);
		noobAI = new NoobAI(player);
		startingAI = new StartingAI(player);
	}
}
