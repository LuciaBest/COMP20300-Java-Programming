package pers.luyu.simonrace.sprite.obstacles;

//import org.junit.After;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pers.luyu.simonrace.sprite.Board;
import pers.luyu.simonrace.sprite.Square;

class ObstacleTest {

    private Obstacle obstacleUnderTest;
    private Board boardUnderTest;

    @BeforeEach
    void setUp() {
        boardUnderTest = new Board(2, 1, new int[]{0, 0, 0, 0, 0});
        obstacleUnderTest = new DoubleFence(boardUnderTest.getSquare(0, 0), boardUnderTest.getSquare(1, 0));
    }

    @Test
    void getLocation() {
        Square test = obstacleUnderTest.getLocation();
        Assertions.assertSame(test, boardUnderTest.getSquare(0, 0));
    }

    @Test
    void setLocation() {
        boardUnderTest.getSquare(1, 0).setObstacle(obstacleUnderTest);
        obstacleUnderTest.setLocation(boardUnderTest.getSquare(1, 0));
        Assertions.assertEquals(1, obstacleUnderTest.getLocation().getLocateRow());
        Assertions.assertEquals(0, obstacleUnderTest.getLocation().getLocateLine());
    }

    @Test
    void getLocation2() {
        Square test = obstacleUnderTest.getSecLocation();
        Assertions.assertSame(test, boardUnderTest.getSquare(1, 0));
    }

    @Test
    void setLocation2() {
        boardUnderTest.getSquare(0, 0).setObstacle(obstacleUnderTest);
        obstacleUnderTest.setSecLocation(boardUnderTest.getSquare(0, 0));
        Assertions.assertEquals(0, obstacleUnderTest.getSecLocation().getLocateRow());
        Assertions.assertEquals(0, obstacleUnderTest.getSecLocation().getLocateLine());
    }
}