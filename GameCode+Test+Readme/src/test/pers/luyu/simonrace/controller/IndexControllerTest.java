package pers.luyu.simonrace.controller;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;
import pers.luyu.simonrace.Main;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndexControllerTest {
    public static GuiTest controller;
    public static IndexController indexController;

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
        indexController = Main.getIndexController();
    }

    @Test
    void isIfPractice() {
        assertEquals(false, indexController.isIfPractice());
    }

    @Test
    void getRow() {
        assertNotNull(indexController.getRow());
    }

    @Test
    void getLine() {
        assertNotNull(indexController.getLine());
    }

    @Test
    void getObstaclesNum() {
        indexController.obstaclesNum = new int[]{5, 4, 3, 2, 1};
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, indexController.getObstaclesNum());
    }

    @Test
    public void testPlayerFlag() {
        String[] playersName = new String[]{"Player 1", "Player 2"};
        String playerNum = "2";
        boolean actualResult = indexController.playerFlag(playersName, playerNum);
        assertTrue(actualResult);

        playersName = new String[]{"Player 1", null, "Player 2"};
        playerNum = "3";
        actualResult = indexController.playerFlag(playersName, playerNum);
        assertFalse(actualResult);

        playersName = new String[]{};
        playerNum = "2";
        actualResult = indexController.playerFlag(playersName, playerNum);
        assertFalse(actualResult);

        playersName = new String[]{"Player 1", "Player 2"};
        playerNum = "0";
        actualResult = indexController.playerFlag(playersName, playerNum);
        assertFalse(actualResult);

        playersName = new String[]{"Player 1"};
        playerNum = "1";
        actualResult = indexController.playerFlag(playersName, playerNum);
        assertFalse(actualResult);
    }


    @Test
    void getIfStartPoint() {
        indexController.ifStartPoint = true;
        assertTrue(indexController.getIfStartPoint());
        indexController.ifStartPoint = false;
        assertFalse(indexController.getIfStartPoint());
    }

    @Test
    void isCustomizeToSize() {
        indexController.isCustomizeSize.setSelected(true);
        indexController.isCustomizeToSize(null);
        assertTrue(indexController.boardRow.isEditable());
        assertTrue(indexController.boardLine.isEditable());

        indexController.isCustomizeSize.setSelected(false);
        indexController.isCustomizeToSize(null);
        assertFalse(indexController.boardRow.isEditable());
        assertFalse(indexController.boardLine.isEditable());
        assertEquals("", indexController.boardRow.getText());
        assertEquals("", indexController.boardLine.getText());
    }

    @Test
    void setMessage() {
        Label message1 = new Label();
        Label message2 = new Label();
        Label message3 = new Label();

        indexController.message1 = message1;
        indexController.message2 = message2;
        indexController.message3 = message3;

        assertEquals("", message1.getText());
        assertEquals("", message2.getText());
        assertEquals("", message3.getText());

        indexController.setMessage("Message 1");
        assertEquals("", message1.getText());
        assertEquals("", message2.getText());
        assertEquals("Message 1", message3.getText());

        indexController.setMessage("Message 2");
        assertEquals("", message1.getText());
        assertEquals("Message 1", message2.getText());
        assertEquals("Message 2", message3.getText());

        indexController.setMessage("Message 3");
        assertEquals("Message 1", message1.getText());
        assertEquals("Message 2", message2.getText());
        assertEquals("Message 3", message3.getText());
    }

    @Test
    void isCustomizedToObstacles() {
        indexController.isCustomizedObstacles.setSelected(true);
        indexController.isCustomizedToObstacles(null);
        assertTrue(indexController.buttonNum.isEditable());
        assertTrue(indexController.holeNum.isEditable());
        assertTrue(indexController.portalNum.isEditable());
        assertTrue(indexController.fenceNum.isEditable());
        assertTrue(indexController.doubleFenceNum.isEditable());
        assertTrue(indexController.difficultySelect.isDisabled());
        assertNull(indexController.difficultySelect.getValue());

        indexController.isCustomizedObstacles.setSelected(false);
        indexController.isCustomizedToObstacles(null);
        assertFalse(indexController.buttonNum.isEditable());
        assertFalse(indexController.holeNum.isEditable());
        assertFalse(indexController.portalNum.isEditable());
        assertFalse(indexController.fenceNum.isEditable());
        assertFalse(indexController.doubleFenceNum.isEditable());
        assertFalse(indexController.difficultySelect.isDisabled());
    }


    @Test
    void saveToPlayerNum() {
        indexController.playerNum.setText("4");
        indexController.saveToPlayerNum(new ActionEvent());
        assertEquals(indexController.playersName.length, 4);
        assertEquals(indexController.playerSelect.getItems().size(), 4);
        assertEquals(indexController.message2.getText(), "The number of players is set correctly.");

        indexController.playerNum.setText("0");
        indexController.saveToPlayerNum(new ActionEvent());
        assertEquals(indexController.playersName.length, 4); // should remain unchanged
        assertEquals(indexController.playerSelect.getItems().size(), 4); // should remain unchanged
        assertEquals(indexController.message2.getText(), "Please setting the players name.");
    }

    @Test
    void boardFlag() {
        assertTrue(indexController.boardFlag(true, "4", "4", ""));
        assertTrue(indexController.boardFlag(true, "4", "4", "2"));
        assertFalse(indexController.boardFlag(true, "4", "4", "5"));
        assertTrue(indexController.boardFlag(false, "", "", ""));
        assertFalse(indexController.boardFlag(false, "4", "", ""));
    }

    @Test
    void obstacleFlag() {
        assertTrue(indexController.obstacleFlag(true, null, "2", "3", "1", "1", "1", 6, 5));
        assertFalse(indexController.obstacleFlag(true, null, "10", "10", "10", "10", "10", 6, 5));
        assertFalse(indexController.obstacleFlag(true, null, "0", "0", "0", "0", "10", 6, 5));
        assertFalse(indexController.obstacleFlag(true, null, "0", "0", "0", "0", "1", 6, 2));
        assertTrue(indexController.obstacleFlag(false, "Easy", "", "", "", "", "", 6, 5));
        assertFalse(indexController.obstacleFlag(false, "Easy", "1", "1", "1", "1", "1", 6, 5));
    }

    @Test
    public void setBoardSize() {
        indexController.boardLine.setText("6");
        indexController.boardRow.setText("8");
        indexController.setBoardSize(true);
        assertEquals(8, indexController.row);
        assertEquals(6, indexController.line);

        indexController.playersName = new String[]{"Tom","Jerry"};
        indexController.setBoardSize(false);
        assertEquals(5, indexController.row);
        assertEquals(2, indexController.line);
    }

    @Test
    public void getPlayersName() {
        indexController.playersName = new String[0];
        String[] playersName = new String[0];
        assertArrayEquals(playersName, indexController.getPlayersName());
        indexController.playersName = new String[] {"Player 1", "Player 2", "Player 3"};
        playersName = new String[] {"Player 1", "Player 2", "Player 3"};
        assertArrayEquals(playersName, indexController.getPlayersName());
    }

    @Test
    void setObstaclesNum() {
        int[] expected = {1, 2, 3, 4, 5};
        indexController.buttonNum.setText("1");
        indexController.holeNum.setText("2");
        indexController.portalNum.setText("3");
        indexController.fenceNum.setText("4");
        indexController.doubleFenceNum.setText("5");
        indexController.setObstaclesNum(true);
        assertArrayEquals(expected, indexController.obstaclesNum);
        indexController.row = 5;
        indexController.line = 6;
        indexController.difficultySelect.setValue("Normal");
        indexController.setObstaclesNum(false);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, indexController.obstaclesNum);
        indexController.difficultySelect.setValue("Easy");
        indexController.setObstaclesNum(false);
        assertTrue(indexController.obstaclesNum[0] >= 0 && indexController.obstaclesNum[0] <= 1);
        assertTrue(indexController.obstaclesNum[1] >= 0 && indexController.obstaclesNum[1] <= 1);
        assertTrue(indexController.obstaclesNum[2] >= 0 && indexController.obstaclesNum[2] <= 1);
        assertTrue(indexController.obstaclesNum[3] >= 0 && indexController.obstaclesNum[3] <= 1);
        assertTrue(indexController.obstaclesNum[4] >= 0 && indexController.obstaclesNum[4] <= 1);
        indexController.difficultySelect.setValue("Hard");
        indexController.row = 5;
        indexController.line = 2;
        indexController.setObstaclesNum(false);
        assertEquals(0, indexController.obstaclesNum[4]);
    }

    @Test
    void gameStart() {
        // Test playerFlag()
        String[] playersName = {"player1", "player2"};
        assertTrue(indexController.playerFlag(playersName, "2"));

        playersName = new String[] {"player1", null};
        assertFalse(indexController.playerFlag(playersName, "2"));

        playersName = null;
        assertFalse(indexController.playerFlag(playersName, "2"));

        playersName = new String[] {"player1", "player2"};
        assertFalse(indexController.playerFlag(playersName, "1"));

// Test boardFlag()
        assertTrue(indexController.boardFlag(true, "5", "6", "2"));
        assertFalse(indexController.boardFlag(true, "5", "", "2"));
        assertFalse(indexController.boardFlag(true, "", "6", "2"));
        assertTrue(indexController.boardFlag(false, "", "", "2"));
        assertFalse(indexController.boardFlag(false, "5", "6", "2"));

// Test obstacleFlag()
        assertFalse(indexController.obstacleFlag(true, null, "5", "6", "7", "8", "9", 10, 6));
        assertFalse(indexController.obstacleFlag(true, null, "5", "6", "7", "8", "9", 2, 6));
        assertFalse(indexController.obstacleFlag(true, null, "5", "6", "7", "8", "9", 10, 2));
        assertTrue(indexController.obstacleFlag(false, "Easy", "", "", "", "", "", 10, 6));
        assertFalse(indexController.obstacleFlag(true, "Easy", "", "", "", "", "", 10, 6));
        assertFalse(indexController.obstacleFlag(false, null, "5", "6", "7", "8", "9", 10, 6));

    }

    @Test
    void saveToName() {
        // Test case 1: input valid player name
        String playerName = "John Doe";
        indexController.playerName1.setText(playerName);
        indexController.playerSelect.setValue("Player 1");
        indexController.tempNum = 0;
        indexController.playersName = new String[1];
        indexController.saveToName(null);
        assertEquals(playerName, indexController.playersName[0]);
        assertEquals("Successfully set the name of Player 1.", indexController.message3.getText());
// Test case 2: no player selected
        indexController.playerSelect.setValue(null);
        indexController.saveToName(null);
        assertEquals("Please select the player first.", indexController.message3.getText());

    }
}