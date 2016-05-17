/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import java.util.Iterator;

public class TrieIterator implements Iterator<String> {

    private TrieNode currentNode, root;
    private StackADT lifo;
    private StackNode stNode, tempNode;
    String nextWord = "";
    
    public TrieIterator(TrieNode root) {
        this.currentNode=root;
    	this.root = root;
    	this.stNode=new StackNode(root,Character.toString(root.letter));
    	lifo=new StackADT();
    	lifo.push(stNode);
    }

 /*
  * Returns true if the iterator has a valid next element.
  *
  */
    public boolean hasNext() {
      return (lifo.isEmpty());
    }

   /*
    * Returns the next element in the Trie.
    */
    public String next(){

    	int previousIndex=-1;
    	int currentIndex=0;
        final int OFFSET=97;
    	final int MAXSIZE=26;
        boolean visitedWord = false,lastwordvisited=true;
    	String previousWord = nextWord;
    	char token = '+';
    	
    	do{
                // search for existing children
    		for(currentIndex=previousIndex+1;currentIndex<MAXSIZE;currentIndex++)
    		{
    			if(currentNode.children[currentIndex] != null)
    				break;
    		}

                // if children is found
    		if(currentIndex!=MAXSIZE)
    		{
                        currentNode = currentNode.children[currentIndex];
   			nextWord = previousWord + token;

    			stNode=new StackNode(currentNode,nextWord);
    			lifo.push(stNode);
    		
      			previousWord = previousWord + currentNode.letter;
    			previousIndex=-1;
    			visitedWord=false;

    		}
    		else
    		{
                        // no child found so pop from stack.
                        tempNode = lifo.pop();

    			if(stNode.node.letter != root.letter)
    				stNode = lifo.peek();
    			else
    				break;
    			visitedWord = true;
    			previousIndex = tempNode.node.letter-OFFSET;
    			nextWord = nextWord.substring(0,nextWord.length()-1);
    			previousWord = nextWord;
    			currentNode = stNode.node;
    			continue;
    		}
                lastwordvisited =  !currentNode.isendOfWord||visitedWord;

    	}while(lifo.isEmpty()|| lastwordvisited);

        // if current stack node is root.
    	if(root.letter == stNode.node.letter )
    	{
    	        return ("");
    	}
        else
        {
                stNode=lifo.peek();
    		nextWord = nextWord.replace(token, stNode.node.letter);
    		return nextWord;
        }
		
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}

