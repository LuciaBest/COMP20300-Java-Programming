package pers.luyu.simonrace.sprite;

import pers.luyu.simonrace.sprite.obstacles.DoubleFence;
import pers.luyu.simonrace.sprite.obstacles.Obstacle;

/**
 * This class represents a board in a game. It contains information about the number of rows and lines, as well as an array of squares.
 * The board also has information about the obstacles on the board and the number of obstacles.
 *
 * @author Luyu
 */
public class Board {

    private final int row;// number of rows on the board
    private final int line;// number of lines on the board
    private final Square[][] board;// array of squares on the board
    private final int[] obstaclesNum;// number of obstacles on the board
    private Obstacles obstacles;// obstacles on the board
    private Square[][] holesBackup;
    private Square[][] ButtonBackup;
    private Square[][] singleFencesBackup;
    private Square[][] portalsBackup;
    private Square[][] doubleFencesBackup;

    /**
     * Constructor for the board.
     *
     * @param row          number of rows on the board
     * @param line         number of lines on the board
     * @param obstaclesNum number of obstacles on the board
     */
    public Board(int row, int line, int[] obstaclesNum) {
        this.obstaclesNum = obstaclesNum;
        this.row = row;
        this.line = line;
        board = new Square[row][line];
        // initialize the squares on the board
        for (int r = 0; r < row; r++) {
            for (int l = 0; l < line; l++) {
                if (r == row - 1) {
                    board[r][l] = new Square(true);
                } else if (r == 0) {
                    board[r][l] = new Square(false);
                } else {
                    board[r][l] = new Square();
                }
                board[r][l].setLocateLine(l);
                board[r][l].setLocateRow(r);
            }
        }
        // initialize the obstacles on the board
        //this.setObstacles(new Obstacles(this, obstaclesNum));

        obstacles = new Obstacles(this, new int[]{0,0,0,line,0});
        this.setObstacles(obstacles);
        for (int i = 0; i<line;i++){
            obstacles.getSingleFences()[i].getLocation().setObstacle(null);
            obstacles.getSingleFences()[i].setLocation(this.getSquare(0,i));
            this.getSquare(0,i).setObstacle(obstacles.getSingleFences()[i]);
        }

    }


    /**
     * Get the obstacles on the board.
     *
     * @return obstacles on the board
     */
    public Obstacles getObstacles() {
        return obstacles;
    }

    /**
     * Set the obstacles on the board.
     *
     * @param obstacles obstacles on the board
     */
    public void setObstacles(Obstacles obstacles) {
        this.obstacles = obstacles;
    }

    /**
     * Get the number of obstacles on the board.
     *
     * @return number of obstacles on the board
     */
    public int[] getObstaclesNum() {
        return obstaclesNum;
    }

    /**
     * Get a square at a specific row and line.
     *
     * @param row  row of the square
     * @param line line of the square
     * @return the square at the specified location
     */
    public Square getSquare(int row, int line) {
        return board[row][line];
    }

    /**
     * Get the number of rows on the board.
     *
     * @return number of rows on the board
     */
    public int getRowNum() {
        return row;
    }

    /**
     * Get the number of lines on the board.
     *
     * @return number of lines on the board
     */
    public int getLineNum() {
        return line;
    }

    /**
     * This method removes the original obstacles for the game and randomly generates new ones.
     *
     * @param obstaclesNum An array containing the numbers of each type of obstacle to be set.
     */
    public void setNewObstacles(int[] obstaclesNum) {
        for (Obstacle[] cells : obstacles.getObstacles()) {
            for (Obstacle cell : cells) {
                if (cell.getLocation().getLocateBox() != null) {
                    cell.getLocation().getLocateBox().getChildren().clear();
                }
                cell.getLocation().setObstacle(null);
                cell.setLocation(null);
                if (cell instanceof DoubleFence) {
                    if (cell.getSecLocation().getLocateBox() != null) {
                        cell.getSecLocation().getLocateBox().getChildren().clear();
                    }
                    cell.getSecLocation().setObstacle(null);
                    cell.setSecLocation(null);
                }
            }
        }
        obstacles = null;
        obstacles = new Obstacles(this, obstaclesNum);
    }

    public void setNewObstacles() {
        for (Obstacle[] cells : obstacles.getObstacles()) {
            for (Obstacle cell : cells) {
                if (cell.getLocation().getLocateBox() != null) {
                    cell.getLocation().getLocateBox().getChildren().clear();
                }
                cell.getLocation().setObstacle(null);
                cell.setLocation(null);
                if (cell instanceof DoubleFence) {
                    if (cell.getSecLocation().getLocateBox() != null) {
                        cell.getSecLocation().getLocateBox().getChildren().clear();
                    }
                    cell.getSecLocation().setObstacle(null);
                    cell.setSecLocation(null);
                }
            }
        }
        obstacles = null;
        obstacles = new Obstacles(holesBackup, ButtonBackup, singleFencesBackup, portalsBackup, doubleFencesBackup);
    }

    public Square[][] backUpObstacles(Obstacle[] obstacles){
        Square[][] obstaclesLocation = new Square[obstacles.length][2];
        for (int i = 0;i<obstacles.length;i++){
            obstaclesLocation[i][0] = obstacles[i].getLocation();
            if (obstacles[i].getSecLocation()!=null){
                obstaclesLocation[i][1] = obstacles[i].getSecLocation();
            }
        }
        return obstaclesLocation;
    }

    public void backUpAllObstacles(){
        holesBackup = this.backUpObstacles(this.getObstacles().getHoles());
        ButtonBackup = this.backUpObstacles(this.getObstacles().getButtons());
        singleFencesBackup = this.backUpObstacles(this.getObstacles().getSingleFences());
        portalsBackup = this.backUpObstacles(this.getObstacles().getPortals());
        doubleFencesBackup = this.backUpObstacles(this.getObstacles().getDoubleFences());
    }



}
