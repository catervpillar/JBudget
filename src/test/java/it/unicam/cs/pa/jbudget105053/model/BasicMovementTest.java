package it.unicam.cs.pa.jbudget105053.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicMovementTest {
    private BasicMovement m1;
    private BasicTag tag1;

    @BeforeEach
    void init() {
        BasicAccount a1 = new BasicAccount(1,AccountType.ASSET, "CONTO CORRENTE", 1000);
        this.m1 = new BasicMovement(1,MovementType.DECREMENT, 200, a1);
        tag1 = new BasicTag(1,"BOLLETTE", "bla bla bla");

        NullPointerException e1 = assertThrows(NullPointerException.class, () -> new BasicMovement(2,null, 100, a1));
        assertEquals(MovementException.MESSAGE_NULL_TYPE, e1.getMessage());

        NullPointerException e3 = assertThrows(NullPointerException.class, () -> new BasicMovement(3,MovementType.DECREMENT, 100, null));
        assertEquals(MovementException.MESSAGE_NULL_ACCOUNT, e3.getMessage());

        IllegalArgumentException e4 = assertThrows(IllegalArgumentException.class, () -> new BasicMovement(4,MovementType.DECREMENT, -1000, a1));
        assertEquals(MovementException.MESSAGE_NEGATIVE_AMOUNT, e4.getMessage());
    }

    @Test
    void addTag() {
        m1.addTag(tag1);
        assertTrue(m1.getTag().contains(tag1));
        assertThrows(IllegalArgumentException.class, () -> m1.addTag(tag1));
        assertThrows(NullPointerException.class, () -> m1.addTag(null));
    }

    @Test
    void removeTag() {
        m1.addTag(tag1);
        m1.removeTag(tag1);
        assertFalse(m1.getTag().contains(tag1));
        assertThrows(IllegalArgumentException.class, () -> m1.removeTag(tag1));
        assertThrows(NullPointerException.class, () -> m1.removeTag(null));
    }
}