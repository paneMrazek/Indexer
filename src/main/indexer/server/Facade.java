package main.indexer.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import main.indexer.server.daos.Database;
import main.indexer.shared.communication.params.Search_Params;
import main.indexer.shared.communication.params.SubmitBatch_Params;
import main.indexer.shared.communication.results.DownloadBatch_Result;
import main.indexer.shared.communication.results.GetFields_Result;
import main.indexer.shared.communication.results.GetProjects_Result;
import main.indexer.shared.communication.results.GetSampleImage_Result;
import main.indexer.shared.communication.results.Search_Result;
import main.indexer.shared.communication.results.SubmitBatch_Result;
import main.indexer.shared.communication.results.ValidateUser_Result;
import main.indexer.shared.models.Batch;
import main.indexer.shared.models.Field;
import main.indexer.shared.models.Project;
import main.indexer.shared.models.Record;
import main.indexer.shared.models.SearchResult;
import main.indexer.shared.models.User;

public class Facade{
	
	public ValidateUser_Result validateUser(String auth){
		ValidateUser_Result result = new ValidateUser_Result();
		if(!auth.matches(".+:.+")){
			result.setError(true);
			return result;
		}
		String decoded = auth;
		Database database = new Database();
		String[] split = decoded.split(":(?=[^:]+$)");
		database.startTransaction();
		User user = database.getUserDAO().readUser(split[0],split[1]);
		database.endTransaction();
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
		if(projectId == 0){
			result.setError(true);
			return result;
		}
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
		if(projectId == 0){
			result.setError(true);
			return result;
		}
		Database database = new Database();
		List<Batch> batches = new ArrayList<>();
		ValidateUser_Result validationResult = validateUser(auth);
		if(validationResult.isValid() && validationResult.getUser().getCurrentBatch() == 0){
			database.startTransaction();
			batches = database.getBatchDAO().readBatchesForProject(projectId);
			if(batches == null || batches.size() == 0){
				result.setError(true);
				database.endTransaction();
				return result;
			}
			Batch batch = batches.get(new Random().nextInt(batches.size()));
			batch.setFields(database.getFieldDAO().readFieldsForProject(batch.getProjectId()));
			batch.setRecords(database.getRecordDAO().readRecordsForBatch(batch.getId()));
			Project project = database.getProjectDAO().readProject(projectId);
			if(project == null){
				result.setError(true);
				database.endTransaction();
				return result;
			}
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
	
	public DownloadBatch_Result getBatch(String auth, int batchId){
		DownloadBatch_Result result = new DownloadBatch_Result();
		if(batchId == 0){
			result.setError(true);
			return result;
		}
		Database database = new Database();
		Batch batch = new Batch();
		ValidateUser_Result validationResult = validateUser(auth);
		if(validationResult.isValid() && validationResult.getUser().getCurrentBatch() == batchId){
			database.startTransaction();
			batch = database.getBatchDAO().readBatch(batchId);
			if(batch == null){
				result.setError(true);
				database.endTransaction();
				return result;
			}
			batch.setFields(database.getFieldDAO().readFieldsForProject(batch.getProjectId()));
			batch.setRecords(database.getRecordDAO().readRecordsForBatch(batch.getId()));
			Project project = database.getProjectDAO().readProject(batch.getProjectId());
			if(project == null){
				result.setError(true);
				database.endTransaction();
				return result;
			}
			batch.setFirstYCoordinate(project.getFirstYCoordinate());
			batch.setRecordHeight(project.getRecordHeight());
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
		if(!validateSubmitBatchParams(params)){
			result.setError(true);
			return result;
		}
		Database database = new Database();
		ValidateUser_Result validationResult = validateUser(auth);
		database.startTransaction();
		Batch batch = database.getBatchDAO().readBatch(params.getBatchId());
		if(validationResult.isValid() && 
				validationResult.getUser().getCurrentBatch() == params.getBatchId()
				&& !batch.isComplete()){
			User user = validationResult.getUser();
			int projectId = batch.getProjectId();
			List<Field> fields = database.getFieldDAO().readFieldsForProject(projectId);
			Record record = new Record();
			record.setBatchId(params.getBatchId());
			record.setValues(new HashMap<Field,String>());
			int orderId = 0;
			for(String[] values : params.getRecordValues()){
				orderId++;
				int fieldCount = 0;
				record = new Record();
				record.setBatchId(params.getBatchId());
				record.setValues(new HashMap<Field,String>());
				record.setOrderId(orderId);
				fieldCount = 0;
				for(String value : values){
                    if(value != null && !value.equals(""))
					    record.getValues().put(fields.get(fieldCount),value);
					fieldCount++;
				}
				database.getRecordDAO().createRecord(record);
			}
			int recordsPerBatch = database.getProjectDAO().readProject(projectId)
					.getRecordsPerImage();
			user.setCurrentBatch(0);
			database.getUserDAO().updateUserAssignedBatch(user.getCurrentBatch(),
					user.getUserId(),user.getIndexedRecords() + recordsPerBatch);
			database.getBatchDAO().updateBatchToComplete(params.getBatchId());
			database.endTransaction();
			if(database.wasSuccesful()){
				return result;
			}
		}
		result.setError(true);
		return result;
	}
	
	private boolean validateSubmitBatchParams(SubmitBatch_Params params){
		if(params.getBatchId() == 0)
			return false;
		if(params.getRecordValues().size() == 0)
			return false;
		return true;
	}

	/**
	 * Returns information about all of the fields for the specified project 
	 * If no project is specified, returns information about all of the fields
	 * for all projects in the system.
	 * @param auth An object with the username and password of the user
	 * and the optional project id.
	 * @return An object containing the desired fields if succesful, otherwise the word failed.
	 */
	public GetFields_Result getFields(String auth, int projectId){
		GetFields_Result result = new GetFields_Result();
		if(projectId == 0){
			result.setError(true);
			return result;
		}
		Database database = new Database();
		List<Field> fields = new ArrayList<>();
		if(validateUser(auth).isValid()){
			database.startTransaction();
			Project project = database.getProjectDAO().readProject(projectId);
			if(projectId == -1 || project != null){
				fields = database.getFieldDAO().readFieldsForProject(projectId);
				database.endTransaction();
				if(database.wasSuccesful()){
					result.setFields(fields);
					return result;
				}
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
		if(!validateSearchParams(params)){
			result.setError(true);
			return result;
		}
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
	//4,5
	//Mr Incredable Bob Parr
	private boolean validateSearchParams(Search_Params params){
		if(params.getFieldId().length == 0)
			return false;
		if(params.getSearchValues().length == 0)
			return false;
		/*if(!params.getFieldId()[0].matches("[0-9]*"))
			return false;
		if(!params.getSearchValues()[0].matches("[A-Za-z0-9]*"))
			return false;*/
		return true;
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
