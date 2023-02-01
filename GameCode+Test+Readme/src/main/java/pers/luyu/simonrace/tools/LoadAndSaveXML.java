package pers.luyu.simonrace.tools;

import javafx.collections.ObservableList;
import org.controlsfx.dialog.Dialogs;
import pers.luyu.simonrace.sprite.RankList.ListWrapper;
import pers.luyu.simonrace.sprite.RankList.Rank;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * This class provides methods for loading and saving XML data to and from a specified file.
 * The XML data must be wrapped in a ListWrapper class and contain a list of Rank objects.
 *
 * @author Luyu
 */
public class LoadAndSaveXML {
    /**
     * This method loads data from an XML file and adds it to the provided list of Rank objects.
     * If an error occurs, a Dialog is displayed with an error message and the exception details.
     *
     * @param rankData the list of Rank objects to be loaded from the XML file
     * @param file     the XML file to be loaded from
     */
    public static void loadDataFromFile(ObservableList<Rank> rankData, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            ListWrapper wrapper = (ListWrapper) um.unmarshal(file);
            rankData.addAll(wrapper.getRanks());
        } catch (Exception e) {
            Dialogs.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + file.getPath())
                    .showException(e);
        }
    }

    /**
     * This method saves the provided list of Rank objects to an XML file.
     * If an error occurs, a Dialog is displayed with an error message and the exception details.
     *
     * @param rankData the list of Rank objects to be saved to the XML file
     * @param file     the XML file to be saved to
     */
    public static void saveDataToFile(ObservableList<Rank> rankData, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ListWrapper wrapper = new ListWrapper();
            wrapper.setRanks(rankData);
            m.marshal(wrapper, file);

        } catch (Exception e) {
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);
        }
    }
}
