package main.java.indexer.server.daos;

import java.util.Map;

import javax.sql.DataSource;

import main.java.indexer.shared.models.Field;
import main.java.indexer.shared.models.Record;

/**
 * <p> Used to access the Field table in the database.  
 * Has all needed CRUD operations.</p>
 *
 */
public class FieldDAO{
	
protected DataSource dataSource;
	
	public FieldDAO(){
		
	}
	
	//CREATE
	
	/**
	 * Adds the given Field to the database
	 * @param field the field to be added to the database
	 * @return Null if unsuccessful, otherwise the field
	 */
	public Field createField(Field field){
		return null;
	}
	
	//READ
	
	/**
	 * Reads and returns all fields and their values for a given record from the database
	 * 
	 * This function will access both the fields table and the recordvalues table
	 * @param record the record to be used in the query
	 * @return A map of all fields and their values for the given record
	 */
	public Map<Field, String> readFieldsForRecord(Record record){
		return null;
	}
	
	//UPDATE
	
	/**
	 * Updates the given field in the database
	 * @param field the field to be updated
	 * @return The field that has been updated
	 */
	public Field updateField(Field field){
		return null;
	}
	
	//DELETE
	
	/**
	 * Delete the given field from the database
	 * @param field the field to be deleted
	 */
	public void deleteField(Field field){
		
	}
	
	/**
	 * Deletes all fields for the given record
	 * @param record The record which defines which batches to be deleted
	 */
	public void deleteFieldsForRecord(Record record){
		
	}
}
