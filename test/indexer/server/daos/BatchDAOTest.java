package indexer.server.daos;

import main.indexer.server.daos.Database;
import main.indexer.shared.models.Batch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BatchDAOTest{
	
	private Database database;
	private Batch batch;
	
	@Before
	public void init(){
		database = new Database();
		batch = new Batch();
		batch.setComplete(false);
		batch.setImageURL("testurl");
		batch.setProjectId(10);
		database.deleteData();
	}
	
	@Test
	public void createBatchTest(){
		database.startTransaction();
		batch = database.getBatchDAO().createBatch(batch);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
	}
	
	@Test
	public void readBatchTest(){
		database.startTransaction();
		batch = database.getBatchDAO().createBatch(batch);
		int id = batch.getId();
		Batch batch2 = database.getBatchDAO().readBatch(id);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(batch.equals(batch2));
	}
	
	@Test
	public void updateBatchTest(){
		database.startTransaction();
		batch = database.getBatchDAO().createBatch(batch);
		batch.setImageURL("testurl/");
		Batch batch2 = database.getBatchDAO().updateBatch(batch);
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
		Assert.assertTrue(batch.equals(batch2));
		Assert.assertSame("testurl/",batch2.getImageURL());
	}
	
	@Test
	public void deleteBatchTest(){
		database.startTransaction();
		batch = database.getBatchDAO().createBatch(batch);
		int id = batch.getId();
		database.getBatchDAO().deleteBatch(id);
		Assert.assertNull(database.getBatchDAO().readBatch(id));
		database.endTransaction();
		Assert.assertTrue(database.wasSuccesful());
	}
}
