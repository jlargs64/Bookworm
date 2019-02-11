
/**
 * A class that uses the contains function that uses two strings, 
 * text and target, as parameters.
 * It then checks to see if all of the letters in 
 * text contains all of the letters in target.
 *
 * @author Justin Largo
 * @version 1/20/18
 */
public class WordFinder
{

    public static boolean contains(String text, String target){

        String newTarget = "";
        for(int i = 0; i < target.length(); i++){           

            //If the text is empty and can't concat anymore letters
            //Return false because the strings are not able to match
            if(text.length() == 0) return false;

            for(int j = 0; j < text.length(); j++){

                //Compare each character within a each string regardless of case
                if(text.substring(j,j+1).equalsIgnoreCase(target.substring(i,i+1))){

                    //Add new char to string needed for string
                    newTarget = newTarget.concat(text.substring(j,j+1));

                    //Remove the char we used to make sure we are distribution sensative
                    text = text.replaceFirst(target.substring(i,i+1),"");

                    //Break because we no longer need to search for the char we need
                    break;
                }
            }           
        }

        //If the newly constructed target string matches the original 
        //return true, otherwise return false. 
        if(newTarget.equalsIgnoreCase(target)){
            return true;
        }else{
            return false;
        }
    }
}