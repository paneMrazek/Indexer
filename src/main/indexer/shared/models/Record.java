package main.indexer.shared.models;

import java.io.Serializable;
import java.util.HashMap;
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
	private Map<Field, String> values = new HashMap<>();
	
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
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + batchId;
		result = prime * result + id;
		result = prime * result + orderId;
		return result;
	}
	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Record other = (Record) obj;
		if(batchId != other.batchId) return false;
		if(id != other.id) return false;
		if(orderId != other.orderId) return false;
		return true;
	}
	
	
}
