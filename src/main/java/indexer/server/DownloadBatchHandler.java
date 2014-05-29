package main.java.indexer.server;

import java.io.IOException;
import java.net.HttpURLConnection;

import main.java.indexer.shared.communication.results.DownloadBatch_Result;
import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class DownloadBatchHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Facade facade = new Facade();
		XStream xmlStream = new XStream(new DomDriver());
		String auth = exchange.getRequestHeaders().getFirst("authorization");
		String uri = exchange.getRequestURI().toString();
		String[] splitUri = uri.split("/");
		int projectId = Integer.parseInt(splitUri[splitUri.length-1]);
		
		DownloadBatch_Result result = facade.downloadBatch(auth,projectId);		

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result,exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}