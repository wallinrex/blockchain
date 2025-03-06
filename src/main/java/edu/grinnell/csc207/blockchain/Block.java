package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {

    private int num;

    private int amount;

    private Hash prevHash;

    private long nonce;

    private Hash currHash;

    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = 0;
        this.currHash = new Hash(calculateHash());
        while (!this.currHash.isValid()) {
            this.nonce++;
            this.currHash = new Hash(calculateHash());
        }
    }

    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
        this.currHash = new Hash(calculateHash());
    }

    /**
     * Calculates the hash for a block based on its number, the amount of the
     * corresponding
     * transaction, the previous hash (if applicable), and the nonce
     * 
     * @return the calculated hash in a byte array
     * @throws NoSuchAlgorithmException if MessageDigest.getInstance is passed an
     *                                  invalid algorithm
     */
    private byte[] calculateHash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(ByteBuffer.allocate(4).putInt(this.num).array());
        md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
        if (this.num != 0) {
            md.update(this.prevHash.getData());
        }
        md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
        byte[] hash = md.digest();
        return hash;
    }

    /**
     * Getter for the num field of a block
     * 
     * @return the block's number
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Getter for the amount field of a block
     * 
     * @return the amount of the transaction the block records
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Getter for the nonce field of a block
     * 
     * @return the block's nonce
     */
    public long getNonce() {
        return this.nonce;
    }

    /**
     * Getter for the prevHash field of a block
     * 
     * @return the hash of the previous block
     */
    public Hash getPrevHash() {
        return this.prevHash;
    }

    /**
     * Getter for the currHash field of a block
     * 
     * @return the block's hash
     */
    public Hash getHash() {
        return this.currHash;
    }

    /**
     * Returns a string representation of the block
     * 
     * @return a string containing all the fields of the block
     */
    public String toString() {
        if (this.prevHash == null) {
            return String.format("Block %d (Amount: %d, Nonce: %d, prevHash: null, hash: %s)",
                    this.num, this.amount, this.nonce, this.currHash.toString());
        } else {
            return String.format("Block %d (Amount: %d, Nonce: %d, prevHash: %s, hash: %s)",
                    this.num, this.amount, this.nonce, this.prevHash.toString(),
                    this.currHash.toString());
        }
    }
}
