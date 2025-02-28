package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private byte[] data;

    public Hash(byte[] data) {
        this.data = data;
    }

    /**
     * Getter for the data field of the Hash
     * @return the byte array data
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Determines whether the hash is valid based on whether the first three bytes are 0
     * @return true if the first three bytes are 0, false otherwise
     */
    public boolean isValid() {
        return this.data[0] == 0 && this.data[1] == 0 && this.data[2] == 0;
    }

    /**
     * Converts the hash to a hexidecimal representation
     * @return a string containing said hexidecimal integer
     */
    public String toString() {
        String dataString = "";
        for (int i = 0; i < this.data.length; i++) {
            dataString += String.format("%x", Byte.toUnsignedInt(this.data[i]));
        }
        return dataString;
    }

    /**
     * Compares this hash to another object for equality
     * @param other the object to which this hash is compared
     * @return true if other is a hash with an identical data array, false otherwise
     */
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash o = (Hash) other;
            if (Arrays.equals(this.data, o.getData())) {
                return true;
            }
        }
        return false;
    }
}
