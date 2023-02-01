package pers.luyu.simonrace;

import javafx.application.Application;
import javafx.stage.Stage;
import pers.luyu.simonrace.controller.GameViewController;
import pers.luyu.simonrace.controller.IndexController;

import java.io.IOException;

/**
 * Main class extends the Application class and overrides the start method.
 * The main method calls the launch method of the Application class.
 *
 * @author Luyu
 */
public class Main extends Application {

    static Stage stage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    public static GameViewController getGameViewController() throws IOException {
        return Director.getInstance().toGame();
    }

    /**
     * This method is used to initialize the primary stage of the application.
     * It calls the singleton instance of the Director class and invokes its init() method
     * with the primary stage as a parameter.
     *
     * @param primaryStage the primary stage of the application
     * @throws Exception if any error occurs during initialization
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Director.getInstance().init(primaryStage);
        stage = primaryStage;
    }

    public static IndexController getIndexController() throws IOException {
        return Director.getInstance().toIndex();
    }
}