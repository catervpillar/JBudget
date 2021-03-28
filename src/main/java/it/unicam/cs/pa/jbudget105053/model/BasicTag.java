package it.unicam.cs.pa.jbudget105053.model;

import java.util.Objects;

/**
 * This class implements the interface {@link Tag} and has the responsibility of defining a tag/category.
 * It allows to get the information related to the tag: ID, name and description.
 * It is also possible to set new values for the fields {@code name} and {@code description} via the
 * appropriate setter methods.
 *
 * @author Tommaso Catervi
 */
public class BasicTag implements Tag {
    private final int ID;
    private String name;
    private String description;

    /**
     * Constructs a {@link BasicTag} with the given parameters after having controlled them.
     *
     * @param ID          the value used to set the {@code ID} field in the {@link BasicTag}.
     * @param name        the value used to set the {@code name} field in the {@link BasicTag}.
     * @param description the value used to set the {@code description} field in the {@link BasicTag}.
     */
    public BasicTag(int ID, String name, String description) {
        this.ID = controlID(ID);
        this.name = controlName(name).toUpperCase();
        this.description = controlDescription(description);
    }

    /**
     * Controls that the given ID is not smaller than 1.
     *
     * @param ID the value to control.
     * @return the controlled ID.
     */
    private int controlID(int ID) {
        if (ID < 1)
            throw new IllegalArgumentException(Tag.MESSAGE_WRONG_ID);
        return ID;
    }

    /**
     * Controls that the given name is not null.
     *
     * @param name the value to control.
     * @return the controlled name.
     */
    private String controlName(String name) {
        if (Objects.isNull(name))
            throw new NullPointerException(Tag.MESSAGE_NULL_NAME);
        return name;
    }

    /**
     * Controls that the given description is not null.
     *
     * @param description the value to control.
     * @return the controlled description.
     */
    private String controlDescription(String description) {
        if (Objects.isNull(description))
            throw new NullPointerException(Tag.MESSAGE_NULL_DESCRIPTION);
        return description;
    }

    /**
     * Getter method for the {@code ID} field in the {@link BasicTag}.
     *
     * @return the ID of this {@link BasicTag}.
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Getter method for the {@code name} field in the {@link BasicTag}.
     *
     * @return the name of this {@link BasicTag}.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Setter method for the {@code name} field in the {@link BasicTag}.
     *
     * @param newName the new name to set.
     */
    @Override
    public void setName(String newName) {
        this.name = controlName(newName).toUpperCase();
    }

    /**
     * Getter method for the {@code description} field in the {@link BasicTag}.
     *
     * @return the description of this {@link BasicTag}.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Setter method the {@code description} field in the {@link BasicTag}.
     *
     * @param newDescription the new description to set.
     */
    @Override
    public void setDescription(String newDescription) {
        this.description = controlDescription(newDescription);
    }

    /**
     * Two tags are equal if they have the same {@code ID} and/or {@code name}.
     *
     * @param o the object compared to this.
     * @return true if this object is equal to the given object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicTag basicTag = (BasicTag) o;
        return ID == basicTag.ID ||
                name.equals(basicTag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name);
    }

    @Override
    public String toString() {
        return ID + ") " + name + " - " + description;
    }
}