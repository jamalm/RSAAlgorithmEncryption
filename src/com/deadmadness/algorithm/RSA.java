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
		//int nMinus = (p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE)));
		/*
		String pubKey;
		int e;
		while(n < 0 || nMinus < 0) {
			p = generator.genLargePrime();
			q = generator.genLargePrime();
			n = p*q;
			if ((nMinus = (p-1)*(q-1)) < 0) {
				System.out.println("nMinus is too large");
				n = -1;
			}
		}
		

		System.out.println("N:" + n);
		
		e = generator.genPrime();
		
		System.out.println("P:" + p + " Q:" + q + " N minus: " + nMinus);
		
		if(generator.relPrime(e,nMinus)){
			pubKey = "" + n +", "+ e;
			System.out.println("Public Key:" + pubKey);
		}
		else {
			System.out.println("No relPrime found");
		}*/
	}

}
