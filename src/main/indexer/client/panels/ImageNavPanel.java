package main.indexer.client.panels;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageNavPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public ImageNavPanel(){
		super();
		this.setBackground(new Color(128,128,128));
		createComponents();
	}

	private void createComponents(){
		JLabel label = new JLabel("Image Nav");
		this.setSize(500,300);
		this.add(label);
	}
	
}
