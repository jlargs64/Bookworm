
/**
 * @author Justin Largo
 * @version 2/22/19
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Bookworm {

    public void findWord(String targetWord) {

    }

    public void findWord(String targetWord, String currentWord) {

    }

    public void printGrid(String[][] grid) {

        System.out.println();
        //Print out each row at a time to look
        //similar to how the game looks IRL
        for (int col = 0; col < grid.length; col++) {
            for (int row = 0; row < grid.length; row++) {

                //Any uninitialized array space to be replaced with a space
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
            String fileName = userInput.nextLine();
            File inputFile = new File(fileName);

            //Collect cols and words from file
            Scanner scanner = new Scanner(inputFile);
            int row = 0;
            int col = 0;
            int lineCount = 0;
            while (scanner.hasNextLine()) {

                //Read in our cols for the first 7 lines
                if (lineCount <= 7){
                    String currentCol = scanner.nextLine();
                    for (int i = 0; i < currentCol.length(); i++) {

                        //Account for adding u to Q
                        if (currentCol.charAt(i) == 'Q' || currentCol.charAt(i) == 'q') {
                            // I use the +"" to make the char into a string
                            grid[row][col] = currentCol.charAt(i) + "u";
                        } else {

                            grid[row][col] = currentCol.charAt(i) + "";
                        }
                        col++;
                    }
                    //Increment row to keep reading in each row and reset col
                    row++;
                    col = 0;                   
                }
                //Read our words after blank space and add to arraylist
                else{
                    if (lineCount == 8){
                        scanner.nextLine();
                    }
                    String word = scanner.nextLine();
                    words.add(word);
                } 
                lineCount++;
            }
            // Print out the grid
            Bookworm b = new Bookworm();
            b.printGrid(grid);

            //Prompt user to find a word in the grid
            System.out.println("Enter a word to find: ");
            String targetWord = userInput.nextLine();

            userInput.close();          
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}