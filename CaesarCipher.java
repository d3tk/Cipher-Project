import java.util.*;
public class CaesarCipher
{
    static String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890 !?.";
        public static void main(String[] args)
    {
        new CaesarCipher();
    }
    public CaesarCipher(){
          new detectEnglish();
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the mode(i.e. Encrypt, Decrypt");
        String myMode = scan.nextLine().trim().toLowerCase();

        if(myMode.equals("encrypt"))
        {
            System.out.println("Please enter the message.");
            String msg = scan.nextLine().trim();
            System.out.println("Please enter the key.");
            int key = scan.nextInt();
            scan.close();
           if(checkKey(key) == true)
           {
               System.out.println("The encoded message is: " + encryptMessage(msg,key));
            }
            else
            {
                System.out.println("Key was not correctly inputed, should be between 1-66");
            }
        }
        else if(myMode.equals("decrypt"))
        {
            System.out.println("Please enter the message.");
            String msg = scan.nextLine().trim();
            System.out.println("Please enter the key. Enter -1 for brute force");
            int key = scan.nextInt();
            scan.close();
           if(key == -1)
           {
               decryptCipherBrute(msg);
            }
            else
            {
                System.out.println("The decoded message is: " + decryptCipher(msg,key));
            }
        }
    }
        // Check the key values
    static boolean checkKey(int key)
    {
        if(key > SYMBOLS.length() || key <= 0)
        {
            return false;
        }
        else
            return true;
    }
    // Encrypts text using a shift of x
    public static String encryptMessage(String text, int x)
    {
        String msg = "";
        int key = x;
        for (int i = 0; i < text.length(); i++)
         {
           int symbolIndex = SYMBOLS.indexOf(text.charAt(i));// current symbol
           int translatedIndex = symbolIndex + key;//new symbol
           if(translatedIndex > SYMBOLS.length())//Out of bounds catch
           {
               translatedIndex = translatedIndex - SYMBOLS.length();
            }
           msg += SYMBOLS.charAt(translatedIndex);

           }
        return msg;

    }
    // decrypts text using a provided key
    public static String decryptCipher(String text, int x)
    {
        String msg = "";
        int key = x;
        for (int i = 0; i < text.length(); i++)
         {
           int symbolIndex = SYMBOLS.indexOf(text.charAt(i));// psn of symbol that needs decrypting
           int translatedIndex = symbolIndex - key; //psn of new symbols
           if(translatedIndex < 0)//catch if psn of new symbol is Out of Bounds
           {
               translatedIndex = translatedIndex + SYMBOLS.length();
            }
           msg += SYMBOLS.charAt(translatedIndex);

           }
        return msg;
    }
    // decrypts text using every possible shift and english detector
    public static void decryptCipherBrute(String text)
    {
      TreeMap<Integer,String> solutions = new TreeMap<Integer,String>();
      String msg = "";
      for(int k = 0; k<SYMBOLS.length(); k++)
      {
        if(checkKey(k))
          {
            if(detectEnglish.isEnglish(decryptCipher(text,k) ))
            {
              msg = decryptCipher(text,k);
              solutions.put(k,msg);
            }
          }
      }

      for(Integer key: solutions.keySet())
        System.out.println("Possible decryption using Key: "+ key+" is: " + solutions.get(key));
    }
}
