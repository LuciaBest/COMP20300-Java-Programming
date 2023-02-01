package pers.luyu.simonrace.sprite.obstacles;

import pers.luyu.simonrace.sprite.Square;

/**
 * This class represents an obstacle in the Simon Race game.
 * An obstacle can occupy one or two squares in the game.
 *
 * @author Luyu
 */

public class Obstacle {

    private Square Location;
    private Square secLocation;

    /**
     * Constructor for creating an obstacle that occupies one square.
     *
     * @param square The square that the obstacle will occupy.
     */
    public Obstacle(Square square) {
        this.Location = square;
        square.setObstacle(this);
    }

    /**
     * Constructor for creating an obstacle that occupies two squares.
     *
     * @param firSquare The first square that the obstacle will occupy.
     * @param secSquare The second square that the obstacle will occupy.
     */
    public Obstacle(Square firSquare, Square secSquare) {
        this.Location = firSquare;
        this.secLocation = secSquare;
        firSquare.setObstacle(this);
        secSquare.setObstacle(this);
    }

    /**
     * Returns the first square that the obstacle occupies.
     *
     * @return The first square that the obstacle occupies.
     */
    public Square getLocation() {
        return Location;
    }

    /**
     * Sets the first square that the obstacle occupies.
     *
     * @param location The square to be set as the first location of the obstacle.
     */
    public void setLocation(Square location) {
        Location = location;
    }

    /**
     * Returns the second square that the obstacle occupies.
     * If the obstacle only occupies one square, this method will return null.
     *
     * @return The second square that the obstacle occupies, or null if it only occupies one square.
     */
    public Square getSecLocation() {
        return secLocation;
    }

    /**
     * Sets the second square that the obstacle occupies.
     *
     * @param secLocation The square to be set as the second location of the obstacle.
     */
    public void setSecLocation(Square secLocation) {
        this.secLocation = secLocation;
    }
}
