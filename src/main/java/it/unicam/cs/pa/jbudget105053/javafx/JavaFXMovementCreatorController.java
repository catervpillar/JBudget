package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user create a {@link Movement}.
 *
 * @author Tommaso Catervi
 */
public class JavaFXMovementCreatorController implements JavaFXEditor, JavaFXController {
    private final Controller controller;
    private final JavaFXTransactionCreatorController transactionController;
    private final List<Tag> addedTags = new ArrayList<>();

    /**
     * The string error message for trying to create a {@link Movement} without having selected a
     * {@link MovementType}.
     */
    public final String MESSAGE_WRONG_TYPE = "Devi selezionare un tipo per poter creare un movimento!";

    /**
     * The string error message for trying to create a {@link Movement} without a negative or equal to zero
     * {@code amount} or with a non-numeric {@code amount}.
     */
    public final String MESSAGE_WRONG_AMOUNT = "L'importo del movimento deve essere un valore numerico non negativo!";

    /**
     * The string error message for trying to create a {@link Movement} without having selected a
     * {@link Account}.
     */
    public final String MESSAGE_ACCOUNT_NOT_SELECTED = "Devi selezionare un account per poter creare un movimento!";

    /**
     * The string error message for trying to add or remove a {@link Tag} without having selected it.
     */
    public final String MESSAGE_TAG_NOT_SELECTED = "Per procedere seleziona un tag!";

    /**
     * Constructs a JavaFXMovementCreatorController with the given controller and transactionController.
     *
     * @param controller            the controller given.
     * @param transactionController the JavaFXTransactionCreatorController given.
     */
    public JavaFXMovementCreatorController(Controller controller, JavaFXTransactionCreatorController transactionController) {
        this.controller = controller;
        this.transactionController = transactionController;
    }

    @FXML
    ChoiceBox<MovementType> movementTypeChoiceBox = new ChoiceBox<>();
    @FXML
    TextField movementAmountField;
    @FXML
    ChoiceBox<Account> accountNameChoiceBox = new ChoiceBox<>();
    @FXML
    ChoiceBox<Tag> tagNameChoiceBox = new ChoiceBox<>();
    @FXML
    Button abortButton;
    @FXML
    TableView<Tag> tagTableView;
    @FXML
    TableColumn<Tag, String> tagNameColumn;

    /**
     * Creates a new {@link Movement} via the controller deriving the necessary constructor
     * parameters from the values of {@code movementTypeChoiceBox}, {@code movementAmountField}
     * and {@code accountNameChoiceBox}. Then it adds all the tags in the {@code addedTags} list
     * to the {@link Movement} created and eventually adds it to the {@link Transaction}.
     * Then it closes the current window.
     * This method is linked to the {@code createButton}.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            Movement newMove = controller.createMovement(controlType(), controlAmount(), controlAccount());
            addedTags.forEach(newMove::addTag);
            transactionController.getNewTransaction().addMovement(newMove);
            close(abortButton);
        } catch (NumberFormatException e) {
            createErrorAlert(MESSAGE_WRONG_AMOUNT);
        } catch (IllegalArgumentException | NullPointerException e) {
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
     * Populates all {@link ChoiceBox} of the class.
     * This method is linked to the OnMouseMoved of the {@link javafx.scene.layout.VBox}
     * of the current window.
     */
    @FXML
    public void populateChoiceBoxAction() {
        populateMovementTypeChoiceBox();
        populateAccountChoiceBox();
        populateTagChoiceBox();
    }

    /**
     * If the current value of the {@code movementTypeChoiceBox} is null, it fills
     * the {@code movementTypeChoiceBox} with all the selectable values.
     */
    private void populateMovementTypeChoiceBox() {
        if (Objects.isNull(movementTypeChoiceBox.getValue()))
            movementTypeChoiceBox.setItems(FXCollections.observableArrayList(MovementType.values()));
    }

