package main.java.indexer.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import main.java.indexer.server.daos.Database;
import main.java.indexer.shared.communication.results.GetProjects_Result;
import main.java.indexer.shared.models.Project;
import main.java.indexer.shared.models.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class GetProjectsHandler extends BasicHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		database = new Database();
		XStream xmlStream = new XStream(new DomDriver());
		String auth = exchange.getRequestHeaders().getFirst("authorization");
		User user = validateUser(auth);

		database.startTransaction();		
		List<Project> projects = new ArrayList<>();
		if(user != null && database.wasSuccesful()){
			projects = database.getProjectDAO().readProjects();
		}
		database.endTransaction();
		
		GetProjects_Result result = new GetProjects_Result();
		result.setError(!database.wasSuccesful());
		result.setProjects(projects);
		

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result,exchange.getResponseBody());
		exchange.getResponseBody().close();

	}
}