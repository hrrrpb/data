/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import lecture1.Account;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yi
 */
public class AccountTest {
    Account ac; 
    
    public AccountTest() {
        ac = new Account();
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
       
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void TestNameT() {
         ac.setName("Yi");
         assertTrue(ac.getName() == "Yi");  
     }
     
     @Test
     public void TestNameF() {
         
         ac.setName("Yi");
         assertFalse(ac.getName() == "yi");
     
     }
     
     @Test
     public void TestDOBT() {
         ac.setDOB("1/1/1980");      
         assertTrue(ac.getDOB() == "1/1/1980");
    
     }
     
 
     
     @Test
     public void TestDOBF() {
         Account ac = new Account();
         ac.setDOB("1/1/1980");      
         assertFalse(ac.getDOB() == "1/31/1980");
    
     }
     
     @Test
     public void TestEmailT() {
         
         ac.setEmail("yxuecs@gmail.com");
         assertTrue(ac.getEmail() == "yxuecs@gmail.com");
     
     }
     
     @Test
     public void TestEmailF() {
         
         ac.setEmail("yxuecs@gmail.com");
         assertFalse(ac.getEmail() == "yxue@gmail.com");
     
     }
     
     @Test
     public void TestFull_nameT() {
         
         ac.setFull_name("Yi Xue");
         assertTrue(ac.getFull_name() == "Yi Xue");
        
     
     }
     
     @Test
     public void TestFull_nameF() {
         
         ac.setFull_name("Yi Xue");
         assertFalse(ac.getFull_name() == "yi sue");
     
     }
     
     @Test
     public void TestSSNT() {
         
         ac.setSSN("12345678");
         assertTrue(ac.getSSN() == "12345678");
        
     }
     
     @Test
     public void TestSSNF() {
         
         ac.setSSN("12345678");
         assertFalse(ac.getSSN() == "87655678");
        
     }
     
     
     @Test
     public void TestTypeT() {
         
         ac.setType("graduate");
         assertTrue(ac.getType() == "graduate");
     
     }
     
     @Test
     public void TestTypeF() {
         
         ac.setType("graduate");
         assertFalse(ac.getType() == "undergraduate");
     
     }
     
     @Test
     public void TestUserT() {
         
         ac.setUser("Group1");
         assertTrue(ac.getUser() == "Group1");
         
     
     }
     
     @Test
     public void TestUserF() {
         
         ac.setUser("Group1");
         assertFalse(ac.getUser() == "Group2");  
     
     }
     
     
}
