package main.java.indexer.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server{
	
	private HttpServer server;
	
	private int portNum = 8080;
	
	private Server(String[] args){
		if(args.length == 1){
			portNum = Integer.parseInt(args[0]);
		}
		return;
	}
	
private void run() {
		
		try {
			server = HttpServer.create(new InetSocketAddress(portNum),10);
		} 
		catch (IOException e) {
			//e.printStackTrace();
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/DownloadBatch", downloadBatchHandler);
		server.createContext("/GetFields",getFieldsHandler);
		server.createContext("/GetProjects",getProjectsHandler);
		server.createContext("/GetSampleImage",getSampleImageHandler);
		server.createContext("/Search",searchHandler);
		server.createContext("/SubmitBatch",submitBatchHandler);
		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/",downloadFileHandler);
				
		server.start();
	}

	private HttpHandler downloadBatchHandler = new DownloadBatchHandler();
	private HttpHandler getFieldsHandler = new GetFieldsHandler();
	private HttpHandler getProjectsHandler = new GetProjectsHandler();
	private HttpHandler getSampleImageHandler = new GetSampleImageHandler();
	private HttpHandler searchHandler = new SearchHandler();
	private HttpHandler submitBatchHandler = new SubmitBatchHandler();
	private HttpHandler validateUserHandler = new ValidateUserHandler();
	private HttpHandler downloadFileHandler = new DownloadFileHandler();

	public static void main(String[] args) {
		new Server(args).run();
	}
}
