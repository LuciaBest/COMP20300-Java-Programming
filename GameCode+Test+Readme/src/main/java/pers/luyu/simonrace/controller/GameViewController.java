package pers.luyu.simonrace.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pers.luyu.simonrace.sprite.Board;
import pers.luyu.simonrace.sprite.Players;
import pers.luyu.simonrace.sprite.RankList.Rank;
import pers.luyu.simonrace.sprite.Square;
import pers.luyu.simonrace.sprite.obstacles.Fence;
import pers.luyu.simonrace.sprite.obstacles.GameButton;
import pers.luyu.simonrace.sprite.obstacles.Hole;
import pers.luyu.simonrace.tools.RandomOutput;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * GameViewController is a JavaFX controller class that controls the UI of the game.
 * It contains FXML elements that are binded to the corresponding UI elements in the game view,
 * and the methods that handle the user interactions with the UI.
 *
 * @author Luyu
 */

public class GameViewController {

    @FXML
    Label directionNum;
    @FXML
    Label firConsole;
    @FXML
    Label secConsole;
    @FXML
    Label stepNum;
    @FXML
    private VBox console;
    @FXML
    private HBox dice;
    @FXML
    private Button diceButton;
    @FXML
    private Label firCode;
    @FXML
    private Label firName;
    @FXML
    private TableColumn<Rank, String> firRank;
    @FXML
    private GridPane gameBoard;
    @FXML
    private AnchorPane gameView;
    @FXML
    private Button gradeButton;
    @FXML
    private Button leftButton;
    @FXML
    private VBox playerCode;
    @FXML
    private VBox playerNameList;
    @FXML
    private TableView<Rank> rankList;
    @FXML
    private Button rightButton;
    @FXML
    private Button stepButton;
    @FXML
    private Label secCode;
    @FXML
    private Label secName;
    @FXML
    private TableColumn<Rank, String> secRank;
    @FXML
    private ScrollPane showPane;
    @FXML
    private Button stayButton;
    @FXML
    private Label thirdCode;

    @FXML
    private Label thirdName;

    @FXML
    private Button undoButton;

    @FXML
    private TableColumn<Rank, String> thirdRank;
    VBox[][] vBoxes;

    private int line;
    private int row;

    /**
     * Returns the TableView that displays the current game ranks.
     *
     * @return a TableView of ranks
     */
    public TableView<Rank> getTable() {
        return rankList;
    }

    /**
     * Returns the GridPane that displays the current game board.
     *
     * @return a GridPane representing the game board
     */
    public GridPane getBoard() {
        return gameBoard;
    }

    /**
     * Returns the Label that displays the first player's name.
     *
     * @return a Label with the first player's name
     */
    public Label getFirName() {
        return firName;
    }

    /**
     * Returns the Label that displays the second player's name.
     *
     * @return a Label with the second player's name
     */
    public Label getSecName() {
        return secName;
    }

    /**
     * Returns the Label that displays the third player's name.
     *
     * @return a Label with the third player's name
     */
    public Label getThirdName() {
        return thirdName;
    }

    /**
     * Returns the Label that displays the first player's code.
     *
     * @return a Label with the first player's code
     */
    public Label getFirCode() {
        return firCode;
    }

    /**
     * Returns the Label that displays the second player's code.
     *
     * @return a Label with the second player's code
     */
    public Label getSecCode() {
        return secCode;
    }

    /**
     * Returns the Label that displays the third player's code.
     *
     * @return a Label with the third player's code
     */
    public Label getThirdCode() {
        return thirdCode;
    }

    /**
     * Returns the Button that allows the player to move left.
     *
     * @return a Button to move left
     */
    public Button getLeftButton() {
        return leftButton;
    }

    /**
     * Returns the Button that allows the player to move right.
     *
     * @return a Button to move right
     */
    public Button getRightButton() {
        return rightButton;
    }

    /**
     * Returns the Button that allows the player to stay in their current position.
     *
     * @return a Button to stay in current position
     */
    public Button getStayButton() {
        return stayButton;
    }

