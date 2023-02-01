package pers.luyu.simonrace.sprite;

import javafx.scene.control.Button;
import pers.luyu.simonrace.sprite.obstacles.*;
import pers.luyu.simonrace.tools.RandomOutput;

/**
 * The Obstacles class is used to generate and store the obstacles on the game board.
 * It contains several instance variables of different types of obstacles, such as buttons, holes,
 * single fences, double fences, and portals. It also has a method to generate random empty squares
 * on the game board to place the obstacles.
 *
 * @author Luyu
 */
public class Obstacles {

    private Obstacle[] buttons; //An array containing all the buttons on the board.
    private Obstacle[] singleFences; //An array containing all the single fences on the board.
    private Obstacle[] doubleFences; //An array containing all the double fences on the board.
    private Obstacle[] holes; //An array containing all the holes on the board.
    private Obstacle[] portals; //An array containing all the portals on the board.
    private Obstacle[][] obstacles; //// An array containing all the obstacles on the board.
    private int[] fenceNum; //An array that keeps track of how many fences are in each column of the board.

    /**
     * Constructor that initializes the obstacles on the board according to the specified numbers ofeach type of obstacle.
     *
     * @param board        The board on which the obstacles will be placed.
     * @param obstaclesNum An array containing the numbers of each type of obstacle to be generated.
     *                     The indices of the array correspond to the types of obstacles as follows:
     *                     0 - buttons, 1 - holes, 2 - portals, 3 - single fences, 4 - double fences.
     */
    public Obstacles(Board board, int[] obstaclesNum) {
        fenceNum = new int[board.getRowNum() - 2];
        if (obstaclesNum == null) {
            return;
        }
        if (obstaclesNum[0] > 0) {
            buttons = new GameButton[obstaclesNum[0]];
            for (int i = 0; i < obstaclesNum[0]; i++) {
                Square temp = getEmptySquare(board);
                buttons[i] = new GameButton(temp);
                temp.setObstacle(buttons[i]);
            }
        } else {
            buttons = new GameButton[0];
        }
        if (obstaclesNum[1] > 0) {
            holes = new Hole[obstaclesNum[1]];
            for (int i = 0; i < obstaclesNum[1]; i++) {
                Square temp = getEmptySquare(board);
                holes[i] = new Hole(temp);
                temp.setObstacle(holes[i]);
            }
        } else {
            holes = new Hole[0];
        }

        if (obstaclesNum[3] > 0) {
            singleFences = new SingleFence[obstaclesNum[3]];
            for (int i = 0; i < obstaclesNum[3]; i++) {
                Square temp = getEmptySquare(board);
                singleFences[i] = new SingleFence(temp);
                temp.setObstacle(singleFences[i]);
            }
        } else {
            singleFences = new SingleFence[0];
        }

        if (obstaclesNum[4] > 0) {
            doubleFences = new DoubleFence[obstaclesNum[4]];
            for (int i = 0; i < obstaclesNum[4]; i++) {
                Square[] temp = getEmptyDoubleSquare(board);
                doubleFences[i] = new DoubleFence(temp[0], temp[1]);
                temp[0].setObstacle(doubleFences[i]);
                temp[1].setObstacle(doubleFences[i]);
            }
        } else {
            doubleFences = new DoubleFence[0];
        }

        if (obstaclesNum[2] > 0) {
            portals = new Portal[obstaclesNum[2]];
            for (int i = 0; i < obstaclesNum[2]; i++) {
                portals[i] = new Portal(getEmptySquare(board));
            }
        } else {
            portals = new Portal[0];
        }
        obstacles = new Obstacle[][]{buttons, holes, portals, singleFences, doubleFences};
    }

    public Obstacles(Square[][] holesBackup, Square[][] ButtonBackup, Square[][] singleFencesBackup, Square[][] portalsBackup, Square[][] doubleFencesBackup) {
        holes = new Hole[holesBackup.length];
        for (int i =0;i<holesBackup.length;i++){
            holes[i] = new Hole(holesBackup[i][0]);
            holesBackup[i][0].setObstacle(holes[i]);
        }
        buttons = new GameButton[ButtonBackup.length];
        for (int i =0;i<ButtonBackup.length;i++){
            buttons[i] = new GameButton(ButtonBackup[i][0]);
            ButtonBackup[i][0].setObstacle(buttons[i]);
        }
        portals = new Portal[portalsBackup.length];
        for (int i =0;i<portalsBackup.length;i++){
            portals[i] = new Portal(portalsBackup[i][0]);
            portalsBackup[i][0].setObstacle(portals[i]);
        }
        singleFences = new SingleFence[singleFencesBackup.length];
        for (int i =0;i<portalsBackup.length;i++){
            singleFences[i] = new SingleFence(singleFencesBackup[i][0]);
            singleFencesBackup[i][0].setObstacle(singleFences[i]);
        }
        doubleFences = new DoubleFence[doubleFencesBackup.length];
        for (int i =0;i<holes.length;i++){
            doubleFences[i] = new DoubleFence(doubleFencesBackup[i][0],doubleFencesBackup[i][1]);
            doubleFencesBackup[i][0].setObstacle(doubleFences[i]);
            doubleFencesBackup[i][1].setObstacle(doubleFences[i]);
        }
    }


    /**
     * Returns an array of buttons from the board.
     *
     * @return an array of buttons
     */
    public Obstacle[] getButtons() {
        return buttons;
    }

    /**
     * Returns a 2D array of obstacles from the board.
     *
     * @return a 2D array of obstacles
     */
    public Obstacle[][] getObstacles() {
        return obstacles;
    }

    /**
     * Returns an array of single fences from the board.
     *
     * @return an array of single fences
     */
    public Obstacle[] getSingleFences() {
        return singleFences;
    }

    /**
     * Returns an array of double fences from the board.
     *
     * @return an array of double fences
     */
    public Obstacle[] getDoubleFences() {
        return doubleFences;
    }

    /**
     * Returns an array of holes from the board.
     *
     * @return an array of holes
     */
    public Obstacle[] getHoles() {
        return holes;
    }

    /**
     * Returns an array of portals from the board.
     *
     * @return an array of portals
     */
    public Obstacle[] getPortals() {
        return portals;
    }

    /**
     * Returns a random empty square from the board.
     *
     * @param board the game board
     * @return an empty square on the board
     */
    public Square getEmptySquare(Board board) {
        while (true) {
            int row = RandomOutput.randomInt(1, board.getRowNum() - 2);
            int line = RandomOutput.randomInt(0, board.getLineNum() - 1);
            if (board.getSquare(row, line).getObstacle() == null && fenceNum[row - 1] < board.getLineNum() - 1) {
                fenceNum[row - 1]++;
                return board.getSquare(row, line);
            }
        }
    }

    /**
     * Returns a pair of empty squares on the board, positioned next to each other.
     *
     * @param board the game board
     * @return a pair of empty squares on the board
     */
    public Square[] getEmptyDoubleSquare(Board board) {
        Square[] temp = new Square[2];
        while (true) {
            int row = RandomOutput.randomInt(1, board.getRowNum() - 2);
            int line = RandomOutput.randomInt(0, board.getLineNum() - 2);
            if (board.getSquare(row, line).getObstacle() == null) {
                if (board.getSquare(row, line + 1).getObstacle() == null && fenceNum[row - 1] < board.getLineNum() - 2) {
                    temp[0] = board.getSquare(row, line);
                    temp[1] = board.getSquare(row, line + 1);
                    fenceNum[row - 1] += 2;
                    break;
                }
            }
        }
        return temp;
    }
}
