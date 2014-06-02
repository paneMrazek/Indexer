package main.indexer.shared.models;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A batch is an image with a collection or batch of <code>Records</code> 
 * that needs to be indexed.</p>
 *
 */
public class Batch implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String imageURL;
	private int projectId;
	private int firstYCoordinate;
	private int recordNum;
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
	public int getRecordNum(){
		return recordNum;
	}
	public void setRecordNum(int recordNum){
		this.recordNum = recordNum;
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
				+ recordNum + "\n" + fields.size() + "\n";
		for(Field field : fields){
			ret += field.toString();
		}
		return ret;
	}
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + (complete ? 1231 : 1237);
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + firstYCoordinate;
		result = prime * result + id;
		result = prime * result
				+ ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + projectId;
		result = prime * result + recordHeight;
		result = prime * result + ((records == null) ? 0 : records.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Batch other = (Batch) obj;
		if(complete != other.complete) return false;
		if(fields == null){
			if(other.fields != null) return false;
		}else if(!fields.equals(other.fields)) return false;
		if(firstYCoordinate != other.firstYCoordinate) return false;
		if(id != other.id) return false;
		if(imageURL == null){
			if(other.imageURL != null) return false;
		}else if(!imageURL.equals(other.imageURL)) return false;
		if(projectId != other.projectId) return false;
		if(recordHeight != other.recordHeight) return false;
		if(records == null){
			if(other.records != null) return false;
		}else if(!records.equals(other.records)) return false;
		return true;
	}
	
	
	
	
	
}
