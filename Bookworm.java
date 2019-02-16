
/**
 * @author Justin Largo
 * @version 2/22/19
 */

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bookworm {

    /**
     * The main recursive method to find a word within a given grid
     * 
     * @param targetWord
     * @param grid
     */
    public static void findWord(String targetWord, String currentWord, String[][] grid, List<String> words) {
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
        // Check to see if the word is our target
        if (targetWord.equalsIgnoreCase(currentWord)) {

            System.out.println(targetWord + " was found.");
            return;
        }

        String targetChar = targetWord.charAt(currentWord.length()) + "";
        if (targetChar.charAt(0) == 'Q' && targetChar.length() == 1) {
            targetChar = targetChar.concat("U");
        }
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {

                // If search finds a starting char start a recursive search there
                // We put them both at lowercase to make sure capitalizations don't throw
                // results
                if (!words.contains(targetWord)) {
                    return;
                }
                if (grid[row][col] != null) {
                    if (charsUsed[row][col] == false && grid[row][col].equalsIgnoreCase(targetChar)) {

                        charsUsed[row][col] = true;
                        currentWord = targetChar + "";
                        findNeighbors(grid, row, col, targetWord, currentWord, charsUsed, words);
                    }
                }
            }
        }
    }

    public static void findNeighbors(String[][] arr, int x, int y, String targetWord, String currentWord,
            boolean[][] charsUsed, List<String> words) {
        // Keeping track of char for debugging purposes
        char targetChar = targetWord.charAt(currentWord.length());
        int originalX = x;
        int originalY = y;
        // Check everything inside
        // Here we check to see if its out of bounds
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                // Make sure not to check itself
                if (row != x - 1 || col != y - 1) {

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
                    // If the char hasen't been used AND it equals the char needed
                    if (charsUsed[currentRow][currentCol] == false
                            && arr[currentRow][currentCol].equalsIgnoreCase(targetChar + "")) {

                        // Mark char as used and add to word
                        charsUsed[currentRow][currentCol] = true;
                        findNeighbors(arr, originalX, originalY, targetWord, currentWord, charsUsed, words);
                        // Add to word
                        currentWord = currentWord.concat(arr[currentRow][currentCol]);

                        // An extra check to make sure if the word just constructed is the target
                        if (targetWord.equalsIgnoreCase(currentWord)) {

                            //Avoid printing multiple times
                            if (words.contains(targetWord)) {
                                // Remove the word here so that when printing everything that was
                                // not found it won't show up
                                words.remove(targetWord);
                                System.out.println(targetWord + " was found.");
                            }
                            return;
                        } else {

                            findNeighbors(arr, currentRow, currentCol, targetWord, currentWord, charsUsed, words);
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
            List<String> words = new CopyOnWriteArrayList<String>();

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
                findWord(word, "", grid, words);
            }
            // Words found were removed from arraylist so we print words not found here
            for (String word : words) {
                System.out.println(word + " was not found in the grid.");
            }

            userInput.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}