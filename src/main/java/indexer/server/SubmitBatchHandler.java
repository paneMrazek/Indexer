package main.java.indexer.server;

import java.io.IOException;

import com.sun.net.httpserver.*;


public class SubmitBatchHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		// Process AddContact request
		// 1. Deserialize the request object from the request body
		// 2. Extract the new contact object from the request object
		// 3. Call the model to add the new contact

	}
}