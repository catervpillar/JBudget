package it.unicam.cs.pa.jbudget105053.controller;

import it.unicam.cs.pa.jbudget105053.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LedgerMenuControllerTest {
    private LedgerMenuController controller;

    @BeforeEach
    void init() {
        controller = new LedgerMenuController();
    }

    @Test
    void createMovement() {
        controller.addAccount(AccountType.ASSET, "CONTO", 15000);

        NullPointerException e1 = assertThrows(NullPointerException.class, () -> controller.createMovement(null, 1000, controller.getAccounts().get(0)));
        assertEquals(MovementException.MESSAGE_NULL_TYPE, e1.getMessage());
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> controller.createMovement(MovementType.DECREMENT, -1000, controller.getAccounts().get(0)));
        assertEquals(MovementException.MESSAGE_NEGATIVE_AMOUNT, e2.getMessage());
        NullPointerException e3 = assertThrows(NullPointerException.class, () -> controller.createMovement(MovementType.DECREMENT, 1000, null));
        assertEquals(MovementException.MESSAGE_NULL_ACCOUNT, e3.getMessage());
    }

    @Test
    void createMovementWithID() {
        controller.addAccount(AccountType.ASSET, "POSTEPAY", 500);
        Transaction t = controller.createTransaction(Date.from(Instant.now()));
        t.addMovement(controller.createMovementWithID(1, MovementType.DECREMENT, 100, controller.getAccounts().get(0)));
        controller.addTransaction(t);
        assertFalse(controller.getMovements().isEmpty());
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> controller.createMovementWithID(-1, MovementType.DECREMENT, 20, controller.getAccounts().get(0)));
        assertEquals(MovementException.MESSAGE_WRONG_ID, e.getMessage());
    }

    @Test
    void createTransaction() {
        NullPointerException e1 = assertThrows(NullPointerException.class, () -> controller.createTransaction(null));
        assertEquals(Transaction.MESSAGE_NULL_DATE, e1.getMessage());
    }

    @Test
    void createTransactionWithID() {
        controller.addTransaction(controller.createTransactionWithID(1, Date.from(Instant.now())));
        controller.addTransaction(controller.createTransaction(Date.from(Instant.now())));
        assertEquals(2, controller.getTransactions().get(1).getID());
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> controller.createTransactionWithID(-1, Date.from(Instant.now())));
        assertEquals(Transaction.MESSAGE_WRONG_ID, e.getMessage());
    }
}