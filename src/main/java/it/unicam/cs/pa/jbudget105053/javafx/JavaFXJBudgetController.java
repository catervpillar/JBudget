package it.unicam.cs.pa.jbudget105053.javafx;

import it.unicam.cs.pa.jbudget105053.persistence.ExportManager;
import it.unicam.cs.pa.jbudget105053.persistence.ImportManager;
import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.controller.LedgerMenuController;
import it.unicam.cs.pa.jbudget105053.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Objects;

/**
 * This class implements the interfaces {@link JavaFXController} and has the responsibility to manage the
 * main window of the application as well as to let visualize all data of the application in tables.
 * Provides specific buttons for each functionality of the application.
 *
 * @author Tommaso Catervi
 */
public class JavaFXJBudgetController implements JavaFXController {
    private final Controller controller = new LedgerMenuController();

    /**
     * The string error message for trying to remove or modify an {@link Account} or create a {@link Transaction}
     * without having any {@link Account} in the account list.
     */
    public static final String MESSAGE_EMPTY_ACCOUNT_LIST = "Non e' possibile procedere perche' la lista degli accounts e' vuota!";

    /**
     * The string error message for trying to remove or modify a {@link Tag} without having any {@link Tag}
     * in the tag list.
     */
    public static final String MESSAGE_EMPTY_TAG_LIST = "Non e' possibile procedere tags perche' la lista dei tag e' vuota!";

    /**
     * The string error message for trying to remove a {@link Transaction} without having any {@link Transaction}
     * in the transaction list.
     */
    public static final String MESSAGE_EMPTY_TRANSACTION_LIST = "Non e' possibile procedere perche' la lista delle transazioni e' vuota!";

    /**
     * The string error message for not selecting a valid directory when a directory chooser dialog is being opened.
     */
    public static final String MESSAGE_WRONG_PATH = "Non hai selezionato nessun percorso!";

    /**
     * The string error message for not selecting any default path in the app settings.
     */
    public static final String MESSAGE_NULL_DEFAULT_PATH = "Non hai selezionato nessun percorso predefinito!";

    /**
     * The string confirmation message for trying to reset the ledger or exit the app without having saved data.
     */
    public static final String MESSAGE_NOT_SAVED = "Non e' stato effettuato alcun salvataggio. Desideri comunque procedere senza salvare?";

    /**
     * The string confirmation message for trying to exit the app.
     */
    public static final String MESSAGE_ASK_EXIT_CONFIRMATION = "Sei sicuro di voler uscire dall'app? :(";

    // All buttons
    @FXML
    Button exportButton;
    @FXML
    Button importButton;
    @FXML
    Button createAccountButton;
    @FXML
    Button deleteAccountButton;
    @FXML
    Button createTagButton;
    @FXML
    Button modifyTagButton;
    @FXML
    Button deleteTagButton;
    @FXML
    Button addTransactionButton;
    @FXML
    Button deleteTransactionButton;
    @FXML
    Button chooseDefaultPathButton;
    @FXML
    RadioButton askAlwaysPathRadioButton;
    @FXML
    RadioButton useDefaultPathRadioButton;

    // Default path
    @FXML
    TextField defaultPathTextField;
    @FXML
    Label setPathLabel;

    // TableView and TableColumn for the accounts table
    @FXML
    TableView<Account> accountListTableView;
    @FXML
    TableColumn<Account, Integer> accountIDColumn;
    @FXML
    TableColumn<Account, String> accountNameColumn;
    @FXML
    TableColumn<Account, AccountType> accountTypeColumn;
    @FXML
    TableColumn<Account, String> accountInitialBalanceColumn;
    @FXML
    TableColumn<Account, String> accountCurrentBalanceColumn;

    // TableView and TableColumn for the tags table
    @FXML
    TableView<Tag> tagListTableView;
    @FXML
    TableColumn<Tag, Integer> tagIDColumn;
    @FXML
    TableColumn<Tag, String> tagNameColumn;
    @FXML
    TableColumn<Tag, String> tagDescriptionColumn;

    // TableView and TableColumn for the transactions table
    @FXML
    TableView<Transaction> transactionListTableView;
    @FXML
    TableColumn<Transaction, Integer> transactionIDColumn;
    @FXML
    TableColumn<Transaction, String> transactionAmountColumn;
    @FXML
    TableColumn<Transaction, String> transactionDateColumn;
    @FXML
    TableColumn<Transaction, String> transactionTagColumn;

