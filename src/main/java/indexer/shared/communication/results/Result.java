package main.java.indexer.shared.communication.results;

/**
 * The base Result class that insansiates the shared error value in all result classes.
 */
public abstract class Result{
	
	private boolean error;
	
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
