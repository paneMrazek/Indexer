package main.indexer.client.models;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class QualityChecker{

    private List<QualityCheckerListener> listeners = new ArrayList<>();

    private Trie trie;
    private Map<String, Trie> tries = new HashMap<>();

    private boolean hasKnownData;

    public void addListener(QualityCheckerListener listener){
        listeners.add(listener);
    }
	
	public void fieldchange(String knowndata){
        if(knowndata == null || knowndata.equals("")){
            hasKnownData = false;
            return;
        }
        hasKnownData = true;
        if(tries.containsKey(knowndata))
            trie = tries.get(knowndata);
        else{
            trie = new Trie();
            try{
                Scanner in = new Scanner(new URL(knowndata).openStream());
                in.useDelimiter(",");
                while (in.hasNext())
                    trie.add(in.next().toLowerCase());
                in.close();
                tries.put(knowndata, trie);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public void isValidEntry(int row, int col, String value){
		if(trie != null && hasKnownData) {
            boolean invalid = !trie.contains(value.toLowerCase());
            for(QualityCheckerListener listener : listeners){
                listener.setInvalid(row, col,invalid);
            }
        }
	}

    public List<String> findSuggestions(String inputWord, String knowndata){
        inputWord = inputWord.toLowerCase();
        ArrayList<String> possibilities = getAllEditDistanceWords(inputWord);
        List<String> similar = getAllValidWords(possibilities);

        if(similar.size() == 0){
            possibilities = getAllEditDistanceWords(possibilities);
            similar = getAllValidWords(possibilities);
        }
        return similar;
    }

    public ArrayList<String> getAllEditDistanceWords(List<String> words){
        ArrayList<String> possibilities = new ArrayList<>();
        for(String inputWord : words){
            possibilities.addAll(getAllEditDistanceWords(inputWord));
        }
        return possibilities;
    }

    public ArrayList<String> getAllEditDistanceWords(String inputWord){
        ArrayList<String> possibilities = new ArrayList<String>();
        possibilities.addAll(getDeletionWords(inputWord));
        possibilities.addAll(getTranspositionWords(inputWord));
        possibilities.addAll(getAlterationWords(inputWord));
        possibilities.addAll(getInsertionWords(inputWord));
        return possibilities;
    }

    public ArrayList<String> getDeletionWords(String word){
        ArrayList<String> deletions = new ArrayList<>();
        for(int i = 0; i < word.length(); i++){
            if(word.length() == 1)
                return deletions;
            String search = word.substring(0,i) + word.substring(i+1,word.length());
            deletions.add(search);
        }
        return deletions;
    }

    public ArrayList<String> getTranspositionWords(String word){
        ArrayList<String> transpositions = new ArrayList<>();
        for(int i = 0; i < word.length()-1; i++){
            String search = word.substring(0,i) + word.charAt(i+1) +
                    word.charAt(i) + word.substring(i+2,word.length());
            transpositions.add(search);
        }
        if(word.length() == 2)
            transpositions.add("" + word.charAt(1) + word.charAt(0));
        return transpositions;
    }

    public ArrayList<String> getAlterationWords(String word){
        ArrayList<String> alterations = new ArrayList<>();
        for(int i = 0; i < word.length(); i++){
            for(int j = 0; j < 26; j++){
                char c = (char) ('a' + j);
                String search = word.substring(0,i) + c + word.substring(i+1,word.length());
                alterations.add(search);
            }
        }
        return alterations;
    }

    public ArrayList<String> getInsertionWords(String word){
        ArrayList<String> insertions = new ArrayList<>();
        for(int i = 0; i < word.length() + 1; i++){
            for(int j = 0; j < 26; j++){
                char c = (char) ('a' + j);
                String search = word.substring(0,i) + c + word.substring(i,word.length());
                insertions.add(search);
            }
        }
        return insertions;
    }

    public List<String> getAllValidWords(List<String> possibilities){
        List<String> similar = new ArrayList<>();
        for(String word : possibilities){
            if(trie.contains(word))
                similar.add(word);
        }
        return similar;
    }

	private class Trie{

		private Node root = new Node();

		public void add(String word) {
			root.add(word.toLowerCase());
		}

		public boolean contains(String word) {
			return root.contains(word.toLowerCase());
		}
		
	}
	
	private class Node{
		boolean valid = false;
		Node[] children = new Node[26];

		public void add(String word) {
			char c = word.charAt(0);
			if(c-'a' < 0 || c-'a' > children.length)
				return;
            if(children[c-'a'] == null)
                children[c-'a'] = new Node();
			if(word.length() == 1){
                children[c-'a'].valid = true;
			}else{
				children[c-'a'].add(word.substring(1));
			}
		}

		public boolean contains(String word) {
            if (word.length() == 0)
                return true;
            char c = word.charAt(0);
            if (c - 'a' < 0 || c - 'a' > children.length)
                return true;
            if (word.length() == 1 && children[c - 'a'] != null) {
                return children[c - 'a'].valid;
            } else {
                if (children[c - 'a'] == null) {
                    return false;
                } else {
                    return children[c - 'a'].contains(word.substring(1));
                }
            }
        }

	}

    public interface QualityCheckerListener{
        public void setInvalid(int row, int col, boolean invalid);
    }

}
