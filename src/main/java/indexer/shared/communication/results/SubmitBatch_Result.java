package main.java.indexer.shared.communication.results;

public class SubmitBatch_Result extends Result{
	
	@Override
	public String toString(){
		if(isError())
			return "FAILED\n";
		else
			return "TRUE\n";
	}
}
