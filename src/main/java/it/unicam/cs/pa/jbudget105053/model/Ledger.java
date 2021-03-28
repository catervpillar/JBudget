package it.unicam.cs.pa.jbudget105053.model;

import java.util.List;
import java.util.function.Predicate;

/**
 * This interface is implemented by all the classes that have the responsibility to manage all
 * data of the application.
 * It has the responsibility to create and remove accounts, create and remove tags and
 * add and remove transactions.
 * It is also possible to view the entire list of stored transactions and accounts as well as a
 * list of all transactions or accounts that satisfy a particular predicate.
 */
public interface Ledger {
    /**
     * Allows to create a new {@link Account} with the given parameters and then adds
     * it to the accounts list.
     *
     * @param accountType    the value used to set the {@code accountType} field of the {@link Account}.
     * @param name           the value used to set the {@code name} field of the {@link Account}.
     * @param initialBalance the value used to set the {@code initialBalance} field of the {@link Account}.
     */
    void addAccount(AccountType accountType, String name, double initialBalance);

    /**
     * Allows to create and add a new {@link Account} to the {@code accountList} without automatically
     * generating its ID.
     *
     * @param ID             the value used to set the {@code ID} field in the {@code Account}.
     * @param accountType    the value used to set the {@code accountType} field in the {@code Account}.
     * @param name           the value used to set the {@code name} field in the {@code Account}.
     * @param initialBalance the value used to set the {@code initialBalance} field in the {@code Account}.
     */
    void addAccountWithID(int ID, AccountType accountType, String name, double initialBalance);

    /**
     * Allows to modify the given {@link Account} with the given parameters
     *
     * @param a              the account to modify.
     * @param accountType    the new {@link AccountType} to set.
     * @param name           the new name to set.
     * @param initialBalance the new initial balance to set.
     */
    void modifyAccount(Account a, AccountType accountType, String name, double initialBalance);

    /**
     * Allows to remove an {@link Account} from the accounts list.
     *
     * @param a the {@link Account} to remove.
     */
    void removeAccount(Account a);

    /**
     * Getter method for the accounts list.
     *
     * @return the list of all accounts.
     */
    List<Account> getAccounts();

    /**
     * Getter method for the accounts list after filtering it according
     * to a certain predicate.
     *
     * @param p the predicate the accounts must satisfy.
     * @return the filtered accounts list.
     */
    List<Account> getAccounts(Predicate<Account> p);

    /**
     * Allows to add a new {@link Transaction} to the transactions list.
     *
     * @param t the new {@link Transaction} to add.
     */
    void addTransaction(Transaction t);

    /**
     * Allows to remove a {@link Transaction} from the transactions list.
     *
     * @param t the {@link Transaction} to remove.
     */
    void removeTransaction(Transaction t);

    /**
     * Getter method for the transactions list.
     *
     * @return the list of all transactions.
     */
    List<Transaction> getTransactions();

    /**
     * Getter method for the transactions list after filtering it according
     * to a certain predicate.
     *
     * @param p the predicate the transactions must satisfy.
     * @return the filtered transactions list.
     */
    List<Transaction> getTransactions(Predicate<Transaction> p);

    /**
     * Getter method for the movements list.
     *
     * @return the list of all movements.
     */
    List<Movement> getMovements();

    /**
     * Getter method for the tags list.
     *
     * @return the list of all tags.
     */
    List<Tag> getTags();

    /**
     * Allows to create a new {@link Tag} and then adds it to the tags list.
     *
     * @param name        the value used to set the {@code name} field of the {@link Transaction}.
     * @param description the value used to set the {@code description} field of the {@link Transaction}.
     */
    void addTag(String name, String description);

    /**
     * Allows to create and add a new {@link Tag} to the {@code tagList} without automatically
     * generating its ID.
     *
     * @param ID          the value used to set the {@code ID} field in the {@code Account}.
     * @param name        the value used to set the {@code name} field in the {@code Tag}.
     * @param description the value used to set the {@code description} field in the {@code Tag}.
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
     * Allows to remove a {@link Tag} from the tags list.
     *
     * @param t the {@link Tag} to remove.
     */
    void removeTag(Tag t);

    /**
     * Resets the ledger.
     */
    void resetLedger();
}
