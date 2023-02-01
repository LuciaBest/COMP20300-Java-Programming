package pers.luyu.simonrace.sprite;

import javafx.scene.layout.VBox;
import pers.luyu.simonrace.sprite.obstacles.Obstacle;

/**
 * The Square class represents a square on a board game. It has fields for storing information about its location,
 * whether it is a starting or finishing point, and any obstacles or players on the square.
 * It also has methods for retrieving and setting this information, as well as methods for accessing neighboring squares on the board.
 *
 * @author Luyu
 */
public class Square {

    private final boolean startPoint; //Indicates whether this square is a starting point on the board.
    private final boolean finishPoint; //Indicates whether this square is a finishing point on the board.
    private Obstacle obstacle; //The obstacle on this square, if any.
    private Player player; //The player currently on this square, if any.
    private int locateRow; //The row location of this square on the board.
    private int locateLine; //The column location of this square on the board.
    private VBox location; //The visual representation of this square on the board.

    /**
     * Creates a new Square with default values.
     */
    public Square() {
        this.startPoint = false;
        this.finishPoint = false;
        this.obstacle = null;
        this.player = null;
    }

    /**
     * Creates a new Square with the specified starting or finishing point.
     *
     * @param flag true if this square is a starting point, false if it is a finishing point
     */
    public Square(boolean flag) {
        this.obstacle = null;
        if (flag) {
            this.startPoint = true;
            this.finishPoint = false;
        } else {
            this.startPoint = false;
            this.finishPoint = true;
        }
    }

    /**
     * Returns the obstacle on this square.
     *
     * @return the obstacle on this square
     */
    public Obstacle getObstacle() {
        return obstacle;
    }

    /**
     * Sets the obstacle on this square.
     *
     * @param obstacle the obstacle to be placed on this square
     */
    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    /**
     * Returns the visual representation of this square on the board.
     *
     * @return the visual representation of this square on the board
     */
    public VBox getLocateBox() {
        return location;
    }

    /**
     * Sets the visual representation of this square on the board.
     *
     * @param location the visual representation of this square on the board
     */
    public void setLocation(VBox location) {
        this.location = location;
    }

    /**
     * Returns true if this square is a finishing point on the board.
     *
     * @return true if this square is a finishing point on the board
     */
    public boolean isFinishPoint() {
        return finishPoint;
    }

    /**
     * Returns true if this square is a starting point on the board.
     *
     * @return true if this square is a starting point on the board
     */
    public boolean isStartPoint() {
        return startPoint;
    }

    /**
     * Gets the column location of the square on the board.
     *
     * @return the column location of the square
     */
    public int getLocateLine() {
        return locateLine;
    }

    /**
     * Sets the column location of the square on the board.
     *
     * @param locateLine the column location of the square
     */
    public void setLocateLine(int locateLine) {
        this.locateLine = locateLine;
    }

    /**
     * Gets the row location of the square on the board.
     *
     * @return the row location of the square
     */
    public int getLocateRow() {
        return locateRow;
    }

    /**
     * Sets the row location of the square on the board.
     *
     * @param locateRow the row location of the square
     */
    public void setLocateRow(int locateRow) {
        this.locateRow = locateRow;
    }

    /**
     * Gets the player on the square.
     *
     * @return the player on the square
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player on the square.
     *
     * @param player the player to be placed on the square
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the square in front of the current square on the board.
     *
     * @param board the game board
     * @return the square in front of the current square
     */
    public Square frontSquare(Board board) {
        return board.getSquare(this.locateRow - 1, this.locateLine);
    }

    /**
     * Gets the square behind the current square on the board.
     *
     * @param board the game board
     * @return the square behind the current square
     */
    public Square backSquare(Board board) {
        return board.getSquare(this.locateRow + 1, this.locateLine);
    }

    /**
     * Gets the square to the left of the current square on the board.
     *
     * @param board the game board
     * @return the square to the left of the current square
     */
    public Square leftSquare(Board board) {
        return board.getSquare(this.locateRow, this.locateLine + 1);
    }

    /**
     * Gets the square to the right of the current square on the board.
     *
     * @param board the game board
     * @return the square to the right of the current square
     */
    public Square rightSquare(Board board) {
        return board.getSquare(this.locateRow, this.locateLine - 1);
    }
}
