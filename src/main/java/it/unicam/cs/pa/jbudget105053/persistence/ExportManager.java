package it.unicam.cs.pa.jbudget105053.persistence;

import java.io.IOException;

/**
 * This interface is implemented by all the classes that have the responsibility to
 * export all data of a ledger into specific files of a certain extension.
 *
 * @author Tommaso Catervi
 */
public interface ExportManager {
    /**
     * The string error message for when an error occurs when trying to save data to a specific path.
     */
    String MESSAGE_FAILED_EXPORT = "Non e' stato possibile salvare nel percorso specificato.";

    /**
     * Exports all data in a specific file in a given path.
     *
     * @param path the path where the data will be saved.
     * @throws IOException if something goes wrong
     */
    void exportAll(String path) throws IOException;
}
