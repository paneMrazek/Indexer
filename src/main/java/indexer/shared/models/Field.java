package main.java.indexer.shared.models;

/**
 * <p>A field represents each column in a batch file. 
 * The first row of the batch contains the name of the field.</p>
 *
 */
public class Field{
	
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
}
