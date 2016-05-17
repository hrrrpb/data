/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;


public class TrieNode {

	public char letter; 
	public TrieNode[] children;
	public boolean isendOfWord;

	
	public TrieNode(char letter) {
		this.letter = letter;
		children = new TrieNode[26]; 
		this.isendOfWord = false; 
	}

	public boolean addWord(String word) {
		if (word.length() == 0) {
			isendOfWord = true;
			return true;
		}
		TrieNode forWord = nodeFor(word);
		word = word.substring(1);
		forWord.addWord(word);
                return true;            
	}

	public TrieNode nodeFor(String word) {
		char c = word.charAt(0);
		int index = c - 'a';
		if (children[index] == null)
			children[index] = new TrieNode(c);
		return children[index];
	}
}
