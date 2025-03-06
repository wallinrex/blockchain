package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {

    /**
     * Node class to support the linked list behavior of the blockchain
     */
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

    private int alice;

    private int bob;

    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block data = new Block(0, initial, null);
        Node newNode = new Node(data, null);
        this.first = newNode;
        this.last = newNode;
        this.alice = initial;
        this.bob = 0;
    }

    /**
     * Finds a valid nonce for the next block
     * 
     * @param amount The transaction to be recorded by said block
     * @return A block containing the appropriate number, amount, previous hash, and
     *         a valid
     *         nonce and hash
     * @throws NoSuchAlgorithmException If MessageDigest.getInstance is passed an
     *                                  invalid algorithm
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block lastBlock = this.last.data;
        Block ret = new Block(lastBlock.getNum() + 1, amount, lastBlock.getHash());
        return ret;
    }

    /**
     * Returns the size of the blockchain
     * 
     * @return The number of nodes in the blockchain
     */
    public int getSize() {
        return this.last.data.getNum() + 1;
    }

    /**
     * Adds the specified block to the end of the blockchain if it is valid
     * 
     * @param blk The block to (maybe) be added
     * @throws IllegalArgumentException If the block is invalid
     */
    public void append(Block blk) throws IllegalArgumentException {
        if (!blk.getPrevHash().equals(this.last.data.getHash()) ||
                !blk.getHash().isValid() ||
                this.alice + blk.getAmount() < 0 ||
                this.bob - blk.getAmount() < 0 ||
                blk.getNum() != this.last.data.getNum() + 1) {
            throw new IllegalArgumentException();
        } else {
            Node newNode = new Node(blk, null);
            this.last.next = newNode;
            this.last = newNode;
            this.alice += blk.getAmount();
            this.bob -= blk.getAmount();
        }
    }

    /**
     * Removes the last block of the blockchain, provided the blockchain has more
     * than one block
     * 
     * @return false if the blockchain has no block to remove, true otherwise
     */
    public boolean removeLast() {
        if (this.getSize() <= 1) {
            return false;
        } else {
            this.alice -= this.last.data.getAmount();
            this.bob += this.last.data.getAmount();
            Node temp = this.first;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            this.last = temp;
            this.last.next = null;
            return true;
        }
    }

    /**
     * Getter for the currHash field of the last block in the blockchain
     * 
     * @return The Hash of the block in the last node of the list
     */
    public Hash getHash() {
        return this.last.data.getHash();
    }

    /**
     * Determines whether the blockchain is valid by making sure each block is valid
     * 
     * @return false if one of the blocks is invalid, true otherwise
     */
    public boolean isValidBlockChain() {
        Node temp = this.first;
        if (temp.data.getNum() != 0 ||
                !temp.data.getHash().isValid() ||
                this.alice < 0) {
            return false;
        }
        if (temp.next == null) {
            return true;
        }
        Hash previous = temp.data.getHash();
        temp = temp.next;
        int index = 1;
        while (temp != null) {
            if (temp.data.getNum() != index ||
                    !temp.data.getPrevHash().equals(previous) ||
                    !temp.data.getHash().isValid() ||
                    this.alice < 0 ||
                    this.bob < 0) {
                return false;
            }
            index++;
            previous = temp.data.getHash();
            temp = temp.next;
        }
        return true;
    }

    /**
     * Prints Alice and Bob's respective balances
     */
    public void printBalances() {
        System.out.format("Alice: %d, Bob: %d", this.alice, this.bob);
    }

    /**
     * Returns a string representation of the blockchain
     * 
     * @return A string containing the string representation of each block in the
     *         blockchain,
     *         each on its own line
     */
    public String toString() {
        Node temp = this.first;
        String ret = temp.data.toString();
        temp = temp.next;
        while (temp != null) {
            ret += "\n" + temp.data.toString();
            temp = temp.next;
        }
        return ret;
    }
}
