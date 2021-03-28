package it.unicam.cs.pa.jbudget105053.model;

import java.util.Date;
import java.util.List;

/**
 * This interface extends the interface {@link HasID} and is implemented by all the classes that have the
 * responsibility to manage a transaction.
 * It allows to get the information related to the transaction: ID, date, total money amount, list of all
 * the movements associated with the transaction and the list of all the tags associated to the transaction.
 * The value obtainable with the method {@code getTotalAmount()} represents the total variation of the
 * movements of the transaction.
 */
public interface Transaction extends HasID {
    /**
     * The string error message for when an ID smaller than 1 is passed.
     */
    String MESSAGE_WRONG_ID = "L'ID' della transazione passato e' minore di 1, ergo non valido.";

    /**
     * The string error message for when a null date is passed.
     */
    String MESSAGE_NULL_DATE = "La data della transazione passata e' nulla, ergo non valida.";

    /**
     * The string error message for when a null {@link Transaction} is passed.
     */
    String MESSAGE_NULL_TRANSACTION = "La transazione passata e' nulla, ergo non valida.";

    /**
     * The string error message for when a {@link Transaction} is passed but it is already contained in the transactions list.
     */
    String MESSAGE_TRANSACTION_ALREADY_EXISTS = "La transazione passata e' gia' contenuta nella lista.";

    /**
     * The string error message for when a {@link Transaction} is passed to be removed but it is not contained in the transactions list.
     */
    String MESSAGE_TRANSACTION_DOES_NOT_EXIST = "La transazione passata non e' contenuta nella lista.";

    /**
     * Getter method for the ID field of the {@link Transaction}.
     *
     * @return the ID of the {@link Transaction}.
     */
    int getID();

    /**
     * Getter method for the movements list of the {@link Transaction}.
     *
     * @return the list of all movements of the {@link Transaction}.
     */
    List<Movement> getMovements();

    /**
     * Allows to add a new {@link Movement} to the movements list.
     *
     * @param m the {@link Movement} to add.
     */
    void addMovement(Movement m);

    /**
     * Allows to remove a {@link Movement} from the movements list.
     *
     * @param m the {@link Movement} to remove.
     */
    void removeMovement(Movement m);

    /**
     * Getter method for the tags list of the {@link Transaction}.
     *
     * @return the list of all tags of the {@link Transaction}.
     */
    List<Tag> getTag();

    /**
     * Allows to add a new {@link Tag} to the movements list.
     *
     * @param t the {@link Tag} to add.
     */
    void addTag(Tag t);

    /**
     * Allows to remove a {@link Tag} from the movements list.
     *
     * @param t the {@link Tag} to remove.
     */
    void removeTag(Tag t);

    /**
     * Returns a string with all the IDs of the tags associated with this
     * {@link Transaction} separated by a comma.
     *
     * @return a String with the IDs of all the tags of the {@link Transaction}.
     */
    String getTagsID();

    /**
     * Getter method for the date field of the {@link Transaction}.
     *
     * @return the date of the {@link Transaction}.
     */
    Date getDate();

    /**
     * Setter method for the date field of the {@link Transaction}.
     *
     * @param d the new date to set.
     */
    void setDate(Date d);

    /**
     * Getter method for the total amount field of the {@link Transaction}.
     *
     * @return the total amount of the {@link Transaction}.
     */
    double getTotalAmount();
}
