package main.java.indexer.shared.communication.params;

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
	private String[] recordValues;
	
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
	public String[] getRecordValues(){
		return recordValues;
	}
	/**
	 * @param recordValues the recordValues to set
	 */
	public void setRecordValues(String[] recordValues){
		this.recordValues = recordValues;
	}
}
