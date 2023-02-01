package pers.luyu.simonrace.sprite.obstacles;

import pers.luyu.simonrace.sprite.Square;

/**
 * The GameButton class represents a game button in the Simon race game.
 * It extends the Obstacle class and inherits its attributes and methods.
 * In the game if the player steps on Button, the board will regenerate the obstacles randomly with the original number.
 *
 * @author Luyu
 * @see Obstacle
 */
public class GameButton extends Obstacle {
    /**
     * Constructs a GameButton object with the specified game board.
     *
     * @param board the game board
     */
    public GameButton(Square board) {
        super(board);
    }
}
