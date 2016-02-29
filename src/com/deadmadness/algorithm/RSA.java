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

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrimeGen generator = new PrimeGen();
		BigInteger p = generator.genLargePrime();
		BigInteger q = generator.genLargePrime();
		BigInteger n = p.multiply(q);
		
		System.out.println("Done!!");
		System.out.println("P = " + p);
		System.out.println("Q = " + q);
		System.out.println("N = " + n);
		
		// phi = (p-1) * (q-1)
		BigInteger phi = (p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE))));
		
		BigInteger pubKey;

		System.out.println("Phi:" + phi);
		/*
		pubKey = generator.genPublicKey(phi);
		*/
		/*
		e = generator.genPrime();
		
		System.out.println("P:" + p + " Q:" + q + " N minus: " + nMinus);
		
		if(generator.relPrime(e,nMinus)){
			pubKey = "" + n +", "+ e;
			System.out.println("Public Key:" + pubKey);
		}
		else {
			System.out.println("No relPrime found");
		}
		*/
	}

}
