package main.java.indexer.shared.communication.results;

import java.util.List;

import main.java.indexer.shared.models.Project;

public class GetProjects_Result extends Result{
	
	private List<Project> projects;
	
	/**
	 * @return the projects
	 */
	public List<Project> getProjects(){
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project> projects){
		this.projects = projects;
	}
	
	/* 
	 * 
	 */
	@Override
	public String toString(){
		if(isError())
			return super.toString();
		else{
			String ret = "";
			for(Project project : projects){
				ret += project.toString();
			}
			return ret;
		}
	}
	
}
