package diaballik.resource;

import diaballik.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.util.ArrayList;

public class TestScenario extends TestCase {

    //[R22_1_SCENARIO_STANDARD]
    //[R22_2_SCENARIO_BALL_RANDOM]
    //[R22_2_SCENARIO_ENEMY_AMONG_US]


    private Board ballrandomboard;
    private Board ennemyboard;
    private Board standardboard;

    private Player[] players;


    @BeforeEach
    public void setUp() {
        BoardBuilder BallRandomBuilder = new BallRandomBuilder();
        BoardBuilder enemyamongusbuilder = new EnemyAmongUsBuilder();
        BoardBuilder standardbuilder = new StandardBuilder();


        this.players =new Player[2];
        this.players[0] = new Player("Player1", Color.RED);
        this.players[1] = new Player("Player2", Color.BLUE);
        
        this.ballrandomboard = BallRandomBuilder.build(players[0], players[1]);
        this.ennemyboard = enemyamongusbuilder.build(players[0], players[1]);
        this.standardboard = standardbuilder.build(players[0], players[1]);




    }
    ;
    @Test
    public void testScenarios() {
    int nbplayer1 = 0;
    int nbplayer2 = 0;
    int ball=0;
    for (int i=0; i<7; i++){
        if (ennemyboard.getPiece(new Position(0, i)).getPlayer().getName().equals("Player1")) {
            nbplayer1++;
        }
        else {
            nbplayer2++;
        }

        if(ballrandomboard.getPiece(new Position(0,i )).hasBall()) {
            ball++;
        }
    }

    assertEquals(nbplayer1,5);
    assertEquals(nbplayer2,2);
    assertEquals(1, ball);


        ball=0;
        nbplayer1=0;
    for (int i=0; i<7; i++) {

        if (ballrandomboard.getPiece(new Position(0, i)).getPlayer().getName().equals("Player1")) {
            nbplayer1++;
        }

        if(ballrandomboard.getPiece(new Position(0,i)).hasBall()) {
            ball++;
        }

    }

        assertEquals(7,nbplayer1);
        assertEquals(1,ball);

         nbplayer1 = 0;
         ball=0;
        for (int i=0; i<7; i++){

            if(standardboard.getPiece(new Position(0,i )).hasBall()) {
                ball++;
            }

            if (standardboard.getPiece(new Position(0, i)).getPlayer().getName().equals("Player1")) {
                nbplayer1++;
            }


        }

        assertEquals(7,nbplayer1);
        assertEquals(1,ball);





    }
}
