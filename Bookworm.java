
/**
 * @author Justin Largo
 * @version 2/22/19
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Bookworm {

    public void findWord(String targetWord, String[][] grid) {

        //Use this to keep track of chars in use
        boolean[][] charsUsed = new boolean[8][8];
        //Init the null spots in the grid array to true so they don't
        //crash the program
        for(int i = 0; i < charsUsed.length; i++){
            charsUsed[7][i] = true;
        }
        for(int i = 0; i < charsUsed.length-1; i++){
            if(i % 2 == 0 || i == 0){
                charsUsed[i][7] = true;
            }            
        }

        //Use this to display if search failed to find a char that starts with target
        int charsFound = 0;
        for(int row = 0; row < grid.length; row++){
            for(int col = 0; col < grid.length; col++){
                
                //If search finds a starting char start a recursive search there
                //We put them both at lowercase to make sure capitalizations don't throw results
                if(grid[row][col].toLowerCase().startsWith((targetWord.charAt(0)+"").toLowerCase())){

                    findWord(targetWord, grid, grid[row][col], row, col);
                    charsFound++;
                }
            }
        }

        //If no chars were found, print an error
        if(charsFound == 0){
            System.out.println(targetWord = " was not found.");
        }    
    }

    public void findWord(String targetWord, String[][] grid, String currentWord, int row, int col) {
        
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
            b.findWord(targetWord, grid);

            userInput.close();          
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}