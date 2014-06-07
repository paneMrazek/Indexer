package main.indexer.client.popups;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.indexer.shared.models.Project;

public class DownloadBatchWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JLabel label;
	private JComboBox<String> projectsComboBox;
	private JButton viewSampleButton;
	private JButton cancelButton;
	private JButton downloadButton;
	
	List<DownloadBatchWindowListener> listeners;
	List<Project> projects;
	String[] titles;
	
	public void addListener(DownloadBatchWindowListener listener){
		this.listeners.add(listener);
	}
	
	
	private void setProjects(List<Project> projects){
		this.projects = projects;
		titles = new String[projects.size()];
		for(int i = 0; i < projects.size(); i++){
			titles[i] = projects.get(i).getTitle();
		}
	}
	

	public DownloadBatchWindow(String title, List<Project> projects){
		super(title);
		setProjects(projects);
		createComponents();
	}

	private void createComponents(){
		
		this.listeners = new ArrayList<>();
		
		this.setSize(375,120);
		this.setLocationRelativeTo(null);
		
		label = new JLabel("Project:");
		projectsComboBox = new JComboBox<String>(titles);
		
		viewSampleButton = new JButton("View Sample");
		viewSampleButton.addActionListener(actionListener);
		
		Panel projectPanel = new Panel();
		projectPanel.add(label);
		projectPanel.add(projectsComboBox);
		projectPanel.add(viewSampleButton);
		
		cancelButton = new JButton("Cancel");
		downloadButton = new JButton("Download");
		cancelButton.addActionListener(actionListener);
		downloadButton.addActionListener(actionListener);
		
		Panel buttonPanel = new Panel();
		buttonPanel.add(cancelButton);
		buttonPanel.add(downloadButton);
		
		this.add(projectPanel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		
	}
	
	private void cancel(){
		this.setVisible(false);
	}
	
	private ActionListener actionListener = new ActionListener() {
    	
	    public void actionPerformed(ActionEvent e) {
	    	Project project = new Project();
        	for(Project p : projects){
        		if(p.getTitle().equals(projectsComboBox.getSelectedItem()))
        			project = p;
        	}if(e.getSource() == viewSampleButton){
	        	for(DownloadBatchWindowListener listener : listeners){
	        		listener.downloadSampleImage(project);
	        	}
	        }else if(e.getSource() == downloadButton){
	        	for(DownloadBatchWindowListener listener : listeners){
	        		listener.downloadBatch(project.getId());
	        	}
	        	cancel();
	        }else if (e.getSource() == cancelButton) {
	            cancel();
	        }
	    }
    };
	
	public interface DownloadBatchWindowListener{
		
		public void downloadSampleImage(Project project);
		public void downloadBatch(int projectId);
	}
}
