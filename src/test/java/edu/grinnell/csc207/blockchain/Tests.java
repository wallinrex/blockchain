package edu.grinnell.csc207.blockchain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

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

    @Test
    @DisplayName("Hash String Representation")
    public void testToString() {
        byte[] data1 = ByteBuffer.allocate(4).putInt(0).array();
        byte[] data2 = ByteBuffer.allocate(4).putInt(Integer.MAX_VALUE).array();
        Hash test1 = new Hash(data1);
        Hash test2 = new Hash(data2);
        assertEquals("00000000", test1.toString());
        assertEquals("7fffffff", test2.toString());
    }

    @Test
    @DisplayName("Non-Mining Constructor")
    public void nonMiningConstructor() throws NoSuchAlgorithmException {
        byte[] testData = ByteBuffer.allocate(4).putInt(0).array();
        Hash testHash = new Hash(testData);
        Block test = new Block(1, -100, testHash, 1234567);
        assertEquals(1, test.getNum());
        assertEquals(-100, test.getAmount());
        assertEquals(testHash, test.getPrevHash());
        assertEquals(1234567, test.getNonce());
        assertEquals(true, test.getPrevHash().isValid());
    }

    @Test
    @DisplayName("Mining Constructor")
    public void miningConstructor() throws NoSuchAlgorithmException {
        byte[] testData = ByteBuffer.allocate(4).putInt(0).array();
        Hash testHash = new Hash(testData);
        Block test = new Block(1, -100, testHash);
        assertEquals(1, test.getNum());
        assertEquals(-100, test.getAmount());
        assertEquals(testHash, test.getPrevHash());
        assertEquals(true, test.getPrevHash().isValid());
        assertEquals(true, test.getHash().isValid());
    }
}