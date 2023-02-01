package pers.luyu.simonrace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pers.luyu.simonrace.controller.GameViewController;
import pers.luyu.simonrace.controller.IndexController;
import pers.luyu.simonrace.scene.GameScene;
import pers.luyu.simonrace.scene.Index;
import pers.luyu.simonrace.sprite.*;
import pers.luyu.simonrace.sprite.RankList.Rank;
import pers.luyu.simonrace.sprite.obstacles.Fence;
import pers.luyu.simonrace.sprite.obstacles.GameButton;
import pers.luyu.simonrace.sprite.obstacles.Hole;
import pers.luyu.simonrace.sprite.obstacles.Portal;
import pers.luyu.simonrace.tools.LoadAndSaveXML;
import pers.luyu.simonrace.tools.RandomOutput;

import java.io.File;
import java.io.IOException;

/**
 * The Director class is the main controller for managing the game.
 * It is implemented as a singleton and contains the game board, players, and various observables and controllers for managing the game.
 * The Director class initializes and starts the game, controls the players' actions and movements, and manages the game view interactions.
 *
 * @author Luyu
 */
public class Director {
    public static final double WIDTH = 620, HEIGHT = 450; //set game window size
    private static final Director instance = new Director(); //singleton instance of the Director
    private static Stage stage; //JavaFX stage for the game
    private final ObservableList<Rank> gradeRankData = FXCollections.observableArrayList(); //observable list for storing ranks based on grades
    private final ObservableList<Rank> stepRankData = FXCollections.observableArrayList(); //observable list for storing ranks based on steps
    private Players players; //all players in the game
    private int stepNum; //current step number in the game
    private Obstacles obstacles; //obstacles on the game board
    private Rank[] gradeRanks; //array of ranks based on grades
    private Rank[] stepRanks; //array of ranks based on steps
    private Board board; //game board
    private GameViewController gameViewController; //game view controller for managing game view interactions
    private IndexController indexController; //index controller for managing index view interactions
    private boolean practiceMode; //flag for determining if the game is in practice mode
    private boolean backUpObstaclesFlag = false;

    /**
     * Private constructor for the singleton instance of the Director class.
     */
    private Director() {
    }

    /**
     * Returns the singleton instance of the Director class.
     *
     * @return the singleton instance of the Director class
     */
    public static Director getInstance() {
        return instance;
    }

    /**
     * Initializes the game by setting the stage, creating the game's scene, and loading the index view.
     *
     * @param stage the JavaFX stage for the game
     * @throws IOException if there is an error loading the index view
     */
    public void init(Stage stage) throws IOException {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Simon’s Race");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        Director.stage = stage;
        toIndex();
        stage.show();
    }

    /**
     * Loads the index view.
     *
     * @throws IOException if there is an error loading the index view
     */
    public IndexController toIndex() throws IOException {
        Index.load(stage);
        return Index.returnIndexViewController();
    }

    public GameViewController toGame() throws IOException {
        GameScene.load(stage);
        return GameScene.returnGameViewController();
    }

    /**
     * The gameStart method initializes and starts a new game.
     * It loads the game scene and retrieves the index and game view controllers.
     * If the game is in practice mode, the undo button is enabled.
     * The game board is created and obstacles are placed on it.
     * The game board and players list are set in the game view controller.
     * The grade and step rankings are loaded from xml files and set in the game view controller.
     * The players are created and added to the game.
     * The starting position for each player is set on the game board.
     * The step and grade rankings are displayed in the game view controller.
     * The dice roll, turn left, turn right, and stay actions are enabled for the players.
     * The game console displays a message to the current player to roll the dice.
     *
     * @throws IOException if there is an error loading the game scene or xml files
     */
    public void gameStart() throws IOException {
        GameScene.load(stage);
        indexController = Index.returnIndexViewController();
        gameViewController = GameScene.returnGameViewController();
        if (indexController.isIfPractice()) {
            practiceMode = true;
            unDoButton();
        } else {
            gameViewController.getUndoButton().setDisable(true);
        }
        board = new Board(indexController.getRow(), indexController.getLine(), indexController.getObstaclesNum());
        obstacles = board.getObstacles();
        gameViewController.setGameBoard(gameViewController.getBoard(), board);
        setRank(gradeRankData, new File("resources/xml/gradeRank.xml"));
        gradeRanks = gradeRankData.toArray(new Rank[0]);
        setRank(stepRankData, new File("resources/xml/stepRank.xml"));
        stepRanks = stepRankData.toArray(new Rank[0]);
        players = new Players(indexController.getPlayersName(), gradeRanks);
        gameViewController.updateImage(board, players);
        gameViewController.setPlayersList(players);
        gameViewController.setPlayerStartPoint(indexController.getIfStartPoint(), board, players);
        showStepRank();
        showGradeRank();
        activeRollDice();
        turnLeft();
        turnRight();
        stay();
    }

