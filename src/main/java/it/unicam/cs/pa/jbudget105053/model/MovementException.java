package it.unicam.cs.pa.jbudget105053.model;

/**
 * This class extends the class {@link Exception} and has the responsibility to provide description for all
 * possible errors deriving from the creation/removal of a movement.
 *
 * @author Tommaso Catervi
 */
public class MovementException extends Exception {
    /**
     * The string error message for when an ID smaller than 1 is passed.
     */
    public static final String MESSAGE_WRONG_ID = "L'ID del movimento passato e' minore di uno, ergo non valido.";

    /**
     * The string error message for when a null {@link Movement} is passed.
     */
    public static final String MESSAGE_NULL_TYPE = "Il tipo di movimento passato e' nullo, ergo non valido.";

    /**
     * The string error message for when a null {@link Transaction} is passed.
     */
    public static final String MESSAGE_NULL_TRANSACTION = "La transazione passata e' nulla, ergo non valida.";

    /**
     * The string error message for when a null {@link Account} is passed.
     */
    public static final String MESSAGE_NULL_ACCOUNT = "L'account passato e' nullo, ergo non valido.";

    /**
     * The string error message for when a negative {@code amount} is passed.
     */
    public static final String MESSAGE_NEGATIVE_AMOUNT = "L'importo del movimento deve essere maggiore di zero.";

    /**
     * The string error message for when a null date is passed.
     */
    public static final String MESSAGE_NULL_DATE = "La data passata e' nulla, ergo non valida.";

    /**
     * The string error message for when a null {@link Movement} is passed.
     */
    public static final String MESSAGE_NULL_MOVEMENT = "Il movimento passato e' nullo.";

    /**
     * The string error message for when a {@link Movement} is passed but it is already contained in the movements list.
     */
    public static final String MESSAGE_MOVEMENT_ALREADY_EXISTS = "Il movimento passato e' gia' contenuto nella lista dei movimenti.";

    /**
     * The string error message for when a a {@link Movement} to remove is passed but it is not contained in the movements list.
     */
    public static final String MESSAGE_MOVEMENT_DOES_NOT_EXIST = "Il movimento passato non e' contenuto nella lista dei movimenti.";

    /**
     * Constructs a {@link MovementException} with the given message.
     *
     * @param errorMessage the error message of the exception.
     */
    public MovementException(String errorMessage) {
        super(errorMessage);
    }
}