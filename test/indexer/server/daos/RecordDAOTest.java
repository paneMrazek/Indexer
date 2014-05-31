package indexer.server.daos;

import main.indexer.server.daos.Database;
import main.indexer.shared.models.Record;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecordDAOTest{
	
	Database database;
	Record record;
	
	@Before
	public void init(){
		database = new Database();
		database.deleteData();
		record = new Record();
		record.setBatchId(5);
		record.setOrderId(1);
	}
	
	@Test
	public void createRecordTest(){
		database.startTransaction();
		record = database.getRecordDAO().createRecord(record);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
	}
	
	@Test
	public void readRecordTest(){
		database.startTransaction();
		record = database.getRecordDAO().createRecord(record);
		int id = record.getId();
		Record record2 = database.getRecordDAO().readRecord(id);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(record.equals(record2));
	}
	
	@Test
	public void updateRecordTest(){
		database.startTransaction();
		record = database.getRecordDAO().createRecord(record);
		record.setBatchId(8);
		Record record2 = database.getRecordDAO().updateRecord(record);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(record.equals(record2));
		Assert.assertSame(8,record2.getBatchId());
	}
	
	@Test
	public void deleteRecordTest(){
		database.startTransaction();
		record = database.getRecordDAO().createRecord(record);
		int id = record.getId();
		database.getRecordDAO().deleteRecord(id);
		Assert.assertNull(database.getRecordDAO().readRecord(id));
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
	}
}
