/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsaattack;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author yi
 */
public class RSAattack {
    
    
    public void start() {
        System.out.println("RSA Attack Demo");
        
        Scanner userInput = new Scanner(System.in);
        String choice = "";
        while(!choice.equals("3")){
            System.out.println("\n\n1. Factorization attack");
            System.out.println("2. Discrete Logarithms attack");
            System.out.println("3. Quit \n");
            
            System.out.println("Choose your operation: ");
            choice = userInput.nextLine().trim();
            
            
            switch(choice){
                case "1": {
                    //input the required info for encryption
                    System.out.println("Please input the e, n and C, separated by space: ");
                    int e = Integer.parseInt(userInput.next());
                    int n = Integer.parseInt(userInput.next());
                    int C = Integer.parseInt(userInput.next());
                    
                    //clear the input buffer
                    userInput.nextLine();
                    
                    
                    //set parameters
                    Attack1 attk = new Attack1();
                    attk.setC(C);
                    attk.setEnKey(e);
                    attk.setN(n);
                    
                    long startTime = System.currentTimeMillis();

                    attk.attack();

                    long endTime = System.currentTimeMillis();

                    System.out.println("The attack took " + (endTime - startTime) + " milliseconds");
                   
                    break;
                }
                case "2":{
                    
                    //input the required info for encryption
                    System.out.println("Please input the e, n and C, separated by space: ");
                    int e = Integer.parseInt(userInput.next());
                    int n = Integer.parseInt(userInput.next());
                    int C = Integer.parseInt(userInput.next());
                    
                    //clear the input buffer
                    userInput.nextLine();
                    
                    
                    //set parameters
                    Attack2 attk = new Attack2();
                    attk.setC(C);
                    attk.setEnKey(e);
                    attk.setN(n);
                    
                    long startTime = System.currentTimeMillis();

                    attk.attack();

                    long endTime = System.currentTimeMillis();

                    System.out.println("The attack took " + (endTime - startTime) + " milliseconds");
                   
                    break;
                }
                
                
                case "3" : break;
                default: System.out.println("You have to choose from 1-3");
            }
            
            
        }  
        
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //new test 
        //2737 226679  1234 129902
        
        RSAattack test = new RSAattack();
        test.start();
        
    }
    
}
