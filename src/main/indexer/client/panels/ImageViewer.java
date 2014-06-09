package main.indexer.client.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.indexer.client.panels.IndexerDataModel.IndexerDataListener;
import main.indexer.shared.models.Batch;

public class ImageViewer extends JPanel implements IndexerDataListener{

	private static final long serialVersionUID = 1L;
	
	private Batch batch;
	
	private Image image;
	private Image displayImage;
	private Double scale;
	private boolean inverted;
	private int x;
	private int y;
	
	private IndexerDataModel model;
	
	public Batch getBatch(){
		return batch;
	}
	
	public double getScale(){
		return scale;
	}
	
	public void setScale(double scale){
		this.scale = scale;
		redraw();
	}
	
	public boolean getInverted(){
		return inverted;
	}
	
	public void setInverted(boolean inverted){
		this.inverted = inverted;
		redraw();
	}
	
	public ImageViewer(IndexerDataModel model){
		super(new FlowLayout(FlowLayout.CENTER));
		this.model = model;
		model.addListener(this);
		this.addMouseWheelListener(mouseWheelListener);
		this.setLayout(null);
		this.setBackground(new Color(128,128,128));
	}

	public void setBatch(Batch batch){
		try{
			scale = 0.7;
			inverted = false;
			this.batch = batch;
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

	@Override
	public void cellSelect(int row, int col){}

	@Override
	public void dataChange(int row, int col, String data){}
	
}
