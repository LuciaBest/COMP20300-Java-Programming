package pers.luyu.simonrace.sprite.obstacles;

import pers.luyu.simonrace.sprite.Square;

/**
 * This class represents an abstract fence obstacle in the Simon Race game. It extends the Obstacle class.
 * In order to successfully complete the game, players must navigate through these areas.
 *
 * @author Luyu
 * @see Obstacle
 */
public abstract class Fence extends Obstacle {
    /**
     * Constructs a fence obstacle with a single square.
     *
     * @param square The square representing the fence obstacle.
     */
    public Fence(Square square) {
        super(square);
    }

    /**
     * Constructs a fence obstacle with two squares.
     *
     * @param firSquare The first square representing the fence obstacle.
     * @param secSquare The second square representing the fence obstacle.
     */
    public Fence(Square firSquare, Square secSquare) {
        super(firSquare, secSquare);
    }
}
