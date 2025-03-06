package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    /**
     * The main entry point for the program.
     * 
     * @param args the command-line arguments
     * @throws NoSuchAlgorithmException If MessageDigest.getInstance is passed an
     *                                  invalid algorithm
     * @throws NumberFormatException    If Integer.parseInt fails to convert to an
     *                                  int
     */
    public static void main(String[] args) throws NumberFormatException, NoSuchAlgorithmException {
        if (args.length != 1) {
            System.out.println("Usage: BlockChainDriver <initial>");
        } else if (Integer.parseInt(args[0]) < 0) {
            System.out.println("initial should be a nonnegative integer");
        } else {
            BlockChain chain = new BlockChain(Integer.parseInt(args[0]));
            boolean stillRunning = true;
            Scanner userInput = new Scanner(System.in);
            while (stillRunning) {
                System.out.print("\n" + chain.toString() + "\nCommand? ");
                String command = userInput.next();
                if (command.compareTo("mine") == 0) {
                    mineDriver(chain, userInput);
                } else if (command.compareTo("append") == 0) {
                    appendDriver(chain, userInput);
                } else if (command.compareTo("remove") == 0) {
                    if (!chain.removeLast()) {
                        System.out.println("No block available to remove.");
                    }
                } else if (command.compareTo("check") == 0) {
                    System.out.println(chain.isValidBlockChain()
                            ? "Chain is valid!"
                            : "Chain is invalid!");
                } else if (command.compareTo("report") == 0) {
                    chain.printBalances();
                } else if (command.compareTo("help") == 0) {
                    printCommands();
                } else if (command.compareTo("quit") == 0) {
                    stillRunning = false;
                } else {
                    System.out.println("Unrecognized command!");
                    System.out.println("Enter \"help\" to see a list of valid commands.");
                }
            }
        }
    }

    private static void mineDriver(BlockChain chain, Scanner userInput)
            throws NoSuchAlgorithmException {
        System.out.print("Amount transferred? ");
        int amount = userInput.nextInt();
        Block newBlock = new Block(chain.getSize(), amount, chain.getHash());
        System.out.format("amount = %d, nonce = %d\n", newBlock.getAmount(),
                newBlock.getNonce());
    }

    private static void appendDriver(BlockChain chain, Scanner userInput)
            throws NoSuchAlgorithmException {
        System.out.print("Amount transferred? ");
        int amount = userInput.nextInt();
        System.out.print("Nonce? ");
        long nonce = userInput.nextLong();
        Block newBlock = new Block(chain.getSize(), amount, chain.getHash(), nonce);
        chain.append(newBlock);
    }

    private static void printCommands() {
        System.out.println("Valid commands:");
        System.out.println("    mine: discovers the nonce for a given transaction");
        System.out.println("    append: appends a new block onto the end of the chain");
        System.out.println("    remove: removes the last block from the end of the chain");
        System.out.println("    check: checks that the block chain is valid");
        System.out.println("    report: reports the balances of Alice and Bob");
        System.out.println("    help: prints this list of commands");
        System.out.println("    quit: quits the program");
    }
}
