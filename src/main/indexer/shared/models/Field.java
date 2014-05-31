package main.indexer.shared.models;

import java.io.Serializable;

/**
 * <p>A field represents each column in a batch file. 
 * The first row of the batch contains the name of the field.</p>
 *
 */
public class Field implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int projectId;
	private int orderId;
	private String title;
	private int xCoordinate;
	private int width;
	private String helpFile;
	private String knownData;
	
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
	 * @return the orderId
	 */
	public int getOrderId(){
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId){
		this.orderId = orderId;
	}
	/**
	 * @return the title
	 */
	public String getTitle(){
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * @return the x
	 */
	public int getXCoordinate(){
		return xCoordinate;
	}
	/**
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setXCoordinate(int xCoordinate){
		this.xCoordinate = xCoordinate;
	}
	/**
	 * @return the width
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width){
		this.width = width;
	}
	/**
	 * @return the helpFile
	 */
	public String getHelpFile(){
		return helpFile;
	}
	/**
	 * @param helpFile the helpFile to set
	 */
	public void setHelpFile(String helpFile){
		this.helpFile = helpFile;
	}
	/**
	 * @return the knownData
	 */
	public String getKnownData(){
		return knownData;
	}
	/**
	 * @param knownData the knownData to set
	 */
	public void setKnownData(String knownData){
		this.knownData = knownData;
	}
	
	@Override
	public String toString(){
		String ret = id + "\n" + orderId + "\n" + title + "\n"
				+ helpFile + "\n" + xCoordinate + "\n"
				+ width + "\n" + knownData + "\n";
		return ret;
	}
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((helpFile == null) ? 0 : helpFile.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((knownData == null) ? 0 : knownData.hashCode());
		result = prime * result + orderId;
		result = prime * result + projectId;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + width;
		result = prime * result + xCoordinate;
		return result;
	}
	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Field other = (Field) obj;
		if(helpFile == null){
			if(other.helpFile != null) return false;
		}else if(!helpFile.equals(other.helpFile)) return false;
		if(id != other.id) return false;
		if(knownData == null){
			if(other.knownData != null) return false;
		}else if(!knownData.equals(other.knownData)) return false;
		if(orderId != other.orderId) return false;
		if(projectId != other.projectId) return false;
		if(title == null){
			if(other.title != null) return false;
		}else if(!title.equals(other.title)) return false;
		if(width != other.width) return false;
		if(xCoordinate != other.xCoordinate) return false;
		return true;
	}
	
	
}
