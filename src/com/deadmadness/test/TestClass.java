package com.deadmadness.test;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.deadmadness.algorithm.*;

public class TestClass {
	
	public static class Gen extends PrimeGen{
		static Gen instance;
		public Gen(String bits) {
			super(bits);
			instance = this;
			// TODO Auto-generated constructor stub
		}
		
		protected BigInteger genLargePrime(){
			SecureRandom r = new SecureRandom();
			BigInteger p;
			int bitSize = 128;
		
			while(true){
				p = new BigInteger(bitSize, r); //p = r.nextInt(Integer.MAX_VALUE/5 - (Integer.MAX_VALUE/10)) + (Integer.MAX_VALUE/10);
				
				System.out.println("Calculating Prime: " + p + " with bitSize: " + bitSize);
				
				if(isPrime(p)) {
					System.out.println("Prime Number:" + p);
					break;
				}
				else {
					System.out.println("no prime found");
				}
			}
			return p;
		}
		
		protected boolean isPrime(BigInteger n){
SecureRandom r = new SecureRandom();
			
			BigInteger ZERO = BigInteger.ZERO;
			BigInteger ONE = BigInteger.ONE;
			BigInteger TWO = new BigInteger("2");
			
			BigInteger nMinus = n.subtract(ONE);

			//check if p is even
			if(n.mod(TWO).equals(ZERO)){
				System.out.println("P%2 returned true: False prime");
				return false;
			}
			
			//find n-1 = 2^k*m
			BigInteger m = nMinus;
			int k = m.getLowestSetBit();
			m = m.shiftRight(k);

			System.out.println("Starting loop");
			
			//j is our level of certainty
			for(int j=0;j < 100;j++){
				//generate a random variable, where 1 < a < n-1
				BigInteger a;
				do{
					a = new BigInteger(n.bitLength(), r);
				}while(a.compareTo(n) >= 0 || a.compareTo(ONE) <= 0);
				
				int i = 0;
				
				// b = a^m mod n
				BigInteger b = a.modPow(m, n);
				
				// loop while !b=1 & i=0 OR !b = n-1
				//these are the conditions required for composite checking
				while(!((i==0 && b.equals(ONE)) || b.equals(nMinus))){
					
					//keep checking if b=1 & i > 0 || i+1 = k
					/* so skip first check(i=0)
					 * then b = b^2 mod n, 
					 * then check if b = 1 or if the next i = k
					 * k is the lowest set bit number from before
					 */
					if(i>0 && b.equals(ONE) || ++i==k){
						return false;
					}
					b = b.modPow(TWO, n);
				}
			}
			return true;
		}
	}
	
	public TestClass(){
		
	}
	static PrimeGen generator;
	static BigInteger[] keys = new BigInteger[3];
	static String message = "hello";
	public static void main(String[] args) {
		
		generator = new Gen("16");
		
		
		keys = generator.getKeys();
		System.out.println("Public Key(n): " + keys[0]);
		System.out.println("Public Exponent(e): " + keys[1]);
		System.out.println("Private Key(d): " + keys[2]);
		
		// TODO Auto-generated method stub
		//PrimeGen generate = new PrimeGen("50");
		RSA encryptor = new RSA();
		String message = "a";
		String decryptedMessage = "";
		//BigInteger[] keys = new BigInteger[3];
		BigInteger[] encrypted;
		BigInteger[] decrypted;

		
		//keys = generate.getKeys();
		
		System.out.println("INITIATING TEST ....\n---------------------------------------------------------------------------- \n");
		System.out.println("Test String: " + message);
		encrypted = encryptor.encrypt(message, keys[0], keys[1]);
		
		System.out.println("encrypted Message: ");
		for(int i=0;i<encrypted.length;i++){
			System.out.print(encrypted[i]);
		}
		
		decrypted = encryptor.decrypt(encrypted,keys[0], keys[2]);
		
		for(int i=0;i<decrypted.length;i++){
			decryptedMessage += ((char)decrypted[i].intValue());
		}
		System.out.println("\nDecrypted Message: " + decryptedMessage);
	}

}
