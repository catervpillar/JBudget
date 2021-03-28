package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user remove a {@link Movement}.
 *
 * @author Tommaso Catervi
 */
public class JavaFXMovementRemoverController implements JavaFXEditor, JavaFXController {
    private final JavaFXTransactionCreatorController transactionController;

    /**
     * The string error message for trying to remove a {@link Movement} without having selected one
     * from the movements list of the {@link Transaction}.
     */
    public final String MESSAGE_MOVEMENT_NOT_SELECTED = "Per procedere seleziona un movimento da rimuovere!";

    /**
     * Constructs a JavaFXTagCreatorController with the given transactionController.
     *
     * @param transactionController the controller given.
     */
    public JavaFXMovementRemoverController(JavaFXTransactionCreatorController transactionController) {
        this.transactionController = transactionController;
    }

    @FXML
    ChoiceBox<Movement> movementChoiceBox = new ChoiceBox<>();
    @FXML
    Button abortButton;

    /**
     * Deletes a specific {@link Movement} via the controller deriving the necessary parameter
     * from the values of {@code movementChoiceBox}. Then it closes the current window.
     * This method is linked to the {@code removeButton}.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            transactionController.getNewTransaction().removeMovement(controlMovement());
            close(abortButton);
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
     * If the current value of the {@code movementChoiceBox} is null, it fills
     * the {@code movementChoiceBox} with all the selectable values.
     * This method is linked to the OnMouseMoved of the {@link javafx.scene.layout.VBox}
     * of the current window.
     */
    @FXML
    public void populateChoiceBoxAction() {
        if (Objects.isNull(movementChoiceBox.getValue()))
            movementChoiceBox.setItems(FXCollections.observableArrayList(transactionController.getNewTransaction().getMovements()));
    }

    /**
     * Controls that the value of the {@code movementChoiceBox} is not null
     * and returns it.
     *
     * @return the controlled value of the {@code movementChoiceBox}.
     */
    private Movement controlMovement() {
        if (Objects.isNull(movementChoiceBox.getValue()))
            throw new NullPointerException(MESSAGE_MOVEMENT_NOT_SELECTED);
        return movementChoiceBox.getValue();
    }
}