    /**
     * If the current value of the {@code accountNameChoiceBox} is null, it fills
     * the {@code accountNameChoiceBox} with all the selectable values.
     */
    private void populateAccountChoiceBox() {
        if (Objects.isNull(accountNameChoiceBox.getValue()))
            accountNameChoiceBox.setItems(FXCollections.observableArrayList(controller.getAccounts()));
    }

    /**
     * If the current value of the {@code tagNameChoiceBox} is null, it fills
     * the {@code tagNameChoiceBox} with all the selectable values.
     */
    private void populateTagChoiceBox() {
        if (Objects.isNull(tagNameChoiceBox.getValue()))
            tagNameChoiceBox.setItems(FXCollections.observableArrayList(controller.getTags()));
    }

    /**
     * Adds a new {@link Tag} to {@code addedTags} (which is the list of {@link Tag}
     * that will be added to the {@link Movement}) as long as it is not already contained
     * in it. Then it refreshes the table of the added tags.
     */
    @FXML
    public void addTagAction() {
        try {
            if (addedTags.contains(controlTag()))
                throw new IllegalArgumentException(Tag.MESSAGE_TAG_ALREADY_EXISTS);
            addedTags.add(controlTag());
            refreshMovementTags();
        } catch (NullPointerException | IllegalArgumentException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Removes a {@link Tag} from {@code addedTags} (which is the list of {@link Tag}
     * that will be added to the {@link Movement}) as long as it is contained in it.
     * Then it refreshes the table of the added tags.
     */
    @FXML
    public void removeTagAction() {
        try {
            if (!addedTags.contains(controlTag()))
                throw new IllegalArgumentException(Tag.MESSAGE_TAG_DOES_NOT_EXIST);
            addedTags.remove(controlTag());
            refreshMovementTags();
        } catch (NullPointerException | IllegalArgumentException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Controls that the value of the {@code tagNameChoiceBox} field is not null
     * and returns it.
     *
     * @return the controlled value of the {@code tagNameChoiceBox} field.
     */
    private Tag controlTag() {
        if (Objects.isNull(tagNameChoiceBox.getValue()))
            throw new NullPointerException(MESSAGE_TAG_NOT_SELECTED);
        return tagNameChoiceBox.getValue();
    }

    /**
     * Refreshes the tags table content with the {@link Tag} contained
     * in {@code addedTags}.
     */
    private void refreshMovementTags() {
        populateTagsTableView();
        tagTableView.getItems().clear();
        tagTableView.getItems().addAll(addedTags);
    }

    /**
     * Specifies which field of a {@link Tag} the column of the tags
     * table should get.
     */
    private void populateTagsTableView() {
        tagNameColumn.setCellValueFactory(tag -> new SimpleObjectProperty<>(tag.getValue().getName()));
    }

    /**
     * Controls that the value of the {@code movementTypeChoiceBox} field is not null
     * and returns it.
     *
     * @return the controlled value of the {@code movementTypeChoiceBox} field.
     */
    private MovementType controlType() {
        if (Objects.isNull(movementTypeChoiceBox.getValue()))
            throw new NullPointerException(MESSAGE_WRONG_TYPE);
        return movementTypeChoiceBox.getValue();
    }

    /**
     * Returns the value of the {@code movementAmountField} after trying to execute the
     * Double.parseDouble() method on it.
     *
     * @return the parseDouble value of the {@code movementAmountField}.
     */
    private Double controlAmount() {
        return Double.parseDouble(movementAmountField.getText());
    }

    /**
     * Controls that the value of the {@code accountNameChoiceBox} field is not null
     * and returns it.
     *
     * @return the controlled value of the {@code accountNameChoiceBox} field.
     */
    private Account controlAccount() {
        if (Objects.isNull(accountNameChoiceBox.getValue()))
            throw new NullPointerException(MESSAGE_ACCOUNT_NOT_SELECTED);
        return accountNameChoiceBox.getValue();
    }
}