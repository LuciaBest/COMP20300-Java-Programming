package pers.luyu.simonrace.sprite.obstacles;

import pers.luyu.simonrace.sprite.Square;

/**
 * Represents a single fence in the Simon race game. This is a type of obstacle that consists of one square,
 * which the player must navigate around in order to successfully complete the game.
 *
 * @author Luyu
 * @see Fence
 */
public class SingleFence extends Fence {
    /**
     * Constructs a single fence object with the given square as its base.
     *
     * @param square the square that serves as the base of the fence
     */
    public SingleFence(Square square) {
        super(square);
    }
}
