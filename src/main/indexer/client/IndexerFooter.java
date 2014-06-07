package main.indexer.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import main.indexer.client.panels.FieldHelpPanel;
import main.indexer.client.panels.FormEntryPanel;
import main.indexer.client.panels.ImageNavPanel;
import main.indexer.client.panels.TableEntryPanel;

public class IndexerFooter extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTabbedPane leftTabbedPane;
	private JTabbedPane rightTabbedPane;
	
	private TableEntryPanel tableEntryPanel;
	private FormEntryPanel formEntryPanel;
	private FieldHelpPanel fieldHelpPanel;
	private ImageNavPanel imageNavPanel;
	
	public IndexerFooter(){
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
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftTabbedPane,rightTabbedPane);
		splitPane.setDividerLocation(400);
		this.add(splitPane, BorderLayout.CENTER);
	}
	
}
