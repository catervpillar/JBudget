package it.unicam.cs.pa.jbudget105053.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class BasicAccountTest {
    private BasicAccount account;
    private final LedgerMenu ledger = new LedgerMenu();
    BasicTransaction t1;
    BasicTransaction t2;

    @BeforeEach
    void init() {
        this.account = new BasicAccount(1, AccountType.ASSET, "Conto corrente", 15000);

        NullPointerException e1 = assertThrows(NullPointerException.class, () -> new BasicAccount(1, null, "Conto corrente", 15000));
        assertEquals(Account.MESSAGE_NULL_TYPE, e1.getMessage());
        NullPointerException e2 = assertThrows(NullPointerException.class, () -> new BasicAccount(2, AccountType.ASSET, null, 15000));
        assertEquals(Account.MESSAGE_NULL_NAME, e2.getMessage());
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> new BasicAccount(3, AccountType.ASSET, "Conto corrente", -15000));
        assertEquals(Account.MESSAGE_NEGATIVE_BALANCE, e3.getMessage());

        t1 = new BasicTransaction(1, new GregorianCalendar(2020, Calendar.JANUARY, 15).getTime());
        BasicMovement m1 = new BasicMovement(1, MovementType.DECREMENT, 1000, account);
        BasicMovement m2 = new BasicMovement(2, MovementType.INCREMENT, 2000, account);
        t1.addMovement(m1);
        t1.addMovement(m2);

        t2 = new BasicTransaction(2, new GregorianCalendar(2049, Calendar.FEBRUARY, 21).getTime());
        BasicMovement m3 = new BasicMovement(3, MovementType.DECREMENT, 3000, account);
        BasicMovement m4 = new BasicMovement(4, MovementType.INCREMENT, 4000, account);
        t2.addMovement(m3);
        t2.addMovement(m4);

        ledger.addTransaction(t1);
        ledger.addTransaction(t2);
    }

    @Test
    void getBalance() {
        assertEquals(16000, account.getBalance());
    }

    @Test
    void getMovementsList() {
        // lista dei movimenti della transazione t1
        assertEquals(2, account.getMovementsList(movement -> movement.getTransaction().equals(t1)).size());
        // lista dei movimenti della transazione t2
        assertEquals(2, account.getMovementsList(movement -> movement.getTransaction().equals(t2)).size());
        // lista dei movimenti di tipo INCREMENT
        assertEquals(2, account.getMovementsList(movement -> movement.getMovementType().equals(MovementType.INCREMENT)).size());
        // lista dei movimenti di tipo DECREMENT
        assertEquals(2, account.getMovementsList(movement -> movement.getMovementType().equals(MovementType.DECREMENT)).size());
        // lista dei movimenti con amount maggiore di 2000
        assertEquals(2, account.getMovementsList(movement -> movement.getAmount() > 2000).size());
        // lista dei movimenti con amount minore o uguale a 2000
        assertEquals(2, account.getMovementsList(movement -> movement.getAmount() <= 2000).size());
        // lista dei movimenti effettuati nella data della transazione 2
        assertEquals(2, account.getMovementsList(movement -> movement.getMovementDate().equals(t2.getDate())).size());
    }
}