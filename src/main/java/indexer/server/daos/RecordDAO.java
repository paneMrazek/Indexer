package main.java.indexer.server.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.indexer.shared.models.Field;
import main.java.indexer.shared.models.Record;
import main.java.indexer.shared.models.SearchResult;

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
		String sql = "INSERT INTO records (batchid,orderid) VALUES (?,?)";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,record.getBatchId());
			statement.setInt(2,record.getOrderId());
			if(statement.executeUpdate() == 1){
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					record.setId(resultSet.getInt(1));
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		for(Field field : record.getValues().keySet()){
			createRecordValues(record.getId(),field.getId(),record.getValues().get(field));
		}
		return record;
	}
	
	
	public void createRecordValues(int recordId,int fieldId,String value){
		String sql = "INSERT INTO recordvalues (recordid,fieldid,value) VALUES (?,?,?)";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,recordId);
			statement.setInt(2,fieldId);
			statement.setString(3,value);
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
	}
	
	//READ
	
	/**
	 * Reads in the record with the given recordId.
	 * @param recordId the id of the record being searched for.
	 * @return the record with the given recordId.
	 */
	public Record readRecord(int recordId){
		String sql = "SELECT * FROM records WHERE id = ?";
		Record record = new Record();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,recordId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				record.setId(rs.getInt("id"));
				record.setBatchId(rs.getInt("batchid"));
				record.setOrderId(rs.getInt("orderid"));
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return record;
	}
	
	/**
	 * Returns all records from a given batch.
	 * @param batch the batch used to query the database.
	 * @return a list of all records with the given batchId.
	 */
	public List<Record> readRecordsForBatch(int batchId){
		String sql = "SELECT * FROM records WHERE batchid = ?";
		List<Record> records = new ArrayList<>();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,batchId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Record record = new Record();
				record.setId(rs.getInt("id"));
				record.setBatchId(rs.getInt("batchid"));
				record.setOrderId(rs.getInt("orderid"));
				records.add(record);
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return records;
	}
	
	//UPDATE
	
	/**
	 * Updates the given record in the database. 
	 * @param record the record to be updated.
	 * @return the updated record.
	 */
	public Record updateRecord(Record record){
		String sql = "UPDATE records SET batchid=?,orderid=? WHERE id=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,record.getBatchId());
			statement.setInt(2,record.getOrderId());
			statement.setInt(3,record.getId());
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		for(Field field : record.getValues().keySet()){
			deleteRecordValues(record.getId());
			createRecordValues(record.getId(),field.getId(),record.getValues().get(field));
		}
		return record;
	}
	
	//DELETE
	
	/**
	 * Deletes the record with the given recordId.
	 * @param recordId the id of the record to be deleted.
	 */
	public void deleteRecord(int recordId){
		String sql = "DELETE FROM records WHERE id=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,recordId);
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		deleteRecordValues(recordId);
	}
	
	/**
	 * Deletes the record values for the given recordId.
	 * @param recordId the recordId which defines which records to be deleted.
	 */
	public void deleteRecordValues(int recordId){
		String sql = "DELETE FROM recordvalues WHERE recordid=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,recordId);
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
	}
	
	public List<SearchResult> searchRecords(int fieldId,String[] searchValues){
		List<SearchResult> results = new ArrayList<>();
		for(String value : searchValues){
			results.add(searchRecord(fieldId,value));
		}
		return results;
	}
	
	public SearchResult searchRecord(int fieldId,String searchValue){
		String sql = "SELECT * FROM recordvalues WHERE fieldid = ? AND value LIKE ?";
		SearchResult result = new SearchResult();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,fieldId);
			statement.setString(2,searchValue);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				Record record = readRecord(rs.getInt("recordid"));
				result.setFieldId(fieldId);
				result.setRecordNumber(record.getOrderId());
				result.setBatchId(record.getBatchId());
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return result;
	}
	
}
