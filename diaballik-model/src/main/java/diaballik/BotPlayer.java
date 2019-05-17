package diaballik;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.awt.Color;
import java.util.Objects;

public class BotPlayer extends Player {

	@JsonProperty("ai")
	private AIStrategy ai;

    /**BotPlayer instancie la classe Player en ajoutant l'attribut ai et la méthode getAi
     * @param name
     * @param color
     */
	public BotPlayer(@JsonProperty("name") final String name,
					 @JsonProperty("color") final Color color) {
		super(name, color);
		ai = null;
	}

	public AIStrategy getAI() {
		return ai;
	}

	/**
	 * Choisir la stratégie du BotPlayer
	 * @param ai
	 */
	public void setAI(final AIStrategy ai) {
		this.ai = ai;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final BotPlayer player = (BotPlayer) o;
		return Objects.equals(name, player.name) &&
				Objects.equals(color, player.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, color);
	}
}
