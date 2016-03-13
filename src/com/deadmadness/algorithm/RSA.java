/**
 * 
 */
package com.deadmadness.algorithm;

import java.math.BigInteger;

/**
 * @author deadmadness
 *
 */
public class RSA {
/*
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


		

		
		keys = generator.getKeys();
		
		System.out.println("Done!!");
		System.out.println("E = " + keys[0]);	//public exponent
		System.out.println("N = " + keys[1]);	//public key
		System.out.println("D = " + keys[2]);	//private key
		
		System.out.println("\nplainText message: " + message + "\n");
		

		
		System.out.print("Ciphered message: ");
		for(int i=0;i<message.length();i++){
			System.out.print(ciphertext[i]);
		}
		
		
		
		decryptedMessage = decrypt(ciphertext, keys[1], keys[2]);

		
		System.out.println("\n\nDecrypted String: " + decryptedMessage);
		
	}
*/
	
	public RSA(){
		
	}
	
	
	public BigInteger[] encrypt(String m, BigInteger pubKey, BigInteger pubEx ){
		//Current implementation only ciphers plaintext
		
		BigInteger ciphertext[] = new BigInteger[m.length()];
		int[] plaintext = new int[m.length()];
		
		System.out.println("\n\nPassed Public Key: " + pubKey);
		System.out.println("Passed Public Exponent: " + pubEx);
		System.out.println("Passed message: " + m);
		
		//gets the character values of the message
		for(int i=0;i<m.length();i++){
			System.out.println("CHAR: " + m.charAt(i));
			plaintext[i] = ((int) m.charAt(i));
			System.out.println("INT: " + plaintext[i]);
		}
		
		System.out.println("Inside Encrypt(), Encrypted message: ");
		// c = m^e mod n
		for(int i=0;i<plaintext.length;i++){
			ciphertext[i] = new BigInteger(""+plaintext[i]).modPow(pubEx, pubKey);
			System.out.print(ciphertext[i]);
		}

		return ciphertext;
	}

	public BigInteger[] decrypt(BigInteger[] ciphertext, BigInteger pubKey, BigInteger privKey){
		BigInteger[] decrypted = new BigInteger[ciphertext.length];
		
		System.out.println("\n\nPassed Public Key: " + pubKey);
		System.out.println("Passed Private Key: " + privKey);
		
		System.out.println("\nInside decrypt, Encrypted Message: ");
		for(int i=0;i<ciphertext.length;i++){
			System.out.print(ciphertext[i]);
		}
		
		System.out.println("\nCiphertext length: " + ciphertext.length);
		System.out.println("\nInside decrypt, Decrypted Message: ");
		for(int i=0;i<ciphertext.length;i++){
			decrypted[i] = ciphertext[i].modPow(privKey, pubKey);
			System.out.print(decrypted[i]);
		}
		/*
		System.out.print("\nDecrypted message: ");
		for(int i=0;i<decrypted.length;i++){
			System.out.print(decrypted[i]);
		}
		
		System.out.println("\nInside Decrypt, Plaintext: ");
		for(int i=0;i<decrypted.length;i++){
			plaintext += ((char)decrypted[i].intValue());
			
		}
		System.out.print(plaintext);*/
		
		return decrypted;
	}
}