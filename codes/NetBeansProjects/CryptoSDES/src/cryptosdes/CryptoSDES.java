/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptosdes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * HOMEWORK 1 PROGRAMMING
 * @author yi
 */
public class CryptoSDES {

    private String inputText;
    private String key;
    private SDES sdes; 
    private List<Integer> cipherText; 
    
    
    public CryptoSDES(){
        sdes = new SDES();
    }
    
    //convert input String to Integer list to feed into the SDES class
    private List<Integer> strToList(String text) {
        ArrayList<Integer> intList = new ArrayList();
        for(int index=0; index< text.length();index++) {
            intList.add(Integer.valueOf(text.charAt(index)-48));
            
        }
        return intList;
    }
    
    
    private void attack(){
        System.out.println("Brutal force attack yields : ");
        for (int key = 0; key< 1024; key++) {
            String binaryFormat = Integer.toBinaryString(key);
            for(int i = 10 - binaryFormat.length(); i> 0; i--) {
                binaryFormat = "0"+binaryFormat;
            }
           
            sdes.setSecretKey(strToList(binaryFormat));
            sdes.encryption();
            if (cipherText.equals(sdes.getCipherText())) {
                
                System.out.println("The hacked secret key is: " + sdes.getSecretKey());
                
            }
            
        }
        
        
        //System.out.println("Brutal force attack could not find solution.");
        
       
        
    }
    
    public void start() {
        System.out.println("Simplied DES Demo");
        
        Scanner userInput = new Scanner(System.in);
        String choice = "";
        while(!choice.equals("5")){
            System.out.println("\n\n1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Encryption with modified S0");
            System.out.println("4. Brutal force attack");
            System.out.println("5. Quit");
            
            System.out.println("Choose your operation: ");
            choice = userInput.nextLine().trim();
            
            
            switch(choice){
                case "1": {
                    //input the required info for encryption
                    System.out.println("Please input the plain text: ");
                    inputText = userInput.nextLine().trim();
                    System.out.println("Please input the key: ");
                    key = userInput.nextLine().trim();
                    
                    //set up SDES parameters
                    sdes.setPlainText(strToList(inputText));
                    sdes.setSecretKey(strToList(key));
                    sdes.encryption();
                    break;
                }
                case "2":{
                    System.out.println("Please input the cipher text: ");
                    inputText = userInput.nextLine().trim();
                    System.out.println("Please input the key: ");
                    key = userInput.nextLine().trim();
                    
                    //set up SDES parameters
                    sdes.setCipherText(strToList(inputText));
                    sdes.setSecretKey(strToList(key));
                    sdes.decryption();
                    break;
                }
                case "3" : {
                    
                    System.out.println("Please input the plain text: ");
                    inputText = userInput.nextLine().trim();
                    System.out.println("Please input the key: ");
                    key = userInput.nextLine().trim();
                    
                    //set up SDES parameters
                    sdes.setPlainText(strToList(inputText));
                    sdes.setSecretKey(strToList(key));
                    
                    //change the S0, this part can be input by user, for simplicity, it is set in the code
                    List<Integer> modS0 = Arrays.asList(1,0,3,2,
                                                        3,1,3,2,
                                                        0,2,1,3,
                                                        3,2,1,0);
                    sdes.modifyS0(modS0);
                    
                    sdes.encryption();
                    sdes.resetS0();  //reset S0 to the published value
                    break;
                
                }
                
                case "4" : {
                    System.out.println("Please input the plain text: ");
                    inputText = userInput.nextLine().trim();
                    sdes.setPlainText(strToList(inputText));
                    
                    System.out.println("Please input the cipher text: ");
                    inputText = userInput.nextLine().trim();
                    cipherText = strToList(inputText);
                    
                    attack();
                    break;
                }
                
                case "5" : break;
                default: System.out.println("You have to choose from 1-5");
            }
            
            
        }  
        
    }
    
    
    
    public static void main(String[] args) {
        CryptoSDES sdesTest = new CryptoSDES();
        sdesTest.start();
      

    }
    
}