    /**
     * Returns the TableColumn that displays the third player's rank.
     *
     * @return a TableColumn of the third player's rank
     */
    public TableColumn<Rank, String> getThirdRank() {
        return thirdRank;
    }

    /**
     * Returns the Button that allows the player to grade the current square.
     *
     * @return a Button to grade the current square
     */
    public Button getGradeButton() {
        return gradeButton;
    }

    /**
     * Returns the Button that allows the player to step on the current square.
     *
     * @return a Button to step on the current square
     */
    public Button getStepButton() {
        return stepButton;
    }

    /**
     * Returns the Button that allows the player to undo their last move.
     *
     * @return a Button to undo the last move
     */
    public Button getUndoButton() {
        return undoButton;
    }

    /**
     * This method sets the text in the console with the given text and shifts the previous console text to the second console.
     *
     * @param text The text to be set in the console.
     */
    public void setConsole(String text) {
        firConsole.setText(secConsole.getText());
        secConsole.setText(text);
    }

    /**
     * This method returns the dice button.
     *
     * @return The dice button.
     */
    public Button getDiceButton() {
        return diceButton;
    }

    /**
     * This method sets the dice result on the direction and step number labels.
     *
     * @param diceResult The array containing the direction and step number from the dice roll.
     */
    public void setDiceResult(int[] diceResult) {
        directionNum.setText(String.valueOf(diceResult[0]));
        stepNum.setText(String.valueOf(diceResult[1]));
    }

    /**
     * This method sets the player names on the corresponding labels.
     *
     * @param players The Players object containing the player names and number of players.
     */
    public void setPlayersList(Players players) {
        getFirName().setText(players.getPlayer(0).getName());
        getSecName().setText(players.getPlayer(1).getName());
        if (players.getPlayersNum() == 2) {
            getThirdCode().setText("Player 1");
            getThirdName().setText(players.getPlayer(0).getName());
        } else {
            getThirdName().setText(players.getPlayer(2).getName());
        }
    }

    /**
     * This method sets the game board with the given grid pane and board.
     * It sets the line and row numbers, sets the width of the grid pane, and creates column and row constraints.
     * It then creates a 2D array of VBoxes and populates the grid pane with these VBoxes, as well as labels with their coordinates.
     * Finally, it sets the locations of the squares in the board with their corresponding VBoxes.
     *
     * @param gridPane the grid pane to set the game board on
     * @param board    the game board to be set on the grid pane
     */
    public void setGameBoard(GridPane gridPane, Board board) {
        line = board.getLineNum();
        row = board.getRowNum();
        gridPane.setPrefWidth(406.0);
        ColumnConstraints[] column = new ColumnConstraints[line];
        RowConstraints[] rowConstraints = new RowConstraints[row];
        for (int i = 0; i < line; i++) {
            column[i] = new ColumnConstraints(400.0 / line);
            gridPane.getColumnConstraints().add(column[i]);
        }
        for (int i = 0; i < row; i++) {
            rowConstraints[i] = new RowConstraints(400.0 / line);
            gridPane.getRowConstraints().add(rowConstraints[i]);
        }
        vBoxes = new VBox[row][line];
        for (int l = 0; l < line; l++) {
            for (int r = 0; r < row; r++) {
                vBoxes[r][l] = new VBox();
                vBoxes[r][l].setAlignment(Pos.CENTER);
                getBoard().add(vBoxes[r][l], l, r);
                board.getSquare(r, l).setLocation(vBoxes[r][l]);
            }
        }
    }

