package it.unicam.cs.pa.jbudget105053.controller;

import it.unicam.cs.pa.jbudget105053.persistence.ExportManager;
import it.unicam.cs.pa.jbudget105053.persistence.ImportManager;
import it.unicam.cs.pa.jbudget105053.model.*;
import it.unicam.cs.pa.jbudget105053.persistence.TextFileExporter;
import it.unicam.cs.pa.jbudget105053.persistence.TextFileImporter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class implements the interface {@link Controller} and has the responsibility to
 * act as a bridge between the view and the model of the application.
 *
 * @author Tommaso Catervi
 */
public class LedgerMenuController implements Controller {
    private final Ledger ledger = new LedgerMenu();
    private ExportManager exportManager;
    private ImportManager importManager;
    private boolean isSaved = true;

    /**
     * Allows to add a new {@link Account} with the given parameters to the {@link Ledger}.
     * {@code isSaved} is set to false.
     *
     * @param accountType    the value used to set the {@code accountType} field of the {@link Account}.
     * @param name           the value used to set the {@code name} field of the {@link Account}.
     * @param initialBalance the value used to set the {@code initialBalance} field of the {@link Account}.
     */
    @Override
    public void addAccount(AccountType accountType, String name, double initialBalance) {
        ledger.addAccount(accountType, name, initialBalance);
        isSaved = false;
    }

    /**
     * Allows to create and add a new {@link Account} to the {@link Ledger} without automatically
     * generating its ID. {@code isSaved} is set to false.
     *
     * @param ID             the value used to set the {@code ID} field of the {@link Account}.
     * @param accountType    the value used to set the {@code accountType} field of the {@link Account}.
     * @param name           the value used to set the {@code name} field of the {@link Account}.
     * @param initialBalance the value used to set the {@code initialBalance} field of the {@link Account}.
     */
    @Override
    public void addAccountWithID(int ID, AccountType accountType, String name, double initialBalance) {
        ledger.addAccountWithID(ID, accountType, name, initialBalance);
        isSaved = false;
    }

    /**
     * Allows to modify the given {@link Account} with the give parameters.
     * {@code isSaved} is set to false.
     *
     * @param a              the {@link Account} to modify.
     * @param accountType    the new account type to set.
     * @param name           the new name to set.
     * @param initialBalance the new initial balance to set.
     */
    @Override
    public void modifyAccount(Account a, AccountType accountType, String name, double initialBalance) {
        ledger.modifyAccount(a, accountType, name, initialBalance);
        isSaved = false;
    }

    /**
     * Allows to remove an {@link Account} from the {@link Ledger}.
     * {@code isSaved} is set to false.
     *
     * @param account the {@link Account} to remove.
     */
    @Override
    public void removeAccount(Account account) {
        ledger.removeAccount(account);
        isSaved = false;
    }

    /**
     * Returns the list of all the accounts of the {@link Ledger}.
     *
     * @return the accounts list of the {@link Ledger}.
     */
    @Override
    public List<Account> getAccounts() {
        return ledger.getAccounts();
    }

    /**
     * Returns the list of all the accounts of the {@link Ledger} after filtering it
     * according to a certain predicate.
     *
     * @param predicate the predicate the accounts must satisfy.
     * @return the filtered accounts list.
     */
    @Override
    public List<Account> getAccounts(Predicate<Account> predicate) {
        return ledger.getAccounts(predicate);
    }

    /**
     * Allows to create a new {@link Movement} with the given parameters and by generating its ID with
     * the class {@link Indexer} and then returns it.
     *
     * @param movementType the value used to set the {@code movementType} field of the {@link Movement}.
     * @param amount       the value used to set the {@code amount} field of the {@link Movement}.
     * @param account      the value used to set the {@code account} field of the {@link Movement}.
     * @return the created {@link Movement}.
     */
    @Override
    public Movement createMovement(MovementType movementType, double amount, Account account) {
        return new BasicMovement(Indexer.getInstance().generateMovementID(), movementType, amount, account);
    }

    /**
     * Allows to create a new {@link Movement} with the given parameters without automatically generating
     * its ID and then returns it.
     *
     * @param ID           the value used to set the {@code ID} field of the {@link Movement}.
     * @param movementType the value used to set the {@code movementType} field of the {@link Movement}.
     * @param amount       the value used to set the {@code amount} field of the {@link Movement}.
     * @param account      the value used to set the {@code account} field of the {@link Movement}.
     * @return the created {@link Movement}.
     */
    @Override
    public Movement createMovementWithID(int ID, MovementType movementType, double amount, Account account) {
        return new BasicMovement(ID, movementType, amount, account);
    }

    /**
     * Returns the list of all the movements of the {@link Ledger}.
     *
     * @return the movements list of the {@link Ledger}.
     */
    @Override
    public List<Movement> getMovements() {
        return ledger.getMovements();
    }

    /**
     * Allows to create a new {@link Transaction} with the given parameter and by generating its ID with
     * the class {@link Indexer} and then returns it.
     *
     * @param date the value used to set the {@code date} field of the {@link Transaction}.
     * @return the created {@link Transaction}.
     */
    @Override
    public Transaction createTransaction(Date date) {
        return new BasicTransaction(Indexer.getInstance().generateTransactionID(), date);
    }

