package main.java.indexer.server;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import main.java.indexer.server.daos.Database;
import main.java.indexer.shared.communication.results.ValidateUser_Result;
import main.java.indexer.shared.models.User;


public class ValidateUserHandler extends BasicHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		database = new Database();
		XStream xmlStream = new XStream(new DomDriver());
		String auth = exchange.getRequestHeaders().getFirst("authorization");
		User user = validateUser(auth);
		
		ValidateUser_Result result = new ValidateUser_Result();
		result.setError(!database.wasSuccesful());
		result.setValid(user != null);
		result.setUser(user);
		

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result,exchange.getResponseBody());
		exchange.getResponseBody().close();

	}
}