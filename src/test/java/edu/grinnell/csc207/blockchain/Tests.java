package edu.grinnell.csc207.blockchain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests {
    
    @Test
    @DisplayName("Empty Hash")
    public void emptyHash() {
        byte[] testData = new byte[0];
        Hash test = new Hash(testData);
        assertEquals(testData, test.getData());
        assertEquals(false, test.isValid());
    }

    @Test
    @DisplayName("Non-empty Valid Hash")
    public void nonEmptyValidHash() {
        byte[] testData = ByteBuffer.allocate(4).putInt(0).array();
        Hash test = new Hash(testData);
        assertEquals(testData, test.getData());
        assertEquals(true, test.isValid());
    }

    @Test
    @DisplayName("Non-empty Invalid Hash")
    public void nonEmptyInvalidHash() {
        byte[] testData = ByteBuffer.allocate(4).putInt(Integer.MAX_VALUE).array();
        Hash test = new Hash(testData);
        assertEquals(testData, test.getData());
        assertEquals(false, test.isValid());
    }

    @Test
    @DisplayName("Hash Equality")
    public void testEquals() {
        byte[] data1 = ByteBuffer.allocate(4).putInt(0).array();
        byte[] data2 = ByteBuffer.allocate(4).putInt(Integer.MAX_VALUE).array();
        Hash test1 = new Hash(data1);
        Hash test2 = new Hash(data1);
        Hash test3 = new Hash(data2);
        assertEquals(false, test1.equals(test3));
        assertEquals(true, test1.equals(test2));
    }
}