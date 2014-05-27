package main.java.indexer.server;

import java.io.IOException;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import main.java.indexer.server.daos.Database;


public class ValidateUserHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		Database database = new Database();
		XStream xmlStream = new XStream(new DomDriver());
		ValidateUserParam param = xmlStream.fromXML(exchange.getRequestBody());

		try {
			db.startTransaction();
			db.get
			db.endTransaction();
		}
		catch (ServerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			db.endTransaction(false);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);

		
		// Process AddContact request
		// 1. Deserialize the request object from the request body
		// 2. Extract the new contact object from the request object
		// 3. Call the model to add the new contact

	}
}