package pers.luyu.simonrace.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pers.luyu.simonrace.Director;
import pers.luyu.simonrace.tools.RandomOutput;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Objects;

/**
 * The IndexController class is the controller for the game's index page.
 * It contains methods for handling the user input for players, board, and obstacles, and starting the game.
 * The class also provides getters for the game's practice mode, board size, and obstacle numbers.
 *
 * @author Luyu
 */
public class IndexController {

    int tempNum;

    int row;

    int line;
    private String[] numList;
    @FXML
    ChoiceBox playerSelect;
    @FXML
    TextField boardRow;
    @FXML
    TextField boardLine;
    @FXML
    TextField buttonNum;
    @FXML
    ChoiceBox difficultySelect;
    @FXML
    TextField doubleFenceNum;
    @FXML
    Label message1;
    @FXML
    Label message2;
    @FXML
    Label message3;
    @FXML
    TextField fenceNum;
    @FXML
    TextField holeNum;
    @FXML
    CheckBox isCustomizeSize;
    @FXML
    CheckBox isCustomizedObstacles;
    @FXML
    private CheckBox isCustomizedStartPoint;
    @FXML
    CheckBox isPractise;
    @FXML
    TextField playerName1;
    @FXML
    TextField playerNum;

    @FXML
    TextField portalNum;
    @FXML
    private Button saveName;
    @FXML
    private Button savePlayerNum;
    @FXML
    private Button startGame;
    boolean ifStartPoint;
    String[] playersName;
    int[] obstaclesNum;
    private boolean ifPractice;

    /**
     * Returns a boolean indicating if the game is in practice mode.
     *
     * @return A boolean indicating if the game is in practice mode.
     */
    public boolean isIfPractice() {
        return ifPractice;
    }

    /**
     * Returns the number of rows in the game board.
     *
     * @return The number of rows in the game board.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the number of columns in the game board.
     *
     * @return The number of columns in the game board.
     */
    public int getLine() {
        return line;
    }

    /**
     * Returns an array containing the number of each type of obstacle in the game.
     * The array contains the numbers of buttons, holes, portals, fences, and double fences in that order.
     *
     * @return an array containing the number of each type of obstacle in the game.
     */
    public int[] getObstaclesNum() {
        return obstaclesNum;
    }

