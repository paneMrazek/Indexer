package main.indexer.client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.indexer.shared.models.Batch;

public class ImageViewer extends JPanel{

	private static final long serialVersionUID = 1L;
	
	Image image;
	Image displayImage;
	Double scale;
	boolean inverted;
	int x;
	int y;
	
	JLabel imageLabel = new JLabel();
	
	public ImageViewer(){
		super(new FlowLayout(FlowLayout.CENTER));
		
		this.addMouseWheelListener(mouseWheelListener);
		this.setLayout(null);
		this.setBackground(new Color(128,128,128));
	}

	public void setBatch(Batch batch){
		try{
			scale = 0.7;
			inverted = false;
			image = ImageIO.read(new URL(batch.getImageURL()));
			redraw();
			paintComponent(getGraphics());
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void zoomIn(){
		scale += 0.1;
		redraw();
	}
	
	public void zoomOut(){
		scale -= 0.1;
		redraw();
	}
	
	public void invertImage(){
		inverted = !inverted;
		redraw();
	}
	
	private void redraw(){
		int newImageWidth = (int) (image.getWidth(null) * scale);
		int newImageHeight = (int) (image.getHeight(null) * scale);
		displayImage = new BufferedImage(newImageWidth , newImageHeight, ((BufferedImage) image).getType());
		Graphics2D g = ((BufferedImage) displayImage).createGraphics();
		g.drawImage(image, 0, 0, newImageWidth , newImageHeight , null);
		g.dispose();
		if(inverted){
			RescaleOp op = new RescaleOp(-1.0f, 255f, null);
			displayImage = op.filter((BufferedImage) displayImage, null);
		}
		paintComponent(getGraphics());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(displayImage != null)
			g.drawImage(displayImage, this.getWidth()/2-displayImage.getWidth(null)/2, 
				this.getHeight()/2-displayImage.getHeight(null)/2, null);
	}
	
	private MouseWheelListener mouseWheelListener = new MouseWheelListener(){
		@Override
		public void mouseWheelMoved(MouseWheelEvent e){
			if(e.getWheelRotation() > 0)
				zoomIn();
			else
				zoomOut();
		}
	};
	
}
