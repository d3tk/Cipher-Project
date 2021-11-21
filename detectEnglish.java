import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.FileNotFoundException;
/**
 * Able to detect english words froim a dictionary. Scan dictionary.
 * word pattern recognition soon.
 *
 * @author DeTK.
 * @version 1.0.0
 */
public class detectEnglish
{
    // instance variables - replace the example below with your own
    private String UPPERLETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String LETTERS_AND_SPACE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
    private static File dict = new File("dictionary.txt");
   public detectEnglish()
    {
    File dict = new File("dictionary.txt");
    if (dict.exists()) {
      System.out.println("Loaded file name: " + dict.getName());
      System.out.println("Loaded file path: " + dict.getAbsolutePath());
    } else {
      System.out.println("The dictionary file does not exist."); //FileNotFoundException
    }
   }
   private static List  makeWords(File file){
        File dict = file;
        try // new loopt thing I found. used to stop FileNotFoundException.
        {
            String psn = "";
            Scanner reader = new Scanner(dict);
            List<String> words = new ArrayList<String>();
            // while loop
            while (reader.hasNext()) {
              psn = reader.next();
              words.add(psn);
            }
            reader.close();
            return words;
        }
        catch(FileNotFoundException e)// needed for FileNotFoundException
        {
          e.printStackTrace();
          return null;
        }
    }
  public static double getEnglishCount(String msg){
       String message = msg.toUpperCase().trim();
       List<String> possibleWords = new ArrayList<String>(Arrays.asList(message.split(" ")));
       if (possibleWords.size() == 0)
       {
           return 0.0;
        }
       else{
         double count = 0.0;
        for(String str: possibleWords)
        {
            if(makeWords(dict).contains(str)){
               count += 1.0;
            }
        }
        return count/(double)possibleWords.size();
        }
    }
public static boolean isEnglish(String message){
    if(getEnglishCount(message) >= .75)
      return true;
    return false;
    }
}
