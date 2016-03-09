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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrimeGen generator = new PrimeGen();
		BigInteger[] keys = new BigInteger[3];
		
		keys = generator.getKeys();
		
		System.out.println("Done!!");
		System.out.println("E = " + keys[0]);
		System.out.println("N = " + keys[1]);
		System.out.println("D = " + keys[2]);

		

	}

}
