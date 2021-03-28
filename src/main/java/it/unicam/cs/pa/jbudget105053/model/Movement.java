package it.unicam.cs.pa.jbudget105053.model;

import java.util.Date;
import java.util.List;

/**
 * This interface extends the interface {@link HasID} and is implemented by all the classes that have the
 * responsibility to manage a single movement.
 * It allows to get the information related to the movement: ID, type of movement, money amount, date,
 * associated account, associated transaction and the list of all the tags associated with the movement.
 * Each movement derives its date from the transaction it is associated with.
 */
public interface Movement extends HasID {
    /**
     * Getter method for the ID field the {@link Movement}.
     *
     * @return the ID of the {@link Movement}.
     */
    @Override
    int getID();

    /**
     * Getter method for the type field of the {@link Movement}.
     *
     * @return the type of the {@link Movement}.
     */
    MovementType getMovementType();

    /**
     * Getter method for the amount field of the {@link Movement}.
     *
     * @return the amount of the {@link Movement}.
     */
    double getAmount();

    /**
     * Getter method for the date field of the {@link Movement}.
     *
     * @return the date of the{@link Movement}.
     */
    Date getMovementDate();

    /**
     * Setter method for the date field of the {@link Movement}.
     *
     * @param movementDate the new date to set.
     */
    void setDate(Date movementDate);

    /**
     * Getter method for the tags list of the {@link Movement}.
     *
     * @return the list of all tags of the the {@link Movement}.
     */
    List<Tag> getTag();

    /**
     * Allows to add a new {@link Tag} to the tags list.
     *
     * @param t the {@link Tag} to add.
     */
    void addTag(Tag t);

    /**
     * Allows to remove a {@link Tag} from the tags list.
     *
     * @param t the {@link Tag} to remove.
     */
    void removeTag(Tag t);

    /**
     * Returns a string with all the IDs of the tags associated with the
     * {@link Movement} separated by a comma.
     *
     * @return a String with the IDs of all the tags of the {@link Movement}.
     */
    String getTagsID();

    /**
     * Getter method for the account field of the {@link Movement}.
     *
     * @return the {@link Account} of the {@link Movement}.
     */
    Account getAccount();

    /**
     * Getter method for the transaction field of the {@link Movement}.
     *
     * @return the {@link Transaction} of the {@link Movement}.
     */
    Transaction getTransaction();

    /**
     * Setter method for the transaction field of the {@link Movement}.
     *
     * @param t the {@link Transaction} to set.
     */
    void setTransaction(Transaction t);
}
