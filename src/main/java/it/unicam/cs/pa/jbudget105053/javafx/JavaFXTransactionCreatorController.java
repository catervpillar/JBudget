package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and {@link JavaFXEditor} and has the
 * responsibility to manage the window that lets the user create a {@link Transaction}.
 *
 * @author Tommaso Catervi
 */
public class JavaFXTransactionCreatorController implements JavaFXEditor, JavaFXController {
    private final Controller controller;
    private Transaction newTransaction;

    /**
     * The string error message for trying to create a {@link Transaction} without having selected a
     * {@link Date}.
     */
    public final String MESSAGE_WRONG_DATE = "Devi prima selezionare una data per poter creare una transazione, aggiungere o rimuovere movimenti ed aggiungere o rimuovere tag!";

    /**
     * The string error message for trying to create a {@link Transaction} without having added any
     * {@link Movement}.
     */
    public final String MESSAGE_EMPTY_MOVEMENTS_LIST = "Per procedere devi prima aggiungere dei movimenti!";

    /**
     * The string error message for trying to remove a {@link Movement} without having added any
     * {@link Movement} to the {@link Transaction}.
     */
    public final String MESSAGE_CANNOT_REMOVE_MOVEMENTS = "Non e' possibile eliminare movimenti perche' la lista dei movimenti e' vuota!";

    /**
     * The string error message for trying to add or remove a {@link Tag} without having selected any
     * {@link Tag}.
     */
    public final String MESSAGE_TAG_NOT_SELECTED = "Per procedere seleziona un tag!";

    /**
     * Constructs a JavaFXTransactionCreatorController with the given controller.
     *
     * @param controller the controller given.
     */
    public JavaFXTransactionCreatorController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    DatePicker transactionDatePicker;
    @FXML
    ChoiceBox<Tag> tagChoiceBox = new ChoiceBox<>();
    @FXML
    Button abortButton;
    @FXML
    Label totalAmountLabel;

    @FXML
    TableView<Movement> transactionMovementsTableView;
    @FXML
    TableColumn<Movement, Integer> movementIDColumn;
    @FXML
    TableColumn<Movement, MovementType> movementTypeColumn;
    @FXML
    TableColumn<Movement, Double> movementAmountColumn;
    @FXML
    TableColumn<Movement, String> movementDateColumn;
    @FXML
    TableColumn<Movement, Integer> movementTransactionColumn;
    @FXML
    TableColumn<Movement, String> movementAccountColumn;
    @FXML
    TableColumn<Movement, String> movementTagColumn;


