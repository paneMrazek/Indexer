package main.indexer.server.importer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import main.indexer.server.daos.Database;
import main.indexer.shared.models.Batch;
import main.indexer.shared.models.Field;
import main.indexer.shared.models.Project;
import main.indexer.shared.models.Record;
import main.indexer.shared.models.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DataImporter{
	
	Database database = new Database();
	
	public static void main(String[] args){
		new DataImporter().importData(args[0]);
	}

	public void importData(String filename){
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			database.deleteData();
			database.startTransaction();
			File file = new File(filename);
			Document doc = builder.parse(file);
			parseUsers(doc.getElementsByTagName("user"));
			parseProjects(doc.getElementsByTagName("project"));
			database.endTransaction();
			
			copyFiles(file.getParentFile(),file.getParent());
		}catch(SAXException | IOException | ParserConfigurationException e){
			//e.printStackTrace();
		}
	}

	private void parseUsers(NodeList userList){
		for(int i = 0; i < userList.getLength(); i++){
			Element userElem = (Element) userList.item(i);
			String username = ((Element)userElem.getElementsByTagName("username")
					.item(0)).getTextContent();
			String password = ((Element)userElem.getElementsByTagName("password")
					.item(0)).getTextContent();
			String firstName = ((Element)userElem.getElementsByTagName("firstname")
					.item(0)).getTextContent();
			String lastName = ((Element)userElem.getElementsByTagName("lastname")
					.item(0)).getTextContent();
			String email = ((Element)userElem.getElementsByTagName("email")
					.item(0)).getTextContent();
			String indexedRecords = ((Element)userElem.getElementsByTagName("indexedrecords")
					.item(0)).getTextContent();
			
			User user = new User();
			user.setUserName(username);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setIndexedRecords(Integer.parseInt(indexedRecords));
			database.getUserDAO().createUser(user);
		}
	}
	
	private void parseProjects(NodeList projectList){
		for(int i = 0; i < projectList.getLength(); i++){
			Element projectElem = (Element) projectList.item(i);
			String title = ((Element)projectElem.getElementsByTagName("title")
					.item(0)).getTextContent();
			String recordsPerImage = ((Element)projectElem.getElementsByTagName("recordsperimage")
					.item(0)).getTextContent();
			String firstYCoord = ((Element)projectElem.getElementsByTagName("firstycoord")
					.item(0)).getTextContent();
			String recordHeight = ((Element)projectElem.getElementsByTagName("recordheight")
					.item(0)).getTextContent();
			
			Project project = new Project();
			project.setTitle(title);
			project.setRecordsPerImage(Integer.parseInt(recordsPerImage));
			project.setFirstYCoordinate(Integer.parseInt(firstYCoord));
			project.setRecordHeight(Integer.parseInt(recordHeight));
			project = database.getProjectDAO().createProject(project);
			
			List<Field> fields = parseFields(projectElem.getElementsByTagName("field"),project.getId());
			parseBatches(projectElem.getElementsByTagName("image"),project.getId(),fields);
			
		}
	}

	private List<Field> parseFields(NodeList fieldList, int projectId){
		List<Field> fields = new ArrayList<>();
		for(int i = 0; i < fieldList.getLength(); i++){
			Element fieldElem = (Element) fieldList.item(i);
			String title = ((Element)fieldElem.getElementsByTagName("title")
					.item(0)).getTextContent();
			String xCoord = ((Element)fieldElem.getElementsByTagName("xcoord")
					.item(0)).getTextContent();
			String width = ((Element)fieldElem.getElementsByTagName("width")
					.item(0)).getTextContent();
			
			Element helpHtmlElem = (Element)fieldElem.getElementsByTagName("helphtml")
					.item(0);
			String helpHtml = "";
			if(helpHtmlElem != null)
				helpHtml = helpHtmlElem.getTextContent();
			
			Element knownDataElem = (Element)fieldElem.getElementsByTagName("knowndata")
					.item(0);
			String knownData = "";
			if(knownDataElem != null)
				knownData = knownDataElem.getTextContent();
			
			Field field = new Field();
			field.setProjectId(projectId);
			field.setOrderId(i+1);
			field.setTitle(title);
			field.setXCoordinate(Integer.parseInt(xCoord));
			field.setWidth(Integer.parseInt(width));
			field.setHelpFile(helpHtml);
			field.setKnownData(knownData);
			fields.add(database.getFieldDAO().createField(field));
		}
		return fields;
	}
	
	private void parseBatches(NodeList batchList, int projectId, List<Field> fields){
		for(int i = 0; i < batchList.getLength(); i++){
			Element batchElem = (Element) batchList.item(i);
			String imageURL = ((Element)batchElem.getElementsByTagName("file")
					.item(0)).getTextContent();
			
			
			Batch batch = new Batch();
			batch.setProjectId(projectId);
			batch.setImageURL(imageURL);
			batch.setRecordNum(batchElem.getElementsByTagName("record").getLength());
			int batchId = database.getBatchDAO().createBatch(batch).getId();
			parseRecords(batchElem.getElementsByTagName("record"),projectId,batchId,fields);
		}
	}
	
	private void parseRecords(NodeList recordList, int projectId, int batchId, List<Field> fields){
		for(int i = 0; i < recordList.getLength(); i++){
			Element recordElem = (Element) recordList.item(i);
			Record record = new Record();
			Map<Field,String> recordValues = new HashMap<Field,String>();
			NodeList values = recordElem.getElementsByTagName("value");
			for(int j = 0; j < values.getLength(); j++){
				Element value = (Element) (recordElem.getElementsByTagName("value").item(j));
				recordValues.put(fields.get(j),value.getTextContent());
			}
			record.setBatchId(batchId);
			record.setOrderId(i+1);
			record.setValues(recordValues);
			database.getRecordDAO().createRecord(record);
		}
	}
	
	private void copyFiles(File file,String parentFile){
		if(file.isDirectory()){
			for(File child : file.listFiles()){
				if(!file.getPath().equals(parentFile))
					new File("Server/data/" + file.getPath().substring(parentFile.length())).mkdir();
				copyFiles(child,parentFile);
			}
		}else{
			File to = new File("Server/data/" + file.getPath().substring(parentFile.length()));
			copyFile(to,file);
		}
	}
	
	private void copyFile(File to, File from){
		try{
			if(to.exists()) 
				to.delete();
			Files.copy(from.toPath(), to.toPath());
		}catch(IOException e){
			//e.printStackTrace();
		}
	}
	
}
