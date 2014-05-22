package main.java.indexer.server.daos;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{
	
	private BatchDAO batchDAO;
	private FieldDAO fieldDAO;
	private ProjectDAO projectDAO;
	private RecordDAO recordDAO;
	private UserDAO userDAO;
	
	private Connection connection;
	
	public Database(){
		batchDAO = new BatchDAO(this);
		fieldDAO = new FieldDAO(this);
		projectDAO = new ProjectDAO(this);
		recordDAO = new RecordDAO(this);
		userDAO = new UserDAO(this);
		
		try{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
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
	
	public void startTransaction(){
		String connectionUrl = "jdbc:sqlite:sql" + File.separator + "IndexerDB.db";
		try{
			connection = DriverManager.getConnection(connectionUrl);
			connection.setAutoCommit(false);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void endTransaction(){
		boolean success = true;
		try{
			if(success)
				connection.commit();
			else
				connection.rollback();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	}

}
