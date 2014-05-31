package main.indexer.shared.models;

import java.io.Serializable;

public class SearchResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int batchId;
	private String imageURL;
	private int recordNumber;
	private int fieldId;
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
	 * @return the imageURL
	 */
	public String getImageURL(){
		return imageURL;
	}
	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}
	/**
	 * @return the recordNumber
	 */
	public int getRecordNumber(){
		return recordNumber;
	}
	/**
	 * @param recordNumber the recordNumber to set
	 */
	public void setRecordNumber(int recordNumber){
		this.recordNumber = recordNumber;
	}
	/**
	 * @return the fieldId
	 */
	public int getFieldId(){
		return fieldId;
	}
	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(int fieldId){
		this.fieldId = fieldId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return batchId + "\n" + imageURL + "\n" + recordNumber + "\n" + fieldId + "\n";
	}
	
	
	
}
