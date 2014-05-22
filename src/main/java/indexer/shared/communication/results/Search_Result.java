package main.java.indexer.shared.communication.results;

import java.util.List;

import main.java.indexer.shared.models.SearchResult;

/**
 * A class with the searchResults that is returned from search.
 *
 */
public class Search_Result{
	
	private List<SearchResult> results;

	/**
	 * @return the results
	 */
	public List<SearchResult> getResults(){
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<SearchResult> results){
		this.results = results;
	}

	@Override
	public String toString(){
		String ret = "";
		for(SearchResult result : results){
			ret += result.toString();
		}
		return ret;
	}
	
	
}
