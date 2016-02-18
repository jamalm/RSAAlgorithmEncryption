/**
 * 
 */
package com.deadmadness.algorithm;

import java.util.Random;
/**
 * @author deadmadness
 *
 */
public class PrimeGen {
	private int p;
	private int e;
	private Random r;
	
	PrimeGen(){
		//constructor
	}
	
	//generates a prime number in a large  num range
	public int genLargePrime() {
		r = new Random();
	
		while(true){
			p = r.nextInt(Integer.MAX_VALUE/5 - (Integer.MAX_VALUE/10)) + (Integer.MAX_VALUE/10);
			if(isPrime(p)) {
				System.out.println("Prime Number:" + p);
				break;
			}
		}
		return p;
	}
	
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
		
	}
	// checks to see if prime number
	private boolean isPrime(int n) {
		int i;
		
		//checks number for primality
		for(i=2;i<=Math.sqrt(n);i++){
			if(n % i == 0) {
				return false;
			}
		}
		return true;
	}
	

}
