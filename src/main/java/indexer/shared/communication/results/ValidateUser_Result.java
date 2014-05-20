package main.java.indexer.shared.communication.results;

import main.java.indexer.shared.models.User;

public class ValidateUser_Result extends Result{
	User user;
	boolean valid;
	
	/* 
	 * 
	 */
	@Override
	public String toString(){
		if(!valid)
			return "FALSE\n";
		if(isError())
			return super.toString();
		else
			return "TRUE\n" + user.getFirstName() + "\n" + 
				user.getLastName() + "\n" + user.getIndexedRecords() + "\n";
	}
	
	
	
}
