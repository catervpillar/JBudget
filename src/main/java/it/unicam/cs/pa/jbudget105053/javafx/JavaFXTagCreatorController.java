package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.model.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user create a {@link Tag}.
 *
 * @author Tommaso Catervi
 */
public class JavaFXTagCreatorController implements JavaFXEditor, JavaFXController {
    private final Controller controller;

    /**
     * The string error message for trying to create a {@link Tag} without having typed anything in
     * the {@code tagNameField} {@link TextField}.
     */
    public final String MESSAGE_WRONG_NAME = "Suvvia, scrivi qualcosa in piu' nel nome del tag!";

    /**
     * Constructs a JavaFXTagCreatorController with the given controller.
     *
     * @param controller the controller given.
     */
    public JavaFXTagCreatorController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    TextField tagNameField;
    @FXML
    TextField descriptionBox;
    @FXML
    Button abortButton;

    /**
     * Creates a new {@link Tag} via the controller deriving the necessary constructor
     * parameters from the values of {@code tagNameField} and {@code descriptionBox}.
     * Then it closes the current window.
     * This method is linked to the {@code createButton}.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            controller.addTag(controlName(), descriptionBox.getText());
            close(abortButton);
        } catch (IllegalArgumentException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Closes the current window. It is linked to the {@code abortButton}.
     */
    @Override
    @FXML
    public void abortAction() {
        close(abortButton);
    }

    /**
     * Controls that the value of the {@code tagNameField} is not ""
     * and that it is not equal to the name of another {@link Tag}
     * already existing and then returns it.
     *
     * @return the controlled value of the {@code tagNameField}.
     */
    private String controlName() {
        if (tagNameField.getText().equals(""))
            throw new IllegalArgumentException(MESSAGE_WRONG_NAME);
        return tagNameField.getText();
    }
}
