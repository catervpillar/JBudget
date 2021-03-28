package it.unicam.cs.pa.jbudget105053.model;

import it.unicam.cs.pa.jbudget105053.controller.Controller;
import it.unicam.cs.pa.jbudget105053.controller.LedgerMenuController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class IndexerTest {
    private final Controller controller = new LedgerMenuController();

    @BeforeEach
    void init() {
        controller.resetLedger();
    }

    @Test
    void fixAccountID() {
        controller.addAccount(AccountType.ASSET, "CONTO", 1000);
        controller.addAccount(AccountType.ASSET, "CONTO2", 2000);
        assertEquals(3, Indexer.getInstance().generateAccountID());

        controller.resetLedger();
        assertEquals(1, Indexer.getInstance().generateAccountID());

        controller.addAccountWithID(1, AccountType.ASSET, "CARTA", 1000);
        controller.addAccountWithID(2, AccountType.ASSET, "CARTA2", 2000);
        assertEquals(3, Indexer.getInstance().generateAccountID());
    }

    @Test
    void fixTagID() {
        controller.addTag("SPORT", "bla bla bla");
        controller.addTag("CASA", "bla bla bla");
        assertEquals(3, Indexer.getInstance().generateTagID());

        controller.resetLedger();
        assertEquals(1, Indexer.getInstance().generateTagID());

        controller.addTagWithID(1, "SPORT", "bla bla bla");
        controller.addTagWithID(2, "CASA", "bla bla bla");
        assertEquals(3, Indexer.getInstance().generateTagID());
    }

    @Test
    void fixTransactionID() {
        controller.addTransaction(controller.createTransaction(Date.from(Instant.now())));
        controller.addTransaction(controller.createTransaction(Date.from(Instant.now())));
        assertEquals(3, Indexer.getInstance().generateTransactionID());

        controller.resetLedger();
        assertEquals(1, Indexer.getInstance().generateTransactionID());

        controller.addTransaction(controller.createTransactionWithID(1, Date.from(Instant.now())));
        controller.addTransaction(controller.createTransactionWithID(2, Date.from(Instant.now())));
        assertEquals(3, Indexer.getInstance().generateTransactionID());
    }

    @Test
    void fixMovementID() {
        controller.addAccount(AccountType.ASSET, "CONTO", 5000);
        Transaction t = new BasicTransaction(1, Date.from(Instant.now()));
        t.addMovement(controller.createMovement(MovementType.DECREMENT, 100, controller.getAccounts().get(0)));
        t.addMovement(controller.createMovement(MovementType.DECREMENT, 300, controller.getAccounts().get(0)));
        controller.addTransaction(t);
        assertEquals(3, Indexer.getInstance().generateMovementID());

        controller.resetLedger();
        t.getMovements().clear();
        assertEquals(1, Indexer.getInstance().generateMovementID());

        controller.addAccount(AccountType.ASSET, "CONTO", 5000);
        t.addMovement(controller.createMovementWithID(1, MovementType.DECREMENT, 100, controller.getAccounts().get(0)));
        t.addMovement(controller.createMovementWithID(2, MovementType.DECREMENT, 1, controller.getAccounts().get(0)));
        controller.addTransaction(t);
        assertEquals(3, Indexer.getInstance().generateMovementID());
    }
}