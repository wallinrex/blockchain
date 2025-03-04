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

    public byte[] calculateHash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(ByteBuffer.allocate(4).putInt(this.num).array());
        md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
        if (this.num != 1) {
            md.update(this.prevHash.getData());
        }
        md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
        byte[] hash = md.digest();
        return hash;
    }

    public int getNum() {
        return this.num;
    }

    public int getAmount() {
        return this.amount;
    }

    public long getNonce() {
        return this.nonce;
    }

    public Hash getPrevHash() {
        return this.prevHash;
    }

    public Hash getHash() {
        return this.currHash;
    }

    public String toString() {
        return String.format("Block %d (Amount: %d, Nonce: %d, prevHash: %s, hash: %s)",
                             this.num, this.amount, this.nonce, this.prevHash.toString(),
                             this.currHash.toString());
    }
}
