package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.model.Tag;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user modify an existing {@link Tag}.
 *
 * @author Tommaso Catervi
 */
public class JavaFXTagModifierController implements JavaFXEditor, JavaFXController {
    private final Controller controller;

    /**
     * Constructs a JavaFXTagModifierController with the given controller.
     *
     * @param controller the controller given.
     */
    public JavaFXTagModifierController(Controller controller) {
        this.controller = controller;
    }

    /**
     * The string error message for trying to remove a {@link Tag} without having selected anything
     * from the {@code tagChoiceBox}.
     */
    public final String MESSAGE_TAG_NOT_SELECTED = "Per procedere seleziona un tag da modificare!";

    /**
     * The string error message for trying to change the name of a {@link Tag} without having typed
     * anything in the {@code newNameTextField}.
     */
    public final String MESSAGE_INVALID_NAME = "Il nuovo nome del tag non puo' essere vuoto!";

    @FXML
    ChoiceBox<Tag> tagChoiceBox = new ChoiceBox<>();

    @FXML
    TextField newNameTextField;
    @FXML
    TextArea newDescriptionTextArea;

    @FXML
    Button abortButton;

    /**
     * Modifies a specific {@link Tag} via the controller. Then it closes the current window.
     * This method is linked to the {@code modifyButton}.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            controller.modifyTag(controlTag(), controlName(), newDescriptionTextArea.getText());
            abortAction();
        } catch (NullPointerException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Closes the current window. It is linked to the {@code abortButton}.
     */
    @Override
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
        else {
            newNameTextField.setPromptText(tagChoiceBox.getValue().getName());
            newDescriptionTextArea.setPromptText(tagChoiceBox.getValue().getDescription());
        }
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

    /**
     * Controls that the value of the {@code newNameTextField} is not ""
     * and that it is not null and then returns it.
     *
     * @return the controlled value of the {@code tagNameField}.
     */
    private String controlName() {
        if (newNameTextField.getText().equals(""))
            throw new NullPointerException(MESSAGE_INVALID_NAME);
        return newNameTextField.getText();
    }
}