    /**
     * This method checks if the player's name and number are valid input.
     *
     * @param playersName The array of player's names.
     * @param PlayerNum   The number of players.
     * @return True if the player's name and number are valid, false otherwise.
     */
    boolean playerFlag(String[] playersName, String PlayerNum) {
        boolean playerFlag = false;
        boolean flag = false;
        if (playersName != null) {
            for (String s : playersName) {
                if (s != null) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if (flag && Integer.parseInt(PlayerNum) > 1) {
            playerFlag = true;
        }
        return playerFlag;
    }

    /**
     * This method checks if the board size and player number are valid input.
     *
     * @param isCustomizeSize True if the user chose to customize the board size, false otherwise.
     * @param boardRow        The number of rows in the board.
     * @param boardLine       The number of lines in the board.
     * @param playerNum       The number of players.
     * @return True if the board size and player number are valid, false otherwise.
     */
    boolean boardFlag(boolean isCustomizeSize, String boardRow, String boardLine, String playerNum) {
        boolean boardFlag = false;
        if ((isCustomizeSize && !Objects.equals(boardRow, "") && !Objects.equals(boardLine, "")) || (!isCustomizeSize && Objects.equals(boardRow, "") && Objects.equals(boardLine, ""))) {
            boardFlag = true;
            if (!Objects.equals(playerNum, "") && isCustomizeSize) {
                if (Integer.parseInt(playerNum) > Integer.parseInt(boardLine)) {
                    setMessage("The players number cannot be larger than the board line.");
                    boardFlag = false;
                }
            }
        }
        return boardFlag;
    }

    /**
     * This method checks if the obstacles are valid input.
     *
     * @param isCustomizedObstacles True if the user chose to customize the obstacles, false otherwise.
     * @param difficultySelect      The selected difficulty if the user did not customize the obstacles.
     * @param buttonNum             The number of buttons on the board.
     * @param holeNum               The number of holes on the board.
     * @param portalNum             The number of portals on the board.
     * @param fenceNum              The number of fences on the board.
     * @param doubleFenceNum        The number of double fences on the board.
     * @param row                   The number of rows in the board.
     * @param line                  The number of lines in the board.
     * @return True if the obstacles are valid, false otherwise.
     */
    boolean obstacleFlag(boolean isCustomizedObstacles, Object difficultySelect, String buttonNum, String holeNum, String portalNum, String fenceNum, String doubleFenceNum, int row, int line) {
        boolean obstacleFlag = false;
        boolean isCustomized = (isCustomizedObstacles && difficultySelect == null && !Objects.equals(buttonNum, "") && !Objects.equals(holeNum, "") && !Objects.equals(portalNum, "") && !Objects.equals(fenceNum, "") && !Objects.equals(doubleFenceNum, ""));
        boolean notCustomized = ((!isCustomizedObstacles && Objects.equals(buttonNum, "") && Objects.equals(holeNum, "") && Objects.equals(portalNum, "") && Objects.equals(fenceNum, "") && difficultySelect != null && Objects.equals(doubleFenceNum, "")));
        if (isCustomized || notCustomized) {
            obstacleFlag = true;
            if (isCustomizedObstacles) {
                if (Integer.parseInt(buttonNum) + Integer.parseInt(holeNum) + Integer.parseInt(portalNum) + Integer.parseInt(fenceNum) + 2 * Integer.parseInt(doubleFenceNum) > (row - 2) * line / 3 * 2) {
                    setMessage("Please reduce the number of obstacles.");
                    obstacleFlag = false;
                } else if (Integer.parseInt(fenceNum) + Integer.parseInt(doubleFenceNum) > row - 2) {
                    setMessage("Please reduce the number of fence.");
                    obstacleFlag = false;
                } else if (line == 2 && !Objects.equals(doubleFenceNum, "0")) {
                    setMessage("Double fence is not allowed when there are only two columns.");
                    obstacleFlag = false;
                }
            }
        }
        return obstacleFlag;
    }

    /**
     * This method sets the number of obstacles on the board.
     *
     * @param ifObstacles True if the user chose to customize the obstacles, false otherwise.
     */
    void setObstaclesNum(boolean ifObstacles) {
        obstaclesNum = new int[5];
        if (ifObstacles) {
            obstaclesNum[0] = Integer.parseInt(buttonNum.getText());
            obstaclesNum[1] = Integer.parseInt(holeNum.getText());
            obstaclesNum[2] = Integer.parseInt(portalNum.getText());
            obstaclesNum[3] = Integer.parseInt(fenceNum.getText());
            obstaclesNum[4] = Integer.parseInt(doubleFenceNum.getText());
        } else {
            String difficulty = (String) difficultySelect.getValue();
            int obstacleNum = switch (difficulty) {
                case "Easy" -> ((row - 2) * line) / 4;
                case "Normal" -> ((row - 2) * line) / 3;
                case "Hard" -> ((row - 2) * line) / 2;
                default -> 0;
            };
            if (obstacleNum < 7) {
                for (int i = 0; i < 5; i++) {
                    obstaclesNum[i] = 1;
                }
                if (obstacleNum < 6) {
                    int temp = 6;
                    while (obstacleNum < temp) {
                        int randomNum = RandomOutput.randomInt(0, 4);
                        if (randomNum == 4) {
                            obstaclesNum[4] = 0;
                            temp -= 2;
                        } else {
                            obstaclesNum[randomNum] = 0;
                            temp--;
                        }
                    }
                }
            } else {
                obstaclesNum[3] = RandomOutput.randomInt(1, row - 3);
                obstaclesNum[4] = RandomOutput.randomInt(1, row - 2 - obstaclesNum[3]);
                obstacleNum = obstacleNum - obstaclesNum[3] - obstaclesNum[4] * 2;
                if (obstacleNum < 4) {
                    for (int i = 0; i < 2; i++) {
                        obstaclesNum[i] = 1;
                    }
                }
                if (obstacleNum < 3) {
                    int temp = 3;
                    while (obstacleNum < temp) {
                        int randomNum = RandomOutput.randomInt(0, 2);
                        obstaclesNum[randomNum] = 0;
                        temp--;
                    }
                } else {
                    obstaclesNum[0] = obstacleNum / 3 + RandomOutput.randomInt(-1, 1);
                    obstaclesNum[1] = obstacleNum / 3 + RandomOutput.randomInt(-1, 1);
                    obstaclesNum[2] = obstacleNum - obstaclesNum[0] - obstaclesNum[1];
                }
            }
            if (line == 2 && obstaclesNum[4] != 0) {
                obstaclesNum[RandomOutput.randomInt(0, 3)] += obstaclesNum[4];
                obstaclesNum[4] = 0;
            }
        }
    }

    /**
     * Sets the board size based on the user input. If the user selects the custom size option, the size will be set to the values entered in the boardRow and boardLine text fields. Otherwise, the size will be set to the default values of 5 rows and the number of player names.
     *
     * @param ifSize a boolean value representing whether the user has selected the custom size option
     */
    void setBoardSize(boolean ifSize) {
        if (ifSize) {
            line = Integer.parseInt(boardLine.getText());
            row = Integer.parseInt(boardRow.getText());
        } else {
            row = 5;
            if(playersName==null){
                setMessage("Please set the number of players first.");
            } else {
                line = playersName.length;
            }

        }
    }

    /**
     * Starts the game. Checks if the player, board, and obstacle information is correctly set, and if so, initializes the game.
     *
     * @throws IOException   if an error occurs while accessing game resources
     * @throws JAXBException if there is an error with the XML binding
     */
    @FXML
    void gameStart() throws IOException, JAXBException {
        setBoardSize(isCustomizeSize.isSelected());

        boolean playerFlag = playerFlag(playersName, playerNum.getText());
        boolean boardFlag = boardFlag(isCustomizeSize.isSelected(), boardRow.getText(), boardLine.getText(), playerNum.getText());
        boolean obstacleFlag = obstacleFlag(isCustomizedObstacles.isSelected(), difficultySelect.getValue(), buttonNum.getText(), holeNum.getText(), portalNum.getText(), fenceNum.getText(), doubleFenceNum.getText(), row, line);

        if (!playerFlag) {
            setMessage("Players information is set incorrectly.");
        }
        if (!boardFlag) {
            setMessage("Board information is set incorrectly.");
        }
        if (!obstacleFlag) {
            setMessage("Obstacles information is set incorrectly.");
        }

        if (playerFlag && boardFlag && obstacleFlag) {
            boolean ifObstacles = isCustomizedObstacles.isSelected();
            ifPractice = isPractise.isSelected();
            ifStartPoint = isCustomizedStartPoint.isSelected();
            setObstaclesNum(ifObstacles);
            Director.getInstance().gameStart();
        }

    }

    /**
     * Gets the names of all players in the game.
     *
     * @return an array of strings containing the names of all players in the game
     */

    public String[] getPlayersName() {
        return playersName;
    }

    /**
     * Gets a boolean value indicating whether the current game board has a start point.
     *
     * @return true if the current game board has a start point, false otherwise
     */
    public boolean getIfStartPoint() {
        return ifStartPoint;
    }

    /**
     * isCustomizeToSize handles the "isCustomizeSize" checkbox. When it is selected,
     * the boardRow and boardLinetext fields are enabled, allowing the user to customize
     * the size of the board. When it is deselected, the boardRow and boardLine text fields
     * are disabled and their text is cleared.
     *
     * @param event the ActionEvent that triggered this method
     */
    @FXML
    void isCustomizeToSize(ActionEvent event) {
        if (isCustomizeSize.isSelected()) {
            boardRow.setEditable(true);
            boardLine.setEditable(true);
        } else {
            boardRow.setText("");
            boardLine.setText("");
            boardRow.setEditable(false);
            boardLine.setEditable(false);
        }
    }

    /**
     * setMessage sets the text of the message1, message2, and message3 labels.
     * The text of message1 is set to the current text of message2, and the text
     * of message2 is set to the current text of message3. The given text is then
     * set as the text of message3.
     *
     * @param text the text to set as the message
     */
    public void setMessage(String text) {
        message1.setText(message2.getText());
        message2.setText(message3.getText());
        message3.setText(text);
    }

    /**
     * isCustomizedToObstacles handles the "isCustomizedObstacles" checkbox.
     * When it is selected, the buttonNum, holeNum, portalNum, fenceNum,
     * and doubleFenceNum text fields are enabled, allowing the user to customize
     * the obstacles on the board. The difficultySelect combo box is disabled
     * and its value is cleared. When the checkbox is deselected, the text fields are disabled
     * and their text is cleared, and the difficultySelect combo box is enabled.
     *
     * @param event the ActionEvent that triggered this method
     */
    @FXML
    void isCustomizedToObstacles(ActionEvent event) {
        if (isCustomizedObstacles.isSelected()) {
            buttonNum.setEditable(true);
            holeNum.setEditable(true);
            portalNum.setEditable(true);
            fenceNum.setEditable(true);
            doubleFenceNum.setEditable(true);
            difficultySelect.setDisable(true);
            difficultySelect.setValue(null);
        } else {
            buttonNum.setText("");
            holeNum.setText("");
            portalNum.setText("");
            fenceNum.setText("");
            doubleFenceNum.setText("");
            buttonNum.setEditable(false);
            holeNum.setEditable(false);
            portalNum.setEditable(false);
            fenceNum.setEditable(false);
            doubleFenceNum.setEditable(false);
            difficultySelect.setDisable(false);
        }
    }

    /**
     * The saveToName method is used to save the name of a selected player.
     *
     * @param event the ActionEvent that triggered the method call
     *              This method checks if the number of players has been saved and if a player has been selected.
     *              If both conditions are met, the name of the selected player is saved and a success message is displayed.
     *              Otherwise, an error message is displayed.
     */
    @FXML
    void saveToName(ActionEvent event) {
        if (playersName == null) {
            setMessage("Please input the number of players first.");
        } else if (playerSelect.getValue() == null) {
            setMessage("Please select the player first.");
        } else {
            playersName[tempNum] = playerName1.getText();
            setMessage(String.format("Successfully set the name of %s.", playerSelect.getValue()));
        }
    }

    /**
     * The saveToPlayerNum method is used to save the number of players in the game.
     *
     * @param event the ActionEvent that triggered the method call
     *              This method checks if the entered number of players is valid (i.e. not empty and greater than 0).
     *              If the number is valid, the player list, player name list, and player number list are updated accordingly.
     *              A success message is displayed, followed by a message to set the players' names.
     *              Otherwise, an error message is displayed.
     */
    @FXML
    void saveToPlayerNum(ActionEvent event) {
        if (!Objects.equals(playerNum.getText(), "") && Integer.parseInt(playerNum.getText()) > 0) {
            int temp = Integer.parseInt(playerNum.getText());
            String[] playerList = new String[temp];
            numList = new String[temp];
            playersName = new String[temp];
            for (int i = 0; i < temp; i++) {
                playerList[i] = String.format("Player %d", i + 1);
                numList[i] = String.valueOf(i);
            }
            playerSelect.setItems(FXCollections.observableArrayList(playerList));
            playerSelect.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue ov, Number value, Number new_value) {
                    tempNum = Integer.parseInt(numList[new_value.intValue()]);
                }
            });
            setMessage("The number of players is set correctly.");
            setMessage("Please setting the players name.");
        } else {
            setMessage("Please enter the correct number of players.");
        }
    }

}
