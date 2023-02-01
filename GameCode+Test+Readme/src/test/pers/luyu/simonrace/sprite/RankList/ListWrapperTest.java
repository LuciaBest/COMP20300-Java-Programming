package pers.luyu.simonrace.sprite.RankList;

import org.junit.Test;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ListWrapperTest {

    @Test
    public void testListWrapper() {
        ListWrapper wrapper = new ListWrapper();
        List<Rank> ranks = Arrays.asList(
                new Rank("1", "Alice", "10"),
                new Rank("2", "Bob", "9"),
                new Rank("3", "Charlie", "8")
        );
        wrapper.setRanks(ranks);

        assertEquals(ranks, wrapper.getRanks());
    }
}