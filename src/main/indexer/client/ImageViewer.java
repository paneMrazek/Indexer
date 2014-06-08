package main.indexer.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.indexer.shared.models.Batch;

public class ImageViewer extends JPanel{

	private static final long serialVersionUID = 1L;
	
	Image image;
	JLabel imageLabel = new JLabel();
	
	public ImageViewer(){
		super(new FlowLayout(FlowLayout.CENTER));
		
		this.setLayout(null);
		this.setBackground(new Color(128,128,128));
		this.add(imageLabel);
	}

	public void setBatch(Batch batch, byte[] file){
		try{
			image = ImageIO.read(new URL(batch.getImageURL()));
			imageLabel.setIcon(new ImageIcon(image));
			this.setSize(image.getWidth(null),image.getHeight(null));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void setSize(int width, int height){
		imageLabel.setSize(width, height);
		super.setSize(width, height);
	}
	
}
