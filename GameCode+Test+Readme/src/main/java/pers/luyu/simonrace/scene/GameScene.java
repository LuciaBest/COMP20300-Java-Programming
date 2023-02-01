package pers.luyu.simonrace.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pers.luyu.simonrace.controller.GameViewController;

import java.io.IOException;

/**
 * The GameScene class is responsible for loading and displaying the game view on the stage.
 * It also provides a method to access the game view controller.
 *
 * @author Luyu
 */
public class GameScene {
    /**
     * The controller for the game view.
     */
    static GameViewController controller;
    /**
     * The AnchorPane for the game view.
     */
    static AnchorPane GameOverview;

    /**
     * Loads the game view onto the given stage.
     *
     * @param stage The stage on which the game view will be displayed.
     * @throws IOException If there is an error loading the game view.
     */
    public static void load(Stage stage) throws IOException {
        try {
            FXMLLoader gameLoader = new FXMLLoader();
            gameLoader.setLocation(Index.class.getResource("/fxml/gameView.fxml"));
            GameOverview = gameLoader.load();
            controller = gameLoader.getController();
            Parent root = GameOverview;
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the controller for the game view.
     *
     * @return The game view controller.
     */
    public static GameViewController returnGameViewController() {
        return controller;
    }
}
