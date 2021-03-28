package it.unicam.cs.pa.jbudget105053.persistence;

import it.unicam.cs.pa.jbudget105053.model.Account;
import it.unicam.cs.pa.jbudget105053.model.Movement;

import java.io.IOException;
import java.text.ParseException;

/**
 * This interface is implemented by all the classes that have the responsibility to
 * import all data from specific files of a certain extension into a ledger.
 *
 * @author Tommaso Catervi
 */
public interface ImportManager {
    /**
     * The string error message for when a an error occurs when trying to save data to a specific path.
     */
    String MESSAGE_FAILED_IMPORT = "Non e' stato possibile importare dati dal percorso specificato.";
    /**
     * The string error message for when a {@link Movement} is associated with an {@link Account} that
     * does not exist in the accounts list.
     */
    String MESSAGE_WRONG_ACCOUNT = "Non e' stato possibile completare l'importazione dei movimenti\nper un errore nella lista degli accounts.";

    /**
     * Imports all data from a specific file in a given path.
     *
     * @param path the path from which the file is read.
     * @throws IOException    if something goes wrong.
     * @throws ParseException if something goes wrong.
     */
    void importAll(String path) throws IOException, ParseException;
}
