package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.model.Tag;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user remove an already existing {@link Tag}.
 *
 * @author Tommaso Catervi
 */
public class JavaFXTagRemoverController implements JavaFXEditor, JavaFXController {
    private final Controller controller;

    /**
     * The string error message for trying to remove a {@link Tag} without having selected anything
     * from the {@code tagChoiceBox}.
     */
    public final String MESSAGE_TAG_NOT_SELECTED = "Per procedere seleziona un tag da rimuovere!";

    /**
     * Constructs a JavaFXTagRemoverController with the given controller.
     *
     * @param controller the controller given.
     */
    public JavaFXTagRemoverController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    ChoiceBox<Tag> tagChoiceBox = new ChoiceBox<>();

    @FXML
    Button abortButton;

    /**
     * Removes a specific {@link Tag} via the controller deriving the necessary parameter
     * from the values of {@code tagChoiceBox}. Then it closes the current window.
     * This method is linked to the {@code removeButton}.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            controller.removeTag(controlTag());
            abortAction();
        } catch (NullPointerException e) {
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
     * If the current value of the {@code tagChoiceBox} is null, it fills
     * the {@code tagChoiceBox} with all the selectable values.
     * This method is linked to the OnMouseMoved of the {@link javafx.scene.layout.VBox}
     * of the current window.
     */
    @FXML
    public void populateChoiceBoxAction() {
        if (Objects.isNull(tagChoiceBox.getValue()))
            tagChoiceBox.setItems(FXCollections.observableArrayList(controller.getTags()));
    }

    /**
     * Controls that the value of the {@code tagChoiceBox} is not null
     * and then returns it.
     *
     * @return the controlled value of the {@code tagChoiceBox}.
     */
    private Tag controlTag() {
        if (Objects.isNull(tagChoiceBox.getValue()))
            throw new NullPointerException(MESSAGE_TAG_NOT_SELECTED);
        return tagChoiceBox.getValue();
    }
}
