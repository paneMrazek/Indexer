package main.indexer.server;

import java.io.IOException;
import java.net.HttpURLConnection;

import main.indexer.shared.communication.results.GetFields_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class GetFieldsHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Facade facade = new Facade();
		XStream xmlStream = new XStream(new DomDriver());
		String auth = exchange.getRequestHeaders().getFirst("authorization");
		String uri = exchange.getRequestURI().toString();
		String[] splitUri = uri.split("/");
		GetFields_Result result = new GetFields_Result();
		if(splitUri.length > 0){
			int projectId = Integer.parseInt(splitUri[splitUri.length-1]);		
			result = facade.getFields(auth,projectId);
		}else{
			result.setError(true);
		}

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result,exchange.getResponseBody());
		exchange.getResponseBody().close();

	}
}