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
	
	//Set to 128 for 2^128 numbers
	private static int bitSize = 50;
	private static final BigInteger TWO = new BigInteger("2");

	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private BigInteger e;
	private BigInteger d;
	private BigInteger phi;
	
	
	
	//constructor
	PrimeGen(){

		p = genLargePrime();
		q = genLargePrime();
		n = p.multiply(q);
				
		// phi = (p-1) * (q-1)
		phi = (p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE))));
		e = genE(phi);
		
		
		while(e.equals(BigInteger.ZERO)){
			e = genE(phi);
		}
		
		d = modInverse(phi, e);

	}
	
	
	

	
	//generates a prime number in a large  num range
	private BigInteger genLargePrime() {
		r = new SecureRandom();
		BigInteger p;
	
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

	
	
	
	
	
	
	private BigInteger genE(BigInteger phi) {
		BigInteger e = genLargePrime();
		
		if(e.compareTo(phi) == 1){
			System.out.println("E is larger than Phi - failed!");
			return BigInteger.ZERO;
		}
		
		if(gcd(e,phi)) {
			System.out.println("Relatively prime: "+e+" "+phi);
			return e;
		}
		else{
			System.out.println("Failed, E and phi not relatively prime");
			return BigInteger.ZERO;
		}
	}

	
	
	
	
	//calculates if e and phi are relatively prime
	private boolean gcd(BigInteger a, BigInteger b) {
		BigInteger i;
		
		if(b.mod(a) == BigInteger.ZERO) {
			return false;
		}
		else {
			i = b.mod(a);
		}
		while(i.compareTo(BigInteger.ZERO) != 0) {
			b = a;
			a = i;
			i = b.mod(a);
		}
		if(a.compareTo(BigInteger.ONE) == 0){
			return true;
		}
		else {
			return false;
		}

	}
	
	
	
	
	
	
	// checks to see if prime number
	private boolean isPrime(BigInteger n) {
		BigInteger sqRoot = root(n);
		
		// if n is less than 2, false
		if(n.compareTo(TWO) == -1) {
			return false;
		}
		
		// check if even number
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
			//check if num divides evenly anywhere
			if(n.remainder(i).equals(BigInteger.ZERO)) {
				
				System.out.println("Not prime at number: " + i);
				return false;
			}
			
		}
		return true;
	}
	
	
	
	
	
	//generates an approx root of prime number
	private static BigInteger root(BigInteger n) {
		BigInteger divisor = n.shiftRight(1);
		//while ((n/2) * (n/2)) greater than N, 
		while(divisor.multiply(divisor).compareTo(n) > 0) {
			divisor = divisor.shiftRight(1);
		}
		//System.out.println("Root: " + divisor.shiftLeft(1));
		return divisor.shiftLeft(1);
	}
	
	
	
	
	
	
	//Gets ed mod phi = 1
	private BigInteger modInverse(BigInteger phi ,BigInteger e){
		
		BigInteger p = phi;
		BigInteger x1 = BigInteger.ZERO;
		BigInteger x2 = BigInteger.ONE;
		BigInteger y1 = BigInteger.ONE;
		BigInteger y2 = BigInteger.ZERO;
		BigInteger temp;
		BigInteger q, r;
		
		while(!e.equals(BigInteger.ZERO)){
			q = p.divide(e);
			r = p.subtract(q.multiply(e));
			
			p = e;
			e = r;
			
			temp = x1;
			x1 = x2.subtract(q.multiply(x1));
			x2 = temp;
			
			temp = y1;
			y1 = y2.subtract(q.multiply(y1));
			y2 = temp;
		}
		if(y2.compareTo(BigInteger.ZERO) == -1){
			return phi.add(y2);
		}
		else{
			return y2;
		}
	}

	
	
	

	//returns generated keys
	public BigInteger[] getKeys(){
		BigInteger[] keys = new BigInteger[3];
		keys[0] = e;
		keys[1] = n;
		keys[2] = d;
		return keys;
	}
}
