package test.java.indexer.server.daos;

import main.java.indexer.server.daos.Database;
import main.java.indexer.shared.models.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDAOTest{
	
	Database database;
	User user;
	int id;
	
	@Before
	public void init(){
		database = new Database();
		user = new User();
		user.setUserName("createTest");
		user.setPassword("test");
		user.setFirstName("zach");
		user.setLastName("porter");
		user.setEmail("jj@gmail.com");
		user.setIndexedRecords(0);
		user.setUserId(id);
		database.deleteData();
	}
	
	@Test
	public void createUserTest(){
		database.startTransaction();
		user = database.getUserDAO().createUser(user);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertNotSame(user.getUserId(),id);
		id = user.getUserId();
	}
	
	@Test
	public void readUserTest(){
		database.startTransaction();
		user = database.getUserDAO().createUser(user);
		User user2 = database.getUserDAO().readUser(user.getUserName(),user.getPassword());
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(user.equals(user2));
	}
	
	@Test
	public void updateUserTest(){
		database.startTransaction();
		user = database.getUserDAO().createUser(user);
		user.setFirstName("zack");
		User user2 = database.getUserDAO().updateUser(user);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(user.equals(user2));
		Assert.assertSame("zack",user2.getFirstName());
	}
	
	@Test
	public void deleteUserTest(){
		database.startTransaction();
		user = database.getUserDAO().createUser(user);
		database.getUserDAO().updateUser(user);
		database.getUserDAO().deleteUser(user.getUserName(),user.getPassword());
		Assert.assertNull(database.getUserDAO().readUser(user.getUserName(),user.getPassword()));
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
	}
}
