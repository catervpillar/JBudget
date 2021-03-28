package it.unicam.cs.pa.jbudget105053.controller;

import it.unicam.cs.pa.jbudget105053.model.*;
import it.unicam.cs.pa.jbudget105053.persistence.ExportManager;
import it.unicam.cs.pa.jbudget105053.persistence.ImportManager;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * This interface is implemented by all the classes that have the responsibility to
 * act as a bridge between the view and the model of the application.
 *
 * @author Tommaso Catervi
 */
public interface Controller {
    /**
     * Allows to add a new {@link Account} to the ledger.
     *
     * @param accountType    the value used to set the {@code accountType} field of the {@link Account}.
     * @param name           the value used to set the {@code name} field of the {@link Account}.
     * @param initialBalance the value used to set the {@code initialBalance} field of the {@link Account}.
     */
    void addAccount(AccountType accountType, String name, double initialBalance);

    /**
     * Allows to add a new {@link Account} to the ledger (the ID field is not automatically generated).
     *
     * @param ID             the value used to set the {@code ID} field of the {@link Account}.
     * @param accountType    the value used to set the {@code accountType} field of the {@link Account}.
     * @param name           the value used to set the {@code name} field of the {@link Account}.
     * @param initialBalance the value used to set the {@code initialBalance} field of the {@link Account}.
     */
    void addAccountWithID(int ID, AccountType accountType, String name, double initialBalance);

    /**
     * Allows to modify the given {@link Account} with the given new parameters.
     *
     * @param a              the {@link Account} to modify.
     * @param accountType    the new account type to set.
     * @param name           the new name to set.
     * @param initialBalance the new initial balance to set.
     */
    void modifyAccount(Account a, AccountType accountType, String name, double initialBalance);

    /**
     * Allows to remove an {@link Account} from the ledger.
     *
     * @param account the {@link Account} to remove.
     */
    void removeAccount(Account account);

    /**
     * Getter method for the list of all the accounts contained in the ledger.
     *
     * @return the accounts list of the ledger.
     */
    List<Account> getAccounts();

    /**
     * Returns the accounts list of the ledger after filtering it according to a
     * certain predicate.
     *
     * @param predicate the predicate the accounts must satisfy.
     * @return the filtered accounts list.
     */
    List<Account> getAccounts(Predicate<Account> predicate);

    /**
     * Creates a new {@link Movement} with the given parameters and returns it.
     *
     * @param movementType the value used to set the {@code movementType} field of the {@link Movement}.
     * @param amount       the value used to set the {@code amount} field of the {@link Movement}.
     * @param account      the value used to set the {@code account} field of the {@link Movement}.
     * @return the created {@link Movement}.
     */
    Movement createMovement(MovementType movementType, double amount, Account account);

    /**
     * Creates a new {@link Movement} with the given parameters and returns it.
     * (The ID is not automatically generated).
     *
     * @param ID           the value used to set the {@code ID} field of the {@link Movement}.
     * @param movementType the value used to set the {@code movementType} field of the {@link Movement}.
     * @param amount       the value used to set the {@code amount} field of the {@link Movement}.
     * @param account      the value used to set the {@code account} field of the {@link Movement}.
     * @return the created {@link Movement}.
     */
    Movement createMovementWithID(int ID, MovementType movementType, double amount, Account account);

    /**
     * Getter method for the list of all the movements contained in the ledger.
     *
     * @return the movements list of the ledger.
     */
    List<Movement> getMovements();

    /**
     * Creates a new {@link Transaction} with the given parameters and returns it.
     *
     * @param date the value used to set the {@code date} field of the {@link Transaction}.
     * @return the created {@link Transaction}.
     */
    Transaction createTransaction(Date date);

    /**
     * Creates a new {@link Transaction} with the given parameters and returns it.
     * (The ID is not automatically generated).
     *
     * @param ID   the value used to set the {@code ID} field of the {@link Transaction}.
     * @param date the value used to set the {@code date} field of the {@link Transaction}.
     * @return the created {@link Transaction}.
     */
    Transaction createTransactionWithID(int ID, Date date);

    /**
     * Allows to add a new {@link Transaction} to the transactions list of the ledger.
     *
     * @param t the {@link Transaction} to add.
     */
    void addTransaction(Transaction t);

    /**
     * Allows to remove a {@link Transaction} from the transactions list of the ledger.
     *
     * @param t the {@link Transaction} to remove.
     */
    void removeTransaction(Transaction t);

    /**
     * Getter method for the list of all the transactions contained in the ledger.
     *
     * @return the transactions list of the ledger.
     */
    List<Transaction> getTransactions();

    /**
     * Returns the transactions list of the ledger after filtering it according to a
     * certain predicate.
     *
     * @param predicate the predicate the transactions must satisfy.
     * @return the filtered transactions list.
     */
    List<Transaction> getTransactions(Predicate<Transaction> predicate);

    /**
     * Allows to create a new {@link Tag} with the given parameters and then adds it
     * to the tags list of the ledger.
     *
     * @param name        the value used to set the {@code name} field of the {@link Tag}.
     * @param description the value used to set the {@code description} field of the {@link Tag}.
     */
    void addTag(String name, String description);

    /**
     * Allows to create a new {@link Tag} with the given parameters and then adds it
     * to the tags list of the ledger (the ID field is not automatically generated).
     *
     * @param ID          the value used to set the {@code ID} field of the {@link Tag}.
     * @param name        the value used to set the {@code name} field of the {@link Tag}.
     * @param description the value used to set the {@code description} field of the {@link Tag}.
     */
    void addTagWithID(int ID, String name, String description);

    /**
     * Allows to modify the given {@link Tag} with the given new parameters.
     *
     * @param t           the {@link Tag} to modify.
     * @param name        the new name to set.
     * @param description the new description to set.
     */
    void modifyTag(Tag t, String name, String description);

    /**
     * Allows to remove a {@link Tag} from the tags list of the ledger.
     *
     * @param t the {@link Tag} to remove.
     */
    void removeTag(Tag t);

    /**
     * Getter method for the list of all tags contained in the ledger.
     *
     * @return the tags list of the ledger.
     */
    List<Tag> getTags();

    /**
     * Exports all data of the application into file(s) in a given path.
     *
     * @param path the path where to save data.
     * @throws IOException if something goes wrong.
     */
    void exportData(String path) throws IOException;

    /**
     * Imports data into the application from file(s) in a given path.
     *
     * @param path the path from which the data is loaded.
     * @throws IOException    if something goes wrong.
     * @throws ParseException if something goes wrong.
     */
    void importData(String path) throws IOException, ParseException;

    /**
     * Resets the ledger.
     */
    void resetLedger();

    /**
     * Setter method for the {@link ExportManager} that will have the responsibility to
     * save all data.
     *
     * @param exportManager the {@link ExportManager} to set.
     */
    void setExportManager(ExportManager exportManager);

    /**
     * Setter method for the {@link ImportManager} that will have the responsibility to
     * load all data.
     *
     * @param importManager the {@link ImportManager} to set.
     */
    void setImportManager(ImportManager importManager);

    /**
     * Getter method for the boolean value that allows to know if data has been
     * saved or not.
     *
     * @return true if the data has been saved, false otherwise.
     */
    boolean getIsSaved();
}
