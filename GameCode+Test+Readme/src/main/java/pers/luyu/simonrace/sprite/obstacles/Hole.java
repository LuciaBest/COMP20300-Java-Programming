package pers.luyu.simonrace.sprite.obstacles;

import pers.luyu.simonrace.sprite.Square;

/**
 * The Hole class extends from the Obstacle class and represents a hole on the game board.
 * In the game if the player steps on Hole, the player will suspend action for 1 round.
 *
 * @author Luyu
 * @see Obstacle
 */
public class Hole extends Obstacle {
    /**
     * Constructs a Hole object with the given square as its base.
     *
     * @param square the square representing the location and size of the hole
     */
    public Hole(Square square) {
        super(square);
    }
}
