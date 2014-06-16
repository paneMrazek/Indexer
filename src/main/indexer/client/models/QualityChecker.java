package main.indexer.client.models;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class QualityChecker{

    private List<QualityCheckerListener> listeners = new ArrayList<>();
    private List<SeeSuggestionsListener> suggestionsListeners = new ArrayList<>();

    private Trie trie;
    private Map<String, Trie> tries = new HashMap<>();

    private boolean hasKnownData;

    public void addListener(QualityCheckerListener listener){
        listeners.add(listener);
    }
    public void addListener(SeeSuggestionsListener listener){
        suggestionsListeners.add(listener);
    }
	
	public void fieldChange(String knownData){
        if(knownData == null || knownData.equals("")){
            hasKnownData = false;
            return;
        }
        hasKnownData = true;
        if(tries.containsKey(knownData))
            trie = tries.get(knownData);
        else{
            trie = new Trie();
            try{
                Scanner in = new Scanner(new URL(knownData).openStream());
                in.useDelimiter(",");
                while (in.hasNext())
                    trie.add(in.next().toLowerCase());
                in.close();
                tries.put(knownData, trie);
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

    public void findSuggestions(String inputWord, String knownData, int row, int col){
        trie = tries.get(knownData);
        inputWord = inputWord.toLowerCase();
        Set<String> possibilities = getAllEditDistanceWords(inputWord);
        possibilities.addAll(getAllEditDistanceWords(possibilities));
        Set<String> similar = getAllValidWords(possibilities);
        List<String> ret = new ArrayList<>();
        ret.addAll(similar);
        for(SeeSuggestionsListener listener : suggestionsListeners){
            listener.seeSuggestions(ret, row, col);
        }
    }

    public List<String> findSuggestions(String inputWord, String knownData){
        trie = tries.get(knownData);
        inputWord = inputWord.toLowerCase();
        List<String> ret = new ArrayList<>();
        Set<String> possibilities = getAllEditDistanceWords(inputWord);
        possibilities.addAll(getAllEditDistanceWords(possibilities));
        Set<String> similar = getAllValidWords(possibilities);
        ret.addAll(similar);
        return ret;
    }

    private Set<String> getAllEditDistanceWords(Set<String> words){
        Set<String> possibilities = new HashSet<>();
        for(String inputWord : words){
            possibilities.addAll(getAllEditDistanceWords(inputWord));
        }
        return possibilities;
    }

    private Set<String> getAllEditDistanceWords(String inputWord){
        Set<String> possibilities = new HashSet<>();
        possibilities.addAll(getDeletionWords(inputWord));
        possibilities.addAll(getTranspositionWords(inputWord));
        possibilities.addAll(getAlterationWords(inputWord));
        possibilities.addAll(getInsertionWords(inputWord));
        return possibilities;
    }

    private List<String> getDeletionWords(String word){
        List<String> deletions = new ArrayList<>();
        for(int i = 0; i < word.length(); i++){
            if(word.length() == 1)
                return deletions;
            String search = word.substring(0,i) + word.substring(i+1,word.length());
            deletions.add(search);
        }
        return deletions;
    }

    private List<String> getTranspositionWords(String word){
        List<String> transpositions = new ArrayList<>();
        for(int i = 0; i < word.length()-1; i++){
            String search = word.substring(0,i) + word.charAt(i+1) +
                    word.charAt(i) + word.substring(i+2,word.length());
            transpositions.add(search);
        }
        if(word.length() == 2)
            transpositions.add("" + word.charAt(1) + word.charAt(0));
        return transpositions;
    }

    private List<String> getAlterationWords(String word){
        List<String> alterations = new ArrayList<>();
        for(int i = 0; i < word.length(); i++){
            for(int j = 0; j < 27; j++){
                char c = j == 26 ? ' ' : (char) ('a' + j);
                String search = word.substring(0,i) + c + word.substring(i+1,word.length());
                alterations.add(search);
            }
        }
        return alterations;
    }

    private List<String> getInsertionWords(String word){
        List<String> insertions = new ArrayList<>();
        for(int i = 0; i < word.length() + 1; i++){
            for(int j = 0; j < 27; j++){
                char c = j == 26 ? ' ' : (char) ('a' + j);
                String search = word.substring(0,i) + c + word.substring(i,word.length());
                insertions.add(search);
            }
        }
        return insertions;
    }

    private Set<String> getAllValidWords(Set<String> possibilities){
        Set<String> similar = new TreeSet<>();
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
		Node[] children = new Node[27];

		public void add(String word) {
			char c = word.charAt(0);
            int pos = c == ' ' ? 26 : c - 'a';
			if(pos < 0 || pos > children.length)
				return;
            if(children[pos] == null)
                children[pos] = new Node();
			if(word.length() == 1){
                children[pos].valid = true;
			}else{
				children[pos].add(word.substring(1));
			}
		}

		public boolean contains(String word) {
            if (word.length() == 0)
                return true;
            char c = word.charAt(0);
            int pos = c == ' ' ? 26 : c - 'a';
            if (pos < 0 || pos > children.length)
                return false;
            if (word.length() == 1 && children[pos] != null) {
                return children[pos].valid;
            } else {
                if (children[pos] == null) {
                    return false;
                } else {
                    return children[pos].contains(word.substring(1));
                }
            }
        }

	}

    public interface QualityCheckerListener{
        public void setInvalid(int row, int col, boolean invalid);
    }

    public interface SeeSuggestionsListener{
        public void seeSuggestions(List<String> similar, int row, int col);
    }

}
