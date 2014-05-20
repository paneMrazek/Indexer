package main.java.indexer.shared.models;

import java.util.List;

/**
 * <p>A project is a collection of images or <code>batches.</code> 
 * Each batch contains a table of data values that need to be indexed.</p>
 * 
 * <p>The <code>fields</code> of each batch in a project are the same</p>
 */
public class Project{
	
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
}
