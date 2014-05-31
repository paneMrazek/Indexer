package main.java.indexer.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import main.java.indexer.server.daos.Database;
import main.java.indexer.shared.communication.params.Search_Params;
import main.java.indexer.shared.communication.params.SubmitBatch_Params;
import main.java.indexer.shared.communication.results.DownloadBatch_Result;
import main.java.indexer.shared.communication.results.GetFields_Result;
import main.java.indexer.shared.communication.results.GetProjects_Result;
import main.java.indexer.shared.communication.results.GetSampleImage_Result;
import main.java.indexer.shared.communication.results.Search_Result;
import main.java.indexer.shared.communication.results.SubmitBatch_Result;
import main.java.indexer.shared.communication.results.ValidateUser_Result;
import main.java.indexer.shared.models.Batch;
import main.java.indexer.shared.models.Field;
import main.java.indexer.shared.models.Project;
import main.java.indexer.shared.models.Record;
import main.java.indexer.shared.models.SearchResult;
import main.java.indexer.shared.models.User;

public class Facade{
	
	public ValidateUser_Result validateUser(String auth){
		String decoded = auth;
		Database database = new Database();
		String[] split = decoded.split(":(?=[^:]+$)");
		database.startTransaction();
		User user = database.getUserDAO().readUser(split[0],split[1]);
		database.endTransaction();
		ValidateUser_Result result = new ValidateUser_Result();
		result.setError(!database.wasSuccesful());
		result.setValid(user != null);
		result.setUser(user);
		
		return result;
	}
	
	/**
	 * Returns information about all of the available projects
	 * @param auth An object with the username and password
	 * @return An object with all available projects if successful,
	 * otherwise the word failed.
	 */
	public GetProjects_Result getProjects(String auth){
		GetProjects_Result result = new GetProjects_Result();
		Database database = new Database();
		if(validateUser(auth).isValid()){
			database.startTransaction();
			List<Project> projects = database.getProjectDAO().readProjects();
			database.endTransaction();
			if(database.wasSuccesful()){
				result.setProjects(projects);
				return result;
			}
		}
		result.setError(true);
		return result;
	}
	
	/**
	 * Returns a sample image for the specified project.
	 * @param params A object with the username and password 
	 * and project id of the desired sample image.
	 * @return An object with the file url if successful, otherwise the word failed.
	 */
	public GetSampleImage_Result getSampleImage(String auth, int projectId){
		GetSampleImage_Result result = new GetSampleImage_Result();
		Database database = new Database();
		List<Batch> batches = new ArrayList<>();
		if(validateUser(auth).isValid()){
			database.startTransaction();
			batches = database.getBatchDAO().readBatchesForProject(projectId);
			database.endTransaction();
			if(database.wasSuccesful()){
				result.setUrl(batches.get(new Random().nextInt(batches.size())).getImageURL());
				return result;
			}
		}
		result.setError(true);
		return result;
	}
	
	/**
	 * Assigns and downloads a batch for the user to index if the user doesn't already
	 * have a batch assigned to them. 
	 * 
	 * @param params An object with the username and 
	 * password of the user and the projectId of the desired batch
	 * @return An object with the new batch if succesful.  Otherwise the word failed.
	 */
	public DownloadBatch_Result downloadBatch(String auth, int projectId){
		DownloadBatch_Result result = new DownloadBatch_Result();
		Database database = new Database();
		List<Batch> batches = new ArrayList<>();
		ValidateUser_Result validationResult = validateUser(auth);
		if(validationResult.isValid() && validationResult.getUser().getCurrentBatch() == 0){
			database.startTransaction();
			batches = database.getBatchDAO().readBatchesForProject(projectId);
			Batch batch = batches.get(new Random().nextInt(batches.size()));
			batch.setFields(database.getFieldDAO().readFieldsForProject(batch.getProjectId()));
			batch.setRecords(database.getRecordDAO().readRecordsForBatch(batch.getId()));
			Project project = database.getProjectDAO().readProject(projectId);
			batch.setFirstYCoordinate(project.getFirstYCoordinate());
			batch.setRecordHeight(project.getRecordHeight());
			validationResult.getUser().setCurrentBatch(batch.getId());
			database.getUserDAO().updateUser(validationResult.getUser());
			database.endTransaction();
			if(database.wasSuccesful()){
				result.setBatch(batch);
				return result;
			}
		}
		result.setError(true);
		return result;
	}
	
