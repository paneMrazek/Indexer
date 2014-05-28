package main.java.indexer.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import main.java.indexer.server.daos.Database;
import main.java.indexer.shared.communication.params.GetProjects_Params;
import main.java.indexer.shared.communication.results.GetProjects_Result;
import main.java.indexer.shared.models.Project;
import main.java.indexer.shared.models.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class GetProjectsHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Database database = new Database();
		XStream xmlStream = new XStream(new DomDriver());
		GetProjects_Params param = (GetProjects_Params) xmlStream.fromXML(exchange.getRequestBody());

		database.startTransaction();
		User user = database.getUserDAO().readUser(param.getUserName(),param.getPassword());
		
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