    /**
     * This method sets the starting point of the player based on the flag.
     * If flag is true, the user is allowed to select the starting point.
     * If flag is false, the starting point is selected randomly.
     *
     * @param flag    a boolean value representing if the user can select the starting point
     * @param board   the game board containing the squares
     * @param players the list of players in the game
     */
    public void setPlayerStartPoint(boolean flag, Board board, Players players) {
        if (flag) {
            setConsole(String.format("%s please select the starting point.", players.getCurrentPlayer().getName()));
            Button[] setStartButton = new Button[board.getLineNum()];
            for (int i = 0; i < board.getLineNum(); i++) {
                int temp = i;
                setStartButton[i] = new Button("select");
                vBoxes[board.getRowNum() - 1][i].getChildren().add(setStartButton[i]);
                setStartButton[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        players.getCurrentPlayer().setPlayerLocation(board.getSquare(row - 1, temp));
                        players.getCurrentPlayer().getLocateSquare().getLocateBox().getChildren().remove(setStartButton[temp]);
                        boolean flag = Objects.equals(players.getCurrentPlayer().getNextPlayer().getPlayerList(), "Player 1");
                        if (flag) {
                            setConsole("All players' have been assigned.");
                            setConsole(String.format("%s please roll the dice.", players.getPlayer(0).getName()));
                            for (int i = 0; i < line; i++) {
                                if (board.getSquare(row - 1, i).getPlayer() == null) {
                                    vBoxes[row - 1][i].getChildren().clear();
                                }
                            }
                        } else {
                            setConsole(String.format("%s please select the starting point.", players.getCurrentPlayer().getNextPlayer().getName()));
                        }
                        updateImage(board, players);
                        changeCurrentPLayer(players);
                    }
                });
            }
        } else {
            for (int i = 0; i < players.getPlayersNum(); i++) {
                while (true) {
                    int selectLine = RandomOutput.randomInt(0, line - 1);
                    if (board.getSquare(row - 1, selectLine).getPlayer() == null) {
                        players.getCurrentPlayer().setPlayerLocation(board.getSquare(row - 1, selectLine));
                        break;
                    }
                }
                changeCurrentPLayer(players);
            }
            updateImage(board, players);
            setConsole(String.format("%s please roll dice.%n", players.getCurrentPlayer().getName()));
        }
    }

    /**
     * This method changes the current player in the game,
     * and updates the list of players on the game screen.
     *
     * @param players the list of players in the game
     */
    public void changeCurrentPLayer(Players players) {
        players.setCurrentPlayer(players.getCurrentPlayer().getNextPlayer());
        getFirName().setText(players.getCurrentPlayer().getName());
        getFirCode().setText(players.getCurrentPlayer().getPlayerList());
        getSecName().setText(players.getCurrentPlayer().getNextPlayer().getName());
        getSecCode().setText(players.getCurrentPlayer().getNextPlayer().getPlayerList());
        getThirdName().setText(players.getCurrentPlayer().getNextPlayer().getNextPlayer().getName());
        getThirdCode().setText(players.getCurrentPlayer().getNextPlayer().getNextPlayer().getPlayerList());
    }

    /**
     * This method updates the images on the game board based on the obstacles and players on the board.
     *
     * @param board   the game board containing the squares
     * @param players the list of players in the game
     */
    public void updateImage(Board board, Players players) {

        if (board.getObstacles().getButtons() != null) {
            for (int i = 0; i < board.getObstacles().getButtons().length; i++) {
                setObstacleImage(board.getObstacles().getButtons()[i].getLocation());
            }
        }
        if (board.getObstacles().getDoubleFences() != null) {
            for (int i = 0; i < board.getObstacles().getDoubleFences().length; i++) {
                setObstacleImage(board.getObstacles().getDoubleFences()[i].getLocation());
                setObstacleImage(board.getObstacles().getDoubleFences()[i].getSecLocation());
            }
        }
        if (board.getObstacles().getSingleFences() != null) {
            for (int i = 0; i < board.getObstacles().getSingleFences().length; i++) {
                setObstacleImage(board.getObstacles().getSingleFences()[i].getLocation());
            }
        }
        if (board.getObstacles().getHoles() != null) {
            for (int i = 0; i < board.getObstacles().getHoles().length; i++) {
                setObstacleImage(board.getObstacles().getHoles()[i].getLocation());
            }
        }
        if (board.getObstacles().getPortals() != null) {
            for (int i = 0; i < board.getObstacles().getPortals().length; i++) {
                setObstacleImage(board.getObstacles().getPortals()[i].getLocation());
            }
        }

        for (int i = 0; i < players.getPlayersNum(); i++) {
            if (players.getPlayer(i).getLocateSquare() != null) {
                setPlayerImage(players.getPlayer(i).getLocateSquare(), players);
            }
        }
    }

    /**
     * This method sets the image of an obstacle on a given location.
     * If the location contains a Fence, a Hole, a GameButton or a Portal, the corresponding image will be set.
     * The image will be scaled according to the dimensions of the game grid.
     *
     * @param location the square on which the obstacle image is to be set
     */
    private void setObstacleImage(Square location) {
        Image fence;
        Image hole;
        Image portal;
        Image button;
        ImageView imageView = new ImageView();
        try {
            fence = new Image(new FileInputStream("resources/image/obstacle-fence.png"));
            hole = new Image(new FileInputStream("resources/image/obstacle-hole.png"));
            portal = new Image(new FileInputStream("resources/image/obstacle-portal.png"));
            button = new Image(new FileInputStream("resources/image/obstacle-button.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (location.getObstacle() instanceof Fence) {
            imageView = new ImageView(fence);
        } else if (location.getObstacle() instanceof Hole) {
            imageView = new ImageView(hole);
        } else if (location.getObstacle() instanceof GameButton) {
            imageView = new ImageView(button);
        } else {
            imageView = new ImageView(portal);
        }
        imageView.setFitHeight(400.0 / line);
        imageView.setFitWidth(400.0 / line);
        location.getLocateBox().getChildren().clear();
        location.getLocateBox().getChildren().add(imageView);
    }

    /**
     * This method sets the image of a player on a given location.
     * If the player on the location is not the current player, a standby image will be set.
     * Otherwise, an alive image will be set. The player's name will also be displayed.
     * The image will be scaled according to the dimensions of the game grid.
     *
     * @param location the square on which the player image is to be set
     * @param players  the list of players in the game
     */
    private void setPlayerImage(Square location, Players players) {
        Image playerStandby;
        Image playerAlive;
        ImageView imageView = new ImageView();
        try {
            playerStandby = new Image(new FileInputStream("resources/image/player-standby.png"));
            playerAlive = new Image(new FileInputStream("resources/image/player-alive.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (location.getPlayer() != players.getCurrentPlayer()) {
            imageView = new ImageView(playerStandby);
        } else {
            imageView = new ImageView(playerAlive);
        }
        imageView.setFitHeight((400.0 / line) * 0.8);
        imageView.setFitWidth((400.0 / line) * 0.8);
        location.getLocateBox().getChildren().clear();
        location.getLocateBox().getChildren().add(imageView);
        location.getLocateBox().getChildren().add(new Label(location.getPlayer().getName()));
    }

    /**
     * Updates the buttons on the game screen with the given boolean values.
     *
     * @param dice  Whether the dice button should be disabled or not.
     * @param left  Whether the left button should be disabled or not.
     * @param right Whether the right button should be disabled or not.
     * @param stay  Whether the stay button should be disabled or not.
     * @param undo  Whether the undo button should be disabled or not.
     */
    public void updateButton(boolean dice, boolean left, boolean right, boolean stay, boolean undo) {
        getDiceButton().setDisable(dice);
        getLeftButton().setDisable(left);
        getRightButton().setDisable(right);
        getStayButton().setDisable(stay);
        getUndoButton().setDisable(undo);

    }

    /**
     * Initializes the cells in the game's high score table with the appropriate values.
     */
    @FXML
    private void initialize() {
        firRank.setCellValueFactory(cellData -> cellData.getValue().listProperty());
        secRank.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        thirdRank.setCellValueFactory(cellData -> cellData.getValue().otherProperty());
    }

}
