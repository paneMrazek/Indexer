package main.java.indexer.shared.models;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A project is a collection of images or <code>batches.</code> 
 * Each batch contains a table of data values that need to be indexed.</p>
 * 
 * <p>The <code>fields</code> of each batch in a project are the same</p>
 */
public class Project implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private int recordsPerImage;
	private int firstYCoordinate;
	private int recordHeight;
	private List<Batch> batches;
	
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
	 * @return the recordsPerImage
	 */
	public int getRecordsPerImage(){
		return recordsPerImage;
	}
	/**
	 * @param recordsPerImage the recordsPerImage to set
	 */
	public void setRecordsPerImage(int recordsPerImage){
		this.recordsPerImage = recordsPerImage;
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
	 * @return the batches
	 */
	public List<Batch> getBatches(){
		return batches;
	}
	/**
	 * @param batches the batches to set
	 */
	public void setBatches(List<Batch> batches){
		this.batches = batches;
	}
	
	@Override
	public String toString(){
		return getId() + "\n" + getTitle() + "\n";
	}
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + firstYCoordinate;
		result = prime * result + id;
		result = prime * result + recordHeight;
		result = prime * result + recordsPerImage;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Project other = (Project) obj;
		if(firstYCoordinate != other.firstYCoordinate) return false;
		if(id != other.id) return false;
		if(recordHeight != other.recordHeight) return false;
		if(recordsPerImage != other.recordsPerImage) return false;
		if(title == null){
			if(other.title != null) return false;
		}else if(!title.equals(other.title)) return false;
		return true;
	}
	
	
}
