package main.java.indexer.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server{
	
	private HttpServer server;
	
	private static final int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private Server(){
		return;
	}
	
private void run() {
		
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/DownloadBatch", downloadBatchHandler);
		server.createContext("/GetFields",getFieldsHandler);
		server.createContext("/GetProjects",getProjectsHandler);
		server.createContext("/GetSampleImage",getSampleImageHandler);
		server.createContext("/Search",searchHandler);
		server.createContext("/SubmitBatch",submitBatchHandler);
		server.createContext("/ValidateUser", validateUserHandler);
				
		server.start();
	}

	private HttpHandler downloadBatchHandler = new DownloadBatchHandler();
	private HttpHandler getFieldsHandler = new GetFieldsHandler();
	private HttpHandler getProjectsHandler = new GetProjectsHandler();
	private HttpHandler getSampleImageHandler = new GetSampleImageHandler();
	private HttpHandler searchHandler = new SearchHandler();
	private HttpHandler submitBatchHandler = new SubmitBatchHandler();
	private HttpHandler validateUserHandler = new ValidateUserHandler();

	public static void main(String[] args) {
		new Server().run();
	}
}
