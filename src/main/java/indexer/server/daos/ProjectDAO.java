package main.java.indexer.server.daos;

import java.util.List;

import main.java.indexer.shared.models.Project;

/**
 * <p> Used to access the Projects table in the database.  
 * Has all needed CRUD operations.</p>
 *
 */
public class ProjectDAO{

	private Database database;
	
	public ProjectDAO(Database database){
		this.database = database;
	}
	
	//CREATE
	
	/**
	 * Adds the given project to the database.
	 * @param project the project to be added to the database.
	 * @return the newly added project if successful, otherwise null.
	 */
	public Project createProject(Project project){
		return null;
	}
	
	//READ
	
	/**
	 * Reads from the database the project with the given name.
	 * @param title the title of the project to be read from the database.
	 * @return the project with the given title
	 */
	public Project readProject(String title){
		return null;
	}
	
	/**
	 * Returns all projects
	 * @return A list of all projects in the database.
	 */
	public List<Project> readProjects(){
		return null;
	}
	
	//UPDATE
	
	/**
	 * Updates the given project in the database.
	 * @param project the project to be updated.
	 * @return the newly updated project.
	 */
	public Project updateProject(Project project){
		return null;
	}
	
	//DELETE
	
	/**
	 * Deletes the given project from the database.
	 * @param project the project to be deleted.
	 */
	public void deleteProject(Project project){
		
	}
	/**
	 * Deletes the project from the database with the given title.
	 * @param title the title of the project to be deleted.
	 */
	public void deleteProject(String title){
		
	}
}
