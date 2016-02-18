/**
 * 
 */
package com.deadmadness.algorithm;

/**
 * @author deadmadness
 *
 */
public class Encryption {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrimeGen generator = new PrimeGen();
		int p = generator.genLargePrime();
		int q = generator.genLargePrime();
		int n = p*q;
		int nMinus = (p-1)*(q-1);
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
		}
	}

}
