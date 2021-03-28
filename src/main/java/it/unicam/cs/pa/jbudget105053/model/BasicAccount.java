package it.unicam.cs.pa.jbudget105053.model;

import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class implements the interface {@link Account} and has the responsibility of managing a basic account.
 * It allows to get the main information from the account: ID, type of the account, name and initial balance.
 * It also allows to calculate the current balance and it is possible to view the entire list of movements
 * associated with this account as well as a list of all movements that satisfy a particular predicate.
 *
 * @author Tommaso Catervi
 */
public class BasicAccount implements Account {
    private final int ID;
    private AccountType accountType;
    private String name;
    private double initialBalance;
    private double balance;
    private final List<Movement> movementsList = new LinkedList<>();

    /**
     * Constructs a {@link BasicAccount} with the given parameters after having controlled them.
     *
     * @param ID             the value used to set the {@code ID} field in the {@link BasicAccount}.
     * @param accountType    the value used to set the {@code accountType} field in the {@link BasicAccount}.
     * @param name           the value used to set the {@code name} field in the {@link BasicAccount}.
     * @param initialBalance the value used to set the {@code initialBalance} field in the {@link BasicAccount}.
     */
    public BasicAccount(int ID, AccountType accountType, String name, double initialBalance) {
        this.ID = controlID(ID);
        this.accountType = controlType(accountType);
        this.name = controlName(name).toUpperCase();
        this.initialBalance = controlInitialBalance(initialBalance);
    }

    /**
     * Controls that the given ID is not smaller than 1.
     *
     * @param ID the value to control.
     * @return the controlled ID.
     */
    private int controlID(int ID) {
        if (ID < 1)
            throw new IllegalArgumentException(Account.MESSAGE_WRONG_ID);
        return ID;
    }

    /**
     * Controls that the given {@link AccountType} is not null.
     *
     * @param accountType the value to control.
     * @return the controlled {@link AccountType}.
     */
    private AccountType controlType(AccountType accountType) {
        if (Objects.isNull(accountType))
            throw new NullPointerException(Account.MESSAGE_NULL_TYPE);
        return accountType;
    }

    /**
     * Controls that the given name is not null.
     *
     * @param name the value to control.
     * @return the controlled name.
     */
    private String controlName(String name) {
        if (Objects.isNull(name))
            throw new NullPointerException(Account.MESSAGE_NULL_NAME);
        return name;
    }

    /**
     * Controls that the given initial balance is not smaller than or equal to zero.
     *
     * @param initialBalance the value to control.
     * @return the controlled initial balance.
     */
    private double controlInitialBalance(double initialBalance) {
        if (initialBalance < 0)
            throw new IllegalArgumentException(Account.MESSAGE_NEGATIVE_BALANCE);
        return initialBalance;
    }

    /**
     * Getter method for the {@code accountType} field in the {@link BasicAccount}.
     *
     * @return the type of this {@link BasicAccount}.
     */
    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Setter method for the {@code accountType} field in the {@link BasicAccount}.
     *
     * @param accountType the new accountType to set.
     */
    @Override
    public void setAccountType(AccountType accountType) {
        this.accountType = controlType(accountType);
    }

    /**
     * Getter method for the {@code name} field in the {@link BasicAccount}.
     *
     * @return the name of this {@link BasicAccount}.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Setter method for the {@code name} field in the {@link BasicAccount}.
     *
     * @param name the new name to set.
     */
    @Override
    public void setName(String name) {
        this.name = controlName(name).toUpperCase();
    }

    /**
     * Getter method for the {@code ID} field in the {@link BasicAccount}.
     *
     * @return the ID of this {@link BasicAccount}.
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Getter method for the {@code initialBalance} field in the {@link BasicAccount}.
     *
     * @return the initialBalance of this {@link BasicAccount}.
     */
    @Override
    public double getInitialBalance() {
        return initialBalance;
    }

    /**
     * Setter method for the {@code initialBalance} field in the {@link BasicAccount}.
     *
     * @param initialBalance the new initial balance to set.
     */
    @Override
    public void setInitialBalance(double initialBalance) {
        this.initialBalance = controlInitialBalance(initialBalance);
    }

    /**
     * Getter method for the {@code balance} field of this {@link BasicAccount}.
     * Returns the balance after having calculated it using the private method
     * {@code generateBalance()}.
     *
     * @return the balance of this {@link BasicAccount}.
     */
    @Override
    public double getBalance() {
        generateBalance();
        return balance;
    }

    /**
     * Calculates the current value of the {@code balance} field in the {@link BasicAccount}.
     * The value of the {@code balance} field is being set equal to the value of {@code initialBalance}
     * and then incremented or decremented with the total variation of the movements depending on the
     * {@link AccountType} of this {@link BasicAccount}.
     */
    private void generateBalance() {
        balance = initialBalance;
        if (accountType == AccountType.ASSET)
            balance += calculateMovementsTotalVariation();
        if (accountType == AccountType.LIABILITY)
            balance -= calculateMovementsTotalVariation();
    }

    /**
     * Calculates the current total variation of the movements of this {@link BasicAccount}.
     *
     * @return the current total variation of the movements.
     */
    private double calculateMovementsTotalVariation() {
        double variation = 0;
        for (Movement movement : movementsList) {
            if (movement.getMovementDate().compareTo(Date.from(Instant.now())) < 0) {
                if (movement.getMovementType().equals(MovementType.INCREMENT))
                    variation += movement.getAmount();
                if (movement.getMovementType().equals(MovementType.DECREMENT))
                    variation -= movement.getAmount();
            }
        }
        return variation;
    }

    /**
     * Getter method for the list {@code movementList} which contains all the {@link Movement}
     * associated with this {@link BasicAccount}.
     *
     * @return the {@code movementsList} of this {@link BasicAccount}.
     */
    @Override
    public List<Movement> getMovementsList() {
        return movementsList;
    }

    /**
     * Returns the List {@code movementList} after filtering it according to the
     * {@link Predicate} {@code p}.
     *
     * @return the filtered {@code movementsList}.
     */
    @Override
    public List<Movement> getMovementsList(Predicate<Movement> p) {
        return movementsList.stream().filter(p).collect(Collectors.toList());
    }

    /**
     * Allows to add a new {@link Movement} to the {@code movementsList} in
     * the {@link BasicAccount} as long as it is not null and it is not already
     * contained in the {@code movementsList}.
     *
     * @param movement the new {@link Movement} to add.
     */
    @Override
    public void addMovement(Movement movement) {
        if (movementsList.contains(controlMovement(movement)))
            throw new IllegalArgumentException(MovementException.MESSAGE_MOVEMENT_ALREADY_EXISTS);
        movementsList.add(movement);
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
     * Two {@link BasicAccount} are equal if they have the same {@code ID} and {@code name}.
     *
     * @param o the object compared to this.
     * @return true if this object is equal to the given object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicAccount that = (BasicAccount) o;
        return ID == that.ID ||
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ID);
    }

    @Override
    public String toString() {
        return ID + ") " + name + " (" + accountType + "), Saldo attuale: " + "\u20ac" + getBalance();
    }
}