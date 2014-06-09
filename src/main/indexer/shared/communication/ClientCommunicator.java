package main.indexer.shared.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import main.indexer.shared.communication.params.*;
import main.indexer.shared.communication.results.*;
import main.indexer.shared.models.Field;
import main.indexer.shared.models.SearchResult;

/**
 * The ClientCommunicator class is the class shared in order to communicate between the
 * server and the client.
 *
 */
public class ClientCommunicator{
	
	private static ClientCommunicator instance;
	
	private XStream xmlStream;
	
	private String host = "localhost";
	private int port = 8080;
	
	public static ClientCommunicator getInstance(){
		if(instance == null) {
			instance = new ClientCommunicator();
		}
		return instance;
	}
	
	private ClientCommunicator() {
		xmlStream = new XStream(new DomDriver());
	}
	
	public void initilize(String host, int port){
		this.host = host;
		this.port = port;
				
	}
	
	public void setHost(String host){
		this.host = host;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	/**
	 * Validates user credentials.
	 * @param params An object with the username and password of the user to validate.
	 * @return The user object if succesful, false if the username or password is invalid,
	 * and failed if any error occurs.
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params){
		ValidateUser_Result result = (ValidateUser_Result) doGet("/ValidateUser",params);
		if(result == null){
			result = new ValidateUser_Result();
			result.setError(true);
		}
		return result;
	}
	
	/**
	 * Returns information about all of the available projects
	 * @param params An object with the username and password
	 * @return An object with all available projects if successful,
	 * otherwise the word failed.
	 */
	public GetProjects_Result getProjects(GetProjects_Params params){
		GetProjects_Result result = (GetProjects_Result) doGet("/GetProjects",params);
		if(result == null){
			result = new GetProjects_Result();
			result.setError(true);
		}
		return result;
	}
	
	/**
	 * Returns a sample image for the specified project.
	 * @param params A object with the username and password 
	 * and project id of the desired sample image.
	 * @return An object with the file url if successful, otherwise the word failed.
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params){
		GetSampleImage_Result result = (GetSampleImage_Result) doGet("/GetSampleImage/" + params.getProjectId(),params);
		if(result == null){
			result = new GetSampleImage_Result();
			result.setError(true);
			return result;
		}
		result.setUrl("http://" + host + ":" + port + "/" + result.getUrl());
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
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params){
		DownloadBatch_Result result = (DownloadBatch_Result) doGet("/DownloadBatch/" + params.getProjectId(),params);
		if(result == null){
			result = new DownloadBatch_Result();
			result.setError(true);
		}
		if(result.getBatch() == null)
			return result;
		for(Field field : result.getBatch().getFields()){
			if(field.getHelpFile() != null && !field.getHelpFile().equals(""))
				field.setHelpFile("http://" + host + ":" + port + "/" + field.getHelpFile());
			if(field.getKnownData() != null && !field.getKnownData().equals(""))
				field.setKnownData("http://" + host + ":" + port + "/" + field.getKnownData());
		}
		result.getBatch().setImageURL("http://" + host + ":" + port + "/" + result.getBatch().getImageURL());
		return result;
	}
	
	public DownloadBatch_Result getBatch(GetBatch_Params params){
		DownloadBatch_Result result = (DownloadBatch_Result) doGet("/GetBatch/" + params.getBatchId(),params);
		if(result == null){
			result = new DownloadBatch_Result();
			result.setError(true);
		}
		if(result.getBatch() == null)
			return result;
		for(Field field : result.getBatch().getFields()){
			if(field.getHelpFile() != null && !field.getHelpFile().equals(""))
				field.setHelpFile("http://" + host + ":" + port + "/" + field.getHelpFile());
			if(field.getKnownData() != null && !field.getKnownData().equals(""))
				field.setKnownData("http://" + host + ":" + port + "/" + field.getKnownData());
		}
		result.getBatch().setImageURL("http://" + host + ":" + port + "/" + result.getBatch().getImageURL());
		return result;
	}
	
	/**
	 * Submits the indexed record field values for a batch to the Server. Unassigns the
	 * User from the given batch and increments the Users indexed record count.
	 * @param params An object with the username and password of the user and the record 
	 * value to be submited.
	 * @return An object with the word true if succesful, otherwise the word failed.
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params){
		SubmitBatch_Result result = (SubmitBatch_Result) doPost("/SubmitBatch",params);
		if(result == null){
			result = new SubmitBatch_Result();
			result.setError(true);
		}
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
	public GetFields_Result getFields(GetFields_Params params){
		GetFields_Result result = (GetFields_Result) doGet("/GetFields/" + params.getProjectId(),params);
		if(result == null){
			result = new GetFields_Result();
			result.setError(true);
		}
		if(result.getFields() == null)
			return result;
		for(Field field : result.getFields()){
			field.setHelpFile("http://" + host + ":" + port + "/" + field.getHelpFile());
			field.setKnownData("http://" + host + ":" + port + "/" + field.getKnownData());
		}
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
	public Search_Result search(Search_Params params){
		Search_Result result = (Search_Result) doPost("/Search",params);
		if(result == null){
			result = new Search_Result();
			result.setError(true);
		}
		if(result.getResults() == null)
			return result;
		for(SearchResult searchResult : result.getResults()){
			searchResult.setImageURL("http://" + host + ":" + port + "/" + searchResult.getImageURL());
		}
		return result;
	}
	
	/**
	 * Downloads and returns the bytes for the requested file.
	 * @param url The url of the file to download.
	 * @return The bytes of the requested file.
	 */
	public byte[] downloadFile(String urlPath){
		try{
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			return extract(connection.getInputStream());
		}catch(IOException | NumberFormatException e){
			//e.printStackTrace();
		}
		return null;
	}	
	
	private byte[] extract(InputStream inputStream) throws IOException {	
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();				
		byte[] result = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(result, 0, result.length)) != -1) {
			byteStream.write(result, 0, read);
		}		
		byteStream.flush();		
		return  byteStream.toByteArray();
	}
	
	private Object doGet(String urlPath, Params params){
		Object result = null;
		try{
			URL url = new URL("http://" + host + ":" + port + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.addRequestProperty("authorization",params.getUserName() + ":" + params.getPassword());
			connection.connect();
			result = xmlStream.fromXML(connection.getInputStream());
		}catch(IOException | NumberFormatException e){
			e.printStackTrace();
		}
		return result;
	}
	
	private Object doPost(String urlPath, Params params){
		Object result = null;
		try{
			URL url = new URL("http://" + host + ":" + port + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.addRequestProperty("authorization",params.getUserName() + ":" + params.getPassword());
			connection.connect();
			xmlStream.toXML(params,connection.getOutputStream());
			
			result = xmlStream.fromXML(connection.getInputStream());
		}catch(IOException | NumberFormatException e){
			//e.printStackTrace();
		}
		return result;
	}

}
