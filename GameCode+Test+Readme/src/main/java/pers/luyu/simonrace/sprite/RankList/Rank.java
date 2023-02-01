package pers.luyu.simonrace.sprite.RankList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import pers.luyu.simonrace.sprite.Players;
import pers.luyu.simonrace.tools.Sort;

import java.util.Arrays;

/**
 * The `Rank` class represents a ranking entry in a leaderboard.
 * Each `Rank` object contains three pieces of information:
 * - A list number (1-10)
 * - A player name
 * - A player score or step count, depending on the type of leaderboard
 * The `Rank` class also provides static methods for updating the leaderboard
 * based on the results of a game.
 *
 * @author Luyu
 */
public class Rank {
    private final StringProperty list;
    private final StringProperty name;
    private final StringProperty other;

    /**
     * Constructor for Rank that sets the list, name, and other values to null.
     */
    public Rank() {
        this(null, null, null);
    }

    /**
     * Constructor for Rank that sets the list, name, and other values based on the given parameters.
     *
     * @param list  the list value to set
     * @param name  the name value to set
     * @param other the other value to set
     */
    public Rank(String list, String name, String other) {
        this.list = new SimpleStringProperty(list);
        this.name = new SimpleStringProperty(name);
        this.other = new SimpleStringProperty(other);
    }

    /**
     * Updates the given grade ranks based on the players and their data.
     *
     * @param players  the players data to use for the update
     * @param ranks    the ranks to update
     * @param rankData the list of ranks to update
     */
    public static void updateGradeRank(Players players, Rank[] ranks, ObservableList<Rank> rankData) {
        boolean isChange = true;
        boolean added = false;
        if (ranks[0].otherProperty().getValue().equals("null")) {
            ranks = new Rank[1];
            ranks[0] = new Rank("1", players.getWinner().getName(), Integer.toString(players.getWinner().getGrade()));
        } else {
            for (Rank rank : ranks) {
                if (players.getWinner().getName().equals(rank.getName())) {
                    rank.setOther(String.valueOf(players.getWinner().getGrade() + 1));
                    added = true;
                }
            }
            if (!added) {
                if (ranks.length < 10) {
                    Rank[] temp = new Rank[ranks.length + 1];
                    System.arraycopy(ranks, 0, temp, 0, ranks.length);
                    temp[ranks.length] = new Rank(Integer.toString(temp.length), players.getWinner().getName(), Integer.toString(players.getWinner().getGrade()));
                    ranks = temp;

                } else {
                    if (players.getWinner().getGrade() > Integer.parseInt(ranks[9].getOther())) {
                        ranks[9] = new Rank("10", players.getWinner().getName(), Integer.toString(players.getWinner().getGrade()));
                    } else {
                        isChange = false;
                    }
                }
            }
        }
        if (isChange) {
            Sort.selectionSort(ranks, false);
            rankData.clear();
            rankData.addAll(Arrays.asList(ranks));
        }
    }

    /**
     * Updates the given step ranks based on the players and their data.
     *
     * @param players  the players data to use for the update
     * @param ranks    the ranks to update
     * @param rankData the list of ranks to update
     */
    public static void updateStepRank(Players players, Rank[] ranks, ObservableList<Rank> rankData) {
        boolean isChange = true;
        if (ranks[0].otherProperty().getValue().equals("null")) {
            ranks = new Rank[1];
            ranks[0] = new Rank("1", players.getWinner().getName(), Integer.toString(players.getWinner().getTotalStepNum()));
        } else if (ranks.length < 10) {
            Rank[] temp = new Rank[ranks.length + 1];
            System.arraycopy(ranks, 0, temp, 0, ranks.length);
            temp[ranks.length] = new Rank(Integer.toString(temp.length), players.getWinner().getName(), Integer.toString(players.getWinner().getTotalStepNum()));
            ranks = temp;
        } else {
            if (players.getWinner().getTotalStepNum() < Integer.parseInt(ranks[9].getOther())) {
                ranks[9] = new Rank("10", players.getWinner().getName(), Integer.toString(players.getWinner().getTotalStepNum()));
            } else {
                isChange = false;
            }

        }
        if (isChange) {
            Sort.selectionSort(ranks, true);
            for (int i=0; i< ranks.length ; i++){
                ranks[i].setList(String.valueOf(i+1));
            }
            rankData.clear();
            rankData.addAll(Arrays.asList(ranks));
        }
    }

    /**
     * Returns the value of the list property.
     *
     * @return a String containing the value of the list property
     */
    public String getList() {
        return list.get();
    }

    /**
     * Sets the value of the list property.
     *
     * @param list a String containing the new value for the list property
     */
    public void setList(String list) {
        this.list.set(list);
    }

    /**
     * Returns the list property.
     *
     * @return a StringProperty representing the list property
     */
    public StringProperty listProperty() {
        return list;
    }

    /**
     * Returns the value of the name property.
     *
     * @return a String containing the value of the name property
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets the value of the name property.
     *
     * @param name a String containing the new value for the name property
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Returns the name property.
     *
     * @return a StringProperty representing the name property
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Returns the value of the other property.
     *
     * @return a String containing the value of the other property
     */
    public String getOther() {
        return other.get();
    }

    /**
     * Sets the value of the other property.
     *
     * @param other a String containing the new value for the other property
     */
    public void setOther(String other) {
        this.other.set(other);
    }

    /**
     * Returns the other property.
     *
     * @return a StringProperty representing the other property
     */
    public StringProperty otherProperty() {
        return other;
    }


}
