package pers.luyu.simonrace.tools;

import java.util.Random;

/**
 * This class generates a random integer within a specified range using the
 * java.util.Random class.
 *
 * @author Luyu
 */
public class RandomOutput {
    /**
     * Generates a random integer within a specified range.
     *
     * @param min the minimum value of the desired range (inclusive)
     * @param max the maximum value of the desired range (inclusive)
     * @return a random integer within the specified range
     */
    public static int randomInt(int min, int max) {
        Random r = new Random();
        int i = r.nextInt(max - min + 1) + min;
        return i;
    }
}
