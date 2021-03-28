package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.model.Account;
import it.unicam.cs.pa.jbudget105053.model.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user modify an already existing {@link Account}.
 *
 * @author Tommaso Catervi
 */
public class JavaFXAccountModifierController implements JavaFXEditor, JavaFXController {
    private final Controller controller;

    /**
     * The string error message for trying to remove an {@link Account} without having selected anything
     * from the {@code accountChoiceBox}.
     */
    public static final String MESSAGE_ACCOUNT_NOT_SELECTED = "Per procedere seleziona un account da rimuovere!";

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

    /**
     * Constructs a JavaFXAccountRemoverController with the given controller.
     *
     * @param controller the controller given.
     */
    public JavaFXAccountModifierController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    ChoiceBox<Account> accountChoiceBox = new ChoiceBox<>();
    @FXML
    ChoiceBox<AccountType> newTypeChoiceBox = new ChoiceBox<>();
    @FXML
    TextField newNameTextField;
    @FXML
    TextField newInitialBalanceTextField;
    @FXML
    Button abortButton;

    /**
     * Modifies a specific {@link Account} via the controller.
     * Then it closes the current window.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            controller.modifyAccount(controlAccount(), controlType(), controlName(), controlInitialBalance());
            abortAction();
        } catch (NumberFormatException e) {
            createErrorAlert(MESSAGE_WRONG_BALANCE);
        } catch (RuntimeException e) {
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
     * Populates {@code accountChoiceBox} and {@code newTypeChoiceBox}.
     */
    @FXML
    public void populateChoiceBoxAction() {
        populateAccountChoiceBox();
        populateAccountTypeChoiceBox();
    }

    /**
     * If the current value of the {@code accountChoiceBox} is null, it fills
     * the {@code accountChoiceBox} with all the selectable values.
     * Otherwise, prompt texts of @code newNameTextField} and @code newInitialBalanceTextField}
     * is being set equal to the respective values of the selected {@link Account}.
     */
    private void populateAccountChoiceBox() {
        if (Objects.isNull(accountChoiceBox.getValue()))
            accountChoiceBox.setItems(FXCollections.observableArrayList(controller.getAccounts()));
        else {
            newNameTextField.setPromptText(accountChoiceBox.getValue().getName());
            newInitialBalanceTextField.setPromptText(accountChoiceBox.getValue().getInitialBalance() + "");
        }
    }

    /**
     * If the current value of the {@code newTypeChoiceBox} is null, it fills
     * the {@code newTypeChoiceBox} with all the selectable values.
     */
    private void populateAccountTypeChoiceBox() {
        if (Objects.isNull(newTypeChoiceBox.getValue()))
            newTypeChoiceBox.setItems(FXCollections.observableArrayList(AccountType.values()));
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

    /**
     * Controls that the value of the {@code accountTypeChoiceBox} is not null
     * and then returns it.
     *
     * @return the controlled value of the {@code accountTypeChoiceBox}.
     */
    private AccountType controlType() {
        if (Objects.isNull(newTypeChoiceBox.getValue()))
            throw new IllegalArgumentException(MESSAGE_WRONG_TYPE);
        return newTypeChoiceBox.getValue();
    }

    /**
     * Controls that the value of the {@code accountNameField} is not ""
     * and that it is not equal to the name of another {@link Account}
     * already existing and then returns it.
     *
     * @return the controlled value of the {@code accountNameField}.
     */
    private String controlName() {
        if (newNameTextField.getText().equals(""))
            throw new IllegalArgumentException(MESSAGE_WRONG_NAME);
        return newNameTextField.getText();
    }

    /**
     * Returns the value of the {@code initialBalanceField} after trying to execute the
     * Double.parseDouble() method on it.
     *
     * @return the parseDouble value of the {@code initialBalanceField}.
     */
    private Double controlInitialBalance() {
        return Double.parseDouble(newInitialBalanceTextField.getText());
    }
}
