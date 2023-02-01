package pers.luyu.simonrace.sprite;

import pers.luyu.simonrace.sprite.RankList.Rank;

/**
 * The Players class represents a group of players in a game.
 * It maintains a list of players and their properties, such as name and grade.
 * It also keeps track of the current player and the winner of the game.
 *
 * @author Luyu
 */
public class Players {
    private final Player[] players;//array of Player objects representing all players in the game
    private final int playersNum;//number of players in the game
    private final Player[] activePlayers = new Player[2];//An array representing the player objects that were active in the game during the last round.
    private Player currentPlayer;//current Player object
    private Player winner;//Player object representing the winner of the game

    /**
     * Constructor for Players class with a given list of player names and grade ranks.
     * Initializes the players array with Player objects and sets their properties.
     *
     * @param playersName a String array of player names
     * @param gradeRanks  a Rank array of grade ranks for each player
     */
    public Players(String[] playersName, Rank[] gradeRanks) {
        playersNum = playersName.length;// count how many players
        players = new Player[playersNum];

        for (int i = 0; i < playersNum; i++) {//Instantiate each player one by one
            players[i] = new Player(playersName[i]);
            players[i].setPlayerList(String.format("Player %d", i + 1));
            if (gradeRanks != null && gradeRanks[0].otherProperty() != null) {
                this.setGrade(players[i], gradeRanks);
            }
            if (playersNum > 1) {
                if (i == playersNum - 1) {//use array to define last and next players
                    players[i - 1].setNextPlayer(players[i]);
                    players[i].setLastPlayer(players[i - 1]);
                    players[i].setNextPlayer(players[0]);
                    players[0].setLastPlayer(players[i]);
                    players[0].setNextPlayer(players[1]);
                } else if (i != 0) {
                    players[i - 1].setNextPlayer(players[i]);
                    players[i].setLastPlayer(players[i - 1]);
                }
            }
        }
        currentPlayer = players[0];//When initialing players array, the first one is the current player
    }

    /**
     * Gets the winner of the game.
     *
     * @return The winner of the game
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Sets the winner of the game.
     *
     * @param winner The winner of the game
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Set a player's grade if that player's name is already in Rank.
     *
     * @param player     The player whose grade will be set
     * @param gradeRanks The array of ranks
     */
    public void setGrade(Player player, Rank[] gradeRanks) {
        for (int i = 0; i < gradeRanks.length; i++) {
            //If the player's name in this game is the same as that of one's in the rank list,
            // assign the grade of this game to this player in the rank list.
            if (player.getName().equals(gradeRanks[i].getName())) {
                player.setGrade(Integer.parseInt(gradeRanks[i].getOther()));
                return;
            }
        }
    }

    /**
     * Gets the array of active players.
     *
     * @return The array of active players
     */
    public Player[] getActivePlayers() {
        return activePlayers;
    }

    /**
     * Sets the first active player.
     *
     * @param activePlayer The first active player
     */
    public void setFirActivePlayers(Player activePlayer) {
        activePlayers[0] = activePlayer;
    }

    /**
     * Sets the second active player.
     *
     * @param activePlayer The second active player
     */
    public void setSecActivePlayers(Player activePlayer) {
        activePlayers[1] = activePlayer;
    }

    /**
     * Returns the player at the specified index.
     *
     * @param i The index of the player to return
     * @return The player at the specified index
     */
    public Player getPlayer(int i) {
        return players[i];
    }

    /**
     * Returns the array of players in the game.
     *
     * @return the array of players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Returns the current player in the game.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player in the game.
     *
     * @param currentPlayer the player to set as the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns the number of players in the game.
     *
     * @return the number of players
     */
    public int getPlayersNum() {
        return playersNum;
    }

    /**
     * Changes the location of two players in the game.
     *
     * @param playerA the first player to change location
     * @param playerB the second player to change location
     */
    public void changePlayersLocation(Player playerA, Player playerB) {
        playerA.getLocateSquare().setPlayer(playerB);
        playerB.getLocateSquare().setPlayer(playerA);
        Square temp = playerA.getLocateSquare();
        playerA.setPlayerLocation(playerB.getLocateSquare());
        playerB.setPlayerLocation(temp);
        playerA.getLocateSquare().setPlayer(playerA);
    }
}
