package main.java.indexer.server.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		String sql = "INSERT INTO projects (title,recordsperimage,firstycoordinate,recordheight) "
				+ "values (?,?,?,?)";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setString(1,project.getTitle());
			statement.setInt(2,project.getRecordsPerImage());
			statement.setInt(3,project.getFirstYCoordinate());
			statement.setInt(4,project.getRecordHeight());
			if(statement.executeUpdate() == 1){
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					project.setId(resultSet.getInt(1));
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return project;
	}
	
	//READ
	
	/**
	 * Reads from the database the project with the given id.
	 * @param projectId the id of the project to be read from the database.
	 * @return the project with the given id.
	 */
	public Project readProject(int projectId){
		String sql = "SELECT * FROM projects WHERE id = ?";
		Project project = new Project();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,projectId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				project.setId(projectId);
				project.setTitle(rs.getString("title"));
				project.setRecordsPerImage(rs.getInt("recordsperimage"));
				project.setFirstYCoordinate(rs.getInt("firstycoordinate"));
				project.setRecordHeight(rs.getInt("recordheight"));
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return project;
	}
	
	/**
	 * Returns all projects
	 * @return A list of all projects in the database.
	 */
	public List<Project> readProjects(){
		String sql = "SELECT * FROM projects";
		List<Project> projects = new ArrayList<>();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setTitle(rs.getString("title"));
				project.setRecordsPerImage(rs.getInt("recordsperimage"));
				project.setFirstYCoordinate(rs.getInt("firstycoordinate"));
				project.setRecordHeight(rs.getInt("recordheight"));
				projects.add(project);
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return projects;
	}
	
	//UPDATE
	
	/**
	 * Updates the given project in the database.
	 * @param project the project to be updated.
	 * @return the newly updated project.
	 */
	public Project updateProject(Project project){
		String sql = "UPDATE projects SET title=?,recordsperimage=?,firstycoordinate=?,recordheight=? "
				+ "WHERE id = ?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setString(1,project.getTitle());
			statement.setInt(2,project.getRecordsPerImage());
			statement.setInt(3,project.getFirstYCoordinate());
			statement.setInt(4,project.getRecordHeight());
			statement.setInt(5,project.getId());
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return project;
	}
	
	//DELETE
	
	/**
	 * Deletes the given project from the database.
	 * @param project the project to be deleted.
	 */
	public void deleteProject(Project project){
		String sql = "DELETE FROM projects WHERE id = ?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,project.getId());
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
	}
}
