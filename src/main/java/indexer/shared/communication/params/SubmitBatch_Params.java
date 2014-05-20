package main.java.indexer.shared.communication.params;

import java.util.List;

public class SubmitBatch_Params extends Params{
	
	private int batchId;
	private List<String> recordValues;
	
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
	 * @return the recordValues
	 */
	public List<String> getRecordValues(){
		return recordValues;
	}
	/**
	 * @param recordValues the recordValues to set
	 */
	public void setRecordValues(List<String> recordValues){
		this.recordValues = recordValues;
	}
}
