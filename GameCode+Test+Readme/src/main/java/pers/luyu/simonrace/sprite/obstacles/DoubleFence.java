package pers.luyu.simonrace.sprite.obstacles;

import pers.luyu.simonrace.sprite.Square;

/**
 * Represents a double fence in the Simon race game. This is a type of obstacle that consists of two squares,
 * which the player must navigate around in order to successfully complete the game.
 *
 * @author Luyu
 * @see Fence
 */
public class DoubleFence extends Fence {
    /**
     * Constructs a new double fence with the given squares.
     *
     * @param firSquare the first square of the double fence
     * @param secSquare the second square of the double fence
     */
    public DoubleFence(Square firSquare, Square secSquare) {
        super(firSquare, secSquare);
    }
}
