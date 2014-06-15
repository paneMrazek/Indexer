package indexer.server.daos;

import main.indexer.server.daos.Database;
import main.indexer.shared.models.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldDAOTest{
	
	private Database database;
	private Field field;
	
	@Before
	public void init(){
		database = new Database();
		database.deleteData();
		field = new Field();
		field.setTitle("test");
		field.setProjectId(7);
		field.setWidth(100);
		field.setOrderId(2);
		field.setXCoordinate(15);
		field.setKnownData("knownData");
		field.setHelpFile("helpFile");
	}
	
	@Test
	public void createFieldTest(){
		database.startTransaction();
		field = database.getFieldDAO().createField(field);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
	}
	
	@Test
	public void readFieldTest(){
		database.startTransaction();
		field = database.getFieldDAO().createField(field);
		int id = field.getId();
		Field field2 = database.getFieldDAO().readField(id);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(field.equals(field2));
	}
	
	@Test
	public void updateFieldTest(){
		database.startTransaction();
		field = database.getFieldDAO().createField(field);
		field.setHelpFile("help file");
		Field field2 = database.getFieldDAO().updateField(field);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(field.equals(field2));
		Assert.assertSame("help file",field2.getHelpFile());
	}
	
	@Test
	public void deleteFieldTest(){
		database.startTransaction();
		field = database.getFieldDAO().createField(field);
		int id = field.getId();
		database.getFieldDAO().deleteField(id);
		Assert.assertNull(database.getFieldDAO().readField(id));
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
	}
}
