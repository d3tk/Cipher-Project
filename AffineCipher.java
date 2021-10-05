import java.util.*;
import java.lang.Math.*;
import java.math.*;
/*
 * I translated it from Python to Java.
 * (Although tedious maybe, I think it really helped me learn the differences between the two languages)
 */
class AffineCipher

{
    static String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890 !?.";

    public static void main(String args[]) {
        new AffineCipher();

    }
    public AffineCipher(){
        new detectEnglish();
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the mode(i.e. Encrypt, Decrypt");
        String myMode = scan.nextLine().trim().toLowerCase();

        if(myMode.equals("decrypt"))
        {
           System.out.println("Please enter the ciphertext (i.e. the message)");
           String myMessage = scan.nextLine().trim();

           System.out.println("Please enter the key. Enter -1 for brute force \n (*WARNING* depending on message length, this could take a long time)");
           int myKey = scan.nextInt();
           scan.close();
            if(myKey != -1)
               {
                System.out.println("Your decrypted message using the Affine Cipher is: " + decryptCipher(myMessage,myKey));
            }
            else
            {
                decryptCipherBrute(myMessage);
            }
        }
        else if(myMode.equals("encrypt"))
        {
           System.out.println("Please enter the plaintext (i.e. the message)");
           String myMessage = scan.nextLine().trim();

           System.out.println("Please enter the key or 0 for random key");
           int myKey = scan.nextInt();
           scan.close();
           if(myKey == 0){
             while(checkKeySilent(myKey)==false){
               myKey = Math.randInt(0,10000);
             }
             System.out.println("Your encrypted message using the Affine cipher is: "+ encryptMessage(myMessage, myKey));

           }
           else{
             if(checkKey(myKey) == true)
             {
              System.out.println("Your encrypted message using the Affine cipher is: "+ encryptMessage(myMessage, myKey));
              //restart = false;
              }
              else
              {
                  System.out.println("Key was incorrectly entered, Restarting program...");
              }
          scan.close();
           }
    // Check the key values
    private boolean checkKey(int key)
    {
        if (getKeyPartA(key) == 1)
        {
            System.out.println("Cipher is weak if key A is 1. Choose a different key.");
            return false;
        }
        else if (getKeyPartB(key) == 0)
        {
            System.out.println("Cipher is weak if key B is 0. Choose a different key.");
            return false;
        }
        else if (getKeyPartA(key) < 0 || getKeyPartB(key) < 0 || getKeyPartB(key) > SYMBOLS.length()-1)
        {
            System.out.println("Key A must be greater than 0 and Key B must be between 0 and " + (SYMBOLS.length()-1));
            return false;
        }
        else if (gcdFinder(getKeyPartA(key),SYMBOLS.length())  != 1)
        {
            System.out.println(getKeyPartA(key) + " and the symbol set size " + SYMBOLS.length() + " are not relatively prime. Choose a different key.");
            return false;
        }
        else {
            return true;
        }
    }
    // Silent for brute force, gets annopying w/ prints when decrypting
    private boolean checkKeySilent(int key)
    {
        if (getKeyPartA(key) == 1)
        {
            return false;
        }
        else if (getKeyPartB(key) == 0)
        {
            return false;
        }
        else if (getKeyPartA(key) < 0 || getKeyPartB(key) < 0 || getKeyPartB(key) > SYMBOLS.length()-1)
        {
            return false;
        }
        else if (gcdFinder(getKeyPartA(key),SYMBOLS.length())  != 1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    // greatest common denominator of two integers from cyrptomath.py
    private int gcdFinder(int key, int length)
    {
      int gcd = 0;
      for(int i = 1; i <= key && i <= length; i++)
      {
        if(key%i==0 && length%i==0)
           gcd = i;
      }
      return gcd;
    }
    // Key value of A
    private int getKeyPartA(int key)
    {
        int keyA = (int)Math.floor(key/SYMBOLS.length());
        return keyA;
    }

    // Key value of B
    private int getKeyPartB(int key)
    {
      int keyB = key % SYMBOLS.length();
      return keyB;
    }

    //modular inverse method from java.math.BigInteger
    private int modInverse(int a, int m)
    {
        BigInteger answer = BigInteger.valueOf(2).add(BigInteger.valueOf(3));
        BigInteger keyA = BigInteger.valueOf(a);
        BigInteger lengthSymbol = BigInteger.valueOf(m);
        return (keyA.modInverse(lengthSymbol).intValue());
    }

    public String encryptMessage(String msg, int key)
    {
       String cipher = "";
       int keyA = getKeyPartA(key);
       int keyB= getKeyPartB(key);
       for(int i = 0; i < msg.length(); i++)
       {
         int symbolIndex = SYMBOLS.indexOf(msg.charAt(i)); // stored to keep math simple.
         cipher += (char) SYMBOLS.charAt(((symbolIndex*keyA)+keyB)%SYMBOLS.length());
       }
        return cipher;
    }

    public String decryptCipher(String cipher, int key)
    {
        String msg = "";
        int keyA = getKeyPartA(key);
        int keyB= getKeyPartB(key);
        int modInvA = modInverse(keyA,SYMBOLS.length());
        for(int i = 0; i< cipher.length(); i++)
        {
          int symbolIndex = SYMBOLS.indexOf(cipher.charAt(i));
          int math1 = symbolIndex-keyB;
          int math2 = math1*modInvA;
          //(((n % m) + m) % m)
          int math3 = (((math2 % SYMBOLS.length()) + SYMBOLS.length()) % SYMBOLS.length());
          msg += (char)SYMBOLS.charAt(math3);
        }
        return msg;
    }

     public void decryptCipherBrute(String cipher)
    {
        boolean notTrue = true; // cipher solved or not
        boolean makeSmart = true; // use detectEnglish or not
        boolean endCipher = false; // failsafe, as to not repeat question.
        Scanner scan = new Scanner(System.in);
        while(notTrue == true){
                if(makeSmart == true){
                    for(int k = 0; k<(Math.pow(SYMBOLS.length(),2)); k++)
                    {
                        if(checkKeySilent(k))
                        {
                            if(detectEnglish.isEnglish(decryptCipher(cipher,k))){
                                System.out.println("Possible decryption using Key: " + k + " is: " + decryptCipher(cipher,k));
                            }
                        }
                        else
                        {
                            continue;
                        }
                    }
                }
                else if(makeSmart == false){
                    for(int k = 0; k<(Math.pow(SYMBOLS.length(),2)); k++)
                    {
                        if(checkKeySilent(k))
                        {
                                System.out.println("Possible decryption using Key: " + k + " is: " + decryptCipher(cipher,k));
                        }
                        else
                        {
                            continue;
                        }
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
