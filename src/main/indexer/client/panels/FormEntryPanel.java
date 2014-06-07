package main.indexer.client.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormEntryPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public FormEntryPanel(){
		super();
		createComponents();
	}

	private void createComponents(){
		JLabel label = new JLabel("Form Entry");
		this.setSize(500,300);
		this.add(label);
	}
	
}