    /**
     * Allows to create a new {@link Transaction} with the given parameters without automatically
     * generating its ID and then returns it.
     *
     * @param ID   the value used to set the {@code ID} field of the {@link Transaction}.
     * @param date the value used to set the {@code date} field of the {@link Transaction}.
     * @return the created {@link Transaction}.
     */
    @Override
    public Transaction createTransactionWithID(int ID, Date date) {
        return new BasicTransaction(ID, date);
    }

    /**
     * Allows to add a new {@link Transaction} to the {@link Ledger}.
     * {@code isSaved} is set to false.
     *
     * @param t the {@link Transaction} to add.
     */
    @Override
    public void addTransaction(Transaction t) {
        ledger.addTransaction(t);
        isSaved = false;
    }

    /**
     * Allows to remove a {@link Transaction} from the {@link Ledger}.
     * {@code isSaved} is set to false.
     *
     * @param t the {@link Transaction} to remove.
     */
    @Override
    public void removeTransaction(Transaction t) {
        ledger.removeTransaction(t);
        isSaved = false;
    }

    /**
     * Returns the list of all the transactions of the {@link Ledger}.
     *
     * @return the transactions list of the {@link Ledger}.
     */
    @Override
    public List<Transaction> getTransactions() {
        return ledger.getTransactions();
    }

    /**
     * Returns the list of all the transactions of the {@link Ledger} after filtering
     * it according to a certain predicate.
     *
     * @param predicate the predicate the transactions must satisfy.
     * @return the filtered transactions list.
     */
    @Override
    public List<Transaction> getTransactions(Predicate<Transaction> predicate) {
        return ledger.getTransactions(predicate);
    }

    /**
     * Allows to add a new {@link Tag} with the given parameters to the {@link Ledger}.
     * {@code isSaved} is set to false.
     *
     * @param name        the value used to set the {@code name} field of the {@link Tag}.
     * @param description the value used to set the {@code description} field of the {@link Tag}.
     */
    @Override
    public void addTag(String name, String description) {
        ledger.addTag(name, description);
        isSaved = false;
    }

    /**
     * Allows to add a new {@link Tag} with the given parameters and without automatically
     * generating its ID to the {@link Ledger}.
     * {@code isSaved} is set to false.
     *
     * @param ID          the value used to set the {@code ID} field of the {@link Tag}.
     * @param name        the value used to set the {@code name} field of the {@link Tag}.
     * @param description the value used to set the {@code description} field of the {@link Tag}.
     */
    @Override
    public void addTagWithID(int ID, String name, String description) {
        ledger.addTagWithID(ID, name, description);
        isSaved = false;
    }

    /**
     * Allows to modify the given {@link Tag} with the given parameters.
     * {@code isSaved} is set to false.
     *
     * @param t           the {@link Tag} to modify.
     * @param name        the new name to set.
     * @param description the new description to set.
     */
    @Override
    public void modifyTag(Tag t, String name, String description) {
        ledger.modifyTag(t, name, description);
        isSaved = false;
    }

    /**
     * Allows to remove a {@link Tag} from the {@link Ledger}.
     * {@code isSaved} is set to false.
     *
     * @param t the {@link Tag} to remove.
     */
    @Override
    public void removeTag(Tag t) {
        ledger.removeTag(t);
        isSaved = false;
    }

    /**
     * Returns the list of all the tags of the {@link Ledger}.
     *
     * @return the tags list of the {@link Ledger}.
     */
    @Override
    public List<Tag> getTags() {
        return ledger.getTags();
    }

    /**
     * Allows to save all data of the application into file(s) in a specific path
     * with the appropriate {@link ExportManager}.
     * {@code isSaved} is set to true.
     *
     * @param path the path where to save data.
     * @throws IOException if something goes wrong.
     */
    @Override
    public void exportData(String path) throws IOException {
        setExportManager(new TextFileExporter(this));
        this.exportManager.exportAll(path);
        isSaved = true;
    }

    /**
     * Allows to load data from specific file(s) in the given path with the
     * appropriate {@link ImportManager}.
     * {@code isSaved} is set to true.
     *
     * @param path the path from which the data is loaded.
     * @throws IOException    if something goes wrong.
     * @throws ParseException if something goes wrong.
     */
    @Override
    public void importData(String path) throws IOException, ParseException {
        setImportManager(new TextFileImporter(this));
        this.importManager.importAll(path);
        isSaved = true;
    }

    /**
     * Resets the {@link Ledger} and all its data.
     * {@code isSaved} is set to true.
     */
    @Override
    public void resetLedger() {
        ledger.resetLedger();
        isSaved = true;
    }

    /**
     * Setter method for the {@code exportManager} field of the {@link LedgerMenuController}.
     *
     * @param exportManager the {@link ExportManager} to set.
     */
    @Override
    public void setExportManager(ExportManager exportManager) {
        this.exportManager = exportManager;
    }

    /**
     * Setter method for the {@code importManager} field of the {@link LedgerMenuController}.
     *
     * @param importManager the {@link ImportManager} to set.
     */
    @Override
    public void setImportManager(ImportManager importManager) {
        this.importManager = importManager;
    }

    /**
     * Getter method for the {@code isSaved} field of the {@link LedgerMenuController}.
     *
     * @return the value of {@code isSaved}.
     */
    @Override
    public boolean getIsSaved() {
        return isSaved;
    }
}
