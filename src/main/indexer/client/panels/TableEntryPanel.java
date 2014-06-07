package main.indexer.client.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TableEntryPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public TableEntryPanel(){
		super();
		createComponents();
	}

	private void createComponents(){
		JLabel label = new JLabel("Table Entry");
		this.setSize(500,300);
		this.add(label);
	}
	
}
