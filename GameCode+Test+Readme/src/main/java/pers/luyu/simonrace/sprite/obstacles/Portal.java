package pers.luyu.simonrace.sprite.obstacles;

import pers.luyu.simonrace.sprite.Square;

/**
 * This class represents a Portal obstacle in the SimonRace game.
 * It extends the Obstacle class and takes in a Square object as its parameter.
 * In the game a player steps on Portal and will exchange positions with another player at random.
 *
 * @author Luyu
 * @see Obstacle
 */
public class Portal extends Obstacle {
    /**
     * Constructs a new Portal object and sets its Square object.
     *
     * @param square The Square object for the Portal obstacle.
     */
    public Portal(Square square) {
        super(square);
    }
}
