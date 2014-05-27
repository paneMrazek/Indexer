package main.java.indexer.shared.communication.results;

import main.java.indexer.shared.models.Batch;

/**
 * A class with the batch that is returned from downloadBatch.
 *
 */
public class DownloadBatch_Result extends Result{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Batch batch;

	/**
	 * @return the batch
	 */
	public Batch getBatch(){
		return batch;
	}

	/**
	 * @param batch the project to set
	 */
	public void setBatch(Batch batch){
		this.batch = batch;
	}
	
	/* 
	 * 
	 */
	@Override
	public String toString(){
		if(isError())
			return super.toString();
		else{
			return batch.toString();
		}
	}
}
