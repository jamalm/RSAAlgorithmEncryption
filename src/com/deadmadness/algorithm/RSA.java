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
	
	public RSA(){
		//constructor 
	}
	
	
	/*
	 * Encrypt Function to encrypt text files and plaintext
	 * 
	 */
	public BigInteger[] encrypt(String m, BigInteger pubKey, BigInteger pubEx ){
		//BigInteger to store encrypted message/file
		BigInteger ciphertext[] = new BigInteger[m.length()];
		//int array to store initial character values
		int[] plaintext = new int[m.length()];
		
		//console checks for parameters passed in from GUI
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
		
		//encrypt the message/file accordingly to ciphertext
		// c = m^e mod n
		for(int i=0;i<plaintext.length;i++){
			ciphertext[i] = new BigInteger(""+plaintext[i]).modPow(pubEx, pubKey);
			System.out.print(ciphertext[i]);
		}

		return ciphertext;
	}
	
	/*
	 * Decrypt function to decrypt encrypted text files and ciphertext
	 */
	public BigInteger[] decrypt(BigInteger[] ciphertext, BigInteger pubKey, BigInteger privKey){
		//BigInteger array to store decrypted ciphertext blocks
		BigInteger[] decrypted = new BigInteger[ciphertext.length];
		
		System.out.println("\n\nPassed Public Key: " + pubKey);
		System.out.println("Passed Private Key: " + privKey);
		
		//console checks for ciphertext passed in correctly from GUI
		System.out.println("\nInside decrypt, Encrypted Message: ");
		for(int i=0;i<ciphertext.length;i++){
			System.out.print(ciphertext[i]);
		}
		
		//decrypting data from ciphertext to plaintext character values and put into BigInteger array
		System.out.println("\nCiphertext length: " + ciphertext.length);
		System.out.println("\nInside decrypt, Decrypted Message: ");
		for(int i=0;i<ciphertext.length;i++){
			decrypted[i] = ciphertext[i].modPow(privKey, pubKey);
			System.out.print(decrypted[i]);
		}
		return decrypted;
	}
}