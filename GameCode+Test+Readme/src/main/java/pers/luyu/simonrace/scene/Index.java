package pers.luyu.simonrace.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pers.luyu.simonrace.controller.IndexController;

import java.io.IOException;

/**
 * The Index class is responsible for loading the index scene, which is the main menu of the SimonRace game.
 * It contains the FXMLLoader and IndexController objects, as well as the AnchorPane that represents the index view.
 *
 * @author Luyu
 */
public class Index {
    /**
     * The controller for the index view.
     */
    private static IndexController controller;
    /**
     * The AnchorPane for the index view.
     */
    private static AnchorPane IndexOverview;

    /**
     * This method loads the index scene, using the index.fxml file as the view, and sets it as the root node of the stage.
     *
     * @param stage The stage object that will be used to display the index scene.
     * @throws IOException If the index.fxml file cannot be found or loaded.
     */
    public static void load(Stage stage) throws IOException {
        try {
            FXMLLoader indexLoader = new FXMLLoader();
            indexLoader.setLocation(Index.class.getResource("/fxml/index.fxml"));
            IndexOverview = indexLoader.load();
            controller = indexLoader.getController();
            Parent root = IndexOverview;
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the IndexController object associated with the index scene.
     *
     * @return The IndexController object.
     */
    public static IndexController returnIndexViewController() {
        return controller;
    }
}
