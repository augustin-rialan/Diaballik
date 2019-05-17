package diaballik.resource;

import diaballik.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.util.ArrayList;

public class TestAction extends TestCase {

    //[R21_8_GAMEPLAY_ACTIONS]

    private Game game;
    private Board board;
    private ArrayList action;
    private ArrayList<Player> players;


    @BeforeEach
    public void setUp() {
        BoardBuilder builder = new StandardBuilder();
        this.players =new ArrayList<>();
        this.players.add(new Player("Player1", Color.RED));
        this.players.add(new Player("Player2", Color.BLUE));
        this.board = builder.build(players.get(0), players.get(1));
        MovePiece action1= new MovePiece(new Position(0,2), new Position(1,2));
        MovePiece action2= new MovePiece(new Position(6,6), new Position(5,6));
        ThrowBall action3= new ThrowBall(new Position(0,3), new Position(1,2));
        action=new ArrayList<Action>();
        action.add(action1);
        action.add(action2);
        action.add(action3);
        game = new Game(0, false, players, board, board, new ArrayList<>());
    }
;
    @Test
    public void testExecuteAction() {
        ThrowBall action3= new ThrowBall(new Position(0,3),new Position(1,2));
        ((MovePiece) action.get(0)).execute(this.board);
        ((MovePiece) action.get(1)).execute(this.board);
        ((ThrowBall) action.get(2)).execute(this.board);

        assertEquals( null, board.getPiece(new Position(0,2)));
        assertEquals( null, board.getPiece(new Position(6,6)));

        Piece piece1 =new Piece(new Position(1,2),this.players.get(0));
        Piece piece2 =new Piece(new Position(5,6),this.players.get(1));
        piece1.setBall(true);

       //board.getPiece(new Position(1,2)).print();
        //piece1.print();

        assertEquals(piece1, board.getPiece(new Position(1,2)));
        assertTrue(board.getPiece(new Position(1,2)).hasBall());

        assertEquals(piece2, board.getPiece(new Position(5,6)));
        assertFalse(board.getPiece(new Position(5,6)).hasBall());


    }
}
