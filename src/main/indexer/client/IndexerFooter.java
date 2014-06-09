package main.indexer.client;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import main.indexer.client.panels.FieldHelpPanel;
import main.indexer.client.panels.FormEntryPanel;
import main.indexer.client.panels.ImageNavPanel;
import main.indexer.client.panels.IndexerDataModel;
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
	
	public IndexerFooter(IndexerDataModel model){
		super(JSplitPane.HORIZONTAL_SPLIT);
		setDividerLocation(600);
		createComponents(model);
	}

	private void createComponents(IndexerDataModel model){
				
		leftTabbedPane = new JTabbedPane();
		tableEntryPanel = new TableEntryPanel(model);
		formEntryPanel = new FormEntryPanel(model);
		leftTabbedPane.addTab("Table Entry",tableEntryPanel);
		leftTabbedPane.addTab("Form Entry",formEntryPanel);
		
		rightTabbedPane = new JTabbedPane();
		fieldHelpPanel = new FieldHelpPanel(model);
		imageNavPanel = new ImageNavPanel(model);
		rightTabbedPane.addTab("Field Help",fieldHelpPanel);
		rightTabbedPane.addTab("Image Navigation",imageNavPanel);
		
		this.setLeftComponent(leftTabbedPane);
		this.setRightComponent(rightTabbedPane);
	}

	public void setBatch(Batch batch){
		tableEntryPanel.setBatch(batch);
		formEntryPanel.setBatch(batch);
		fieldHelpPanel.setBatch(batch);
		imageNavPanel.setBatch(batch);
		
	}
	
}
