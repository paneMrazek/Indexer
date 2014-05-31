package main.java.indexer.server;

import java.io.IOException;
import java.net.HttpURLConnection;

import main.java.indexer.shared.communication.params.Search_Params;
import main.java.indexer.shared.communication.results.Search_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class SearchHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Facade facade = new Facade();
		XStream xmlStream = new XStream(new DomDriver());
		String auth = exchange.getRequestHeaders().getFirst("authorization");
		Search_Params params = (Search_Params) xmlStream.fromXML(exchange.getRequestBody());

		Search_Result result = facade.search(auth,params);
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result,exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}