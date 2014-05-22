package main.java.indexer.server.daos;

import java.util.List;

import javax.sql.DataSource;

import main.java.indexer.shared.models.Batch;
import main.java.indexer.shared.models.Project;

/**
 * <p> Used to access the Batch table in the database.  
 * Has all needed CRUD operations.</p>
 *
 */
public class BatchDAO{
	
protected DataSource dataSource;
	
	public BatchDAO(){
		
	}
	
	//CREATE
	
	/**
	 * Adds the given batch to the database.
	 * @param batch the batch to be added to the database.
	 * @return the batch if successful, otherwise null.
	 */
	public Batch createBatch(Batch batch){
		return null;
	}
	
	//READ
	
	/**
	 * Reads and returns all batches for a given project from the database.
	 * @param project the project to be used in the query.
	 * @return a list of all batches in the given project.
	 */
	public List<Batch> readBatchesForProject(Project project){
		return null;
	}
	
	/**
	 * Reads from the database and returns the batch with the given batchId.
	 * @param batchId the batchId given to query the database.
	 * @return the batch with the given batchId if exists, otherwise null.
	 */
	public Batch readBatch(String batchId){
		return null;
	}
	
	//UPDATE
	
	/**
	 * Updates the given batch in the database.
	 * @param batch the batch to be updated.
	 * @return the updated branch.
	 */
	public Batch updateBatch(Batch batch){
		return null;
	}
	
	//DELETE
	
	/**
	 * Delete the given batch from the database.
	 * @param batch the batch to be deleted.
	 */
	public void deleteBatch(Batch batch){
		
	}
	
	/**
	 * Deletes all batches for the given project.
	 * @param project The project which defines which batches to be deleted.
	 */
	public void deleteBatchesForProject(Project project){
		
	}
}
