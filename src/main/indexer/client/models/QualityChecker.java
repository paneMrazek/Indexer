package main.indexer.client.models;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class QualityChecker{
	
	private Trie trie;
	
	public void fieldchange(String knowndata){
		trie = new Trie();
		try{
			Scanner in = new Scanner(new URL(knowndata).openStream());
			in.useDelimiter(",");
			while(in.hasNext())
				trie.add(in.next());
			in.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public boolean isValidEntry(String value){
		return trie.find(value) != null;
	}

	private class Trie{

		private Node root = new Node();

		public void add(String word) {
			root.add(word.toLowerCase());
		}

		public Node find(String word) {
			return root.find(word.toLowerCase());
		}
		
	};
	
	private class Node{
		int count = 0;
		Node[] children = new Node[26];
		
		public int add(String word) {
			char c = word.charAt(0);
			if(c-'a' < 0 || c-'a' > children.length)
				return 0;
			if(word.length() == 1){
				if(children[c-'a'] == null){
					children[c-'a'] = new Node();
					children[c-'a'].count++;
					return 1;
				}else{
					children[c-'a'].count++;
					return 0;
				}
			}else{
				if(children[c-'a'] == null){
					children[c-'a'] = new Node();
					return 1 + children[c-'a'].add(word.substring(1));
				}else{
					return children[c-'a'].add(word.substring(1));
				}
			}
		}
		
		public Node find(String word){
			char c = word.charAt(0);
			if(c-'a' < 0 || c-'a' > children.length)
				return null;
			if(word.length() == 1 && children[c-'a'] != null){
				if(children[c-'a'].getValue() > 0)
					return children[c-'a'];
				else
					return null;
			}else{
				if(children[c-'a'] == null){
					return null;
				}else{
					return children[c-'a'].find(word.substring(1));
				}
			}
		}
	
		public int getValue(){
			return count;
		}
	}


}
