package test.servertester.controllers;

import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import main.java.indexer.server.Server;
import main.java.indexer.shared.communication.ClientCommunicator;
import main.java.indexer.shared.communication.params.GetProjects_Params;
import main.java.indexer.shared.communication.params.ValidateUser_Params;
import main.java.indexer.shared.communication.results.GetProjects_Result;
import main.java.indexer.shared.communication.results.ValidateUser_Result;
import test.servertester.views.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
		
		String[] args = new String[1];
		args[0] = getView().getPort();
		Server.main(args);
		ClientCommunicator.getInstance().setHost(getView().getHost());
		ClientCommunicator.getInstance().setPort(Integer.parseInt(getView().getPort()));
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		String userName = getView().getParameterValues()[0];
		String password = getView().getParameterValues()[1];
		ValidateUser_Params params = new ValidateUser_Params();
		params.setUserName(userName);
		params.setPassword(password);
		String value = new XStream(new DomDriver()).toXML(params);
		getView().setRequest(value);
		ValidateUser_Result result = ClientCommunicator.getInstance().validateUser(params);
		getView().setResponse(result.toString());
	}
	
	private void getProjects() {
		String userName = getView().getParameterValues()[0];
		String password = getView().getParameterValues()[1];
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(userName);
		params.setPassword(password);
		GetProjects_Result result = ClientCommunicator.getInstance().getProjects(params);
		getView().setResponse(result.toString());
	}
	
	private void getSampleImage() {
	}
	
	private void downloadBatch() {
	}
	
	private void getFields() {
	}
	
	private void submitBatch() {
	}
	
	private void search() {
	}

}

