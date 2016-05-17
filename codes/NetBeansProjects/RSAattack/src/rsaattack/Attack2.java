package rsaattack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Discrete Logarithms attack
 * @author yi
 */
public class Attack2 {
    private int M; 
    private int C;
    private int enKey;
    private int n;
    
    private int moduloPower(int base, int power, int modulus){
        
        if (base ==0) return 0;
        
        int answer = 1;
        for(int i=0; i<power; i++) 
            answer = base * answer % modulus; 
        
        return answer;
            
        
    }
    
    public void attack(){
        for(int i = 0; i< n; i++){
            if (moduloPower(i,enKey,n) == C % n) {
                
                M= i;
                break;
            }
            
        } 
            
            
        System.out.println("The attack yields: ");
        System.out.println("M = "+ M);
    }

    public int getM() {
        return M;
    }

    public void setM(int M) {
        this.M = M;
    }

    public int getC() {
        return C;
    }

    public void setC(int C) {
        this.C = C;
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
    
    
    
}