    // TableView and TableColumn for the movements table
    @FXML
    TableView<Movement> movementsListTableView;
    @FXML
    TableColumn<Movement, Integer> movementIDColumn;
    @FXML
    TableColumn<Movement, MovementType> movementTypeColumn;
    @FXML
    TableColumn<Movement, String> movementAmountColumn;
    @FXML
    TableColumn<Movement, String> movementDateColumn;
    @FXML
    TableColumn<Movement, Account> movementAccountColumn;
    @FXML
    TableColumn<Movement, String> movementTagColumn;

    /**
     * Allows to exit the application after asking confirmation.
     */
    @FXML
    public void exitFromAppAction() {
        if (controlIsSaved() && createConfirmationAlert(MESSAGE_ASK_EXIT_CONFIRMATION))
            close(exportButton);
    }

    /**
     * Allows to reset all data of the application after asking confirmation.
     */
    @FXML
    public void resetLedger() {
        if (controlIsSaved())
            controller.resetLedger();
        refreshAll();
    }

    /**
     * If the user has not saved data yet, asks if he wants to procede
     * with the operation.
     *
     * @return true if the user has already saved data or wants to procede without
     * saving it, false otherwise.
     */
    private boolean controlIsSaved() {
        if (!controller.getIsSaved())
            return createConfirmationAlert(MESSAGE_NOT_SAVED);
        return true;
    }

