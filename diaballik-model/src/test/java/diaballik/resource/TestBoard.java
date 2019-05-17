package diaballik.resource;

import diaballik.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.util.stream.IntStream;

import static java.lang.Integer.valueOf;

public class TestBoard extends TestCase {

    private Player p1, p2;

    @BeforeEach
    public void setUp() {
        p1 = new Player("foo", Color.RED);
        p2 = new Player("bar", Color.BLUE);
    }

    @Test
    public void testStandardBuilder() {
        BoardBuilder builder = new StandardBuilder();
        Board board = builder.build(p1, p2);

        Piece pieceWithBall = board.getPiece(new Position(0, 3));
        Piece piecePlayer1 = board.getPiece(new Position(0, 1));
        Piece noPiece = board.getPiece(new Position(2, 3));

        assertTrue(pieceWithBall.hasBall());
        assertFalse(piecePlayer1.hasBall());
        assertNull(noPiece);
        assertEquals(new Piece(new Position(0,1), p1), piecePlayer1);
    }

    @Test
    public void testBallRandomBuilder() {
        BoardBuilder builder = new BallRandomBuilder();
        Board board = builder.build(p1, p2);

        long nbBalls = IntStream.range(0,7).filter(n -> board.getPiece(new Position(0, n)).hasBall()).count();

        Piece pieceWithBall = board.getPiece(new Position(0, 3));
        Piece piecePlayer1 = board.getPiece(new Position(0, 1));
        Piece noPiece = board.getPiece(new Position(2, 3));

        assertEquals(1, nbBalls);
        assertNull(noPiece);
        assertEquals(p1, piecePlayer1.getPlayer());
    }

    @Test
    public void testEnemyAmongUsBuilder() {
        BoardBuilder builder = new EnemyAmongUsBuilder();
        Board board = builder.build(p1, p2);

        long nbEnemies = IntStream.range(0,7).filter(n ->
                board.getPiece(new Position(0, n)).getPlayer().equals(p2))
                .count();

        Piece piecePlayer1 = board.getPiece(new Position(0, 3));
        Piece noPiece = board.getPiece(new Position(2, 3));

        assertEquals(2, nbEnemies);
        assertNull(noPiece);
        assertEquals(p1, piecePlayer1.getPlayer());
        assertTrue(piecePlayer1.hasBall());
    }
}
