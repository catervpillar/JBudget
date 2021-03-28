package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.model.Transaction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user remove an already existing {@link Transaction}.
 *
 * @author Tommaso Catervi
 */
public class JavaFXTransactionRemoverController implements JavaFXEditor, JavaFXController {
    private final Controller controller;

    /**
     * The string error message for trying to remove a {@link Transaction} without having selected anything
     * from the {@code transactionChoiceBox}.
     */
    public final String MESSAGE_TRANSACTION_NOT_SELECTED = "Per procedere seleziona una transazione da rimuovere!";

    /**
     * Constructs a JavaFXTransactionRemoverController with the given controller.
     *
     * @param controller the controller given.
     */
    public JavaFXTransactionRemoverController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    ChoiceBox<Transaction> transactionChoiceBox;
    @FXML
    Button abortButton;

    /**
     * Deletes a specific {@link Transaction} via the controller deriving the necessary parameter
     * from the values of {@code transactionChoiceBox}. Then it closes the current window.
     * This method is linked to the {@code removeButton}.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            controller.removeTransaction(controlTransaction());
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
     * If the current value of the {@code transactionChoiceBox} is null, it fills
     * the {@code transactionChoiceBox} with all the selectable values.
     * This method is linked to the OnMouseMoved of the {@link javafx.scene.layout.VBox}
     * of the current window.
     */
    @FXML
    public void populateChoiceBoxAction() {
        if (Objects.isNull(transactionChoiceBox.getValue()))
            transactionChoiceBox.setItems(FXCollections.observableArrayList(controller.getTransactions()));
    }

    /**
     * Controls that the value of the {@code transactionChoiceBox} is not null
     * and then returns it.
     *
     * @return the controlled value of the {@code transactionChoiceBox}.
     */
    private Transaction controlTransaction() {
        if (Objects.isNull(transactionChoiceBox.getValue()))
            throw new NullPointerException(MESSAGE_TRANSACTION_NOT_SELECTED);
        return transactionChoiceBox.getValue();
    }
}
