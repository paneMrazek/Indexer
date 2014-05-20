package main.java.indexer.shared.communication.results;

public class Result{
	
	private boolean error;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return error ? "FAILED\n" : "";
	}

	/**
	 * @return the error
	 */
	public boolean isError(){
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(boolean error){
		this.error = error;
	}
	
	
	
	
}
