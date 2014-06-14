package main.indexer.client;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import main.indexer.client.models.IndexerDataModel;
import main.indexer.client.models.QualityChecker;
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
	
	public IndexerFooter(IndexerDataModel model, QualityChecker checker){
		super(JSplitPane.HORIZONTAL_SPLIT);
		createComponents(model, checker);
	}

	private void createComponents(IndexerDataModel model, QualityChecker checker){
				
		leftTabbedPane = new JTabbedPane();
		tableEntryPanel = new TableEntryPanel(model, checker);
		JScrollPane tableScrollPane = new JScrollPane(tableEntryPanel);
		formEntryPanel = new FormEntryPanel(model, checker);
		leftTabbedPane.addTab("Table Entry",tableScrollPane);
		leftTabbedPane.addTab("Form Entry",formEntryPanel);
		
		rightTabbedPane = new JTabbedPane();
		fieldHelpPanel = new FieldHelpPanel(model);
		JScrollPane helpScrollPane = new JScrollPane(fieldHelpPanel);
		imageNavPanel = new ImageNavPanel(model);
		rightTabbedPane.addTab("Field Help",helpScrollPane);
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
	
	public void removeBatch(){
		tableEntryPanel.removeBatch();
		formEntryPanel.removeBatch();
		fieldHelpPanel.removeBatch();
		imageNavPanel.removeBatch();
	}
	
	@Override
	public void setDividerLocation(int value){
		if(value < 45)
			value = 45;
		super.setDividerLocation(value);
	}

	public Object[][] getRecordValues(){
		return formEntryPanel.getRecordValues();
	}

	
}
