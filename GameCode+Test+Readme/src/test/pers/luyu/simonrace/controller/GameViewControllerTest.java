package pers.luyu.simonrace.controller;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;
import pers.luyu.simonrace.Main;
import pers.luyu.simonrace.sprite.Board;
import pers.luyu.simonrace.sprite.Players;
import pers.luyu.simonrace.sprite.RankList.Rank;

import java.io.IOException;

import static org.junit.Assert.*;

class GameViewControllerTest {

    public static GuiTest controller;
    public static GameViewController gameController;

    @BeforeAll
    public static void setUpClass() throws InterruptedException, IOException {
        FXTestUtils.launchApp(Main.class);
        Thread.sleep(2000);
        controller = new GuiTest() {
            @Override
            protected Parent getRootNode() {
                return Main.getStage().getScene().getRoot();
            }
        };
        gameController = Main.getGameViewController();
    }


    @Test
    void getTable() {
        assertNotNull(gameController.getTable());
    }

    @Test
    void getBoard() {
        assertNotNull(gameController.getBoard());
    }

    @Test
    void getFirName() {
        assertNotNull(gameController.getFirName());
    }

    @Test
    void getSecName() {
        assertNotNull(gameController.getSecName());
    }

    @Test
    void getThirdName() {
        assertNotNull(gameController.getThirdName());
    }

    @Test
    void getFirCode() {
        assertNotNull(gameController.getFirCode());
    }

    @Test
    void getSecCode() {
        assertNotNull(gameController.getSecCode());
    }

    @Test
    void getThirdCode() {
        assertNotNull(gameController.getThirdCode());
    }

    @Test
    void getLeftButton() {
        assertNotNull(gameController.getLeftButton());
    }

    @Test
    void getRightButton() {
        assertNotNull(gameController.getRightButton());
    }

    @Test
    void getStayButton() {
        assertNotNull(gameController.getStayButton());
    }

    @Test
    void getThirdRank() {
        assertNotNull(gameController.getThirdRank());
    }

    @Test
    void getGradeButton() {
        assertNotNull(gameController.getGradeButton());
    }

    @Test
    void getStepButton() {
        assertNotNull(gameController.getStepButton());
    }

    @Test
    void getUndoButton() {
        assertNotNull(gameController.getUndoButton());
    }

    @Test
    void setConsole() {
        gameController.firConsole.setText("hello");
        gameController.secConsole.setText("world");
        gameController.setConsole("foo");
        assertEquals("world", gameController.firConsole.getText());
        assertEquals("foo", gameController.secConsole.getText());
    }

    @Test
    void getDiceButton() {
        assertNotNull(gameController.getDiceButton());
    }

    @Test
    void setDiceResult() {
        int[] diceResult = {1, 2};
        gameController.setDiceResult(diceResult);

        assertEquals("1", gameController.directionNum.getText());
        assertEquals("2", gameController.stepNum.getText());
    }

    @Test
    void setPlayersList() {
        Players players = new Players(new String[]{"text 0","text 1","text 2"},new Rank[]{new Rank("","","")});
        gameController.setPlayersList(players);
        assertEquals("text 0", gameController.getFirName().getText());
        assertEquals("text 1", gameController.getSecName().getText());
        assertEquals("Player 3", gameController.getThirdCode().getText());
        assertEquals("text 2", gameController.getThirdName().getText());
        players = new Players(new String[]{"text 0","text 1","text 2"},new Rank[]{new Rank("","","")});
        gameController.setPlayersList(players);
        assertEquals("text 2", gameController.getThirdName().getText());
    }
    @Test
    public void testChangeCurrentPlayer() {
// Initialize Players object with 3 players
        Players players = new Players(new String[]{"text 0","text 1","text 2"},new Rank[]{new Rank("","","")});
// Test changing to next player
        gameController.changeCurrentPLayer(players);
        assertEquals("text 1", gameController.getFirName().getText());
        assertEquals("Player 2", gameController.getFirCode().getText());
        assertEquals("text 2", gameController.getSecName().getText());
        assertEquals("Player 3", gameController.getSecCode().getText());
        assertEquals("text 0", gameController.getThirdName().getText());
        assertEquals("Player 1", gameController.getThirdCode().getText());

// Test changing to next player again
        gameController.changeCurrentPLayer(players);
        assertEquals("text 2", gameController.getFirName().getText());
        assertEquals("Player 3", gameController.getFirCode().getText());
        assertEquals("text 0", gameController.getSecName().getText());
        assertEquals("Player 1", gameController.getSecCode().getText());
        assertEquals("text 1", gameController.getThirdName().getText());
        assertEquals("Player 2", gameController.getThirdCode().getText());
    }


    @Test
    void updateButton() {
        // Test with all buttons enabled
        gameController.updateButton(false, false, false, false, false);
        assertFalse(gameController.getDiceButton().isDisable());
        assertFalse(gameController.getLeftButton().isDisable());
        assertFalse(gameController.getRightButton().isDisable());
        assertFalse(gameController.getStayButton().isDisable());
        assertFalse(gameController.getUndoButton().isDisable());

// Test with all buttons disabled
        gameController.updateButton(true, true, true, true, true);
        assertTrue(gameController.getDiceButton().isDisable());
        assertTrue(gameController.getLeftButton().isDisable());
        assertTrue(gameController.getRightButton().isDisable());
        assertTrue(gameController.getStayButton().isDisable());
        assertTrue(gameController.getUndoButton().isDisable());

// Test with individual buttons disabled
        gameController.updateButton(true, false, true, false, true);
        assertTrue(gameController.getDiceButton().isDisable());
        assertFalse(gameController.getLeftButton().isDisable());
        assertTrue(gameController.getRightButton().isDisable());
        assertFalse(gameController.getStayButton().isDisable());
        assertTrue(gameController.getUndoButton().isDisable());

    }
}