package indexer.server.daos;

import main.indexer.server.daos.Database;
import main.indexer.shared.models.Project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProjectDAOTest{
	
	Database database;
	Project project;
	
	@Before
	public void init(){
		database = new Database();
		database.deleteData();
		project = new Project();
		project.setFirstYCoordinate(10);
		project.setRecordHeight(100);
		project.setRecordsPerImage(10);
		project.setTitle("test");
	}
	
	@Test
	public void createProjectTest(){
		database.startTransaction();
		project = database.getProjectDAO().createProject(project);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
	}
	
	@Test
	public void readProjectTest(){
		database.startTransaction();
		project = database.getProjectDAO().createProject(project);
		int id = project.getId();
		Project project2 = database.getProjectDAO().readProject(id);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(project.equals(project2));
	}
	
	@Test
	public void updateProjectTest(){
		database.startTransaction();
		project = database.getProjectDAO().createProject(project);
		project.setTitle("test2");
		Project project2 = database.getProjectDAO().updateProject(project);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(project.equals(project2));
		Assert.assertSame("test2",project2.getTitle());
	}
	
	@Test
	public void deleteProjectTest(){
		database.startTransaction();
		project = database.getProjectDAO().createProject(project);
		int id = project.getId();
		database.getProjectDAO().deleteProject(id);
		Assert.assertNull(database.getProjectDAO().readProject(id));
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
	}
}
