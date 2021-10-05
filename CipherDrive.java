import java.util.*;
/**
 * Driver to connect all ciphers (has room for additional ciphers later on and is user friendly.)
 *
 * @author DeTK
 * @version 1.0.0
 */
public class CipherDrive
{
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Which cipher would you like to use?\n Options are The Affine cipher and the Caesar Cipher \n Enter 1 for Affine and 2 for Caesar");
        int cipher = scan.nextInt();
        if(cipher == 1)
        {
            System.out.println("Starting The Affine Cipher...");
            new AffineCipher();
        }
        else if(cipher == 2)
        {
            System.out.println("Starting The Caesar Cipher...");
            new CaesarCipher();
        }
        // Room for more ciphers
    }
}
