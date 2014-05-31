package test.servertester.controllers;

import java.util.ArrayList;

import main.java.indexer.server.Server;
import main.java.indexer.shared.communication.ClientCommunicator;
import main.java.indexer.shared.communication.params.DownloadBatch_Params;
import main.java.indexer.shared.communication.params.GetFields_Params;
import main.java.indexer.shared.communication.params.GetProjects_Params;
import main.java.indexer.shared.communication.params.GetSampleImage_Params;
import main.java.indexer.shared.communication.params.Search_Params;
import main.java.indexer.shared.communication.params.SubmitBatch_Params;
import main.java.indexer.shared.communication.params.ValidateUser_Params;
import test.servertester.views.IView;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
		String[] paramValues = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params();
		params.setUserName(paramValues[0]);
		params.setPassword(paramValues[1]);
		getView().setRequest(new XStream(new DomDriver()).toXML(params));
		getView().setResponse(ClientCommunicator.getInstance().validateUser(params).toString());
	}
	
	private void getProjects() {
		String[] paramValues = getView().getParameterValues();
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(paramValues[0]);
		params.setPassword(paramValues[1]);
		getView().setRequest(new XStream(new DomDriver()).toXML(params));
		getView().setResponse(ClientCommunicator.getInstance().getProjects(params).toString());
	}
	
	private void getSampleImage() {
		String[] paramValues = getView().getParameterValues();
		GetSampleImage_Params params = new GetSampleImage_Params();
		params.setUserName(paramValues[0]);
		params.setPassword(paramValues[1]);
		params.setProjectId(Integer.parseInt(paramValues[2]));
		getView().setRequest(new XStream(new DomDriver()).toXML(params));
		getView().setResponse(ClientCommunicator.getInstance().getSampleImage(params).toString());
	}
	
	private void downloadBatch() {
		String[] paramValues = getView().getParameterValues();
		DownloadBatch_Params params = new DownloadBatch_Params();
		params.setUserName(paramValues[0]);
		params.setPassword(paramValues[1]);
		params.setProjectId(Integer.parseInt(paramValues[2]));
		getView().setRequest(new XStream(new DomDriver()).toXML(params));
		getView().setResponse(ClientCommunicator.getInstance().downloadBatch(params).toString());
	}
	
	private void getFields() {
		String[] paramValues = getView().getParameterValues();
		GetFields_Params params = new GetFields_Params();
		params.setUserName(paramValues[0]);
		params.setPassword(paramValues[1]);
		if(!paramValues[2].equals(""))
			params.setProjectId(Integer.parseInt(paramValues[2]));
		getView().setRequest(new XStream(new DomDriver()).toXML(params));
		getView().setResponse(ClientCommunicator.getInstance().getFields(params).toString());
	}
	
	private void submitBatch() {
		String[] paramValues = getView().getParameterValues();
		SubmitBatch_Params params = new SubmitBatch_Params();
		params.setUserName(paramValues[0]);
		params.setPassword(paramValues[1]);
		params.setBatchId(Integer.parseInt(paramValues[2]));
		
		String[] recordValues = paramValues[3].split("[;,]");
		params.setRecordValues(recordValues);
		
		getView().setRequest(new XStream(new DomDriver()).toXML(params));
		getView().setResponse(ClientCommunicator.getInstance().submitBatch(params).toString());
	}
	
	private void search() {
		String[] paramValues = getView().getParameterValues();
		Search_Params params = new Search_Params();
		params.setUserName(paramValues[0]);
		params.setPassword(paramValues[1]);
		params.setFieldId(paramValues[2].split(","));
		params.setSearchValues(paramValues[3].split(","));
		
		getView().setRequest(new XStream(new DomDriver()).toXML(params));
		getView().setResponse(ClientCommunicator.getInstance().search(params).toString());
		
	}

}

