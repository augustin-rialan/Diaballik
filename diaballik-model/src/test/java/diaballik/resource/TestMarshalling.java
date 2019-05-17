package diaballik.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.*;
import diaballik.serialization.DiabalikJacksonProvider;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMarshalling {
	static Stream<Object> getInstancesToMarshall() {
//		final Player p1 = new Player(); //TODO to update
//		etc.
		/*final Player p = new Player("Toto", Color.RED);
		final Piece piece = new Piece(new Position(0, 0), p);
		final Piece piece2 = new Piece(new Position(1, 0), p);
		p.addPiece(piece);
		p.addPiece(piece2);*/
		final Player foo = new Player("foo", Color.RED);
		final BotPlayer bar = new BotPlayer("bar", Color.BLUE);
		final ProgressiveAI ai = new ProgressiveAI(bar);
		bar.setAI(ai);
		final StartingAI ai2 = new StartingAI(bar);
		final NoobAI ai3 = new NoobAI(bar);
		final StandardBuilder builder = new StandardBuilder();
		final Board board = builder.build(foo, bar);
		final Action move = new MovePiece(new Position(0, 2), new Position(1, 2));
		final Action thro = new ThrowBall(new Position(0, 3), new Position(0, 4));
		ArrayList<Player> players = new ArrayList<>();
		players.add(foo);
		players.add(bar);
		ArrayList<Action> actions = new ArrayList<>();
		actions.add(move);
		actions.add(thro);
		final Game game = new Game(0, false, players, board, actions);

		final GameBuilder gameBuilder = new NewGameBuilder(foo, bar, board);
        final Game game2 = gameBuilder.build();


		return Stream.of(board, foo, bar, ai, ai2, ai3, foo.getPieces().get(0), move, thro, game, game2);
	}

	@ParameterizedTest
	@MethodSource("getInstancesToMarshall")
	void testMarshall(final Object objectToMarshall) throws IOException {
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(objectToMarshall);
		System.out.println(serializedObject);
		final Object readValue = mapper.readValue(serializedObject, objectToMarshall.getClass());
		assertEquals(objectToMarshall, readValue);
	}
}
