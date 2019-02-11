
/**
 * A class that takes in a text file for use
 * in the find words method.
 *
 * @author Justin Largo
 * @version 2/6/19
 */

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class Unscrambler {

    private File textFile;

    public Unscrambler(String inputFile) {

        textFile = new File(inputFile);
    }

    /**
     * The findWords method takes in the target string and finds
     * unscrambled words from the length, n, to 2 chars long. 
     * It will then parse through the text file provided in the constructor
     * to find words in a linear search. Once all words fitting the
     * criteria of the contains method found in the WordFinder class,
     * it prints all the words out in 40 character long, alphabetical
     * lines from length n to 2.
     * 
     * @param target the target string to find unscrambled words
     */
    public void findWords(String target) {

        try {

            // Where we will keep our words to print
            ArrayList<String> words = new ArrayList<>();

            // Use a scanner to read from file
            Scanner scnr = new Scanner(textFile);

            // WordFinder is used to check if they can be rearranged
            WordFinder wf = new WordFinder();

            // Parse the text file
            while (scnr.hasNextLine()) {

                String currentWord = scnr.nextLine();

                // We check to see if current word is within 2-n length and
                // our target word can contain it
                if (currentWord.length() >= 2 && currentWord.length() <= target.length()
                        && wf.contains(target.toLowerCase(), currentWord.toLowerCase()) == true) {

                    // If it passes then add it to our words
                    words.add(currentWord);
                }
            }
            // We now have all the words we need and can close scnr
            scnr.close();

            // We now sort the arraylist of words
            // in the natural or alphabetical ordering
            // using null as seen in java 8 api
            words.sort(null);

            // Keep track of length
            int targetLength = target.length();
            // Keep track of current line to print
            String currentLine = "";
            // Used to make sure the header only prints when there is a word to print
            boolean headerPrinted = false;
            // Print out the words in our structure
            for (int i = 0; i < words.size() && targetLength >= 2; i++) {

                // Reset these values for every iteration
                currentLine = "";
                headerPrinted = false;
                // The inner loop decides which words to print next
                for (int j = 0; j < words.size(); j++) {

                    // If the word is the same as targetLength print it
                    if (words.get(j).length() == targetLength) {
                        if (headerPrinted == false) {
                            System.out.println("\n" + targetLength
                                    + " letter words made by unscrambling the letters in " + target);
                            headerPrinted = true;
                        }
                        // So here we keep track of when to break a
                        // new line when exceeding 40 chars when adding a word
                        if (currentLine.length() + words.get(j).length() < 40) {
                            currentLine += words.get(j) + " ";
                        } else {
                            System.out.println(currentLine);
                            currentLine = "";
                            currentLine += words.get(j) + " ";
                        }
                    }
                }
                //Print out anything left in the string
                System.out.print(currentLine);
                targetLength--;
            }
            // If anything goes wrong throw an error
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        Unscrambler us =  new Unscrambler("scrabblewords.txt");
        us.findWords("state");
    }
}