package it.unicam.cs.pa.jbudget105053.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class implements the interface {@link Application} which is the entry point for
 * JavaFX applications and has the responsibility to start the main Application window
 * and then waits for the application to finish.
 *
 * @author Tommaso Catervi
 */
public class JavaFXJBudget extends Application {

    /**
     * Starts the main application settings its title, resolution and icon.
     *
     * @param stage the primary stage for this application.
     * @throws IOException if something goes wrong
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/jbudget.fxml"));

        stage.setTitle("JBudget 1.0.1");
        stage.setScene(new Scene(root, 1060, 636));
        stage.getIcons().add(new Image("/images/jbudget_icon.png"));
        stage.show();
    }
}
