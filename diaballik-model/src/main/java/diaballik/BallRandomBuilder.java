package diaballik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class BallRandomBuilder extends BoardBuilder {


    @Override
    /**
     * La méthode placePieces de BallRandomBuilder place les pièces de chaque joueur de chaque coté du plateau tout en
     * positionant la balle sur une des pièces de chaque joueur au hasard
     * @param p1
     * @param p2
     */
    public void placePieces(final Player p1, final Player p2) {
        pieces[0] = IntStream.range(0, 7).mapToObj(n -> new Piece(new Position(0, n), p1)).toArray(Piece[]::new);
        final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        Collections.shuffle(list);
        pieces[0][list.get(0)].setBall(true);

        pieces[6] = IntStream.range(0, 7).mapToObj(n -> new Piece(new Position(6, n), p2)).toArray(Piece[]::new);
        Collections.shuffle(list);
        pieces[6][list.get(0)].setBall(true);
    }

    public BallRandomBuilder() {
        pieces = new Piece[7][7];
    }

}
