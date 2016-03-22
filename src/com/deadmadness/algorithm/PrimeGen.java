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
	private int bitSize;
	private static final BigInteger TWO = new BigInteger("2");

	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private BigInteger e;
	private BigInteger d;
	private BigInteger phi;
	
	
	
	//constructor
	public PrimeGen(String bits){
		setBits(bits);
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
		System.out.println("(phi): " + phi);

	}
	
	
	

	
	//generates a prime number in a large  num range
	protected BigInteger genLargePrime() {
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
	protected boolean isPrime(BigInteger n) {
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
		/*
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
		 *//*
		for(BigInteger i = new BigInteger("3"); i.compareTo(sqRoot)==-1; i = i.add(TWO)) 
		{
			//check if num divides evenly anywhere
			if(n.remainder(i).equals(BigInteger.ZERO)) {
				
				System.out.println("Not prime at number: " + i);
				return false;
			}
			
		}
		return true;
		*/
	}
	
	
	
	/*
	
	//generates an approx root of prime number
	private static BigInteger root(BigInteger n) {
		BigInteger divisor = n.shiftRight(1);
		//while ((n/2) * (n/2)) greater than N, 
		while(divisor.multiply(divisor).compareTo(n) > 0) {
			divisor = divisor.shiftRight(1);
		}
		//System.out.println("Root: " + divisor.shiftLeft(1));
		return divisor.shiftLeft(1);
	}*/
	
	
	
	
	
	
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
		keys[0] = n;	//public key
		keys[1] = e;	//public exponent
		keys[2] = d;	//private key
		return keys;
	}
	
	public void setBits(String bits){
		
		bitSize = Integer.parseInt(bits);
	}
	
	public int getBits(){
		return bitSize;
	}
}
