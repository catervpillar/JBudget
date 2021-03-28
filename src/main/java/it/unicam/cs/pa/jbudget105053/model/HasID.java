package it.unicam.cs.pa.jbudget105053.model;

/**
 * This interface is implemented by all classes whose objects have a unique ID field that
 * identifies them.
 *
 * @author Tommaso Catervi
 */
public interface HasID {
    /**
     * Getter method for the ID field.
     *
     * @return the value of the ID field.
     */
    int getID();
}
