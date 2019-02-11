
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

    public static void main(String[] args) {
        
        // Find and construct our test file and grid
        File inputFile;
        char[][] grid;
        String fileName = args[0];
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
    }
}