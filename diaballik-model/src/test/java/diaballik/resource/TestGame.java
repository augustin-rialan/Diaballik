package diaballik.resource;

import diaballik.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.util.ArrayList;

public class TestGame extends TestCase {

    //[R21_1_GAME_PLAYERS]
    //[R21_2_GAME_BOARD]
    //[R21_3_GAME_PIECES]
    //[R21_4_GAME_BALL}
    //[R21_9_GAMEPLAY_MOVE_BALL]
    //[R21_10_GAMEPLAY_MOVE_PIECE]
    //[R21_11_GAMEPLAY_MOVE_PIECE_WITH_BALL]



    private Game game;

    @BeforeEach
    public void setUp() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Player1", Color.RED));
        players.add(new Player("Player2", Color.BLUE));
        BoardBuilder builder = new StandardBuilder();
        Board board = builder.build(players.get(0), players.get(1));

        game = new Game(0, false, players, board, board, new ArrayList<>());
    }

    @Test
    public void testDoableActions() {
        ArrayList<Action> actionsCenter =
                (ArrayList<Action>) game.doableActions(game.getBoard().getPiece(new Position(0, 3)));
        Action throw1 = new ThrowBall(new Position(0, 3), new Position(0, 4));
        Action throw2 = new ThrowBall(new Position(0, 3), new Position(0, 2));
        assertTrue(actionsCenter.contains(throw1));
        assertTrue(actionsCenter.contains(throw2));
        assertEquals(2, actionsCenter.size());

        ArrayList<Action> actionsSide =
                (ArrayList<Action>) game.doableActions(game.getBoard().getPiece(new Position(0, 2)));
        Action move1 = new MovePiece(new Position(0, 2), new Position(1, 2));
        assertTrue(actionsSide.contains(move1));
        assertEquals(1, actionsSide.size());
    }
}
