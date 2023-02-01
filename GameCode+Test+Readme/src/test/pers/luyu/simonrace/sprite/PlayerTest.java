package pers.luyu.simonrace.sprite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Luyu");
    }

    @Test
    void getPlayerList() {
        assertEquals("0", player.getPlayerList());
    }

    @Test
    void getDazeTime() {
        assertEquals(0, player.getDazeTime());
    }

    @Test
    void setLastPlayer() {
        Player lastPlayer = new Player("Feng");
        player.setLastPlayer(lastPlayer);
        Assertions.assertSame(lastPlayer, player.getLastPlayer());
    }

    @Test
    void getNextPlayer() {
        Player nextPlayer = new Player("Feng");
        player.setNextPlayer(nextPlayer);
        Assertions.assertSame(nextPlayer, player.getNextPlayer());
    }

    @Test
    void setNextPlayer() {
        Player nextPlayer = new Player("Feng");
        player.setNextPlayer(nextPlayer);
        Assertions.assertNotNull(player.getNextPlayer());
    }

    @Test
    void getName() {
        assertEquals("Luyu", player.getName());
    }

    @Test
    void setPlayerList() {
        player.setPlayerList(Integer.toString(1));
        assertEquals("1", player.getPlayerList());
    }
}