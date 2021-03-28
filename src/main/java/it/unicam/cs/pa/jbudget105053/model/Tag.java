package it.unicam.cs.pa.jbudget105053.model;

/**
 * This interface extends the interface {@link HasID} and is implemented by all the classes that have
 * the responsibility to define a tag/category.
 * It allows to get the information related to the tag: ID, name and description.
 * It is also possible to set new values for the fields {@code name} and {@code description} via the
 * appropriate setter methods.
 */
public interface Tag extends HasID {
    /**
     * The string error message for when a ID smaller than 1 name is passed.
     */
    String MESSAGE_WRONG_ID = "L'ID passato e' minore di uno, ergo non valido.";

    /**
     * The string error message for when a null name is passed.
     */
    String MESSAGE_NULL_NAME = "Il nome passato e' nullo, ergo non valido.";

    /**
     * The string error message for when a null description is passed.
     */
    String MESSAGE_NULL_DESCRIPTION = "La descrizione passata e' nulla, ergo non valida.";

    /**
     * The string error message for when a null {@link Tag} is passed.
     */
    String MESSAGE_NULL_TAG = "Il tag passato e' nullo, ergo non valido.";

    /**
     * The string error message for when a {@link Tag} is passed but it is already contained in the movements list.
     */
    String MESSAGE_TAG_ALREADY_EXISTS = "Nella lista e' gia' contenuto questo tag.";

    /**
     * The string error message for when a {@link Tag} is passed to be removed but it is not contained in the movements list.
     */
    String MESSAGE_TAG_DOES_NOT_EXIST = "Il tag passato non e' contenuto nella lista.";

    /**
     * Getter method for the ID field of the {@link Tag}.
     *
     * @return the ID of the {@link Tag}.
     */
    @Override
    int getID();

    /**
     * Getter method for the name field of the {@link Tag}.
     *
     * @return the name of the {@link Tag}.
     */
    String getName();

    /**
     * Setter method for the name field of the {@link Tag}.
     *
     * @param newName the new name to set.
     */
    void setName(String newName);

    /**
     * Getter method for the description field of the {@link Tag}.
     *
     * @return the description of the {@link Tag}.
     */
    String getDescription();

    /**
     * Setter method for the description field of the {@link Tag}.
     *
     * @param newDescription the new description to set.
     */
    void setDescription(String newDescription);
}
