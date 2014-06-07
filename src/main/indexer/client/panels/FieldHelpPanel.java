package main.indexer.client.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FieldHelpPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public FieldHelpPanel(){
		super();
		createComponents();
	}

	private void createComponents(){
		JLabel label = new JLabel("Field Help");
		this.setSize(500,300);
		this.add(label);
	}
	
}
