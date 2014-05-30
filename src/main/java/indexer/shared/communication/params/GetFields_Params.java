package main.java.indexer.shared.communication.params;

/**
 * A class with the username, password, and projectId used to call getFields.
 *
 */
public class GetFields_Params extends Params{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int projectId = -1;

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
}
