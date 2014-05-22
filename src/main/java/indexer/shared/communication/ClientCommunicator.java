package main.java.indexer.shared.communication;

import main.java.indexer.shared.communication.params.*;
import main.java.indexer.shared.communication.results.*;

/**
 * The ClientCommunicator class is the class shared in order to communicate between the
 * server and the client.
 *
 */
public class ClientCommunicator{
	
	/**
	 * Validates user credentials.
	 * @param params An object with the username and password of the user to validate.
	 * @return The user object if succesful, false if the username or password is invalid,
	 * and failed if any error occurs.
	 */
	public ValidateUser_Result ValidateUser(ValidateUser_Params params){
		return null;
	}
	
	/**
	 * Returns information about all of the available projects
	 * @param params An object with the username and password
	 * @return An object with all available projects if successful,
	 * otherwise the word failed.
	 */
	public GetProjects_Result getProjects(GetProjects_Params params){
		return null;
	}
	
	/**
	 * Returns a sample image for the specified project.
	 * @param params A object with the username and password 
	 * and project id of the desired sample image.
	 * @return An object with the file url if successful, otherwise the word failed.
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params){
		return null;
	}
	
	/**
	 * Assigns and downloads a batch for the user to index if the user doesn't already
	 * have a batch assigned to them. 
	 * 
	 * @param params An object with the username and 
	 * password of the user and the projectId of the desired batch
	 * @return An object with the new batch if succesful.  Otherwise the word failed.
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params){
		return null;
	}
	
	/**
	 * Submits the indexed record field values for a batch to the Server. Unassigns the
	 * User from the given batch and increments the Users indexed record count.
	 * @param params An object with the username and password of the user and the record 
	 * value to be submited.
	 * @return An object with the word true if succesful, otherwise the word failed.
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params){
		return null;
	}
	
	/**
	 * Returns information about all of the fields for the specified project 
	 * If no project is specified, returns information about all of the fields
	 * for all projects in the system.
	 * @param params An object with the username and password of the user
	 * and the optional project id.
	 * @return An object containing the desired fields if succesful, otherwise the word failed.
	 */
	public GetFields_Result getFields(GetFields_Params params){
		return null;
	}
	
	/**
	 * Searches the indexed records for the specified strings .
	 * @param params An object with the username and password of the user,
	 * a list of strings to search for and fields to search.
	 * @return An object with a list of search result objects 
	 * which contains the batchId, Image URL, Record Number, 
	 * and Field ID that match the search criteria.
	 */
	public Search_Result search(Search_Params params){
		return null;
	}
	
	/**
	 * Downloads and returns the bytes for the requested file.
	 * @param url The url of the file to download.
	 * @return The bytes of the requested file.
	 */
	public Byte[] downloadFile(String url){
		return null;
	}
}
