package diaballik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class EnemyAmongUsBuilder extends BoardBuilder {

    @Override
    /**
     * La méthode placePieces de EnnemyAmongUs place les pièces de chaque joueur de chaque coté du plateau avec pour chaque joueur
     * deux pièces de l'adversaires qui se retrouve au hasard parmis ses joueurs. De plus, la balle est placé au milieu
     * @param p1
     * @param p2
     */
    public void placePieces(final Player p1, final Player p2) {
        pieces[0] = IntStream.range(0, 7).mapToObj(n -> new Piece(new Position(0, n), p1)).toArray(Piece[]::new);
        final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 4, 5, 6));
        Collections.shuffle(list);
        pieces[0][list.get(0)].setPlayer(p2);
        pieces[0][list.get(1)].setPlayer(p2);
        pieces[0][3].setBall(true);

        pieces[6] = IntStream.range(0, 7).mapToObj(n -> new Piece(new Position(6, n), p2)).toArray(Piece[]::new);
        Collections.shuffle(list);
        pieces[6][list.get(0)].setPlayer(p1);
        pieces[6][list.get(1)].setPlayer(p1);
        pieces[6][3].setBall(true);
    }

    public EnemyAmongUsBuilder() {
        pieces = new Piece[7][7];

    }

}
