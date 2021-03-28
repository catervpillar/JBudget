package it.unicam.cs.pa.jbudget105053.model;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class implements the interface {@link Transaction} and has the responsibility of managing a basic transaction.
 * It allows to get the information related to the transaction: ID, date, total money amount, list of all the
 * movements associated with the transaction and the list of all the tags associated to the transaction.
 * The value obtainable with the method {@code getTotalAmount()} represents the total variation of the movements
 * of the transaction.
 *
 * @author Tommaso Catervi
 */
public class BasicTransaction implements Transaction {
    private final int ID;
    private final List<Movement> movementsList = new LinkedList<>();
    private final List<Tag> tagsList = new LinkedList<>();
    private Date transactionDate;
    private double totalAmount;

    /**
     * Constructs a {@link BasicTransaction} with the given parameters after having controlled them.
     *
     * @param ID   the value used to set the {@code ID} field of the {@link BasicTransaction}.
     * @param date the value used to set the {@code transactionDate} field of the {@link BasicTransaction}.
     */
    public BasicTransaction(int ID, Date date) {
        this.ID = controlID(ID);
        this.transactionDate = controlDate(date);
    }

    /**
     * Controls that the given ID is not smaller than 1.
     *
     * @param ID the value to control.
     * @return the controlled date.
     */
    private int controlID(int ID) {
        if (ID < 1)
            throw new IllegalArgumentException(Transaction.MESSAGE_WRONG_ID);
        return ID;
    }

    /**
     * Controls that the given date is not null.
     *
     * @param transactionDate the value to control.
     * @return the controlled date.
     */
    private Date controlDate(Date transactionDate) {
        if (Objects.isNull(transactionDate))
            throw new NullPointerException(Transaction.MESSAGE_NULL_DATE);
        return transactionDate;
    }

    /**
     * Getter method for the {@code ID} field in the {@link BasicTransaction}.
     *
     * @return the ID of this {@link BasicTransaction}.
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Getter method for the list {@code movementList} which contains all the
     * movements associated with this {@link BasicTransaction}.
     *
     * @return the list of all movements of this {@link BasicTransaction}.
     */
    @Override
    public List<Movement> getMovements() {
        return movementsList;
    }

    /**
     * Allows to add a new {@link Movement} to the {@code movementList} as long as the given
     * parameter is not null and doesn't already exist in the {@code movementList}.
     * When a movement is added to the {@code movementList}, this {@link BasicTransaction} is
     * assigned to the {@code transaction} field of that {@link Movement}.
     * Also, the date of the added {@link Movement} is set equal to the date of this
     * {@link Transaction}.
     *
     * @param movement the {@link Movement} to add.
     */
    @Override
    public void addMovement(Movement movement) {
        if (movementsList.contains(controlMovement(movement)))
            throw new IllegalArgumentException(MovementException.MESSAGE_MOVEMENT_ALREADY_EXISTS);
        movement.setTransaction(this);
        movement.setDate(this.getDate());
        movementsList.add(movement);
    }

    /**
     * Allows to remove a {@link Movement} from the {@code movementList} as long as the given
     * parameter is not null and is contained in the {@code movementList}.
     * When a {@link Movement} is removed from the {@code movementList}, it is also removed from the
     * {@code movementList} of the {@link Account} it is associated with.
     *
     * @param movement the {@link Movement} to remove.
     */
    @Override
    public void removeMovement(Movement movement) {
        if (!movementsList.contains(controlMovement(movement)))
            throw new IllegalArgumentException(MovementException.MESSAGE_MOVEMENT_DOES_NOT_EXIST);
        movementsList.remove(movement);
        movement.getAccount().getMovementsList().remove(movement);
    }

    /**
     * Controls that the given {@link Movement} is not null.
     *
     * @param movement the value to control.
     * @return the controlled {@link Movement}.
     */
    private Movement controlMovement(Movement movement) {
        if (Objects.isNull(movement))
            throw new NullPointerException(MovementException.MESSAGE_NULL_MOVEMENT);
        return movement;
    }

    /**
     * Getter method for the list {@code tagList} which contains all the {@link Tag}
     * associated with this {@link BasicTransaction}.
     *
     * @return the list of tags of this {@link BasicTransaction}.
     */
    @Override
    public List<Tag> getTag() {
        return tagsList;
    }

