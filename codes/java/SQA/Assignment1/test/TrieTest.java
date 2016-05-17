/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import assignment1.Trie;
import assignment1.TrieNode;
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
public class TrieTest {
    
    Trie trie;
    
    public TrieTest() {
        trie = new Trie();
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        trie.add("sad");
        trie.add("happy");
        
    }
    
    @After
    public void tearDown() {
    }

    //test for the trie constructor
     @Test
     public void TestTrieConstructor() {
         
         
         
         //test if new trie has created successfully the root node.
         assertTrue(trie.root.letter == '#');
         assertTrue(trie.root.isendOfWord == false);
         assertTrue(trie.root.children.length == 26);
     
     }
     
     //test add function
    @Test 
    public void TestTrieAddWord() {
        
        assertTrue(trie.add("space"));
        assertFalse(trie.add(""));
        assertFalse(trie.add(null));
        
    }
    
    //test contain function
    @Test
    public void TestTrieContains() {
        assertTrue(trie.contains("happy"));
        assertFalse(trie.contains("happyending"));
        assertFalse(trie.contains("old"));
    }
    
    //test tostring true
    @Test
    public void TestTrieToStringT() {
        assertTrue(trie.toString().contains("happy"));
        assertTrue(trie.toString().contains("sad"));
        
    }
     
    //test tostring false
    @Test
    public void TestTrieToStringF() {
        assertFalse(trie.toString().contains("happyh"));
        assertFalse(trie.toString().contains("sady"));
        
    }
}