    /**
     * This method is used to set the rank data in the game view controller table.
     *
     * @param rankData an ObservableList of Rank objects containing the rank data
     * @param file     the file where the rank data is stored
     */
    void setRank(ObservableList<Rank> rankData, File file) {
        LoadAndSaveXML.loadDataFromFile(rankData, file);
        gameViewController.getTable().setItems(rankData);
    }

    /**
     * This method is used to handle the action of the undo button in the game.
     * When the undo button is clicked, the game will perform an undo operation on the players,
     * set the current player to the last player in the players list, and update the game view to reflect the changes.
     * The undo button will also be disabled after it is clicked.
     */
    public void unDoButton() {
        gameViewController.getUndoButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                undo(players);
                players.setCurrentPlayer(players.getCurrentPlayer().getLastPlayer().getLastPlayer());
                gameViewController.changeCurrentPLayer(players);
                gameViewController.getUndoButton().setDisable(true);
                gameViewController.updateImage(board, players);
                gameViewController.setConsole(String.format("%s please roll dice.%n", players.getCurrentPlayer().getName()));
            }
        });
    }

    /**
     * This method undoes the actions of the players in the game.
     * It resets the total step number and daze time of the first player,
     * and moves the first and second player to their previous locations if needed.
     *
     * @param players the Players object representing the active players in the game
     */
    public void undo(Players players) {
        if (players.getActivePlayers()[0] != null) {
            players.getActivePlayers()[0].setTotalStepNum(players.getActivePlayers()[0].getPreStepNum());
            players.getActivePlayers()[0].setDazeTime(players.getActivePlayers()[0].getPreDazeTime());
            players.getActivePlayers()[0].move(players.getActivePlayers()[0].getPreLocation());
            if (players.getActivePlayers()[1] != null) {
                players.getActivePlayers()[1].move(players.getActivePlayers()[1].getPreLocation());
            }
            if (backUpObstaclesFlag){
                board.setNewObstacles();
                backUpObstaclesFlag = false;
            }
        }
    }

    /**
     * The showStepRank method is used to display the step rank data in the game view.
     * It sets up an action event handler for the step button, which updates the table with the step rank data and updates the third rank text to "Step".
     */
    public void showStepRank() {
        gameViewController.getStepButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameViewController.getTable().setItems(stepRankData);
                gameViewController.getThirdRank().setText("Step");
            }
        });
    }

    /**
     * This method is used to display the grade rank of the players in a table.
     * When the grade button is clicked, the table is updated with the grade rank data and
     * the text of the third rank is set to "Grade".
     */
    public void showGradeRank() {
        gameViewController.getGradeButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameViewController.getTable().setItems(gradeRankData);
                gameViewController.getThirdRank().setText("Grade");
            }
        });
    }

    /**
     * This method is used to activate the dice roll button. When this button is clicked, it will check if the game is in practice mode.
     * If so, it clears the previous round's player information then backs up the current player information for the undo action.
     * It will then check if the current player is fazed, and if so, reduce their faze time by 1 and display a message.
     * If the player is not dazed, it will roll the dice and call the rollDice() method. If rollDice() returns false,
     * it will change the current player, update the game view and, if in practice mode, enable the undo button.
     */
    public void activeRollDice() {
        gameViewController.getDiceButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (practiceMode) {
                    players.setSecActivePlayers(null);
                    players.setFirActivePlayers(null);
                    players.getCurrentPlayer().setPreStepNum(players.getCurrentPlayer().getTotalStepNum());
                    players.getCurrentPlayer().setPreDazeTime(players.getCurrentPlayer().getDazeTime());
                    players.getCurrentPlayer().setPreStepNum(players.getCurrentPlayer().getTotalStepNum());
                    players.getCurrentPlayer().setPreLocation(players.getCurrentPlayer().getLocateSquare());
                    players.setFirActivePlayers(players.getCurrentPlayer());
                }
                int[] diceResult;
                boolean turn;
                if (players.getCurrentPlayer().getDazeTime() != 0) {
                    gameViewController.setConsole(String.format("%s can act after %d rounds.%n", players.getCurrentPlayer().getName(), players.getCurrentPlayer().getDazeTime()));
                    players.getCurrentPlayer().setDazeTime(players.getCurrentPlayer().getDazeTime() - 1);
                    turn = false;
                } else {
                    diceResult = new int[]{RandomOutput.randomInt(1, 4), RandomOutput.randomInt(1, 4)};
                    gameViewController.setDiceResult(diceResult);
                    turn = rollDice(players, board, diceResult);
                }
                if (!turn) {
                    gameViewController.changeCurrentPLayer(players);
                    gameViewController.setConsole(String.format("%s please roll dice.%n", players.getCurrentPlayer().getName()));
                    gameViewController.updateImage(board, players);
                    if (practiceMode) {
                        gameViewController.getUndoButton().setDisable(false);
                    }
                }

            }
        });
    }

    /**
     * This method rolls the dice for a player and updates their position on the board based on the result of the roll.
     * If the result is 3, the player will move backwards for the number of steps indicated on the dice.
     * If the result is 1 or 2, the player will move forwards for the number of steps indicated on the dice.
     * If the result is 4, the player will stay in place for the round.
     *
     * @param players    the list of players in the game
     * @param board      the game board
     * @param diceResult diceResult An array containing the results of the dice rolls, the first element is the direction of the move, and the second element is the number of squares to be moved
     * @return true if the player can move again, false otherwise
     */
    public boolean rollDice(Players players, Board board, int[] diceResult) {

        if (diceResult[0] == 3) {
            for (int i = diceResult[1]; i > 0; i--) {
                if (gameViewController != null) {
                    gameViewController.setConsole(String.format("%s will move backward for %d squares.%n", players.getCurrentPlayer().getName(), diceResult[1]));
                }
                if (players.getCurrentPlayer().getLocateSquare().isStartPoint()) {
                    if (gameViewController != null) {
                        gameViewController.setConsole(String.format("%s can't go back anymore.%n", players.getCurrentPlayer().getName()));
                    }
                    stepNum = 0;
                } else {
                    boolean[] temp = goAhead(false, board, players);
                    if (!temp[0] && !temp[1]) {
                        break;
                    } else if (!temp[0]) {
                        stepNum = i;
                        return true;
                    }
                }
            }
        } else if (diceResult[0] != 4) {
            if (gameViewController != null) {
                gameViewController.setConsole(String.format("%s will move forward for %d squares.%n", players.getCurrentPlayer().getName(), diceResult[1]));
            }
            for (int i = diceResult[1]; i > 0; i--) {
                boolean[] temp = goAhead(true, board, players);
                if (!temp[0] && !temp[1]) {
                    break;
                } else if (!temp[0]) {
                    stepNum = i;
                    return true;
                }
            }
        } else {
            if (gameViewController != null) {
                gameViewController.setConsole(String.format("%s will stay this round.%n", players.getCurrentPlayer().getName()));
            }
        }
        return false;
    }

    /**
     * This method updates a player's position on the board by moving them one square forward or backward.
     *
     * @param goForward true if the player should move forward, false if they should move backward
     * @param board     a Board object representing the game board
     * @param players   a Players object containing the current player and other players in the game
     * @return an array of booleans indicating whether the player needs to choose a direction (first element) or whether they need to take an action with an obstacle (second element)
     */
    public boolean[] goAhead(boolean goForward, Board board, Players players) {
        Square nextSquare;
        boolean walkBackAndForth = false;
        boolean walkLeftAndRight = false;
        boolean[] walkDirection = new boolean[]{walkBackAndForth, walkLeftAndRight};
        if (goForward) {
            nextSquare = players.getCurrentPlayer().getLocateSquare().frontSquare(board);
        } else {
            nextSquare = players.getCurrentPlayer().getLocateSquare().backSquare(board);
        }
        if (nextSquare.getPlayer() != null) {
            if (gameViewController != null) {
                gameViewController.setConsole(String.format("There are other players in front of %s.%n", players.getCurrentPlayer().getName()));
                gameViewController.setConsole(String.format("%s skip this round.%n", players.getCurrentPlayer().getName()));
            }
            return walkDirection;
        }

        if (nextSquare.getObstacle() instanceof Fence) {
            if (gameViewController != null) {
                gameViewController.setConsole(String.format("%s please select to the left, %n", players.getCurrentPlayer().getName()));
                gameViewController.setConsole("to the right or stay.");
                gameViewController.updateButton(true, false, false, false, true);
            }
            walkDirection[1] = true;
            return walkDirection;
        }
        players.getCurrentPlayer().move(nextSquare);
        players.getCurrentPlayer().setTotalStepNum(players.getCurrentPlayer().getTotalStepNum() + 1);
        if (gameViewController != null) {
            gameViewController.updateImage(board, players);
        }
        if (players.getCurrentPlayer().getLocateSquare().getObstacle() != null) {
            actWithObstacles(board, players);
            return walkDirection;
        }
        if (players.getCurrentPlayer().getLocateSquare().isFinishPoint()) {
            players.setWinner(players.getCurrentPlayer());
            if (gameViewController != null) {
                gameViewController.setConsole(String.format("%s have reached the end of the line", players.getCurrentPlayer().getName()));
            }
            endThisGame(players);
            return new boolean[]{false, true};
        }
        return new boolean[]{true, true};
    }

    /**
     * The goLeftOrRight() method checks if the player is able to move left or right on the board based on the given boolean value.
     * If the move is possible, the player's location is updated and any obstacles on the new square are handled.
     *
     * @param isRight a boolean value indicating whether the player should move right (true) or left (false)
     * @param board   the current board object containing all squares and obstacles
     * @param players the current players object containing all player information
     * @return true if the move was successful, false if the move was not possible
     */
    public boolean goLeftOrRight(boolean isRight, Board board, Players players) {
        if ((players.getCurrentPlayer().getLocateSquare().getLocateLine() == 0 && !isRight) || (players.getCurrentPlayer().getLocateSquare().getLocateLine() == board.getLineNum() - 1 && isRight)) {
            //For test
            if (gameViewController != null) {
                gameViewController.setConsole(String.format("%s are already at the edge of the board.%n", players.getCurrentPlayer().getName()));
            }
            return false;
        }
        Square nextSquare;
        if (isRight) {
            nextSquare = players.getCurrentPlayer().getLocateSquare().leftSquare(board);
        } else {
            nextSquare = players.getCurrentPlayer().getLocateSquare().rightSquare(board);
        }
        if (nextSquare.getPlayer() != null) {
            //For test
            if (gameViewController != null) {
                gameViewController.setConsole(String.format("There are other players in front of %s.%n", players.getCurrentPlayer().getName()));
            }
            return false;
        }
        if (nextSquare.getObstacle() instanceof Fence) {
            //For test
            if (gameViewController != null) {
                if (practiceMode){
                    board.backUpAllObstacles();
                    backUpObstaclesFlag = true;
                }
                gameViewController.setConsole(String.format("%s cannot move, board layout will change.%n", players.getCurrentPlayer().getName()));
                board.setNewObstacles(indexController.getObstaclesNum());
                gameViewController.updateImage(board, players);
            }
            return false;
        }
        players.getCurrentPlayer().move(nextSquare);
        players.getCurrentPlayer().setTotalStepNum(players.getCurrentPlayer().getTotalStepNum() + 1);
        if (players.getCurrentPlayer().getLocateSquare().getObstacle() != null) {
            actWithObstacles(board, players);
            return false;
        }
        return true;
    }

    /**
     * This method is used to handle the event when the "Left" button is clicked. When the button is clicked,
     * the player will move a certain number of steps (specified by the stepNum variable) to the left.
     * If the player cannot move any further to the left (e.g. because they are already at the left edge of the board),
     * the method will stop moving the player and call the endTurn method to end the current player's turn.
     */
    public void turnLeft() {
        gameViewController.getLeftButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < stepNum; i++) {
                    if (!goLeftOrRight(false, board, players)) {
                        break;
                    }
                }
                endTurn(board, players);
            }
        });

    }

    /**
     * This method is used to handle the event when the "Right" button is clicked. When the button is clicked,
     * the player will move a certain number of steps (specified by the stepNum variable) to the right.
     * If the player cannot move any further to the right (e.g. because they are already at the right edge of the board),
     * the method will stop moving the player and call the endTurn method to end the current player's turn.
     */
    public void turnRight() {
        gameViewController.getRightButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < stepNum; i++) {
                    if (!goLeftOrRight(true, board, players)) {
                        break;
                    }
                }
                endTurn(board, players);
            }
        });
    }

    /**
     * This method is used to end the current player's turn. It updates the game board and player information,
     * and enables or disables certain buttons on the game view (e.g. the "Roll Dice" button is enabled and
     * the "Left" and "Right" buttons are disabled). If the current player is standing on a square with an
     * obstacle, the method calls the actWithObstacles method to handle the obstacle.
     *
     * @param board   board the current board object containing all squares and obstacles
     * @param players the players in the game
     * @return true if the turn was successfully ended,or false if the player was on a square with an obstacle
     * and the `actWithObstacles` method returned `false` (indicating that the player's turn should not be ended yet)
     */
    public boolean endTurn(Board board, Players players) {
        if (gameViewController != null) {
            boolean undo = false;
            if (!practiceMode){
                undo = true;
            }
            gameViewController.updateButton(false, true, true, true, undo);
            gameViewController.changeCurrentPLayer(players);
            gameViewController.setConsole(String.format("%s please roll dice.%n", players.getCurrentPlayer().getName()));
            gameViewController.updateImage(board, players);
        }
        return true;
    }

    /**
     * The endTurn() method ends the current player's turn and updates the game state accordingly.
     * Any obstacles on the player's current square are handled, and the next player is set as the current player.
     */
    public void stay() {
        gameViewController.getStayButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameViewController.setConsole(String.format("%s will pass this round.%n", players.getCurrentPlayer().getName()));
                boolean undo = false;
                if (!practiceMode){
                    undo = true;
                }
                gameViewController.updateButton(false, true, true, true, undo);
                gameViewController.changeCurrentPLayer(players);
                gameViewController.setConsole(String.format("%s please roll dice.%n", players.getCurrentPlayer().getName()));
                gameViewController.updateImage(board, players);
            }
        });
    }

    /**
     * This method is used to handle the actions of the players when they step on an obstacle on the board.
     * If the obstacle is a portal, the current player will swap places with a random player on the board.
     * If the obstacle is a hole, the current player will be trapped and unable to move for one turn.
     * If the obstacle is a game button, the layout of the obstacles on the board will change.
     *
     * @param board   The game board that contains the obstacles.
     * @param players The players who are currently playing the game.
     */
    public void actWithObstacles(Board board, Players players) {
        if (players.getCurrentPlayer().getLocateSquare().getObstacle() instanceof Portal) {
            //For test
            if (gameViewController != null) {
                gameViewController.setConsole(String.format("%s stepped on the portal.%n", players.getCurrentPlayer().getName()));
            }
            Player changedPlayer;
            do {
                Player[] playerArray = players.getPlayers();
                changedPlayer = playerArray[RandomOutput.randomInt(0, playerArray.length - 1)];
            } while (players.getCurrentPlayer() == changedPlayer);
            if (practiceMode) {
                changedPlayer.setPreLocation(changedPlayer.getLocateSquare());
                players.setSecActivePlayers(changedPlayer);
            }
            players.changePlayersLocation(players.getCurrentPlayer(), changedPlayer);
            //For test
            if (gameViewController != null) {
                gameViewController.updateImage(board, players);
                gameViewController.setConsole(String.format("%s and %s swapped places.%n", players.getCurrentPlayer().getName(), changedPlayer.getName()));
            }
        } else if (players.getCurrentPlayer().getLocateSquare().getObstacle() instanceof Hole) {
            //For test
            if (gameViewController != null) {
                gameViewController.setConsole(String.format("%s stepped on the hole.%n", players.getCurrentPlayer().getName()));
                gameViewController.setConsole(String.format("The hole traps %s.%n", players.getCurrentPlayer().getName()));
            }
            players.getCurrentPlayer().setDazeTime(1);
        } else if (players.getCurrentPlayer().getLocateSquare().getObstacle() instanceof GameButton) {
            //For test
            if (gameViewController != null) {
                if (practiceMode){
                    board.backUpAllObstacles();
                    backUpObstaclesFlag = true;
                }
                board.setNewObstacles(indexController.getObstaclesNum());
                gameViewController.setConsole(String.format("%s stepped on the button.%n", players.getCurrentPlayer().getName()));
                gameViewController.setConsole("The layout of the obstacles will change.");
                gameViewController.updateImage(board, players);
            }
        }
    }

    /**
     * This method is used to end the current game and update the game data.
     * The winner of the game will have their score incremented by 1.
     * The rank data for the step and grade rankings will be updated and saved to XML files.
     * The game view controller will be updated to reflect the end of the game.
     *
     * @param players The players who are currently playing the game.
     */
    public void endThisGame(Players players) {
        players.getWinner().setGrade(players.getWinner().getGrade() + 1);
        //For test
        if (gameViewController != null) {
            gameViewController.updateButton(true, true, true, true, true);
            gameViewController.setConsole(String.format("Game end, %s win.%n", players.getWinner().getName()));
            Rank.updateGradeRank(players, gradeRanks, gradeRankData);
            Rank.updateStepRank(players, stepRanks, stepRankData);
            LoadAndSaveXML.saveDataToFile(stepRankData, new File("resources/xml/stepRank.xml"));
            LoadAndSaveXML.saveDataToFile(gradeRankData, new File("resources/xml/gradeRank.xml"));
            gameViewController.getTable().setItems(gradeRankData);
        }
    }
}
