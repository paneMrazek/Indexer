package main.indexer.server.daos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//TODO Make a delete all from database method
public class Database{
	
	private BatchDAO batchDAO;
	private FieldDAO fieldDAO;
	private ProjectDAO projectDAO;
	private RecordDAO recordDAO;
	private UserDAO userDAO;
	
	private Connection connection;
	private boolean success;
	
	public Database(){
		batchDAO = new BatchDAO(this); //Tested
		fieldDAO = new FieldDAO(this);
		projectDAO = new ProjectDAO(this);
		recordDAO = new RecordDAO(this);
		userDAO = new UserDAO(this); //Tested
		
		try{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}catch(ClassNotFoundException e){
			//e.printStackTrace();
		}
	}

	/**
	 * @return the batchDAO
	 */
	public BatchDAO getBatchDAO(){
		return batchDAO;
	}

	/**
	 * @return the fieldDAO
	 */
	public FieldDAO getFieldDAO(){
		return fieldDAO;
	}

	/**
	 * @return the projectDAO
	 */
	public ProjectDAO getProjectDAO(){
		return projectDAO;
	}

	/**
	 * @return the recordDAO
	 */
	public RecordDAO getRecordDAO(){
		return recordDAO;
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO(){
		return userDAO;
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection(){
		return connection;
	}
	
	public void error(){
		success = false;
	}
	
	public boolean wasSuccesful(){
		return success;
	}
	
	public void startTransaction(){
		String connectionUrl = "jdbc:sqlite:sql" + File.separator + "IndexerDB.db";
		try{
			connection = DriverManager.getConnection(connectionUrl);
			connection.setAutoCommit(false);
			success = true;
		}catch(SQLException e){
			//e.printStackTrace();
		}
	}
	
	public void endTransaction(){
		try{
			if(success)
				connection.commit();
			else
				connection.rollback();
		}catch(SQLException e){
			//e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch (SQLException e){
				//e.printStackTrace();
			}
		}
	}
	
	public void rollbackTransaction(){
		try{
			connection.rollback();
			connection.close();
		}catch(SQLException e){
			//e.printStackTrace();
		}
	}
	
	public void deleteData(){
		File to = new File("sql" + File.separator + "IndexerDB.db");
		File from = new File("sql" + File.separator + "IndexerBlank.db");
		
		to.delete();
		
		try{
			Files.copy(from.toPath(), to.toPath());
		}catch(IOException e){
			//e.printStackTrace();
		}
	    
	   
	}
	
	

}