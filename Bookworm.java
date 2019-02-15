
/**
 * @author Justin Largo
 * @version 2/22/19
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Bookworm {

    /**
     * The main recursive method to find a word within a given grid
     * 
     * @param targetWord
     * @param grid
     */
    public static void findWord(String targetWord, String[][] grid) {

        // Use this to keep track of chars in use
        boolean[][] charsUsed = new boolean[8][8];

        // Init the null spots in the grid array to true so they
        // don't crash the program
        for (int i = 0; i < charsUsed.length; i++) {
            charsUsed[7][i] = true;
        }
        for (int i = 0; i < charsUsed.length - 1; i++) {
            if (i % 2 == 0 || i == 0) {
                charsUsed[i][7] = true;
            }
        }

        // Use this to display if search failed to find a char that starts with target
        int charsFound = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {

                // If search finds a starting char start a recursive search there
                // We put them both at lowercase to make sure capitalizations don't throw
                // results
                if (grid[row][col] != null) {
                    if (grid[row][col].toLowerCase().startsWith((targetWord.charAt(0) + "").toLowerCase())) {

                        charsUsed[row][col] = true;
                        findWord(targetWord, grid[row][col], grid, row, col, charsUsed);
                        charsFound++;
                    }
                }
            }
        }

        // If no chars were found, print an error
        if (charsFound == 0) {
            System.out.println(targetWord = " was not found.");
        }
    }

    /**
     * The helper method to find the target word recursively
     * 
     * @param targetWord
     * @param currentWord
     * @param grid
     * @param row
     * @param col
     * @param charsUsed
     */
    public static void findWord(String targetWord, String currentWord, String[][] grid, int row, int col,
            boolean[][] charsUsed) {

        // Check to see if the word is our target
        if (targetWord.equalsIgnoreCase(currentWord)) {

            System.out.println(targetWord + " was found.");
        } else {
            // Count how many other times we can find another word
            // If no words left print failure

            // Check to see the neighbors of the current element
            findNeighbors(grid, row, col, targetWord, currentWord, charsUsed);
        }

    }

    public static void findNeighbors(String[][] arr, int x, int y, String targetWord, String currentWord,
            boolean[][] charsUsed) {
        // Keeping track of char for debugging purposes
        char targetChar = targetWord.charAt(currentWord.length());
        // Check everything inside
        // Here we check to see if its out of bounds
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                // Make sure not to check itself
                if (row != x - 1 || col != y - 1) {

                    // System.out.print(arr[x + row][y + col] + " ");
                    // If the char hasen't been used AND it equals the char needed

                    int currentRow = x + row;
                    int currentCol = y + col;
                    if (currentRow > 6) {
                        currentRow = 6;
                    } else if (currentRow < 0) {
                        currentRow = 0;
                    }
                    if (currentCol > 7) {
                        currentCol = 7;
                    } else if (currentCol < 0) {
                        currentCol = 0;
                    }
                    if (charsUsed[currentRow][currentCol] == false
                            && arr[currentRow][currentCol].equalsIgnoreCase(targetChar + "")) {

                        // Mark char as used and add to word
                        charsUsed[currentRow][currentCol] = true;

                        // Add to word
                        currentWord = currentWord.concat(arr[currentRow][currentCol]);

                        // An extra check to make sure if the word just constructed is the target
                        if (targetWord.equalsIgnoreCase(currentWord)) {

                            System.out.println(targetWord + " was found.");
                            return;
                        } else {
                            findWord(targetWord, currentWord, arr, currentRow, currentCol, charsUsed);
                            return;
                        }
                    }
                }
            }

        }
    }

    public static void printGrid(String[][] grid) {

        System.out.println();
        // Print out each row at a time to look
        // similar to how the game looks IRL
        for (int col = 0; col < grid.length; col++) {
            for (int row = 0; row < grid.length; row++) {

                // Any uninitialized array space to be replaced with a space
                if (grid[row][col] == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(grid[row][col] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        try {
            // Construct the grid
            String[][] grid = new String[8][8];
            ArrayList<String> words = new ArrayList<>();

            // Prompt the user for input and
            // construct the grid using inputFile
            Scanner userInput = new Scanner(System.in);
            System.out.print("Welcome to Bookworm! Please enter the name of a text file: ");
            // String fileName = userInput.nextLine();
            File inputFile = new File("input.txt");

            // Collect cols and words from file
            Scanner scanner = new Scanner(inputFile);
            int row = 0;
            int col = 0;
            int lineCount = 0;
            while (scanner.hasNextLine()) {

                // Read in our cols for the first 7 lines
                if (lineCount <= 7) {
                    String currentCol = scanner.nextLine();
                    for (int i = 0; i < currentCol.length(); i++) {

                        // Account for adding u to Q
                        if (currentCol.charAt(i) == 'Q' || currentCol.charAt(i) == 'q') {
                            // I use the +"" to make the char into a string
                            grid[row][col] = currentCol.charAt(i) + "U";
                        } else {

                            grid[row][col] = currentCol.charAt(i) + "";
                        }
                        col++;
                    }
                    // Increment row to keep reading in each row and reset col
                    row++;
                    col = 0;
                }
                // Read our words after blank space and add to arraylist
                else {
                    if (lineCount == 7) {
                        scanner.nextLine();
                    }
                    String word = scanner.nextLine();
                    words.add(word);
                }
                lineCount++;
            }
            // Print out the grid
            printGrid(grid);

            for (String word : words) {
                findWord(word, grid);
            }

            userInput.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}