package pers.luyu.simonrace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pers.luyu.simonrace.controller.GameViewController;
import pers.luyu.simonrace.sprite.Board;
import pers.luyu.simonrace.sprite.Players;
import pers.luyu.simonrace.sprite.RankList.Rank;
import pers.luyu.simonrace.sprite.obstacles.*;

import static org.junit.jupiter.api.Assertions.*;

class DirectorTest {

    private Director directorUnderTest;
    private GameViewController gameViewController;

    @BeforeEach
    void setUp() {
        directorUnderTest = Director.getInstance();
    }

    @Test
    void testRollDice() {
        Board boardForTest = new Board(3, 1, new int[]{0, 0, 0, 0, 0});
        Players playersForTest = new Players(new String[]{"text 0"},new Rank[]{new Rank("","","")});

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(2, 0));
        directorUnderTest.rollDice(playersForTest, boardForTest, new int[]{4, 3});
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateLine());
        assertEquals(2, playersForTest.getCurrentPlayer().getLocateSquare().getLocateRow());

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(2, 0));
        directorUnderTest.rollDice(playersForTest, boardForTest, new int[]{3, 3});
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateLine());
        assertEquals(2, playersForTest.getCurrentPlayer().getLocateSquare().getLocateRow());

        new SingleFence(boardForTest.getSquare(2, 0));
        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 0));
        directorUnderTest.rollDice(playersForTest, boardForTest, new int[]{3, 3});
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateLine());
        assertEquals(1, playersForTest.getCurrentPlayer().getLocateSquare().getLocateRow());

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(2, 0));
        directorUnderTest.rollDice(playersForTest, boardForTest, new int[]{1, 3});
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateLine());
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateRow());

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(1, 0));
        directorUnderTest.rollDice(playersForTest, boardForTest, new int[]{1, 3});
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateLine());
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateRow());
    }

    @Test
    void testGoAhead() {
        Board boardForTest = new Board(3, 1, new int[]{0, 0, 0, 0, 0});
        Players playersForTest = new Players(new String[]{"text 0"},new Rank[]{new Rank("","","")});
        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(2, 0));
        assertArrayEquals(new boolean[]{true, true}, directorUnderTest.goAhead(true, boardForTest, playersForTest));

        Fence testFence = new SingleFence(boardForTest.getSquare(1, 0));
        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(2, 0));
        assertArrayEquals(new boolean[]{false, true}, directorUnderTest.goAhead(true, boardForTest, playersForTest));

        new Hole(boardForTest.getSquare(1, 0));
        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(2, 0));
        assertArrayEquals(new boolean[]{false, false}, directorUnderTest.goAhead(true, boardForTest, playersForTest));

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(1, 0));
        assertArrayEquals(new boolean[]{false, true}, directorUnderTest.goAhead(true, boardForTest, playersForTest));

        playersForTest = new Players(new String[]{"text 0","text 1"},new Rank[]{new Rank("","","")});
        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(1, 0));
        playersForTest.getPlayer(1).setPlayerLocation(boardForTest.getSquare(0, 0));
        assertArrayEquals(new boolean[]{false, false}, directorUnderTest.goAhead(true, boardForTest, playersForTest));

        playersForTest.setCurrentPlayer(playersForTest.getPlayer(1));
        assertArrayEquals(new boolean[]{false, false}, directorUnderTest.goAhead(false, boardForTest, playersForTest));
    }

    @Test
    void testGoLeftOrRight() {

        Board boardForTest = new Board(2, 2, new int[]{0, 0, 0, 0, 0});
        Players playersForTest = new Players(new String[]{"text 0"},new Rank[]{new Rank("","","")});

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 0));
        assertFalse(directorUnderTest.goLeftOrRight(false, boardForTest, playersForTest));

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 1));
        assertFalse(directorUnderTest.goLeftOrRight(true, boardForTest, playersForTest));

        assertTrue(directorUnderTest.goLeftOrRight(false, boardForTest, playersForTest));

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 1));
        new SingleFence(boardForTest.getSquare(0, 0));
        assertFalse(directorUnderTest.goLeftOrRight(false, boardForTest, playersForTest));

        new Hole(boardForTest.getSquare(0, 0));
        assertFalse(directorUnderTest.goLeftOrRight(false, boardForTest, playersForTest));
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateLine());
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateRow());

        playersForTest = new Players(new String[]{"text 0","text 1"},new Rank[]{new Rank("","","")});
        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 0));
        playersForTest.getPlayer(1).setPlayerLocation(boardForTest.getSquare(0, 1));
        assertFalse(directorUnderTest.goLeftOrRight(true, boardForTest, playersForTest));
    }

    @Test
    void testGetInstance() {
        Assertions.assertNotNull(Director.getInstance());
    }

    @Test
    void endTurn() {
        Board boardForTest = new Board(2, 2, new int[]{0, 0, 0, 0, 0});
        Players playersForTest = new Players(new String[]{"text 0"},new Rank[]{new Rank("","","")});

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 0));
        assertTrue(directorUnderTest.endTurn(boardForTest, playersForTest));

        new Hole(boardForTest.getSquare(0, 0));
        assertTrue(directorUnderTest.endTurn(boardForTest, playersForTest));
    }

    @Test
    void actWithObstacles() {
        Board boardForTest = new Board(2, 2, new int[]{0, 0, 0, 0, 0});
        Players playersForTest = new Players(new String[]{"text 0"},new Rank[]{new Rank("","","")});

        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 0));
        playersForTest.getPlayer(0).setDazeTime(0);
        new Hole(boardForTest.getSquare(0, 0));
        directorUnderTest.actWithObstacles(boardForTest, playersForTest);

        new GameButton(boardForTest.getSquare(0, 0));
        directorUnderTest.actWithObstacles(boardForTest, playersForTest);
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateLine());
        assertEquals(0, playersForTest.getCurrentPlayer().getLocateSquare().getLocateRow());

        boardForTest = new Board(2, 1, new int[]{0, 0, 0, 0, 0});
        playersForTest = new Players(new String[]{"text 0","text 1"},new Rank[]{new Rank("","","")});
        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 0));
        playersForTest.getPlayer(1).setPlayerLocation(boardForTest.getSquare(1, 0));
        new Portal(boardForTest.getSquare(0, 0));
        directorUnderTest.actWithObstacles(boardForTest, playersForTest);
        assertEquals(0, playersForTest.getPlayer(0).getLocateSquare().getLocateLine());
        assertEquals(1, playersForTest.getPlayer(0).getLocateSquare().getLocateRow());
        assertEquals(0, playersForTest.getPlayer(1).getLocateSquare().getLocateLine());
        assertEquals(0, playersForTest.getPlayer(1).getLocateSquare().getLocateRow());
    }

    @Test
    void endThisGame() {
        Players playersForTest = new Players(new String[]{"text 0"},new Rank[]{new Rank("","","")});
        playersForTest.getPlayer(0).setGrade(0);
        playersForTest.setWinner(playersForTest.getPlayer(0));
        directorUnderTest.endThisGame(playersForTest);
        assertEquals(1, playersForTest.getPlayer(0).getGrade());
    }

    @Test
    void undo() {
        Board boardForTest = new Board(2, 2, new int[]{0, 0, 0, 0, 0});
        Players playersForTest = new Players(new String[]{"text 0"},new Rank[]{new Rank("","","")});
        playersForTest.setFirActivePlayers(playersForTest.getPlayer(0));
        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 0));
        playersForTest.getPlayer(0).setPreLocation(boardForTest.getSquare(0, 1));
        playersForTest.getPlayer(0).setPreDazeTime(2);
        playersForTest.getPlayer(0).setDazeTime(1);
        playersForTest.getPlayer(0).setTotalStepNum(2);
        playersForTest.getPlayer(0).setPreStepNum(1);
        directorUnderTest.undo(playersForTest);
        assertEquals(2, playersForTest.getPlayer(0).getDazeTime());
        assertEquals(1, playersForTest.getPlayer(0).getTotalStepNum());
        assertEquals(0, playersForTest.getPlayer(0).getLocateSquare().getLocateRow());
        assertEquals(1, playersForTest.getPlayer(0).getLocateSquare().getLocateLine());

        playersForTest = new Players(new String[]{"text 0","text 1"},new Rank[]{new Rank("","","")});
        playersForTest.setFirActivePlayers(playersForTest.getPlayer(0));
        playersForTest.setSecActivePlayers(playersForTest.getPlayer(1));
        playersForTest.getPlayer(0).setPreLocation(boardForTest.getSquare(0, 0));
        playersForTest.getPlayer(1).setPreLocation(boardForTest.getSquare(0, 1));
        playersForTest.getPlayer(0).setPlayerLocation(boardForTest.getSquare(0, 1));
        playersForTest.getPlayer(1).setPlayerLocation(boardForTest.getSquare(0, 0));
        directorUnderTest.undo(playersForTest);
        assertEquals(0, playersForTest.getPlayer(0).getLocateSquare().getLocateRow());
        assertEquals(0, playersForTest.getPlayer(0).getLocateSquare().getLocateLine());
        assertEquals(0, playersForTest.getPlayer(1).getLocateSquare().getLocateRow());
        assertEquals(1, playersForTest.getPlayer(1).getLocateSquare().getLocateLine());
    }

}

