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
	//private static final BigInteger TWO = new BigInteger("2");

	//large primes p and q
	private BigInteger p;
	private BigInteger q;
	//public key
	private BigInteger n;
	//public exponent
	private BigInteger e;
	//private key
	private BigInteger d;
	//phi is n-1
	private BigInteger phi;
	
	
	
	//constructor to initialise public/private keys and public exponent
	public PrimeGen(String bits){
		//set the number size i.e. 2^bits
		setBits(bits);
		//generate p and q prime numbers
		p = genLargePrime();
		q = genLargePrime();
		//set n = pq
		n = p.multiply(q);
				
		// phi = (p-1) * (q-1)
		phi = (p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE))));
		//set e so that 1<e<phi && e and phi are coprime
		e = genE(phi);
		
		//if genE returns 0, error occured, try again
		while(e.equals(BigInteger.ZERO)){
			e = genE(phi);
		}
		
		// e*d = 1 mod(phi)
		//i.e. get the mod inverse of E to find D
		d = modInverse(phi, e);
		System.out.println("(phi): " + phi);

	}
	
	
	

	
	//generates a prime number in a set number range (see bits in constructor)
	protected BigInteger genLargePrime() {
		//securely generates a random number
		r = new SecureRandom();
		//p is our prime in question
		BigInteger p;
		
		// loop until we find a prime
		while(true){
			//initialise p randomly in the range of 2^bitSize
			p = new BigInteger(bitSize, r); //p = r.nextInt(Integer.MAX_VALUE/5 - (Integer.MAX_VALUE/10)) + (Integer.MAX_VALUE/10);
			
			System.out.println("Calculating Prime: " + p + " with bitSize: " + bitSize);
			
			//check if prime, if true, break from loop
			if(isPrime(p)) {
				System.out.println("Prime Number:" + p);
				break;
			}
			//else if false, number is composite and try again
			else {
				System.out.println("no prime found");
			}
		}
		return p;
	}

	
	
	
	/*
	 * genE is used to choose E so that 1<e<phi && e is coprime with phi
	 */
	private BigInteger genE(BigInteger phi) {
		//generate a random number
		BigInteger e = genLargePrime();
		
		//check if e is greater than phi, if true, return error 
		if(e.compareTo(phi) == 1){
			System.out.println("E is larger than Phi - failed!");
			return BigInteger.ZERO;
		}
		
		// if gcd(phi,e) = 1, return e
		if(gcd(e,phi)) {
			System.out.println("Relatively prime: "+e+" "+phi);
			return e;
		}
		//else return error
		else{
			System.out.println("Failed, E and phi not relatively prime");
			return BigInteger.ZERO;
		}
	}

	
	
	
	
	//calculates if e and phi are relatively prime
	private boolean gcd(BigInteger a, BigInteger b) {
		BigInteger i;
		//if phi mod e is 0, return false
		if(b.mod(a) == BigInteger.ZERO) {
			return false;
		}
		//else set i to be phi mod e
		else {
			i = b.mod(a);
		}
		
		//while i is not 0, iterate through the gcd
		/*
		 * algorithm:
		 * 1. phi = e
		 * 2. e = i
		 * 3. i = phi mod e
		 * 4. loop until phi mod e returns 0
		 */
		while(i.compareTo(BigInteger.ZERO) != 0) {
			b = a;
			a = i;
			i = b.mod(a);
		}
		
		//if the remainder is 1, return true
		if(a.compareTo(BigInteger.ONE) == 0){
			return true;
		}
		//else it's not coprime, return false
		else {
			return false;
		}

	}
	
	
	
	
	
	
	// checks to see if number is composite(false) or prime(true)
	/*
	 * for this I used the Rabin Miller probable primality test algorithm 
	 * due to it's speed and higher accuracy compared to Little Fermat's theorem
	 */
	protected boolean isPrime(BigInteger n) {
		//securely generates a random number
		SecureRandom r = new SecureRandom();
		
		BigInteger ZERO = BigInteger.ZERO;
		BigInteger ONE = BigInteger.ONE;
		BigInteger TWO = new BigInteger("2");
		
		// nMinus = n - 1
		BigInteger nMinus = n.subtract(ONE);

		//check if n is even, returns composite if true
		if(n.mod(TWO).equals(ZERO)){
			System.out.println("P%2 returned true: False prime");
			return false;
		}
		
		//find n-1 = 2^k*m
		BigInteger m = nMinus;
		//k is the power, set it to the lowermost bit of m
		int k = m.getLowestSetBit();
		//shift m over by k bits
		m = m.shiftRight(k);

		System.out.println("Starting loop");
		
		//j is our level of certainty which is set to 100 by default, we want a high chance of primality for each number
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
		 * Below is the commented out version of primality testing 
		 * It was my original prime check before using rabin miller algorithm
		 * Much slower for larger numbers
		 */
		
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
	
	
	
	
	
	
	//Gets ed mod phi = 1 using the extended euclidean algorithm
	private BigInteger modInverse(BigInteger phi ,BigInteger e){
		
		BigInteger p = phi;
		BigInteger x1 = BigInteger.ZERO;
		BigInteger x2 = BigInteger.ONE;
		BigInteger y1 = BigInteger.ONE;
		BigInteger y2 = BigInteger.ZERO;
		BigInteger temp;
		BigInteger q, r;
		
		//while e is not 0
		while(!e.equals(BigInteger.ZERO)){
			//quotient = phi/e
			q = p.divide(e);
			//remainder = p - (quotient*e)
			r = p.subtract(q.multiply(e));
			
			//swapping variables before doing arithmetic
			//phi = e
			p = e;
			//e = remainder
			e = r;
			
			//temporarily store x1
			temp = x1;
			//x1 = x2 - (quotient*x1)
			x1 = x2.subtract(q.multiply(x1));
			//move x1 into x2 i.e. the previous X
			x2 = temp;
			
			//temporarily store y1
			temp = y1;
			//y1 = y2 - (quotient*y1)
			y1 = y2.subtract(q.multiply(y1));
			//move the current y into the previous y slot
			y2 = temp;
		}
		//check if the last y was negative
		if(y2.compareTo(BigInteger.ZERO) == -1){
			//if true, return phi+y2
			return phi.add(y2);
		}
		//else if positive, return y2
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
	
	//getter and setter for bit size of prime numbers
	public void setBits(String bits){
		
		bitSize = Integer.parseInt(bits);
	}
	
	public int getBits(){
		return bitSize;
	}
}
