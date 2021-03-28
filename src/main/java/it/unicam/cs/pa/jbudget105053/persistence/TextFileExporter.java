package it.unicam.cs.pa.jbudget105053.persistence;

import it.unicam.cs.pa.jbudget105053.controller.Controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the interface {@link ExportManager} and has the responsibility to export all
 * data of the ledger in .txt files in a given path.
 *
 * @author Tommaso Catervi
 */
public class TextFileExporter implements ExportManager {
    private final Controller controller;

    /**
     * Constructs a new {@link TextFileExporter} with the given controller.
     *
     * @param controller the value used to set the {@code controller} field of the {@link TextFileExporter}.
     */
    public TextFileExporter(Controller controller) {
        this.controller = controller;
    }

    /**
     * Takes a list of strings and writes each string in a new line of the file {@code file}.
     *
     * @param list the list to write in the file.
     * @param file the file to write in
     * @throws IOException if something goes wrong.
     */
    private void writeToFile(List<String> list, File file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (String s : list) {
            bw.append(s);
            bw.newLine();
        }
        bw.close();
    }

    /**
     * Creates different files for accounts, tags, transactions and movements in the given
     * path and then saves all data into them.
     *
     * @throws IOException if something goes wrong.
     */
    @Override
    public void exportAll(String path) throws IOException {
        writeToFile(getStringListOfAccounts(), new File(path + "\\accounts.txt"));
        writeToFile(getStringListOfTags(), new File(path + "\\tags.txt"));
        writeToFile(getStringListOfTransaction(), new File(path + "\\transactions.txt"));
        writeToFile(getStringListOfMovements(), new File(path + "\\movements.txt"));
    }

    /**
     * Creates and returns a list of string with a string representation of each account
     * of the accounts list.
     *
     * @return a list with a string representation of all the accounts.
     */
    public List<String> getStringListOfAccounts() {
        List<String> list = new ArrayList<>();
        controller.getAccounts().forEach(a -> list.add(a.getID() +
                ";" + a.getAccountType() + ";" + a.getName() +
                ";" + a.getInitialBalance()));
        return list;
    }

    /**
     * Creates and returns a list of string with a string representation of each tag of
     * the tags list.
     *
     * @return a list with a string representation of all the tags.
     */
    public List<String> getStringListOfTags() {
        List<String> list = new ArrayList<>();
        controller.getTags().forEach(t -> list.add(t.getID() +
                ";" + t.getName() + ";" + t.getDescription()));
        return list;
    }

    /**
     * Creates and returns a list of strings with a string representation of each transaction
     * of the transactions list.
     *
     * @return a list with a string representation of all the transactions.
     */
    public List<String> getStringListOfTransaction() {
        List<String> list = new ArrayList<>();
        controller.getTransactions().forEach(t -> list.add(t.getID() + ";" +
                new SimpleDateFormat("dd-MM-yyyy").format(t.getDate()) + ";" + t.getTagsID()));
        return list;
    }

    /**
     * Creates and returns a list of string with a string representation of each movement
     * of the movements list.
     *
     * @return a list with a string representation of all the movements.
     */
    public List<String> getStringListOfMovements() {
        List<String> list = new ArrayList<>();
        controller.getMovements().forEach(m -> list.add(m.getID() + ";" + m.getMovementType() + ";" + m.getAmount()
                + ";" + m.getAccount().getID() + ";" + m.getTransaction().getID() + ";" + m.getTagsID()));
        return list;
    }
}
