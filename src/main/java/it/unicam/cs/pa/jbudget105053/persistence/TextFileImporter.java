package it.unicam.cs.pa.jbudget105053.persistence;

import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements the interface {@link ImportManager} and has the responsibility to import all
 * data from .txt files in a given path into a ledger.
 *
 * @author Tommaso Catervi
 */
public class TextFileImporter implements ImportManager {
    private final Controller controller;
    private final List<Transaction> createdTransactions = new LinkedList<>();

    /**
     * Constructs a new {@link TextFileImporter} with the given controller.
     *
     * @param controller the value used to set the {@code controller} field of the {@link TextFileImporter}.
     */
    public TextFileImporter(Controller controller) {
        this.controller = controller;
    }

    /**
     * Reads the given file and adds each line to a list of strings, then returns the list.
     *
     * @param f the file to read.
     * @return the list of strings containing each line of the file.
     * @throws IOException if something goes wrong.
     */
    private List<String> read(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        List<String> stringList = new ArrayList<>();
        String st;
        while ((st = reader.readLine()) != null) {
            stringList.add(st);
        }
        return stringList;
    }

    /**
     * Gets a list of strings each of which contains the data of each {@link Account}. Then splits
     * each string around the ";" to obtain all the string value of the required parameters for the
     * construction and eventually constructs each {@link Account} making the appropriate type
     * conversion from the strings.
     *
     * @throws IOException if something goes wrong.
     */
    private void importAccounts(String path) throws IOException {
        List<String> accountsString = read(new File(path + "\\accounts.txt"));
        for (String s : accountsString) {
            String[] splitString = s.split(";");
            controller.addAccountWithID(Integer.parseInt(splitString[0]), AccountType.valueOf(splitString[1]),
                    splitString[2], Double.parseDouble(splitString[3]));
        }
    }

    /**
     * Gets a list of strings each of which contains the data of each {@link Tag}. Then splits each
     * string around the ";" to obtain all the string value of the required parameters for the
     * construction and eventually constructs each {@link Tag} making the appropriate type conversion
     * from the strings.
     *
     * @throws IOException if something goes wrong.
     */
    private void importTags(String path) throws IOException {
        List<String> tagsString = read(new File(path + "\\tags.txt"));
        for (String s : tagsString) {
            String[] splitString = s.split(";");
            controller.addTagWithID(Integer.parseInt(splitString[0]), splitString[1], splitString[2]);
        }
    }

    /**
     * Gets a list of strings each of which contains the data of each {@link Transaction}. Then splits each
     * string around the ";" to obtain all the string value of the required parameters for the
     * construction and eventually constructs each {@link Transaction} making the appropriate type conversion
     * from the strings. After its construction, a {@link Transaction} gets back all the tags it had (if it
     * had any) through the apposite method and it is added to the list {@code createdTransactions}.
     *
     * @throws IOException    if something goes wrong.
     * @throws ParseException if something goes wrong.
     */
    private void importTransactions(String path) throws IOException, ParseException {
        List<String> transactionsString = read(new File(path + "\\transactions.txt"));
        for (String s : transactionsString) {
            String[] splitString = s.split(";");
            Transaction newTransaction = (controller.createTransactionWithID(Integer.parseInt(splitString[0]),
                    new SimpleDateFormat("yyyy-MM-dd").parse(splitString[1])));
            if (splitString.length > 2) {
                List<Tag> tagsToAdd = getTagsToAdd(splitString[2]);
                tagsToAdd.forEach(newTransaction::addTag);
            }
            createdTransactions.add(newTransaction);
        }
    }

    /**
     * Gets a list of strings each of which contains the data of each {@link Movement}. Then splits each
     * string around the ";" to obtain all the string value of the required parameters for the
     * construction and eventually constructs each {@link Movement} making the appropriate type conversion
     * from the strings. After its construction, a {@link Movement} gets back all the tags it had (if it
     * had any) through the apposite method and then it is added to the correct {@link Transaction}.
     *
     * @throws IOException if something goes wrong.
     */
    private void importMovements(String path) throws IOException {
        List<String> movementsString = read(new File(path + "\\movements.txt"));
        for (String s : movementsString) {
            String[] splitString = s.split(";");
            Movement newMovement = controller.createMovementWithID(Integer.parseInt(splitString[0]),
                    MovementType.valueOf(splitString[1]), Double.parseDouble(splitString[2]), getMovementAccount(splitString[3]));
            if (splitString.length > 5) {
                List<Tag> tagsToAdd = getTagsToAdd(splitString[5]);
                tagsToAdd.forEach(newMovement::addTag);
            }
            setTransaction(newMovement, Integer.parseInt(splitString[4]));
        }
    }

    /**
     * Finds the {@link Transaction} the {@link Movement} belongs to searching by
     * its ID and then adds the {@link Movement} to it.
     *
     * @param m  the movement to add.
     * @param ID the ID of the {@link Transaction} the {@link Movement} belongs to.
     */
    private void setTransaction(Movement m, int ID) {
        createdTransactions.forEach(t -> {
            if (t.getID() == ID) t.addMovement(m);
        });
    }

    /**
     * Reduce the accounts list to a list that contains the desired {@link Account}
     * only and returns that {@link Account}.
     * If any error occurs and the size of the list is not exactly equal to 1, an
     * exception is thrown.
     *
     * @param s the string value of the ID to search for.
     * @return the {@link Account} with the given ID.
     * @throws IllegalStateException if something goes wrong.
     */
    private Account getMovementAccount(String s) {
        List<Account> l = controller.getAccounts(a -> a.getID() == Integer.parseInt(s));
        if ((l.size() != 1))
            throw new RuntimeException(MESSAGE_WRONG_ACCOUNT);
        return l.get(0);
    }

    /**
     * Returns a list containing all the tags with the IDs contained in the
     * provided string.
     *
     * @param s the string containing the IDs of the tags to return.
     * @return a list with all the tags
     */
    private List<Tag> getTagsToAdd(String s) {
        List<Integer> IDs = List.of(s.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        return controller.getTags().parallelStream().filter(tag -> IDs.contains(tag.getID())).collect(Collectors.toList());
    }

    /**
     * Imports all accounts, tags, transactions and movements and then adds
     * each {@link Transaction} to the {@link Ledger}.
     *
     * @throws IOException    if something goes wrong.
     * @throws ParseException if something goes wrong.
     */
    public void importAll(String path) throws IOException, ParseException {
        importAccounts(path);
        importTags(path);
        importTransactions(path);
        importMovements(path);
        createdTransactions.forEach(controller::addTransaction);
    }
}