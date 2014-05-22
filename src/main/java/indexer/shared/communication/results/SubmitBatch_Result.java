package main.java.indexer.shared.communication.results;

/**
 * A class that signifies whether a batch was successfully submitted that is returned
 * from submitBatch.
 *
 */
public class SubmitBatch_Result extends Result{
	
	@Override
	public String toString(){
		if(isError())
			return "FAILED\n";
		else
			return "TRUE\n";
	}
}
