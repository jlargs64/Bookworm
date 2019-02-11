
/**
 * @author Justin Largo
 * @version 2/22/19
 */

import java.io.File;
import java.util.Scanner;

public class Bookworm {

    public void findWord(String targetWord) {

    }

    public void findWord(String targetWord, String currentWord) {

    }

    public void printGrid(String[][] grid){

    }

    public static void main(String[] args) {
        
        // Find and construct our test file and grid
        File inputFile;
        String[][] grid = new String[8][8];
        
        Scanner userInput = new Scanner(System.in);
        String fileName = userInput.nextLine();
        inputFile = new File(fileName);

        // Construct the grid using inputFile
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String currentCol = scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Enter a word to find: ");
        String targetWord = userInput.nextLine();
    }
}