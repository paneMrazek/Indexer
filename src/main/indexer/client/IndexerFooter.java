package main.indexer.client;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import main.indexer.client.panels.FieldHelpPanel;
import main.indexer.client.panels.FormEntryPanel;
import main.indexer.client.panels.ImageNavPanel;
import main.indexer.client.panels.TableEntryPanel;
import main.indexer.shared.models.Batch;

public class IndexerFooter extends JSplitPane{

	private static final long serialVersionUID = 1L;
	
	private JTabbedPane leftTabbedPane;
	private JTabbedPane rightTabbedPane;
	
	private TableEntryPanel tableEntryPanel;
	private FormEntryPanel formEntryPanel;
	private FieldHelpPanel fieldHelpPanel;
	private ImageNavPanel imageNavPanel;
	
	public IndexerFooter(){
		super(JSplitPane.HORIZONTAL_SPLIT);
		setDividerLocation(600);
		createComponents();
	}

	private void createComponents(){
				
		leftTabbedPane = new JTabbedPane();
		tableEntryPanel = new TableEntryPanel();
		formEntryPanel = new FormEntryPanel();
		leftTabbedPane.addTab("Table Entry",tableEntryPanel);
		leftTabbedPane.addTab("Form Entry",formEntryPanel);
		
		rightTabbedPane = new JTabbedPane();
		fieldHelpPanel = new FieldHelpPanel();
		imageNavPanel = new ImageNavPanel();
		rightTabbedPane.addTab("Field Help",fieldHelpPanel);
		rightTabbedPane.addTab("Image Navigation",imageNavPanel);
		
		this.setLeftComponent(leftTabbedPane);
		this.setRightComponent(rightTabbedPane);
	}

	public void setBatch(Batch batch, byte[] file){
		tableEntryPanel.setBatch(batch);
		formEntryPanel.setBatch(batch);
		fieldHelpPanel.setBatch(batch);
		imageNavPanel.setBatch(file);
		
	}
	
}
