package pers.luyu.simonrace.sprite.RankList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pers.luyu.simonrace.sprite.Players;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RankTest {

    ObservableList<Rank> rankData = FXCollections.observableArrayList();
    private Rank[] originRanks;

    @Test
    void updateGradeRank() {
        Rank rank = new Rank("null", "null", "null");
        Players players = new Players(new String[]{"test"}, new Rank[]{rank});
        players.setWinner(players.getPlayer(0));
        players.getWinner().setGrade(1);
        Rank.updateGradeRank(players, new Rank[]{rank}, rankData);
        Rank[] ranks = rankData.toArray(new Rank[0]);
        assertEquals("test", ranks[0].getName());

        players.getWinner().setGrade(1);
        Rank.updateGradeRank(players, ranks, rankData);
        ranks = rankData.toArray(new Rank[0]);
        assertEquals("2", ranks[0].getOther());

        Players players2 = new Players(new String[]{"test2"}, ranks);
        players2.setWinner(players2.getPlayer(0));
        players2.getWinner().setGrade(3);
        Rank.updateGradeRank(players2, ranks, rankData);
        assertEquals("2", ranks[0].getOther());

        rankData.add(new Rank("3", "test3", "3"));
        rankData.add(new Rank("4", "test4", "4"));
        rankData.add(new Rank("5", "test5", "5"));
        rankData.add(new Rank("6", "test6", "6"));
        rankData.add(new Rank("7", "test7", "7"));
        rankData.add(new Rank("8", "test8", "8"));
        rankData.add(new Rank("9", "test9", "9"));
        rankData.add(new Rank("10", "test10", "10"));
        ranks = rankData.toArray(new Rank[0]);

        Players finalPlayers = new Players(new String[]{"final"}, ranks);
        finalPlayers.setWinner(finalPlayers.getPlayer(0));
        finalPlayers.getWinner().setGrade(300);
        Rank.updateGradeRank(finalPlayers, ranks, rankData);
        assertEquals("300", ranks[0].getOther());


        Players zeroPlayers = new Players(new String[]{"zero"}, ranks);
        zeroPlayers.setWinner(zeroPlayers.getPlayer(0));
        zeroPlayers.getWinner().setGrade(0);
        Rank.updateGradeRank(zeroPlayers, ranks, rankData);
        assertEquals("300", ranks[0].getOther());
        Assertions.assertNotEquals("zero", ranks[9].getName());
    }


    @Test
    void updateStepRank() {
        Rank rank = new Rank("null", "null", "null");
        Players players = new Players(new String[]{"test"}, new Rank[]{rank});
        players.setWinner(players.getPlayer(0));
        players.getWinner().setTotalStepNum(1);
        Rank.updateStepRank(players, new Rank[]{rank}, rankData);
        Rank[] ranks = rankData.toArray(new Rank[0]);
        assertEquals("test", ranks[0].getName());

        Players players2 = new Players(new String[]{"test2"}, ranks);
        players2.setWinner(players2.getPlayer(0));
        players2.getWinner().setTotalStepNum(3);
        Rank.updateStepRank(players2, ranks, rankData);
        ranks = rankData.toArray(new Rank[0]);
        assertEquals("3", ranks[1].getOther());

        rankData.add(new Rank("3", "test3", "3"));
        rankData.add(new Rank("4", "test4", "4"));
        rankData.add(new Rank("5", "test5", "5"));
        rankData.add(new Rank("6", "test6", "7"));
        rankData.add(new Rank("7", "test7", "7"));
        rankData.add(new Rank("8", "test8", "7"));
        rankData.add(new Rank("9", "test9", "7"));
        rankData.add(new Rank("10", "test10", "7"));
        ranks = rankData.toArray(new Rank[0]);
        Players finalPlayers = new Players(new String[]{"final"}, ranks);
        finalPlayers.setWinner(finalPlayers.getPlayer(0));
        finalPlayers.getWinner().setTotalStepNum(6);
        Rank.updateStepRank(finalPlayers, ranks, rankData);
        assertEquals("6", ranks[5].getOther());


        Players zeroPlayers = new Players(new String[]{"zero"}, ranks);
        zeroPlayers.setWinner(zeroPlayers.getPlayer(0));
        zeroPlayers.getWinner().setTotalStepNum(8);
        Rank.updateStepRank(zeroPlayers, ranks, rankData);
        assertEquals("7", ranks[9].getOther());
        assertEquals("10", ranks[9].getList());
        Assertions.assertNotEquals("zero", ranks[9].getName());
    }

    @Test
    void setList() {
        Rank rank = new Rank("1", "Feng", "3");
        rank.setList("3");
        assertEquals("3", rank.getList());
    }

    @Test
    void listProperty() {
        Rank rank = new Rank("1", "Luyu", "3");
        assertEquals("1", rank.listProperty().getValue());
    }

    @Test
    void setName() {
        Rank rank = new Rank("1", "Feng", "3");
        rank.setName("Luyu");
        assertEquals("Luyu", rank.getName());
    }

    @Test
    void nameProperty() {
        Rank rank = new Rank("1", "Luyu", "3");
        assertEquals("Luyu", rank.nameProperty().getValue());
    }
}