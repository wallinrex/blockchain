package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {
    
    private class Node {

        private Block data;

        private Node next;

        public Node(Block data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node first;

    private Node last;

    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block data = new Block(0, initial, null);
        Node newNode = new Node(data, null);
        this.first = newNode;
        this.last = newNode;
    }

    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block lastBlock = this.last.data;
        Block ret = new Block(lastBlock.getNum() + 1, amount, lastBlock.getHash());
        return ret;
    }

    public int getSize() {
        return this.last.data.getNum() + 1;
    }

    public void append(Block blk) throws IllegalArgumentException {
        if (!blk.getPrevHash().equals(this.last.data.getHash()) || !blk.getHash().isValid()) {
            throw new IllegalArgumentException();
        } else {
            Node newNode = new Node(blk, null);
            this.last.next = newNode;
            this.last = newNode;
        }
    }

    public boolean removeLast() {
        if (this.getSize() > 1) {
            return false;
        } else {
            Node temp = this.first;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            this.last = temp;
            this.last.next = null;
            return true;
        }
    }

    public Hash getHash() {
        return this.last.data.getHash();
    }

    public boolean isValidBlockChain() {
        return false;
    }
}
