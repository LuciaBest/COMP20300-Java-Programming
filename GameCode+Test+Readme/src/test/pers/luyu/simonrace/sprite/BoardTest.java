package pers.luyu.simonrace.sprite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

    private Board boardUnderTest;
    private Obstacles obstacles;

    @BeforeEach
    void setUp() {
        boardUnderTest = new Board(2, 2, new int[]{0, 0, 0, 0, 0});
    }

    @Test
    void testSetNewObstacles() {
        boardUnderTest = new Board(5, 5, new int[]{1, 1, 1, 1, 1});
        String originalObstacle = boardUnderTest.getObstacles().toString();
        boardUnderTest.setNewObstacles(boardUnderTest.getObstaclesNum());
        String newObstacle = boardUnderTest.getObstacles().toString();
        Assertions.assertNotEquals(newObstacle, originalObstacle);
    }

    @Test
    void setObstacles() {
        boardUnderTest = new Board(5, 5, null);
        Obstacles obstaclesForTest = new Obstacles(boardUnderTest, new int[]{1, 1, 1, 1, 1});
        boardUnderTest.setObstacles(obstaclesForTest);
        Assertions.assertSame(obstaclesForTest, boardUnderTest.getObstacles());
    }

    @Test
    void getRowNum() {
        assertEquals(2, boardUnderTest.getRowNum());
    }

    @Test
    void getLineNum() {
        assertEquals(2, boardUnderTest.getLineNum());
    }
}
