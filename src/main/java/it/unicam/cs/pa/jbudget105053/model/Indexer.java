package it.unicam.cs.pa.jbudget105053.model;

import java.util.List;
import java.util.Objects;

/**
 * This class has the responsibility to manage all IDs of all the accounts, tags,
 * transactions and movements of the application.
 *
 * @author Tommaso Catervi
 */
public final class Indexer {
    private int ACCOUNT_ID_GENERATOR = 1;
    private int TAG_ID_GENERATOR = 1;
    private int TRANSACTION_ID_GENERATOR = 1;
    private int MOVEMENT_ID_GENERATOR = 1;
    private static Indexer indexer;

    /**
     * Constructs an {@link Indexer} with no parameters.
     * The constructor is private because this class is a singleton
     * and its only instance can be obtained through the static
     * method {@code getInstance()}.
     */
    private Indexer() {
    }

    /**
     * Returns the only instance of this singleton class after
     * having initialised it if it is null.
     *
     * @return the instance of Indexer.
     */
    public static Indexer getInstance() {
        if (Objects.isNull(indexer))
            indexer = new Indexer();
        return indexer;
    }

    /**
     * Returns a new ID for the construction of an {@link Account}.
     *
     * @return the latest account ID.
     */
    public int generateAccountID() {
        return ACCOUNT_ID_GENERATOR++;
    }

    /**
     * Returns a new ID for the construction of a {@link Tag}.
     *
     * @return the latest tag ID.
     */
    public int generateTagID() {
        return TAG_ID_GENERATOR++;
    }

    /**
     * Returns a new ID for the construction of a {@link Transaction}.
     *
     * @return the latest transaction ID.
     */
    public int generateTransactionID() {
        return TRANSACTION_ID_GENERATOR++;
    }

    /**
     * Returns a new ID for the construction of a {@link Movement}.
     *
     * @return the latest movement ID.
     */
    public int generateMovementID() {
        return MOVEMENT_ID_GENERATOR++;
    }

    /**
     * Upgrades the {@code ACCOUNT_ID_GENERATOR} with the highest ID + 1
     * between all the accounts of an accounts list.
     *
     * @param list the list to iterate.
     */
    public void fixAccountID(List<? extends Account> list) {
        ACCOUNT_ID_GENERATOR = getFixedID(list);
    }

    /**
     * Upgrades the {@code TAG_ID_GENERATOR} with the highest ID + 1
     * between all the tags of a tag list.
     *
     * @param list the list to iterate.
     */
    public void fixTagID(List<? extends Tag> list) {
        TAG_ID_GENERATOR = getFixedID(list);
    }

    /**
     * Upgrades the {@code TRANSACTION_ID_GENERATOR} with the highest ID + 1
     * between all the transactions of a transactions list.
     *
     * @param list the list to iterate.
     */
    public void fixTransactionID(List<? extends Transaction> list) {
        TRANSACTION_ID_GENERATOR = getFixedID(list);
    }

    /**
     * Upgrades the {@code MOVEMENT_ID_GENERATOR} with the highest ID + 1
     * between all the movements of a movements list.
     *
     * @param list the list to iterate.
     */
    public void fixMovementID(List<? extends Movement> list) {
        MOVEMENT_ID_GENERATOR = getFixedID(list);
    }

    /**
     * Returns the highest ID between the IDs of all the elements of the given
     * list plus one.
     *
     * @param list the list of objects extending {@link HasID} to iterate.
     * @return the highest ID + 1.
     */
    private int getFixedID(List<? extends HasID> list) {
        return list.stream().mapToInt(HasID::getID).max().orElse(0) + 1;
    }

    /**
     * Resets all ID counters to 1.
     */
    public void resetAll() {
        ACCOUNT_ID_GENERATOR = 1;
        TAG_ID_GENERATOR = 1;
        TRANSACTION_ID_GENERATOR = 1;
        MOVEMENT_ID_GENERATOR = 1;
    }
}