	/**
	 * Submits the indexed record field values for a batch to the Server. Unassigns the
	 * User from the given batch and increments the Users indexed record count.
	 * @param params An object with the username and password of the user and the record 
	 * value to be submited.
	 * @return An object with the word true if succesful, otherwise the word failed.
	 */
	public SubmitBatch_Result submitBatch(String auth, SubmitBatch_Params params){
		SubmitBatch_Result result = new SubmitBatch_Result();
		Database database = new Database();
		ValidateUser_Result validationResult = validateUser(auth);
		database.startTransaction();
		Batch batch = database.getBatchDAO().readBatch(params.getBatchId());
		if(validationResult.isValid() && 
				validationResult.getUser().getCurrentBatch() == params.getBatchId()
				&& !batch.isComplete()){
			User user = validationResult.getUser();
			database.startTransaction();
			
			int projectId = batch.getProjectId();
			List<Field> fields = database.getFieldDAO().readFieldsForProject(projectId);
			int fieldCount = 0;
			Record record = new Record();
			record.setBatchId(params.getBatchId());
			record.setValues(new HashMap<Field,String>());
			for(String value : params.getRecordValues()){
				record.getValues().put(fields.get(fieldCount),value);
				fieldCount++;
				if(fieldCount >= fields.size()){
					database.getRecordDAO().createRecord(record);
					record = new Record();
					record.setBatchId(params.getBatchId());
					record.setValues(new HashMap<Field,String>());
					fieldCount = 0;
				}
			}
			int recordsPerBatch = database.getProjectDAO().readProject(projectId).getRecordsPerImage();
			user.setIndexedRecords(user.getIndexedRecords() + recordsPerBatch);
			user.setCurrentBatch(0);
			database.getUserDAO().updateUser(user);
			database.getBatchDAO().updateBatchToComplete(params.getBatchId());
			database.endTransaction();
			if(database.wasSuccesful()){
				return result;
			}
		}
		result.setError(true);
		return result;
	}
	
	/**
	 * Returns information about all of the fields for the specified project 
	 * If no project is specified, returns information about all of the fields
	 * for all projects in the system.
	 * @param params An object with the username and password of the user
	 * and the optional project id.
	 * @return An object containing the desired fields if succesful, otherwise the word failed.
	 */
	public GetFields_Result getFields(String auth, int projectId){
		GetFields_Result result = new GetFields_Result();
		Database database = new Database();
		List<Field> fields = new ArrayList<>();
		if(validateUser(auth).isValid()){
			database.startTransaction();
			fields = database.getFieldDAO().readFieldsForProject(projectId);
			database.endTransaction();
			if(database.wasSuccesful()){
				result.setFields(fields);
				return result;
			}
		}
		result.setError(true);
		return result;
	}
	
	/**
	 * Searches the indexed records for the specified strings .
	 * @param params An object with the username and password of the user,
	 * a list of strings to search for and fields to search.
	 * @return An object with a list of search result objects 
	 * which contains the batchId, Image URL, Record Number, 
	 * and Field ID that match the search criteria.
	 */
	public Search_Result search(String auth,Search_Params params){
		Search_Result result = new Search_Result();
		Database database = new Database();
		List<SearchResult> searchResults = new ArrayList<>();
		if(validateUser(auth).isValid()){
			database.startTransaction();
			for(String fieldId : params.getFieldId()){
				searchResults.addAll(database.getRecordDAO()
						.searchRecords(Integer.parseInt(fieldId),params.getSearchValues()));
			}
			for(SearchResult searchResult : searchResults){
				searchResult.setImageURL(database.getBatchDAO()
						.readBatch(searchResult.getBatchId()).getImageURL());
			}
			database.endTransaction();
			if(database.wasSuccesful()){
				result.setResults(searchResults);
				return result;
			}
		}
		result.setError(true);
		return result;
	}
	
	/**
	 * Downloads and returns the bytes for the requested file.
	 * @param url The url of the file to download.
	 * @return The bytes of the requested file.
	 */
	public byte[] downloadFile(String url){
		File file = new File("Server/data/" + url);
		byte[] result = new byte[(int) file.length()];
		FileInputStream fileInputStream = null;
		try{
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(result);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				fileInputStream.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
