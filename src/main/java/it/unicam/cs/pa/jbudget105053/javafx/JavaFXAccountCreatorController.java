package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.model.Account;
import it.unicam.cs.pa.jbudget105053.model.AccountType;
import it.unicam.cs.pa.jbudget105053.controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user create an {@link Account}..
 *
 * @author Tommaso Catervi
 */
public class JavaFXAccountCreatorController implements JavaFXEditor, JavaFXController {
    private final Controller controller;

    /**
     * Constructs a JavaFXAccountCreatorController with the given controller.
     *
     * @param controller the controller given.
     */
    public JavaFXAccountCreatorController(Controller controller) {
        this.controller = controller;
    }

    /**
     * The string error message for trying to create an {@link Account} without having selected an
     * {@link AccountType}.
     */
    public static final String MESSAGE_WRONG_TYPE = "Devi selezionare un tipo per poter creare un account!";

    /**
     * The string error message for trying to create an {@link Account} without having typed anything in
     * the {@code accountNameField} {@link TextField}.
     */
    public static final String MESSAGE_WRONG_NAME = "Suvvia, scrivi qualcosa in piu' nel nome dell'account!";

    /**
     * The string error message for trying to create an {@link Account} with a negative {@code initialBalance}
     * or a non-numeric {@code initialBalance}.
     */
    public static final String MESSAGE_WRONG_BALANCE = "Il saldo iniziale deve essere un valore numerico non negativo!";

    @FXML
    ChoiceBox<AccountType> accountTypeChoiceBox = new ChoiceBox<>();
    @FXML
    TextField accountNameField;
    @FXML
    TextField initialBalanceField;
    @FXML
    Button abortButton;

    /**
     * Creates a new {@link Account} via the controller deriving the necessary constructor
     * parameters from the values of {@code accountTypeChoiceBox}, {@code accountNameField}
     * and {@code initialBalanceField}. Then it closes the current window.
     * This method is linked to the {@code createButton}.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            controller.addAccount(controlType(), controlName(), controlInitialBalance());
            close(abortButton);
        } catch (NumberFormatException e) {
            createErrorAlert(MESSAGE_WRONG_BALANCE);
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
     * Controls that the value of the {@code accountTypeChoiceBox} is not null
     * and then returns it.
     *
     * @return the controlled value of the {@code accountTypeChoiceBox}.
     */
    private AccountType controlType() {
        if (Objects.isNull(accountTypeChoiceBox.getValue()))
            throw new IllegalArgumentException(MESSAGE_WRONG_TYPE);
        return accountTypeChoiceBox.getValue();
    }

    /**
     * Controls that the value of the {@code accountNameField} is not ""
     * and that it is not equal to the name of another {@link Account}
     * already existing and then returns it.
     *
     * @return the controlled value of the {@code accountNameField}.
     */
    private String controlName() {
        if (accountNameField.getText().equals(""))
            throw new IllegalArgumentException(MESSAGE_WRONG_NAME);
        return accountNameField.getText();
    }

    /**
     * Returns the value of the {@code initialBalanceField} after trying to execute the
     * Double.parseDouble() method on it.
     *
     * @return the parseDouble value of the {@code initialBalanceField}.
     */
    private Double controlInitialBalance() {
        return Double.parseDouble(initialBalanceField.getText());
    }

    /**
     * If the current value of the {@code accountTypeChoiceBox} is null, it fills
     * the {@code accountTypeChoiceBox} with all the selectable values.
     * This method is linked to the OnMouseMoved of the {@link javafx.scene.layout.VBox}
     * of the current window.
     */
    @FXML
    public void populateChoiceBoxAction() {
        if (Objects.isNull(accountTypeChoiceBox.getValue()))
            accountTypeChoiceBox.setItems(FXCollections.observableArrayList(AccountType.values()));
    }
}
