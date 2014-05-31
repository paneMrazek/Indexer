package main.indexer.shared.communication.results;

import java.io.Serializable;

/**
 * The base Result class that insansiates the shared error value in all result classes.
 */
public abstract class Result implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
