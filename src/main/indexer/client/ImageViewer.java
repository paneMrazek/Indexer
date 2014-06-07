package main.indexer.client;

import java.awt.Color;

import javax.swing.JPanel;

public class ImageViewer extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public ImageViewer(){
		
		createComponents();
	}

	private void createComponents(){
		this.setBackground(new Color(128,128,128));
	}
	
}
