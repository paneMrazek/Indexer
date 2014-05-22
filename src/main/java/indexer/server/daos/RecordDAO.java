package main.java.indexer.server.daos;

import java.util.List;

import main.java.indexer.shared.models.Batch;
import main.java.indexer.shared.models.Field;
import main.java.indexer.shared.models.Record;

/**
 * <p> Used to access the Record table in the database.  
 * Has all needed CRUD operations.</p>
 * 
 * <p> Operations on the Records Value table will also be handled in this DAO. </p>
 *
 */
public class RecordDAO{

	private Database database;
	
	public RecordDAO(Database database){
		this.database = database;
	}
	
	//CREATE
	
	/**
	 * Adds the given record in the database.
	 * @param record the record to be added to the database.
	 * @return the record that has been added to the database.
	 */
	public Record createRecord(Record record){
		return null;
	}
	
	//READ
	
	/**
	 * Reads in the record with the given recordId.
	 * @param recordId the id of the record being searched for.
	 * @return the record with the given recordId.
	 */
	public Record readRecord(String recordId){
		return null;
	}
	
	/**
	 * Returns all records from a given batch.
	 * @param batch the batch used to query the database.
	 * @return a list of all records with the given batchId.
	 */
	public List<Record> readRecordsForBatch(Batch batch){
		return null;
	}
	
	//UPDATE
	
	/**
	 * Updates the given record in the database. 
	 * @param record the record to be updated.
	 * @return the updated record.
	 */
	public Record updateRecord(Record record){
		return null;
	}
	
	//DELETE
	
	/**
	 * Deletes the given record.
	 * @param record the record to be deleted.
	 */
	public void deleteRecord(Record record){
		
	}
	
	/**
	 * Deletes the record with the given recordId.
	 * @param recordId the id of the record to be deleted.
	 */
	public void deleteRecord(String recordId){
		
	}
	
	/**
	 * Deletes all records for the given batch.
	 * @param batch the batch which defines which records to be deleted.
	 */
	public void deleteRecordsForBatch(Batch batch){
		
	}
	
	/**
	 * Deletes the record values for the given record.
	 * @param record the record which defines which records to be deleted.
	 */
	public void deleteRecordValues(Record record){
		
	}
	
	/**
	 * Deletes the specified record and field value.
	 * @param record the record that defines the recordId of the recordvalue to be deleted.
	 * @param field the field that defines the fieldId of the recordvalue to be deleted.
	 */
	public void deleteRecordValue(Record record, Field field){
		
	}
	
}
