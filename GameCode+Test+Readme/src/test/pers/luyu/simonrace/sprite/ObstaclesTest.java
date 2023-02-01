package pers.luyu.simonrace.sprite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObstaclesTest {

    private Obstacles obstacles;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(5, 5, new int[]{1, 1, 1, 1, 1});
        obstacles = new Obstacles(board, new int[]{1, 1, 1, 1, 1});
    }

    @Test
    void getObstacles() {
        assertEquals(5, obstacles.getObstacles().length);
    }

    @Test
    void getSingleFences() {
        assertEquals(1, obstacles.getSingleFences().length);
    }

    @Test
    void getDoubleFences() {
        assertEquals(1, obstacles.getDoubleFences().length);
    }

    @Test
    void getHoles() {
        assertEquals(1, obstacles.getHoles().length);
    }

    @Test
    void getPortals() {
        assertEquals(1, obstacles.getPortals().length);
    }
}