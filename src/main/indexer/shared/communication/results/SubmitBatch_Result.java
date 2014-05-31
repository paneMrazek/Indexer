package main.indexer.shared.communication.results;

/**
 * A class that signifies whether a batch was successfully submitted that is returned
 * from submitBatch.
 *
 */
public class SubmitBatch_Result extends Result{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString(){
		if(isError())
			return "FAILED\n";
		else
			return "TRUE\n";
	}
}
