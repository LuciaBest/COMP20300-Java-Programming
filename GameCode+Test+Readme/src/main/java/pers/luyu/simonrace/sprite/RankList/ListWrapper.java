package pers.luyu.simonrace.sprite.RankList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The ListWrapper class is used for storing and managing a list of Rank objects.
 * It is annotated with JAXB annotations for XML serialization and deserialization.
 *
 * @author Luyu
 */

@XmlRootElement(name = "rank")
public class ListWrapper {
    private List<Rank> ranks;

    /**
     * Gets the list of Rank objects.
     *
     * @return the list of Rank objects
     */
    @XmlElement(name = "rank")
    public List<Rank> getRanks() {
        return ranks;
    }

    /**
     * Sets the list of Rank objects.
     *
     * @param ranks the list of Rank objects
     */
    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }
}