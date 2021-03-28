package it.unicam.cs.pa.jbudget105053.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BasicTransactionTest {
    private BasicTransaction t1;
    private BasicAccount a1;
    private BasicTag tag1;
    private BasicMovement m1;

    @BeforeEach
    void init() {
        this.t1 = new BasicTransaction(1, Date.from(Instant.now()));
        a1 = new BasicAccount(1, AccountType.ASSET, "CONTO CORRENTE", 15000);
        tag1 = new BasicTag(1, "BOLLETTE", "bla bla bla");
        m1 = new BasicMovement(1, MovementType.DECREMENT, 2000, a1);
        NullPointerException e = assertThrows(NullPointerException.class, () -> new BasicTransaction(2, null));
        assertEquals(Transaction.MESSAGE_NULL_DATE, e.getMessage());
    }

    @Test
    void addMovement() {
        t1.addMovement(m1);
        assertTrue(t1.getMovements().contains(m1));
        assertThrows(IllegalArgumentException.class, () -> t1.addMovement(m1));
        assertThrows(NullPointerException.class, () -> t1.addMovement(null));
    }

    @Test
    void removeMovement() {
        t1.addMovement(m1);
        t1.removeMovement(m1);
        assertFalse(t1.getMovements().contains(m1));
        assertThrows(IllegalArgumentException.class, () -> t1.removeMovement(m1));
        assertThrows(NullPointerException.class, () -> t1.removeMovement(null));
    }

    @Test
    void addTag() {
        t1.addTag(tag1);
        assertEquals(1, t1.getTag().size());

        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> t1.addTag(tag1));
        assertEquals(Tag.MESSAGE_TAG_ALREADY_EXISTS, e1.getMessage());
        NullPointerException e2 = assertThrows(NullPointerException.class, () -> t1.addTag(null));
        assertEquals(Tag.MESSAGE_NULL_TAG, e2.getMessage());
    }

    @Test
    void removeTag() {
        t1.addTag(tag1);
        t1.removeTag(tag1);
        assertEquals(0, t1.getTag().size());

        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> t1.removeTag(tag1));
        assertEquals(Tag.MESSAGE_TAG_DOES_NOT_EXIST, e1.getMessage());
        NullPointerException e2 = assertThrows(NullPointerException.class, () -> t1.removeTag(null));
        assertEquals(Tag.MESSAGE_NULL_TAG, e2.getMessage());
    }

    @Test
    void getTotalAmount() {
        t1.addMovement(m1);
        assertEquals(-2000, t1.getTotalAmount());
        BasicMovement m2 = new BasicMovement(2, MovementType.INCREMENT, 1000, a1);
        t1.addMovement(m2);
        assertEquals(-1000, t1.getTotalAmount());
    }
}