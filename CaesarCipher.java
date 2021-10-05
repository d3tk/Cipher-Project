import java.util.*;
class CaesarCipher 
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
        // near same code as Affine Cipher just different for loop. 
        boolean notTrue = true; // cipher solved or not
        boolean makeSmart = true; // use detectEnglish or not
        boolean endCipher = false; // failsafe, as to not repeat question.
        Scanner scan = new Scanner(System.in);
        while(notTrue == true){
                if(makeSmart == true){
                     for(int k = 0; k<SYMBOLS.length(); k++)
                    {
                        String checkStr = decryptCipher(text,k);
                        if(detectEnglish.isEnglish(checkStr)){
                            System.out.println("Possible decryption using Key: " + k + " is: " + decryptCipher(text,k));
                        }
                    }
                }
                else if(makeSmart == false){
                         for(int k = 0; k<SYMBOLS.length(); k++)
                        {
                            String checkStr = decryptCipher(text,k);
                            System.out.println("Possible decryption using Key: " + k + " is: " + decryptCipher(text,k));
                        }
                    endCipher = true;
                }
                if(endCipher == false){
                    System.out.println("Was the deciphered message found?(Yes or No)");
                    String ans = scan.nextLine().trim().toLowerCase();
                    
                    if(ans.equals("yes"))
                    {
                        notTrue = false;
                    }
                    else if(ans.equals("no"))
                    {
                        System.out.println("Restarting without english detection...");
                        makeSmart = false;
                        notTrue = true;
                    }
                    else{
                        System.out.println("Answer entered incorrectly");
                        notTrue = false;
                    }
                }
                else{notTrue = false;}

        }
    }
} 
