package pers.luyu.simonrace.sprite;

/**
 * The Player class represents a player in the Simon race game. Each player has a name, a grade, a daze time, and a total step number.
 * The player also has a location and a previous location on the game board. The class contains methods for getting and setting the player's
 * attributes, as well as methods for moving the player on the game board and accessing the player's next and last players in the game.
 *
 * @author Luyu
 */
public class Player {

    private final String name; // player's name
    private int grade; // player's grade
    private int dazeTime; // player's daze time
    private int totalStepNum; // total step number of player
    private String playerList; // list of players
    private Square location; // current location of player
    private Square preLocation; // previous location of player
    private int preDazeTime; // previous daze time of player
    private int preStepNum; // previous step number of player
    private Player lastPlayer; // last player in the list
    private Player nextPlayer; // next player in the list

    /**
     * Constructor for Player class
     *
     * @param name the name of the player
     */
    public Player(String name) {

        this.name = name;
        dazeTime = 0;
        grade = 0;
        totalStepNum = 0;
        playerList = Integer.toString(0);
        totalStepNum = 0;
    }

    /**
     * Gets the player's previous location
     *
     * @return the player's previous location
     */
    public Square getPreLocation() {
        return preLocation;
    }

    /**
     * Sets the player's previous location
     *
     * @param preLocation the previous location to set
     */
    public void setPreLocation(Square preLocation) {
        this.preLocation = preLocation;
    }

    /**
     * Gets the player's total step number
     *
     * @return the player's total step number
     */
    public int getTotalStepNum() {
        return totalStepNum;
    }

    /**
     * Sets the player's total step number
     *
     * @param totalStepNum the total step number to set
     */
    public void setTotalStepNum(int totalStepNum) {
        this.totalStepNum = totalStepNum;
    }

    /**
     * Gets the player's previous daze time
     *
     * @return the player's previous daze time
     */
    public int getPreDazeTime() {
        return preDazeTime;
    }

    /**
     * Sets the player's previous daze time
     *
     * @param preDazeTime the previous daze time to set
     */
    public void setPreDazeTime(int preDazeTime) {
        this.preDazeTime = preDazeTime;
    }

    /**
     * Gets the player list
     *
     * @return the player list
     */
    public String getPlayerList() {
        return playerList;
    }

    /**
     * Sets the player list
     *
     * @param playerList the player list to set
     */
    public void setPlayerList(String playerList) {
        this.playerList = playerList;
    }

    /**
     * Gets the player's daze time
     *
     * @return the player's daze time
     */
    public int getDazeTime() {
        return dazeTime;
    }

    /**
     * Sets the daze time for the player.
     *
     * @param dazeTime the daze time for the player
     */
    public void setDazeTime(int dazeTime) {
        this.dazeTime = dazeTime;
    }

    /**
     * Gets the pre-step number for the player.
     *
     * @return the pre-step number for the player
     */
    public int getPreStepNum() {
        return preStepNum;
    }

    /**
     * Sets the pre-step number for the player.
     *
     * @param preStepNum the pre-step number for the player
     */
    public void setPreStepNum(int preStepNum) {
        this.preStepNum = preStepNum;
    }

    /**
     * Gets the next player for the player.
     *
     * @return the next player for the player
     */
    public Player getNextPlayer() {
        return nextPlayer;
    }

    /**
     * Sets the next player for the player.
     *
     * @param nextPlayer the next player for the player
     */
    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    /**
     * Gets the last player for the player.
     *
     * @return the last player for the player
     */
    public Player getLastPlayer() {
        return lastPlayer;
    }

    /**
     * Sets the last player for the player.
     *
     * @param lastPlayer the last player for the player
     */
    public void setLastPlayer(Player lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    /**
     * Gets the grade for the player.
     *
     * @return the grade for the player
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Sets the grade for the player.
     *
     * @param grade the grade for the player
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current location of the player on the game board.
     *
     * @return the current location of the player on the game board
     */
    public Square getLocateSquare() {
        return location;
    }

    /**
     * Sets the player's location to the specified square. If the player has a previous location,
     * that location is updated to no longer have a player.
     *
     * @param square the square to set as the player's location
     */
    public void setPlayerLocation(Square square) {
        if (this.location != null) {
            location.setPlayer(null);
        }
        location = square;
        square.setPlayer(this);
    }

    /**
     * Moves the player to the specified square. If the player's current square has a box, the box's
     * images are cleared before the move. The player's location is then updated to the new square.
     *
     * @param square the square to move the player to
     */
    public void move(Square square) {
        if (this.getLocateSquare().getLocateBox() != null) {
            this.getLocateSquare().getLocateBox().getChildren().clear();//clear the pictures before
        }
        this.setPlayerLocation(square);
    }

}
