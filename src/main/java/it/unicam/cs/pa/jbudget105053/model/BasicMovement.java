package it.unicam.cs.pa.jbudget105053.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class implements the interface {@link Movement} and has the responsibility of managing a single basic
 * movement. It allows to get the information related to the movement: ID, type of movement, money amount, date,
 * associated account, associated transaction and the list of all the tags associated with the movement.
 * Each movement derives its date from the transaction it is associated with.
 *
 * @author Tommaso Catervi
 */
public class BasicMovement implements Movement {
    private final int ID;
    private final MovementType movementType;
    private final double amount;
    private Date movementDate;
    private final List<Tag> tagsList = new LinkedList<>();
    private Transaction transaction;
    private final Account account;

    /**
     * Constructs a {@link BasicMovement} with the given parameters after having controlled them.
     *
     * @param ID           the value used to set the {@code ID} field in the {@link BasicMovement}.
     * @param movementType the value used to set the {@code movementType} field in the {@link BasicMovement}.
     * @param amount       the value used to set the {@code amount} field in the {@link BasicMovement}.
     * @param account      the value used to set the {@code account} field in the {@link BasicMovement}.
     */
    public BasicMovement(int ID, MovementType movementType, double amount, Account account) {
        this.ID = controlID(ID);
        this.movementType = controlType(movementType);
        this.amount = controlAmount(amount);
        this.account = controlAccount(account);
    }

    /**
     * Controls that the given ID is not smaller than 1.
     *
     * @param ID the value to control.
     * @return the controlled ID.
     */
    private int controlID(int ID) {
        if (ID < 1)
            throw new IllegalArgumentException(MovementException.MESSAGE_WRONG_ID);
        return ID;
    }


    /**
     * Controls that the given {@link MovementType} is not null.
     *
     * @param movementType the value to control.
     * @return the controlled type.
     */
    private MovementType controlType(MovementType movementType) {
        if (Objects.isNull(movementType))
            throw new NullPointerException(MovementException.MESSAGE_NULL_TYPE);
        return movementType;
    }

    /**
     * Controls that the given amount is not smaller than or equal to zero.
     *
     * @param amount the value to control.
     * @return the controlled amount.
     */
    private double controlAmount(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException(MovementException.MESSAGE_NEGATIVE_AMOUNT);
        return amount;
    }

    /**
     * Controls that the given {@link Account} is not null.
     *
     * @param account the value to control.
     * @return the controlled account.
     */
    private Account controlAccount(Account account) {
        if (Objects.isNull(account))
            throw new NullPointerException(MovementException.MESSAGE_NULL_ACCOUNT);
        return account;
    }

    /**
     * Controls that the given {@link Transaction} is not null.
     *
     * @param transaction the value to control.
     * @return the controlled transaction.
     */
    private Transaction controlTransaction(Transaction transaction) {
        if (Objects.isNull(transaction))
            throw new NullPointerException(MovementException.MESSAGE_NULL_TRANSACTION);
        return transaction;
    }

    /**
     * Getter method for the {@code ID} field in the {@link BasicMovement}.
     *
     * @return the ID of this {@link BasicMovement}.
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Getter method for the {@code movementType} field in the {@link BasicMovement}.
     *
     * @return the type of this {@link BasicMovement}.
     */
    @Override
    public MovementType getMovementType() {
        return movementType;
    }

    /**
     * Getter method for the {@code amount} field in the {@link BasicMovement}.
     *
     * @return the amount of this {@link BasicMovement}.
     */
    @Override
    public double getAmount() {
        return amount;
    }

    /**
     * Getter method for the {@code movementDate} field in the {@link BasicMovement}.
     *
     * @return the date of this {@link BasicMovement}.
     */
    @Override
    public Date getMovementDate() {
        return movementDate;
    }

    /**
     * Getter method for the list {@code tagList} which contains all the {@link Tag} associated with this
     * {@link BasicMovement}.
     *
     * @return the list of tags of this {@link BasicMovement}.
     */
    @Override
    public List<Tag> getTag() {
        return tagsList;
    }

    /**
     * Allows to add a new {@link Tag} to the {@code tagList} as long as the given parameter is not
     * null and doesn't already exist in the {@code tagList}.
     *
     * @param t the new {@link Tag} to add to the tag list.
     */
    @Override
    public void addTag(Tag t) {
        if (tagsList.contains(controlTag(t)))
            throw new IllegalArgumentException(Tag.MESSAGE_TAG_ALREADY_EXISTS);
        tagsList.add(t);
    }

    /**
     * Allows to remove a {@link Tag} from the {@code tagList} as long as the given parameter is not
     * null and is contained in the {@code tagList}.
     *
     * @param t the {@link Tag} to remove from the tags list.
     */
    @Override
    public void removeTag(Tag t) {
        if (!tagsList.contains(controlTag(t)))
            throw new IllegalArgumentException(Tag.MESSAGE_TAG_DOES_NOT_EXIST);
        tagsList.remove(t);
    }

    /**
     * Controls that the given {@link Tag} is not null.
     *
     * @param t the {@link Tag} to control.
     * @return the controlled {@link Tag}.
     */
    private Tag controlTag(Tag t) {
        if (Objects.isNull(t))
            throw new NullPointerException(Tag.MESSAGE_NULL_TAG);
        return t;
    }

    /**
     * Getter method for the {@code transaction} field in the {@link BasicMovement}.
     *
     * @return the transaction of this {@link BasicMovement}.
     */
    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets the value of the {@code transaction} field in the {@link BasicMovement} as
     * long as the given parameter is not null.
     *
     * @param transaction the new transaction to set.
     */
    @Override
    public void setTransaction(Transaction transaction) {
        this.transaction = controlTransaction(transaction);
    }

    /**
     * Setter method for the {@code movementDate} field in the {@link BasicMovement}.
     *
     * @param movementDate the new date to set.
     */
    @Override
    public void setDate(Date movementDate) {
        this.movementDate = controlDate(movementDate);
    }

    /**
     * Controls that the given date is not null.
     *
     * @param date the date to control.
     * @return the controlled date.
     */
    private Date controlDate(Date date) {
        if (Objects.isNull(date))
            throw new NullPointerException(MovementException.MESSAGE_NULL_DATE);
        return date;
    }

    /**
     * Getter method for the {@code account} field in the {@link BasicMovement}.
     *
     * @return the account of this {@link BasicMovement}.
     */
    @Override
    public Account getAccount() {
        return account;
    }

    /**
     * Two {@link BasicMovement} objects are equal if they have the same {@code ID}.
     *
     * @param o the object compared to this.
     * @return true if this object is equal to the given object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicMovement that = (BasicMovement) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    /**
     * Returns a string with all the IDs of the tags associated with this movement
     * separated by a comma.
     *
     * @return a String with all the IDs of the tags of this {@link BasicMovement}.
     */
    @Override
    public String getTagsID() {
        String stringIDs = "";
        for (Tag t : tagsList) {
            stringIDs = stringIDs.concat(t.getID() + ",");
        }
        return stringIDs;
    }

    @Override
    public String toString() {
        return ID + ") " +
                "Tipo: " + movementType +
                ", Importo: \u20ac" + amount +
                ", " + "Account: " + account.getName();
    }
}