    /**
     * Allows to set the default path for the data storage.
     */
    @FXML
    public void setDefaultPath() {
        try {
            defaultPathTextField.setText(getPath());
        } catch (NullPointerException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Allows to select a directory through a {@link DirectoryChooser} and returns its
     * absolute path.
     *
     * @return the absolute path of the chosen directory.
     */
    private String getPath() {
        File file = new DirectoryChooser().showDialog(chooseDefaultPathButton.getScene().getWindow());
        if (Objects.isNull(file))
            throw new NullPointerException(MESSAGE_WRONG_PATH);
        return file.getAbsolutePath();
    }

    /**
     * Allows to save all data of the application to a default path or a chosen path
     * depending on what the user chose.
     */
    @FXML
    public void exportAction() {
        try {
            if (useDefaultPathRadioButton.isSelected())
                controller.exportData(controlDefaultPath());
            else
                controller.exportData(getPath());
        } catch (NullPointerException e) {
            createErrorAlert(e.getMessage());
        } catch (IOException e) {
            createErrorAlert(ExportManager.MESSAGE_FAILED_EXPORT);
        }
    }

    /**
     * Controls that the text value of {@code defaultPathTextField} is not null
     * and returns it.
     *
     * @return the controlled text value of {@code defaultPathTextField}.
     */
    private String controlDefaultPath() {
        if (Objects.isNull(defaultPathTextField.getText()))
            throw new NullPointerException(MESSAGE_NULL_DEFAULT_PATH);
        return defaultPathTextField.getText();
    }

    /**
     * Allows to import data into the application from a chosen path.
     */
    @FXML
    public void importAction() {
        try {
            controller.importData(getPath());
            refreshAll();
        } catch (IOException | ParseException e) {
            createErrorAlert(ImportManager.MESSAGE_FAILED_IMPORT);
        } catch (RuntimeException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Shows the "About" window with the info about the app.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void showAboutAction() throws IOException {
        startWindow("About", "/about.fxml", null);
    }

    /**
     * Disable the selection of the default path for the data storage.
     */
    @FXML
    public void askAlwaysPathAction() {
        askAlwaysPathRadioButton.setSelected(true);
        setDefaultPathObjects(true, useDefaultPathRadioButton);
    }

    /**
     * Enable the selection of the default path for the data storage.
     */
    @FXML
    public void useDefaultPathAction() {
        useDefaultPathRadioButton.setSelected(true);
        setDefaultPathObjects(false, askAlwaysPathRadioButton);
    }

    /**
     * Sets the state of the given button to "unselected" and enables or disables
     * {@code setPathLabel}, {@code defaultPathTextField} and {@code chooseDefaultPathButton}
     * depending on the value of {@code bool}.
     *
     * @param bool the boolean to use to set the enable/disable state.
     * @param b    the button to make unselected.
     */
    private void setDefaultPathObjects(boolean bool, RadioButton b) {
        b.setSelected(false);
        setPathLabel.setDisable(bool);
        defaultPathTextField.setDisable(bool);
        chooseDefaultPathButton.setDisable(bool);
    }

    /**
     * Starts the {@link JavaFXAccountCreatorController} class for the creation of a new {@link Account}.
     * When the process finishes, it refreshes all the tables.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void createAccountAction() throws IOException {
        startWindow("Crea Account", "/accountCreator.fxml", new JavaFXAccountCreatorController(controller));
        refreshAll();
    }

    /**
     * Starts the {@link JavaFXAccountModifierController} class for the modification of a {@link Account}.
     * When the process finishes, it refreshes all the tables.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void modifyAccountAction() throws IOException {
        try {
            controlAccountList();
            startWindow("Modifica Account", "/accountModifier.fxml", new JavaFXAccountModifierController(controller));
            refreshAll();
        } catch (IllegalStateException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Starts the JavaFXAccountRemover class for the removal of an already existing
     * {@link Account} as long as the accounts list is not empty.
     * When the process finishes, it refreshes all the tables.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void deleteAccountAction() throws IOException {
        try {
            controlAccountList();
            startWindow("Elimina Account", "/accountRemover.fxml", new JavaFXAccountRemoverController(controller));
            refreshAll();
        } catch (IllegalStateException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Refreshes the accounts table content.
     */
    private void refreshAccount() {
        populateAccountTableView();
        accountListTableView.getItems().clear();
        accountListTableView.getItems().addAll(controller.getAccounts());
    }

    /**
     * Specifies which fields of an {@link Account} each column of the accounts
     * table should get.
     */
    private void populateAccountTableView() {
        accountIDColumn.setCellValueFactory(account -> new SimpleObjectProperty<>(account.getValue().getID()));
        accountNameColumn.setCellValueFactory(account -> new SimpleObjectProperty<>(account.getValue().getName()));
        accountTypeColumn.setCellValueFactory(account -> new SimpleObjectProperty<>(account.getValue().getAccountType()));
        accountInitialBalanceColumn.setCellValueFactory(account -> new SimpleObjectProperty<>("\u20ac " + account.getValue().getInitialBalance()));
        accountCurrentBalanceColumn.setCellValueFactory(account -> new SimpleObjectProperty<>("\u20ac " + account.getValue().getBalance()));
    }

    /**
     * Starts the JavaFXTagCreator class for the creation of a new {@link Tag}.
     * When the process finishes, it refreshes all the tables.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void createTagAction() throws IOException {
        startWindow("Crea Tag", "/tagCreator.fxml", new JavaFXTagCreatorController(controller));
        refreshAll();
    }

    /**
     * Starts the JavaFXTagModifierController class for the modification of a {@link Tag}.
     * When the process finishes, it refreshes all the tables.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void modifyTagAction() throws IOException {
        try {
            controlTagList();
            startWindow("Modifica Tag", "/tagModifier.fxml", new JavaFXTagModifierController(controller));
            refreshAll();
        } catch (IllegalStateException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Starts the JavaFXTagRemover class for the removal of an already existing
     * {@link Tag} as long as the tags list is not empty.
     * When the process finishes, it refreshes all the tables.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void deleteTagAction() throws IOException {
        try {
            controlTagList();
            startWindow("Elimina Tag", "/tagRemover.fxml", new JavaFXTagRemoverController(controller));
            refreshAll();
        } catch (IllegalStateException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Refreshes the tags table content.
     */
    private void refreshTag() {
        populateTagTableView();
        tagListTableView.getItems().clear();
        tagListTableView.getItems().addAll(controller.getTags());
    }

    /**
     * Specifies which fields of a {@link Tag} each column of the tags
     * table should get.
     */
    private void populateTagTableView() {
        tagIDColumn.setCellValueFactory(tag -> new SimpleObjectProperty<>(tag.getValue().getID()));
        tagNameColumn.setCellValueFactory(tag -> new SimpleObjectProperty<>(tag.getValue().getName()));
        tagDescriptionColumn.setCellValueFactory(tag -> new SimpleObjectProperty<>(tag.getValue().getDescription()));
    }

    /**
     * Starts the JavaFXTransactionCreator class for the creation of a new
     * {@link Transaction} as long as the accounts list is not empty.
     * When the process finishes, it refreshes all the tables.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void addTransactionAction() throws IOException {
        try {
            controlAccountList();
            startWindow("Crea Transazione", "/transactionCreator.fxml", new JavaFXTransactionCreatorController(controller));
            refreshAll();
        } catch (IllegalStateException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Starts the JavaFXTransactionRemover class for the removal of an already existing
     * {@link Transaction} as long as the transactions list is not empty.
     * When the process finishes, it refreshes all the tables.
     *
     * @throws IOException if something goes wrong.
     */
    @FXML
    public void deleteTransactionAction() throws IOException {
        try {
            controlTransactionList();
            movementsListTableView.getItems().clear();
            startWindow("Elimina Transazione", "/transactionRemover.fxml", new JavaFXTransactionRemoverController(controller));
            refreshAll();
        } catch (IllegalStateException e) {
            createErrorAlert(e.getMessage());
        }
    }

    /**
     * Gets the selected {@link Transaction} from the transactions table and refreshes
     * the movements table beside with the list of the movements associated with the
     * selected transaction.
     */
    @FXML
    public void handleRowSelect() {
        Transaction row = transactionListTableView.getSelectionModel().getSelectedItem();
        if (Objects.isNull(row)) return;
        refreshMovementsTableView(row.getMovements());
    }

    /**
     * Refreshes the transactions table content.
     */
    private void refreshTransaction() {
        populateTransactionTableView();
        transactionListTableView.getItems().clear();
        transactionListTableView.getItems().addAll(controller.getTransactions());
    }

    /**
     * Specifies which fields of a {@link Transaction} each column of the transactions
     * table should get.
     */
    private void populateTransactionTableView() {
        transactionIDColumn.setCellValueFactory(trans -> new SimpleObjectProperty<>(trans.getValue().getID()));
        transactionAmountColumn.setCellValueFactory(trans -> new SimpleObjectProperty<>("\u20ac " + trans.getValue().getTotalAmount()));
        transactionDateColumn.setCellValueFactory(trans -> new SimpleObjectProperty<>(new SimpleDateFormat("dd-MM-yyyy").format(trans.getValue().getDate())));
        transactionTagColumn.setCellValueFactory(trans -> new SimpleObjectProperty<>(trans.getValue().getTag().toString()));
    }

    /**
     * Refreshes the movements table content with the given list.
     */
    private void refreshMovementsTableView(Collection<? extends Movement> c) {
        populateMovementsTableView();
        movementsListTableView.getItems().clear();
        movementsListTableView.getItems().addAll(c);
    }

    /**
     * Specifies which fields of a {@link Movement} each column of the movements
     * table should get.
     */
    private void populateMovementsTableView() {
        movementIDColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getID()));
        movementTypeColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getMovementType()));
        movementAmountColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>("\u20ac " + mov.getValue().getAmount()));
        movementDateColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(new SimpleDateFormat("dd-MM-yyyy").format(mov.getValue().getMovementDate())));
        movementAccountColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getAccount()));
        movementTagColumn.setCellValueFactory(mov -> new SimpleObjectProperty<>(mov.getValue().getTag().toString()));
    }

    /**
     * Controls that the accounts list is not empty.
     */
    private void controlAccountList() {
        if (controller.getAccounts().isEmpty())
            throw new IllegalStateException(MESSAGE_EMPTY_ACCOUNT_LIST);
    }

    /**
     * Controls that the tags list is not empty.
     */
    private void controlTagList() {
        if (controller.getTags().isEmpty())
            throw new IllegalStateException(MESSAGE_EMPTY_TAG_LIST);
    }

    /**
     * Controls that the transactions list is not empty.
     */
    private void controlTransactionList() {
        if (controller.getTransactions().isEmpty())
            throw new IllegalStateException(MESSAGE_EMPTY_TRANSACTION_LIST);
    }

    /**
     * Refresh the content of all tables.
     * The movements table is cleared instead.
     * isSaved is set to false.
     */
    private void refreshAll() {
        refreshTransaction();
        refreshAccount();
        refreshTag();
        movementsListTableView.getItems().clear();
    }
}