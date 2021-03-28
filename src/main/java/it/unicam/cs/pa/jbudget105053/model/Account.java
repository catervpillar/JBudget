package it.unicam.cs.pa.jbudget105053.model;

import java.util.List;
import java.util.function.Predicate;

/**
 * This interface extends the interface {@link HasID} and is implemented by all the classes that have the
 * responsibility to manage an account.
 * It allows to get the main information from the account: type of the account, name, ID and initial balance.
 * It also allows to calculate the current balance and it is possible to view the entire list of movements
 * associated with this account as well as a list of all movements that satisfy a particular predicate.
 */
public interface Account extends HasID {
    /**
     * The string error message for when an ID smaller than 1 is passed.
     */
    String MESSAGE_WRONG_ID = "L'ID dell'account passato e' negativo, ergo non valido.";

    /**
     * The string error message for when a null account type is passed.
     */
    String MESSAGE_NULL_TYPE = "Il tipo di account passato e' nullo, ergo non valido.";

    /**
     * The string error message for when a null name is passed.
     */
    String MESSAGE_NULL_NAME = "Il nome passato è nullo, ergo non valido.";

    /**
     * The string error message for when a negative or equal to zero balance is passed.
     */
    String MESSAGE_NEGATIVE_BALANCE = "Il saldo iniziale dell'account deve essere maggiore di zero.";

    /**
     * The string error message for when a null {@link Account} is passed.
     */
    String MESSAGE_NULL_ACCOUNT = "L'account passato e' nullo.";

    /**
     * The string error message for when a {@link Account} is passed but it is already contained in the accounts list.
     */
    String MESSAGE_ACCOUNT_ALREADY_EXISTS = "Nella lista esiste gia' un account con questo nome o ID.";

    /**
     * The string error message for when a {@link Account} is passed to be removed but it is not contained in the accounts list.
     */
    String MESSAGE_ACCOUNT_DOES_NOT_EXIST = "L'account passato non e' contenuto nella lista.";

    /**
     * Getter method for the {@code name} field of the {@link Account}.
     *
     * @return the {@code name} field of the {@link Account}.
     */
    String getName();

    /**
     * Setter method for the {@code name} field of the {@link Account}.
     *
     * @param name the new name to set.
     */
    void setName(String name);

    /**
     * Getter method for the {@code ID} field of the {@link Account}.
     *
     * @return the {@code ID} field of the {@link Account}.
     */
    @Override
    int getID();

    /**
     * Getter method for the {@code accountType} field of the {@link Account}.
     *
     * @return the {@code accountType} field of the {@link Account}.
     */
    AccountType getAccountType();

    /**
     * Setter method for the {@code accountType} field of the {@link Account}.
     *
     * @param accountType the new type to set.
     */
    void setAccountType(AccountType accountType);

    /**
     * Getter method for the {@code initialBalance} field of the {@link Account}.
     *
     * @return the {@code initialBalance} field of the {@link Account}.
     */
    double getInitialBalance();

    /**
     * Setter method for the {@code initialBalance} field of the {@link Account}.
     *
     * @param initialBalance the new type to set.
     */
    void setInitialBalance(double initialBalance);

    /**
     * Getter method for the {@code balance} field of the {@link Account}.
     *
     * @return the {@code balance} field of the {@link Account}.
     */
    double getBalance();

    /**
     * Getter method for the movements list of the {@link Account}.
     *
     * @return the movements list of the {@link Account}.
     */
    List<Movement> getMovementsList();

    /**
     * Getter method for the movements list of the {@link Account} after
     * filtering it according to a certain predicate.
     *
     * @param p the predicate the movements must satisfy.
     * @return the movements list of the {@link Account}.
     */
    List<Movement> getMovementsList(Predicate<Movement> p);

    /**
     * Allows to add a new {@link Movement} to the {@code movementsList}.
     *
     * @param movement the movement to be added.
     */
    void addMovement(Movement movement);
}
