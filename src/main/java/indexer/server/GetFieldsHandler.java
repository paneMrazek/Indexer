package main.java.indexer.server;

import java.io.IOException;
import java.net.HttpURLConnection;

import main.java.indexer.server.daos.Database;
import main.java.indexer.shared.communication.params.GetFields_Params;
import main.java.indexer.shared.communication.results.ValidateUser_Result;
import main.java.indexer.shared.models.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class GetFieldsHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Database database = new Database();
		XStream xmlStream = new XStream(new DomDriver());
		GetFields_Params param = (GetFields_Params) xmlStream.fromXML(exchange.getRequestBody());

		database.startTransaction();
		User user = database.getUserDAO().readUser(param.getUserName(),param.getPassword());
		database.endTransaction();
		
		ValidateUser_Result result = new ValidateUser_Result();
		result.setError(!database.wasSuccesful());
		result.setValid(user != null);
		result.setUser(user);
		

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result,exchange.getResponseBody());
		exchange.getResponseBody().close();

	}
}