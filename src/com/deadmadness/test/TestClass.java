package com.deadmadness.test;

import java.math.BigInteger;

import com.deadmadness.algorithm.*;

public class TestClass {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//PrimeGen generate = new PrimeGen("50");
		RSA encryptor = new RSA();
		String message = "a";
		String decryptedMessage = "";
		//BigInteger[] keys = new BigInteger[3];
		BigInteger[] encrypted;
		BigInteger[] decrypted;
		
		//hard coding keys
		BigInteger pub = new BigInteger("21449");
		BigInteger priv = new BigInteger("11843");
		BigInteger ex = new BigInteger("107");
		
		
		//keys = generate.getKeys();
		
		System.out.println("INITIATING TEST ....\n---------------------------------------------------------------------------- \n");
		System.out.println("Test String: " + message);
		encrypted = encryptor.encrypt(message, pub, ex);
		
		System.out.println("encrypted Message: ");
		for(int i=0;i<encrypted.length;i++){
			System.out.print(encrypted[i]);
		}
		
		decrypted = encryptor.decrypt(encrypted, pub, priv);
		
		for(int i=0;i<decrypted.length;i++){
			decryptedMessage += ((char)decrypted[i].intValue());
		}
		System.out.println("\nDecrypted Message: " + decryptedMessage);
	}

}
