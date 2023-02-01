package pers.luyu.simonrace.sprite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pers.luyu.simonrace.sprite.RankList.Rank;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayersTest {

    private Players players;

    @BeforeEach
    void setUp() {
        String[] playersName = new String[]{"Luyu", "Feng", "Test"};
        Rank[] gradeRanks = new Rank[]{new Rank("1", "Luyu", "1")};
        players = new Players(playersName, gradeRanks);
    }


    @Test
    void setGrade() {
        Rank[] gradeRanks = new Rank[]{new Rank("1", "Luyu", "1")};
        players.setGrade(players.getPlayer(0), gradeRanks);
        players.setGrade(players.getPlayer(1), gradeRanks);
        assertEquals(1, players.getPlayer(0).getGrade());
        assertEquals(0, players.getPlayer(1).getGrade());
    }

    @Test
    void getPlayersNum() {
        assertEquals(3, players.getPlayersNum());
    }
}