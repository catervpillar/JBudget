package it.unicam.cs.pa.jbudget105053.javafx;

/**
 * This interface is implemented by all the classes of the GUI view of the app that
 * have the responsibility to interact with the controller to finalize specific actions.
 *
 * @author Tommaso Catervi
 */
public interface JavaFXEditor {
    /**
     * Executes the specific actions of a JavaFX controller and close its window.
     */
    void executeAction();

    /**
     * Aborts the specific actions of a JavaFX controller and close its window.
     */
    void abortAction();
}
