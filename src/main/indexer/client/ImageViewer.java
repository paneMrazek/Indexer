package main.indexer.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.indexer.shared.models.Batch;

public class ImageViewer extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public ImageViewer(){
		this.setBackground(new Color(128,128,128));
	}

	public void setBatch(Batch batch, byte[] file){
		this.setSize(510,430);
		ImageIcon icon;
		try{
			icon = new ImageIcon(ImageIO.read(new ByteArrayInputStream(file)));
			this.add(new JLabel(icon), BorderLayout.CENTER);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private BufferedImage redraw(BufferedImage icon,double zoomLevel){
		int newImageWidth = (int) (icon.getWidth() * zoomLevel);
		int newImageHeight = (int) (icon.getHeight() * zoomLevel);
		BufferedImage resizedImage = new BufferedImage(newImageWidth , newImageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(icon, 0, 0, newImageWidth , newImageHeight , null);
		g.dispose();
		return resizedImage;
	}
	
}
