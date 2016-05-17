/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;


import java.util.*;

public class Trie implements Iterable{

    public TrieNode root;
    protected transient int modCount;

public Trie (){
    
    root=new TrieNode('#');
}


    public boolean add(String word){

        if (word == null) return false;
	if (word.isEmpty() ) return false;
        return root.addWord(word);
    }

 public boolean contains(String word)
    {
        final int offset = 97;

        TrieNode currentNode = root;
        char[] characters = word.toCharArray();
        int size = characters.length;
        int index;                                      

        // loop until last character in the word
        for (index = 0; index < size; index++){

            if (currentNode == null)
                return false;
            currentNode = currentNode.children[characters[index]-offset];
        }

        // branch is shorter than word
        if (index == size && currentNode == null)
            return false;

        // branch is longer than word
        if (currentNode != null && !currentNode.isendOfWord)
            return false;

        return true;
    }


    public Iterator iterator() {
        TrieIterator iterator=new TrieIterator(root);
        return iterator;
    }

 /*
  * Returns a String representation of the Trie Object.
  *
  */
    @Override
      public String toString()
     {
          TrieIterator iterator=new TrieIterator(root);
          StringBuilder sb = new StringBuilder();
          String[] words=new String[10];
          int i=0;
          while(!iterator.hasNext())
    	{
    	        sb=sb.append(iterator.next());
                sb=sb.append("\n");
       	}   
          return sb.toString();
     }


  /*
   * Returns all the words in Trie containing a particular subString.
   */

    public String containsSubString(String subString)
    {
        String nextWord;
        boolean found;
        TrieIterator iterator=new TrieIterator(root);
        StringBuilder sb = new StringBuilder();
        while(!iterator.hasNext())
    	{
    		nextWord = iterator.next();
                found=nextWord.contains(subString);
                if(found)
                {
                  sb=sb.append(nextWord);
                  sb=sb.append("\n");
                }
    	}
        return sb.toString();
    }
       
}
