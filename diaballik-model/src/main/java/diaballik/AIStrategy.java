package diaballik;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = NoobAI.class),
		@JsonSubTypes.Type(value = ProgressiveAI.class),
		@JsonSubTypes.Type(value = StartingAI.class)
})

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class AIStrategy {

	@JsonIdentityReference
	protected BotPlayer player;

	@JsonCreator
	public AIStrategy(@JsonProperty("player") final BotPlayer player) {
		this.player = player;
	}

	/**
	 * La méthode abstraite decide de AIStrategy est redéfini pour chaque Stratégie différente
	 * @param game
	 * @return Action
	 */
	public abstract Action decide(final Game game);

	@Produces(MediaType.APPLICATION_JSON)
	public BotPlayer getPlayer() {
		return player;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final AIStrategy that = (AIStrategy) o;
		return Objects.equals(player, that.player);
	}

	@Override
	public int hashCode() {
		return Objects.hash(player);
	}
}