    /**
     * Allows to add a new {@link Tag} to the {@code tagList} as long as the given parameter
     * is not null and doesn't already exist in the {@code tagList}.
     * When a {@link Tag} is added, it is also added to each {@link Movement}
     * of the {@link BasicTransaction}.
     *
     * @param t the {@link Tag} to add.
     */
    @Override
    public void addTag(Tag t) {
        if (tagsList.contains(controlTag(t)))
            throw new IllegalArgumentException(Tag.MESSAGE_TAG_ALREADY_EXISTS);
        tagsList.add(t);
        movementsList.forEach(m -> {
            if (!m.getTag().contains(t)) m.addTag(t);
        });
    }

    /**
     * Allows to remove a {@link Tag} from the {@code tagList} as long as the given parameter
     * is not null and is contained in the {@code tagList}.
     * When a {@link Tag} is removed, it is also removed from each {@link Movement} of the
     * {@link BasicTransaction}.
     *
     * @param t the {@link Tag} to remove.
     */
    @Override
    public void removeTag(Tag t) {
        if (!tagsList.contains(controlTag(t)))
            throw new IllegalArgumentException(Tag.MESSAGE_TAG_DOES_NOT_EXIST);
        tagsList.remove(t);
        movementsList.forEach(m -> m.getTag().removeIf(tag -> tag.equals(t)));
    }

    /**
     * Controls that the given {@link Tag} is not null.
     *
     * @param t the value to control.
     * @return the controlled {@link Tag}.
     */
    private Tag controlTag(Tag t) {
        if (Objects.isNull(t))
            throw new NullPointerException(Tag.MESSAGE_NULL_TAG);
        return t;
    }

    /**
     * Returns a string with all the IDs of the tags associated with this {@link BasicTransaction}
     * separated by a comma.
     *
     * @return a String with the IDs of all the tags of this {@link BasicTransaction}.
     */
    @Override
    public String getTagsID() {
        String stringIDs = "";
        for (Tag t : tagsList) {
            stringIDs = stringIDs.concat(t.getID() + ",");
        }
        return stringIDs;
    }

    /**
     * Getter method for the {@code transactionDate} field in the {@link BasicTransaction}.
     *
     * @return the date of this {@link BasicTransaction}.
     */
    @Override
    public Date getDate() {
        return transactionDate;
    }

    /**
     * Setter method for the {@code transactionDate} field in the {@link BasicTransaction}.
     * When the date is changed, all movements associated with this {@link BasicTransaction}
     * gets their date changed to the same date too.
     *
     * @param d the new date to set.
     */
    @Override
    public void setDate(Date d) {
        transactionDate = controlDate(d);
        movementsList.forEach(m -> m.setDate(d));
    }

    /**
     * Getter method for the {@code totalAmount} field in the {@link BasicTransaction}.
     * It is returned after having been calculated using the private method
     * {@code generateTotalAmount()}.
     *
     * @return the total amount of this {@link BasicTransaction}.
     */
    @Override
    public double getTotalAmount() {
        generateTotalAmount();
        return totalAmount;
    }

    /**
     * Calculates the current value of the {@code totalAmount} field in the {@link BasicTransaction}
     * which is the total variation of all the movements of the {@link BasicTransaction}.
     * It is being set to zero and then incremented or decremented with the {@code amount} of each
     * {@link Movement} contained in the {@code movementList} depending on the {@link MovementType} of
     * the {@link Movement}.
     */
    private void generateTotalAmount() {
        totalAmount = 0;
        for (Movement movement : movementsList) {
            if (movement.getMovementType().equals(MovementType.INCREMENT))
                totalAmount += movement.getAmount();
            if (movement.getMovementType().equals(MovementType.DECREMENT))
                totalAmount -= movement.getAmount();
        }
    }

    /**
     * Two {@link BasicTransaction} are equal if they have the same {@code ID}.
     *
     * @param o the object compared to this
     * @return true if this object is equal to the given object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicTransaction that = (BasicTransaction) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return ID + ") Data: " +
                new SimpleDateFormat("dd-MM-yyyy").format(transactionDate) +
                ", Variazione totale: \u20ac" + getTotalAmount();
    }
}
