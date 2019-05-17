package diaballik;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MovePiece extends Action {

	@Override
	/**
	 * La fonction execute de MovePiece exécute l'action en bougeant la pièce de la position précédente à la nouvelle position
	 * @param Board
	 */
	public void execute(final Board board) {
        board.setPiece(newPos, board.getPiece(oldPos));
        board.setPiece(oldPos, null);
        board.getPiece(newPos).setPosition(newPos);
	}

	@JsonCreator
	public MovePiece(@JsonProperty("oldpos") final Position oldPos,
					 @JsonProperty("newpos") final Position newPos) {
		super(oldPos, newPos);
	}
}