    /**
     * Adds {@code newTransaction} to the controller. It is possible to add the {@link Transaction}
     * only after having selected the date and added at least one movement.
     * Then it closes the current window via the {@code abortAction()}.
     * This method is linked to the {@code createButton}.
     */
    @Override
    @FXML
    public void executeAction() {
        try {
            controlAll(0);
            controller.addTransaction(newTransaction);
            abortAction();
        } catch (NullPointerException | IllegalArgumentException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Reset {@code newTransaction} to null and closes the current window.
     * It is linked to the {@code abortButton}.
     */
    @Override
    @FXML
    public void abortAction() {
        newTransaction = null;
        close(abortButton);
    }

    /**
     * Allows to add a new {@link Movement} only after having selected the date
     * of the {@link Transaction}.
     * If {@code newTransaction} is null, it gets initialized with {@code initTransaction()}.
     * Then the movements table of the {@link Transaction} is refreshed and the value of
     * the {@code totalAmount} of {@code newTransaction} displayed on the window is refreshed too.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void addMovementAction() throws IOException {
        try {
            controlData();
            initTransaction();
            startWindow("Crea Movimento", "/movementCreator.fxml", new JavaFXMovementCreatorController(controller, this));
            refreshTransactionMovements();
            setTotalAmountLabel();
        } catch (NullPointerException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Allows to remove a {@link Movement} from the movements list of {@code newTransaction}
     * as long as the list is not empty.
     * Then both the movements table and the value of the {@code totalAmount} of {@code newTransaction}
     * displayed on the window are refreshed.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void deleteMovementAction() throws IOException {
        try {
            controlAll(1);
            startWindow("Elimina Movimento", "/movementRemover.fxml", new JavaFXMovementRemoverController(this));
            refreshTransactionMovements();
            setTotalAmountLabel();
        } catch (NullPointerException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Allows to add a new {@link Tag} to {@code newTransaction} only after having selected the date
     * of the {@link Transaction}.
     * Then it refreshes the movements table of the {@code newTransaction}.
     */
    @FXML
    public void addTagAction() {
        try {
            controlAll(0);
            newTransaction.addTag(controlTag());
            refreshTransactionMovements();
        } catch (NullPointerException | IllegalArgumentException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Allows to remove a {@link Tag} from the tags list of {@code newTransaction} only after
     * having selected the date of the {@link Transaction}.
     * Then it refreshes the movements table of the {@code newTransaction}.
     */
    @FXML
    public void removeTagAction() {
        try {
            controlAll(0);
            newTransaction.removeTag(controlTag());
            refreshTransactionMovements();
        } catch (NullPointerException | IllegalArgumentException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Controls that the value of the {@code tagChoiceBox} field is not null and
     * returns it.
     *
     * @return the controlled value of the {@code tagChoiceBox} field.
     */
    private Tag controlTag() {
        if (Objects.isNull(tagChoiceBox.getValue()))
            throw new NullPointerException(MESSAGE_TAG_NOT_SELECTED);
        return tagChoiceBox.getValue();
    }

    /**
     * If the current value of the {@code tagChoiceBox} is null, it fills
     * the {@code tagChoiceBox} with all the selectable values.
     */
    @FXML
    public void populateChoiceBoxAction() {
        if (Objects.isNull(tagChoiceBox.getValue()))
            tagChoiceBox.setItems(FXCollections.observableArrayList(controller.getTags()));
    }

    /**
     * Sets the {@code totalAmount} of {@code newTransaction} as the text of the
     * {@code totalAmountLabel} so that the {@code totalAmount} is displayed on the window
     * during the configuration of a new {@link Transaction}.
     */
    private void setTotalAmountLabel() {
        totalAmountLabel.setText("\u20ac " + newTransaction.getTotalAmount());
    }

    /**
     * Refreshes the movements table content of {@code newTransaction} with all the
     * added {@link Movement}.
     */
    private void refreshTransactionMovements() {
        populateMovementsTableView();
        transactionMovementsTableView.getItems().clear();
        transactionMovementsTableView.getItems().addAll(newTransaction.getMovements());
    }

    /**
     * Specifies which fields of a {@link Movement} each column of the movements
     * table should get.
     */
    private void populateMovementsTableView() {
        movementIDColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getID()));
        movementTypeColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getMovementType()));
        movementAmountColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getAmount()));
        movementDateColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(new SimpleDateFormat("dd-MM-yyyy").format(mov.getValue().getMovementDate())));
        movementTransactionColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getTransaction().getID()));
        movementAccountColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getAccount().getName()));
        movementTagColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getTag().toString()));
    }

    /**
     * Controls that the movements list is not empty and generates two different error
     * messages depending on the value of the parameter {@code code}.
     *
     * @param code the value that determines which message will be generated.
     */
    private void controlMovementsList(int code) {
        if (newTransaction.getMovements().isEmpty())
            switch (code) {
                case 0:
                    throw new NullPointerException(MESSAGE_EMPTY_MOVEMENTS_LIST);
                case 1:
                    throw new NullPointerException(MESSAGE_CANNOT_REMOVE_MOVEMENTS);
            }
    }

    /**
     * Initialize the new transaction {@code newTransaction} if it is null.
     */
    private void initTransaction() {
        if (Objects.isNull(newTransaction))
            newTransaction = controller.createTransaction(controlData());
    }

    /**
     * Controls that the value of the {@code transactionDatePicker} field is not
     * null and returns it.
     *
     * @return the controlled value of the {@code transactionDatePicker} field.
     */
    private Date controlData() {
        if (Objects.isNull(transactionDatePicker.getValue()))
            throw new NullPointerException(MESSAGE_WRONG_DATE);
        return java.sql.Date.valueOf(transactionDatePicker.getValue());
    }

    /**
     * Checks if the date value of the date picker is different from the date of
     * the new {@link Transaction} (if it's not null) and if so sets the transaction
     * date with the value of the date picker.
     */
    @FXML
    private void updateDate() {
        if (!Objects.isNull(getNewTransaction()))
            if (!java.sql.Date.valueOf(transactionDatePicker.getValue()).equals(getNewTransaction().getDate())) {
                getNewTransaction().setDate(java.sql.Date.valueOf(transactionDatePicker.getValue()));
                refreshTransactionMovements();
            }
    }

    /**
     * Controls that the date has being selected as well as that {@code newTransaction}
     * is not null and that the movements list is not empty.
     *
     * @param code the value that determines which message will be generated by {@code controlMovementsList()}
     */
    private void controlAll(int code) {
        controlData();
        initTransaction();
        controlMovementsList(code);
    }

    /**
     * Getter methods for the {@code newTransaction}.
     *
     * @return the {@code newTransaction} field.
     */
    public Transaction getNewTransaction() {
        return newTransaction;
    }
}
