package main.java.indexer.server.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.indexer.shared.models.Batch;
import main.java.indexer.shared.models.Project;

/**
 * <p> Used to access the Batch table in the database.  
 * Has all needed CRUD operations.</p>
 *
 */
public class BatchDAO{
	
	private Database database;
	
	public BatchDAO(Database database) {
		this.database = database;
	}
	
	//CREATE
	
	/**
	 * Adds the given batch to the database.
	 * @param batch the batch to be added to the database.
	 * @return the batch if successful, otherwise null.
	 */
	public Batch createBatch(Batch batch){
		String sql = "INSERT INTO batches "
				+ "(imageurl,projectid) VALUES (?,?)";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setString(1,batch.getImageURL());
			statement.setInt(2,batch.getProjectId());
			if(statement.executeUpdate() == 1){
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					batch.setId(resultSet.getInt(1));
			}
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return batch;
	}
	
	//READ
	
	/**
	 * Reads and returns all batches for a given project from the database.
	 * @param project the project to be used in the query.
	 * @return a list of all batches in the given project.
	 */
	public List<Batch> readBatchesForProject(Project project){
		String sql = "SELECT * FROM batches WHERE projectid = ?";
		ResultSet rs;
		List<Batch> batches = new ArrayList<>();
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,project.getId());
			rs = statement.executeQuery();
			while(rs.next()){
				if(!rs.getBoolean("complete")){
					Batch batch = new Batch();
					batch.setComplete(false);
					batch.setId(rs.getInt("batchid"));
					batch.setImageURL(rs.getString("imageurl"));
					batch.setFirstYCoordinate(rs.getInt("firstycoordinate"));
					batch.setRecordHeight(rs.getInt("recordheight"));
					batches.add(batch);
				}
				
			}
			rs.close();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return batches;
	}
	
	/**
	 * Reads from the database and returns the batch with the given batchId.
	 * @param batchId the batchId given to query the database.
	 * @return the batch with the given batchId if exists, otherwise null.
	 */
	public Batch readBatch(int batchId){
		String sql = "SELECT * FROM batches WHERE batchId = ?";
		Batch batch = null;
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,batchId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				batch = new Batch();
				batch.setId(rs.getInt("batchid"));
				batch.setImageURL(rs.getString("imageurl"));
				batch.setFirstYCoordinate(rs.getInt("firstycoordinate"));
				batch.setRecordHeight(rs.getInt("recordheight"));
				batch.setComplete(rs.getBoolean("complete"));
			}
			rs.close();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return batch;
	}
	
	//UPDATE
	
	/**
	 * Updates the given batch in the database.
	 * @param batch the batch to be updated.
	 * @return the updated branch.
	 */
	public Batch updateBatch(Batch batch){
		String sql = "UPDATE batches SET imageurl=?,projectid=?,firstycoordinate=?,recordheight=?"
				+ "WHERE batchId=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setString(1,batch.getImageURL());
			statement.setInt(2,batch.getProjectId());
			statement.setInt(3,batch.getFirstYCoordinate());
			statement.setInt(4,batch.getRecordHeight());
			statement.setInt(5,batch.getId());
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
		return batch;
	}
	
	//DELETE
	
	/**
	 * Delete the given batch from the database.
	 * @param batch the batch to be deleted.
	 */
	public void deleteBatch(Batch batch){
		String sql = "DELETE FROM batches WHERE batchid=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,batch.getId());
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes all batches for the given project.
	 * @param project The project which defines which batches to be deleted.
	 */
	public void deleteBatchesForProject(Project project){
		String sql = "DELETE FROM batches WHERE projectid=?";
		try(PreparedStatement statement = database.getConnection().prepareStatement(sql)){
			statement.setInt(1,project.getId());
			statement.executeUpdate();
		}catch(SQLException e){
			database.error();
			e.printStackTrace();
		}
	}
}