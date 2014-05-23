package main.java.indexer.shared.models;

import java.util.List;

/**
 * <p>A batch is an image with a collection or batch of <code>Records</code> 
 * that needs to be indexed.</p>
 *
 */
public class Batch{
	
	private int id;
	private String imageURL;
	private int projectId;
	private int firstYCoordinate;
	private int recordHeight;
	private boolean complete;
	private List<Field> fields;
	private List<Record> records;
	
	/**
	 * @return the id
	 */
	public int getId(){
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id){
		this.id = id;
	}
	/**
	 * @return the imageURL
	 */
	public String getImageURL(){
		return imageURL;
	}
	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}
	/**
	 * @return the firstYCoordinate
	 */
	public int getFirstYCoordinate(){
		return firstYCoordinate;
	}
	/**
	 * @param firstYCoordinate the firstYCoordinate to set
	 */
	public void setFirstYCoordinate(int firstYCoordinate){
		this.firstYCoordinate = firstYCoordinate;
	}
	/**
	 * @return the recordHeight
	 */
	public int getRecordHeight(){
		return recordHeight;
	}
	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(int recordHeight){
		this.recordHeight = recordHeight;
	}
	/**
	 * @return the complete
	 */
	public boolean isComplete(){
		return complete;
	}
	/**
	 * @param complete the complete to set
	 */
	public void setComplete(boolean complete){
		this.complete = complete;
	}
	/**
	 * @return the projectId
	 */
	public int getProjectId(){
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(int projectId){
		this.projectId = projectId;
	}
	/**
	 * @return the records
	 */
	public List<Record> getRecords(){
		return records;
	}
	/**
	 * @param records the records to set
	 */
	public void setRecords(List<Record> records){
		this.records = records;
	}
	/**
	 * @return the fields
	 */
	public List<Field> getFields(){
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields){
		this.fields = fields;
	}
	
	
	@Override
	public String toString(){
		String ret = id + "\n" + projectId + "\n" + imageURL + "\n"
				+ firstYCoordinate + "\n" + recordHeight + "\n"
				+ records.size() + "\n" + fields.size() + "\n";
		for(Field field : fields){
			ret += field.toString();
		}
		return ret;
	}
	
	
	
}
