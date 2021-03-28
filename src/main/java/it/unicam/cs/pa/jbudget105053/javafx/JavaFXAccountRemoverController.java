package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.model.Account;
import it.unicam.cs.pa.jbudget105053.controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user remove an already existing {@link Account}.
 *
 * @author Tommaso Catervi
 */
public class JavaFXAccountRemoverController implements JavaFXEditor, JavaFXController {
    private final Controller controller;

    /**
     * The string error message for trying to remove an {@link Account} without having selected anything
     * from the {@code accountChoiceBox}.
     */
    public static final String MESSAGE_ACCOUNT_NOT_SELECTED = "Per procedere seleziona un account da rimuovere!";

    /**
     * Constructs a JavaFXAccountRemoverController with the given controller.
     *
     * @param controller the controller given.
     */
    public JavaFXAccountRemoverController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    ChoiceBox<Account> accountChoiceBox = new ChoiceBox<>();
    @FXML
    Button abortButton;


    /**
     * Deletes a specific {@link Account} via the controller deriving the necessary parameter
     * from the values of {@code accountChoiceBox}. Then it closes the current window.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            controller.removeAccount(controlAccount());
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
     * If the current value of the {@code accountChoiceBox} is null, it fills
     * the {@code accountChoiceBox} with all the selectable values.
     * This method is linked to the OnMouseMoved of the {@link javafx.scene.layout.VBox}
     * of the current window.
     */
    @FXML
    public void populateChoiceBoxAction() {
        if (Objects.isNull(accountChoiceBox.getValue()))
            accountChoiceBox.setItems(FXCollections.observableArrayList(controller.getAccounts()));
    }

    /**
     * Controls that the value of the {@code accountChoiceBox} is not null
     * and then returns it.
     *
     * @return the controlled value of the {@code accountChoiceBox}.
     */
    private Account controlAccount() {
        if (Objects.isNull(accountChoiceBox.getValue()))
            throw new NullPointerException(MESSAGE_ACCOUNT_NOT_SELECTED);
        return accountChoiceBox.getValue();
    }
}
