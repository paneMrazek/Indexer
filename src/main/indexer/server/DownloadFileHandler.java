package main.indexer.server;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DownloadFileHandler implements HttpHandler{
	@Override
	public void handle(HttpExchange exchange) throws IOException{
		Facade facade = new Facade();
		String uri = exchange.getRequestURI().toString();
		byte[] result = facade.downloadFile(uri);		

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		exchange.getResponseBody().write(result);
		exchange.getResponseBody().close();
	}
}
