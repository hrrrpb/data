/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphertest;

/**
 *
 * @author yi
 */
public class CipherTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int shift=65; 
        String test = "ORUBKIUSVAZKXY";
        for(int i=1; i<26; i++) {
            StringBuilder x = new StringBuilder();
            for(int charIndex = 0; charIndex< test.length(); charIndex++) {
                int tmp = ((int)test.charAt(charIndex)-shift+i)%26;
                x.append((char)(tmp+shift));
                
            }
            System.out.println(i);
            System.out.println(x);
        }
        
    }
    
}
