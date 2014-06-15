package main.indexer.server.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.indexer.shared.models.Field;
import main.indexer.shared.models.Record;

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
			//e.printStackTrace();
		}
		return field;
	}
	
	public List<Field> readFieldsForProject(int projectId){
		String sql;
		if(projectId > 0){ sql = "SELECT * FROM fields WHERE projectid = ?"; }
		else{ sql = "SELECT * FROM fields"; }
		
		List<Field> fields = new ArrayList<>();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			if(projectId > 0){ statement.setInt(1,projectId); }
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Field field = new Field();
				field.setId(rs.getInt("id"));
				field.setProjectId(rs.getInt("projectid"));
				field.setOrderId(rs.getInt("orderid"));
				field.setTitle(rs.getString("title"));
				field.setXCoordinate(rs.getInt("xcoordinate"));
				field.setWidth(rs.getInt("width"));
				field.setHelpFile(rs.getString("helpfile"));
				field.setKnownData(rs.getString("knowndata"));
				fields.add(field);
			}
		}catch(SQLException e){
			database.error();
			//e.printStackTrace();
		}
		return fields;
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
			//e.printStackTrace();
		}
		return values;
	}
	
	/**
	 * Reads and returns the field with the given id.
	 * @param fieldId the fieldId to be used in the query.
	 * @return the field with the given id.
	 */
	public Field readField(int fieldId){
		String sql = "SELECT * FROM fields WHERE id = ?";
		Field field = new Field();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,fieldId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				field.setId(rs.getInt("id"));
				field.setProjectId(rs.getInt("projectid"));
				field.setOrderId(rs.getInt("orderid"));
				field.setTitle(rs.getString("title"));
				field.setXCoordinate(rs.getInt("xcoordinate"));
				field.setWidth(rs.getInt("width"));
				field.setHelpFile(rs.getString("helpfile"));
				field.setKnownData(rs.getString("knowndata"));
			}else{
				return null;
			}
		}catch(SQLException e){
			//e.printStackTrace();
			database.error();
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
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			//e.printStackTrace();
		}
		return field;
	}
	
	//DELETE
	
	/**
	 * Delete the given field from the database.
	 * @param field the field to be deleted.
	 */
	public void deleteField(int fieldId){
		String sql = "DELETE FROM fields WHERE id = ?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,fieldId);
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			//e.printStackTrace();
		}
	}
}
