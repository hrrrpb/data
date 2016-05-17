/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsaattack;

import java.util.ArrayList;
import java.util.List;

/**
 * factorization attack
 * @author yi
 */
public class Attack1 {
    
    
    
    //deKey: decryption key; enKey: encryption keyp; p: primer1; q: primer2; C: cipher; M: plain text;
    
    private int deKey;
    private int enKey;
    private int n;
    private int p;
    private int q;
    private int C;
    private int M;
    
    
    
    
    private int findPrivateKey(int enKey, int p, int q) {
        
        
        int totient = (p-1)*(q-1);
        
        //in order to make sure enKey exists, deKey and totient should be coprime
        
        int deKey = findInverseMod(totient, enKey)[1];
        if (deKey < 0) deKey +=totient; 
            return deKey;
        
    }
    
    
    //find all prime numbers <= n
    private List<Integer> findPrimeNumbers(int n) {
        
        //get the sqrt of the number 
        int limit = (int) Math.sqrt(n);
        ArrayList<Integer> primeNumbers = new ArrayList();
        
        //add all odd numbers to the number list, excluding 1
        for(int number =3 ; number <= n; number = number +2) 
            primeNumbers.add(number);
        
        //cross out multiples of numbers from 3 to the sqrt of the number
        for(int i = 3; i <= limit; i=i+2) {
            for(int j=3; j*i <= n; j=j+2) 
                primeNumbers.remove(new Integer(i*j));
              
        }
        
        //add the only even prime number 2
        primeNumbers.add(2);
        
        return primeNumbers;
    }
    
    
    //find prime divisors p and q, if not applicable, return 0,0
    private int[] findPrimeDivisors(int n) {
        
        int[] divisors = new int[2];
        List<Integer> numbers = findPrimeNumbers(n);
        for(int i= 0; i<numbers.size(); i++) 
            for(int j= i+1; j< numbers.size(); j++) 
                if (numbers.get(i)*numbers.get(j) == n) {
                    divisors[0]= numbers.get(i);
                    divisors[1] = numbers.get(j);
                    
                    break;
                }
            
        return divisors;
    }
    
    //Euclidean algorithm to get the modulo inverse, returned is the coefficients of a and b to get 1
    //the final coeff[1] is the inverse modulo of b
    
    private int[] findInverseMod(int a, int b) {
        if(a%b ==1) {
            int[] coeff = new int[2];
            coeff[0] = 1;
            coeff[1] = a/b *(-1);
            return coeff;
        }
        
        if(a%b==0) {
            //in case ,if a and b are not coprime
            int[] array = {0,0};
            return array;
        }
        
        else {
            int r = a%b;
            int q = a/b*(-1);
            int[] coeff = findInverseMod(b,r);
            int[] newcoeff = new int[2];
            newcoeff[0] = coeff[1];
            newcoeff[1] = coeff[0] + coeff[1]* q; 
            return newcoeff;
            
        }
      
    }
    
    //modulo exponetiation calculation
    
    public int moduloPower(int base, int power, int modulus){
        if (base ==0) return 0;
        int answer = 1;
        for(int i=0; i<power; i++) {
            answer = answer * base % modulus; 
            System.out.println(answer);
        }
            
        
        return answer;
            
        
    }
    
    //the attack function
    
    public void attack() {
        
        //find p and q from n
        p = findPrimeDivisors(n)[0];
        q = findPrimeDivisors(n)[1];
        
        //find private key
        deKey = findPrivateKey(enKey, p, q);
        
        //find plain text from cipher text and deKey
        M = moduloPower(C, deKey, n);
        System.out.println("The attack yields: ");
        System.out.println("p = "+ p);
        System.out.println("q = "+ q);
        System.out.println("d = "+ deKey);
        System.out.println("M = "+ M);
        
        
    }

    public int getDeKey() {
        return deKey;
    }

    public void setDeKey(int deKey) {
        this.deKey = deKey;
    }

    public int getEnKey() {
        return enKey;
    }

    public void setEnKey(int enKey) {
        this.enKey = enKey;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getC() {
        return C;
    }

    public void setC(int C) {
        this.C = C;
    }

    public int getM() {
        return M;
    }

    public void setM(int M) {
        this.M = M;
    }
    
    
    
    
    
}
