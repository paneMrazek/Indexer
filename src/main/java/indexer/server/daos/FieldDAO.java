package main.java.indexer.server.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import main.java.indexer.shared.models.Field;
import main.java.indexer.shared.models.Record;

/**
 * <p> Used to access the Field table in the database.  
 * Has all needed CRUD operations.</p>
 *
 */
public class FieldDAO{
	
	private Database database;
	
	public FieldDAO(Database database){
		this.database = database;
	}
	
	//CREATE
	
	/**
	 * Adds the given Field to the database.
	 * @param field the field to be added to the database.
	 * @return the new field if successful, otherwise null.
	 */
	public Field createField(Field field){
		String sql = "INSERT INTO fields "
				+ "(projectid,orderId,title,xCoordinate,width,helpFile,knownData) VALUES (?,?,?,?,?,?,?)";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,field.getProjectId());
			statement.setInt(2,field.getOrderId());
			statement.setString(3,field.getTitle());
			statement.setInt(4,field.getXCoordinate());
			statement.setInt(5,field.getWidth());
			statement.setString(6,field.getHelpFile());
			statement.setString(7,field.getKnownData());
			if(statement.executeUpdate() == 1){
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					field.setId(resultSet.getInt(1));
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return field;
	}
	
	//READ
	
	/**
	 * Reads and returns all fields and their values for a given record from the database.
	 * 
	 * This function accesses both the fields table and the recordvalues table.
	 * @param record the record to be used in the query.
	 * @return a map of all fields and their values for the given record.
	 */
	public Map<Field, String> readFieldsForRecord(Record record){
		String sql = "SELECT * FROM recordvalues WHERE rv.recordid = ?";
		Map<Field, String> values = new HashMap<>();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,record.getId());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				String value = rs.getString("value");
				Field field = readField(rs.getInt("fieldid"));
				values.put(field,value);
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return values;
	}
	
	/**
	 * Reads and returns the field with the given id.
	 * @param fieldId the fieldId to be used in the query.
	 * @return the field with the given id.
	 */
	public Field readField(int fieldId){
		String sql = "SELECT * FROM fields WHERE fieldid = ?";
		Field field = new Field();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,fieldId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				field.setId(rs.getInt("fieldid"));
				field.setProjectId(rs.getInt("projectid"));
				field.setOrderId(rs.getInt("orderid"));
				field.setTitle(rs.getString("title"));
				field.setXCoordinate(rs.getInt("xcoordinate"));
				field.setWidth(rs.getInt("width"));
				field.setHelpFile(rs.getString("helpfile"));
				field.setKnownData(rs.getString("knowndata"));
			}
		}catch(SQLException e){
			
		}
		return field;
	}
	
	//UPDATE
	
	/**
	 * Updates the given field in the database.
	 * @param field the field to be updated.
	 * @return the field that has been updated.
	 */
	public Field updateField(Field field){
		String sql = "UPDATE fields SET projectid = ?,orderid=?,title=?,"
				+ "xcoordinate=?,width=?,helpfile=?,knowndata=? WHERE id=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,field.getProjectId());
			statement.setInt(2,field.getOrderId());
			statement.setString(3,field.getTitle());
			statement.setInt(4,field.getXCoordinate());
			statement.setInt(5,field.getWidth());
			statement.setString(6,field.getHelpFile());
			statement.setString(7,field.getKnownData());
			statement.setInt(8,field.getId());
			statement.executeQuery();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return field;
	}
	
	//DELETE
	
	/**
	 * Delete the given field from the database.
	 * @param field the field to be deleted.
	 */
	public void deleteField(Field field){
		String sql = "DELETE FROM fields WHERE id = ?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,field.getId());
			statement.executeQuery();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
	}
}