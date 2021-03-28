package it.unicam.cs.pa.jbudget105053.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class LedgerMenuTest {
    private LedgerMenu ledge;
    private BasicTransaction t1;

    @BeforeEach
    void init() {
        ledge = new LedgerMenu();
        t1 = new BasicTransaction(1, Date.from(Instant.now()));
        ledge.addAccount(AccountType.ASSET, "CONTO CORRENTE", 1000);
    }

    @Test
    void addAccount() {
        assertEquals(1, ledge.getAccounts().size());

        NullPointerException e1 = assertThrows(NullPointerException.class, () -> ledge.addAccount(null, "CONTO CORRENTE", 1000));
        assertEquals(Account.MESSAGE_NULL_TYPE, e1.getMessage());
        NullPointerException e2 = assertThrows(NullPointerException.class, () -> ledge.addAccount(AccountType.ASSET, null, 1000));
        assertEquals(Account.MESSAGE_NULL_NAME, e2.getMessage());
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> ledge.addAccount(AccountType.ASSET, "CONTO CORRENTE", -1000));
        assertEquals(Account.MESSAGE_NEGATIVE_BALANCE, e3.getMessage());
    }

    @Test
    void addAccountWithID() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ledge.addAccountWithID(-2, AccountType.ASSET, "CONTO 1", 500));
        assertEquals(Account.MESSAGE_WRONG_ID, e.getMessage());
        NullPointerException e1 = assertThrows(NullPointerException.class, () -> ledge.addAccountWithID(5, null, "CONTO 2", 500));
        assertEquals(Account.MESSAGE_NULL_TYPE, e1.getMessage());
        NullPointerException e2 = assertThrows(NullPointerException.class, () -> ledge.addAccountWithID(5, AccountType.ASSET, null, 500));
        assertEquals(Account.MESSAGE_NULL_NAME, e2.getMessage());
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> ledge.addAccountWithID(6, AccountType.ASSET, "CONTO 4", -500));
        assertEquals(Account.MESSAGE_NEGATIVE_BALANCE, e3.getMessage());
    }

    @Test
    void removeAccount() {
        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> ledge.removeAccount(new BasicAccount(2, AccountType.ASSET, "CASSA CONTANTE", 1000)));
        assertEquals(Account.MESSAGE_ACCOUNT_DOES_NOT_EXIST, e1.getMessage());
        NullPointerException e2 = assertThrows(NullPointerException.class, () -> ledge.removeAccount(null));
        assertEquals(Account.MESSAGE_NULL_ACCOUNT, e2.getMessage());
    }

    @Test
    void addTransaction() {
        ledge.addTransaction(t1);
        assertTrue(ledge.getTransactions().contains(t1));
        assertThrows(IllegalArgumentException.class, () -> ledge.addTransaction(t1));
        assertThrows(NullPointerException.class, () -> ledge.addTransaction(null));
    }

    @Test
    void removeTransaction() {
        BasicMovement m1 = new BasicMovement(1, MovementType.DECREMENT, 200, ledge.getAccounts().get(0));
        BasicMovement m2 = new BasicMovement(2, MovementType.DECREMENT, 0.99, ledge.getAccounts().get(0));
        t1.addMovement(m1);
        t1.addMovement(m2);
        ledge.addTransaction(t1);
        ledge.removeTransaction(t1);

        assertFalse(ledge.getTransactions().contains(t1));
        assertThrows(IllegalArgumentException.class, () -> ledge.removeTransaction(t1));
        assertThrows(NullPointerException.class, () -> ledge.removeTransaction(null));
        assertFalse(ledge.getAccounts().get(0).getMovementsList().contains(m1));
        assertFalse(ledge.getAccounts().get(0).getMovementsList().contains(m2));
    }

    @Test
    void addTag() {
        ledge.addTag("Bollette", "bla bla bla");
        assertFalse(ledge.getTags().isEmpty());
        NullPointerException e1 = assertThrows(NullPointerException.class, () -> ledge.addTag(null, "bla bla bla"));
        assertEquals(Tag.MESSAGE_NULL_NAME, e1.getMessage());
        NullPointerException e2 = assertThrows(NullPointerException.class, () -> ledge.addTag("AUTO", null));
        assertEquals(Tag.MESSAGE_NULL_DESCRIPTION, e2.getMessage());
    }

    @Test
    void addTagWithID() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ledge.addTagWithID(-2, "tag1", "blablabla"));
        assertEquals(Tag.MESSAGE_WRONG_ID, e.getMessage());
        NullPointerException e2 = assertThrows(NullPointerException.class, () -> ledge.addTagWithID(1, null, "blablabla"));
        assertEquals(Tag.MESSAGE_NULL_NAME, e2.getMessage());
        NullPointerException e3 = assertThrows(NullPointerException.class, () -> ledge.addTagWithID(1, "tag2", null));
        assertEquals(Tag.MESSAGE_NULL_DESCRIPTION, e3.getMessage());
    }

    @Test
    void removeTag() {
        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> ledge.removeTag(new BasicTag(1, "Bollette", "bla bla bla")));
        assertEquals(Tag.MESSAGE_TAG_DOES_NOT_EXIST, e1.getMessage());
        NullPointerException e2 = assertThrows(NullPointerException.class, () -> ledge.removeTag(null));
        assertEquals(Tag.MESSAGE_NULL_TAG, e2.getMessage());
    }

    @Test
    void getAccounts() {
        ledge.addAccount(AccountType.ASSET, "PREPAGATA", 800);
        ledge.addAccount(AccountType.ASSET, "CASSA CONTANTE", 1500);
        ledge.addAccount(AccountType.LIABILITY, "PRESTITO MACCHINA", 25000);
        ledge.addAccount(AccountType.LIABILITY, "DEBITI DI GIOCO", 50000);
        BasicMovement m1 = new BasicMovement(1, MovementType.DECREMENT, 1000, ledge.getAccounts().get(0));
        t1.addMovement(m1);
        ledge.addTransaction(t1);

        // lista degli account con almeno un movimento registrato
        assertEquals(1, ledge.getAccounts(account -> account.getMovementsList().size() > 0).size());
        // lista degli account di tipo ASSET
        assertEquals(3, ledge.getAccounts(account -> account.getAccountType().equals(AccountType.ASSET)).size());
        // lista degli account di tipo LIABILITY
        assertEquals(2, ledge.getAccounts(account -> account.getAccountType().equals(AccountType.LIABILITY)).size());
        // lista degli account con balance maggiore di 14000
        assertEquals(2, ledge.getAccounts(account -> account.getBalance() > 14000).size());
        // lista degli account con balance minore o uguale di 14000
        assertEquals(3, ledge.getAccounts(account -> account.getBalance() <= 14000).size());
        // lista degli account con balance compreso tra 1000 di 3000
        assertEquals(1, ledge.getAccounts(account -> account.getBalance() > 1000 && account.getBalance() < 3000).size());
    }

    @Test
    void getTransactions() {
        BasicMovement m1 = new BasicMovement(1, MovementType.DECREMENT, 5000, ledge.getAccounts().get(0));
        t1.addMovement(m1);
        assertEquals(-5000, t1.getTotalAmount());

        BasicTransaction t2 = new BasicTransaction(2, new GregorianCalendar(2012, Calendar.DECEMBER, 12).getTime());
        BasicMovement m2 = new BasicMovement(2, MovementType.DECREMENT, 100, ledge.getAccounts().get(0));
        BasicMovement m3 = new BasicMovement(3, MovementType.INCREMENT, 600, ledge.getAccounts().get(0));
        t2.addMovement(m2);
        t2.addMovement(m3);

        BasicTransaction t3 = new BasicTransaction(3, new GregorianCalendar(2002, Calendar.MAY, 18).getTime());
        ledge.addTransaction(t1);
        ledge.addTransaction(t2);
        ledge.addTransaction(t3);

        // lista delle transazioni con almeno un movimento
        assertEquals(2, ledge.getTransactions(transaction -> transaction.getMovements().size() > 0).size());
        // lista delle transazioni con un total amount maggiore di zero
        assertEquals(1, ledge.getTransactions(transaction -> transaction.getTotalAmount() > 0).size());
        // lista delle transazioni con un total amount minore di zero
        assertEquals(1, ledge.getTransactions(transaction -> transaction.getTotalAmount() < 0).size());
        // lista delle transazioni con nessun tag in lista
        assertEquals(0, ledge.getTransactions(transaction -> transaction.getTag().size() > 0).size());
        // lista delle transazioni precedenti al 15 gennaio 2014
        assertEquals(2, ledge.getTransactions(transaction ->
                transaction.getDate().compareTo(new GregorianCalendar(2014, Calendar.JANUARY, 15).getTime()) < 0).size());
    }
}