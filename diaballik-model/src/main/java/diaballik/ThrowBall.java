package diaballik;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ThrowBall extends Action {

	@Override
	/**
	 * La fonction execute de throwball exécute l'action en envoyant la balle de la position précédente à la nouvelle position.
	 */
	public void execute(final Board board) {
		board.setBall(oldPos, false);
		board.setBall(newPos, true);
	}

	@JsonCreator
	public ThrowBall(@JsonProperty("oldpos") final Position oldPos,
					 @JsonProperty("newpos") final Position newPos) {
		super(oldPos, newPos);
	}


}
