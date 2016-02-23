/**
 * 
 */
package com.deadmadness.algorithm;

import java.security.SecureRandom;
import java.math.BigInteger;
/**
 * @author deadmadness
 *
 */
public class PrimeGen {
	private SecureRandom r = new SecureRandom();
	
	private static int bitSize = 48;
	private static final BigInteger TWO = new BigInteger("2");

	private BigInteger p;
	
	PrimeGen(){
		//constructor
	}

	
	//generates a prime number in a large  num range
	public BigInteger genLargePrime() {
		r = new SecureRandom();
	
		while(true){
			p = new BigInteger(bitSize, r); //p = r.nextInt(Integer.MAX_VALUE/5 - (Integer.MAX_VALUE/10)) + (Integer.MAX_VALUE/10);
			
			System.out.println("Calculating Prime: " + p + " with bitSize: " + bitSize);
			
			if(isPrime(p)) {
				System.out.println("Prime Number:" + p);
				break;
			}
			System.out.println("no prime found");
		}
		return p;
	}
	/*
	public int genPrime(){
		r = new Random();
		
		while(true){
			e = r.nextInt(127 - 2) + 2;
			if(isPrime(e)) {
				System.out.println("Choose E:" + e);
				break;
			}
		}
		return e;
	}
	
	public boolean relPrime(int e, int n) {
		if(gcd(e,n) == 1) {
			System.out.println("Done!");
			return true;
		}
		else{
			System.out.println("Failed, not relatively prime");
			return false;
		}
	}
	/*
	//calculates gcd
	private int gcd(int a, int b) {
		int n = 0;
		int i;
		if(b>a) {
			if(b%a == 0) {
				n = b/a;
				return n;
			}
			else {
				i = b%a;
			}
			while(i != 0) {
				b = a;
				a = i;
				i = b%a;
			}
			n = a;
			return n;
		}
		else if(a>b) {
			if(a%b == 0) {
				n = a/b;
				return n;
			}
			else {
				i = a%b;
			}
			while(i != 0) {
				a = b;
				b = i;
				i = a%b;
			}
			n = b;
			return n;
		}
		return n;
		
	}*/
	
	// checks to see if prime number
	private boolean isPrime(BigInteger n) {
		BigInteger sqRoot = root(n);
		
		// if n is less than 2, false
		if(n.compareTo(TWO) == -1) {
			return false;
		}
		
		// check if even number, if it is, it's false
		if(n.remainder(TWO).equals(BigInteger.ZERO)) {
			return false;
		}
		
		System.out.println("Square root prime: " + sqRoot);
		System.out.println("Crunching numbers...");
		
		/*
		 * i start at 3 to num > sqRoot; add 2 to skip even numbers
		 */
		for(BigInteger i = new BigInteger("3"); i.compareTo(sqRoot)==-1; i = i.add(TWO)) 
		{
			System.out.println(i);
			//check if num divides evenly anywhere
			if(n.remainder(i).equals(BigInteger.ZERO)) {
				
				System.out.println("Not prime!");
				return false;
			}
			
		}
		
		return true;
	}
	
	private static BigInteger root(BigInteger n) {
		BigInteger divisor = n.shiftRight(1);
		//while ((n/2) * (n/2)) greater than N, 
		while(divisor.multiply(divisor).compareTo(n) > 0) {
			divisor = divisor.shiftRight(1);
		}
		return divisor.shiftLeft(1);
	}
	

}
