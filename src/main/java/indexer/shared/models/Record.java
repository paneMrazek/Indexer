package main.java.indexer.shared.models;

import java.util.Map;

/**
 * Each row in a table represents a record. 
 * A record is a set of related <code>fields</code>,
 * usually about a specific person.</p>
 */
public class Record{
	
	private int id;
	private Map<Field, String> values;
	
	/**
	 * @return the id
	 */
	public int getId(){
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id){
		this.id = id;
	}
	/**
	 * @return the values
	 */
	public Map<Field, String> getValues(){
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(Map<Field, String> values){
		this.values = values;
	}
}
