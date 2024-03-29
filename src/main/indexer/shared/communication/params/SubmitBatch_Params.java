package main.indexer.shared.communication.params;

import java.util.List;

/**
 * A class with the username, password, batchId, and recordValues used to call submitBatch.
 *
 */
public class SubmitBatch_Params extends Params{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int batchId;
	private List<String[]> recordValues;
	
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
	public List<String[]> getRecordValues(){
		return recordValues;
	}
	/**
	 * @param recordValues2 the recordValues to set
	 */
	public void setRecordValues(List<String[]> recordValues2){
		this.recordValues = recordValues2;
	}
}
