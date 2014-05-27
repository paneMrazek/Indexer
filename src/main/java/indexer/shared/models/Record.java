package main.java.indexer.shared.models;

import java.io.Serializable;
import java.util.Map;

/**
 * Each row in a table represents a record. 
 * A record is a set of related <code>fields</code>,
 * usually about a specific person.</p>
 */
public class Record implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int batchId;
	private int orderId;
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
	 * @return the batchId
	 */
	public int getBatchId(){
		return batchId;
	}
	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(int batchId){
		this.batchId = batchId;
	}
	/**
	 * @return the orderId
	 */
	public int getOrderId(){
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId){
		this.orderId = orderId;
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
