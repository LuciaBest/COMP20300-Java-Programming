package pers.luyu.simonrace.tools;

import pers.luyu.simonrace.sprite.RankList.Rank;

/**
 * This class contains a method for sorting an array of objects of the Rank class.
 * The sorting is done using the selection sort algorithm.
 *
 * @author Luyu
 */
public class Sort {
    /**
     * This method sorts an array of Rank objects in ascending or descending order based on the boolean value passed to it.
     *
     * @param rank      The array of Rank objects to be sorted.
     * @param bigToSmall A boolean value indicating whether the array should be sorted in ascending (false) or descending (true) order.
     */
    public static void selectionSort(Rank[] rank, boolean bigToSmall) {
        if (rank.length == 0)
            return;
        for (int i = 0; i < rank.length; i++) {
            int minIndex = i;
            for (int j = i; j < rank.length; j++) {
                if (bigToSmall) {
                    if (Integer.parseInt(rank[j].getOther()) < Integer.parseInt(rank[minIndex].getOther()))
                        minIndex = j;
                } else {
                    if (Integer.parseInt(rank[j].getOther()) > Integer.parseInt(rank[minIndex].getOther()))
                        minIndex = j;
                }

            }
            Rank temp = rank[minIndex];
            rank[minIndex] = rank[i];
            rank[i] = temp;
        }
    }
}
