package main.java.indexer.server;

import main.java.indexer.server.daos.Database;
import main.java.indexer.shared.models.User;

import com.sun.net.httpserver.HttpHandler;



public abstract class BasicHandler implements HttpHandler{
	
	Database database;
	
	public User validateUser(String auth){
		String decoded = auth;
		/*try{
			decoded = new String(Base64.decode(auth));
		}catch(Base64DecodingException e){
			e.printStackTrace();
		}*/
		String[] split = decoded.split(":(?=[^:]+$)");
		database.startTransaction();
		User user = database.getUserDAO().readUser(split[0],split[1]);
		database.endTransaction();		
		return user;
	}
	
}
