package main.indexer.client.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.indexer.client.models.IndexerDataModel;
import main.indexer.client.models.IndexerDataModel.IndexerDataListener;
import main.indexer.shared.models.Batch;
import main.indexer.shared.models.Field;

public class ImageViewer extends JPanel implements IndexerDataListener{

	private static final long serialVersionUID = 1L;
	
	private Batch batch;
	
	private Image image;
	private Image displayImage;
	private Double scale;
	private Rectangle highlight;
	private boolean inverted;
	private boolean highlightOn;
	@SuppressWarnings("unused")
	private int x;
	@SuppressWarnings("unused")
	private int y;
	
	
	@SuppressWarnings("unused")
	private IndexerDataModel model;
	
	public Batch getBatch(){
		return batch;
	}
	
	public double getScale(){
		return scale;
	}
	
	public void setScale(double scale){
		this.scale = scale;
	}
	
	public boolean isInverted(){
		return inverted;
	}
	
	public void setInverted(boolean inverted){
		this.inverted = inverted;
	}
	
	public boolean isHighlightOn(){
		return highlightOn;
	}
	
	public void setHighlightOn(boolean highlightOn){
		this.highlightOn = highlightOn;
	}
	
	public ImageViewer(IndexerDataModel model){
		super(new FlowLayout(FlowLayout.CENTER));
		this.scale = 1.0;
		this.model = model;
		highlight = new Rectangle();
		model.addListener(this);
		this.addMouseWheelListener(mouseWheelListener);
		this.setLayout(null);
		this.setBackground(new Color(128,128,128));
	}

	public void setBatch(Batch batch){
		try{
			this.batch = batch;
			highlight.setHeight(batch.getRecordHeight());
			image = ImageIO.read(new URL(batch.getImageURL()));
			redraw();
			paintComponent(getGraphics());
			this.addMouseListener(mouseListener);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void removeBatch(){
		this.removeAll();
		displayImage = null;
		this.paintAll(this.getGraphics());
	}
	
	public void zoomIn(){
		scale += 0.1;
		redraw();
	}
	
	public void zoomOut(){
		scale -= 0.1;
		if(scale < 0.1)
			scale = 0.1;
		redraw();
	}
	
	public void invertImage(){
		inverted = !inverted;
		redraw();
	}
	
	public void toggleHighlight(){
		highlightOn = !highlightOn;
		redraw();
	}
	
	private void redraw(){
		if(batch == null)
			return;
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
		if(displayImage != null){
			int x = this.getWidth()/2-displayImage.getWidth(null)/2;
			int y = this.getHeight()/2-displayImage.getHeight(null)/2;
			g.drawImage(displayImage, x, y, null);
			g.setColor(new Color(173,216,230,128));
			if(highlightOn){
				g.fillRect((int) (x+highlight.getX()*scale),(int) (y+highlight.getY()*scale),
						(int) (highlight.getWidth()*scale),(int) (highlight.getHeight()*scale));
			}
		}
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
	
	private MouseAdapter mouseListener = new MouseAdapter(){		
		@Override
		public void mouseClicked(MouseEvent e){
			int x = (int) (e.getX() - (getWidth()/2-displayImage.getWidth(null)/2));
			int y = (int) (e.getY() - (getHeight()/2-displayImage.getHeight(null)/2));
			int row = (int) ((y - (batch.getFirstYCoordinate()*scale))/(batch.getRecordHeight()*scale));
			
			int fieldLength = 0;
			for(Field field : batch.getFields()){
				fieldLength += (field.getWidth()*scale);
			}
			
			int col = batch.getFields().size() - 1;
			while(col >= 0){
				int fieldWidth = (int) (batch.getFields().get(col).getWidth()*scale);
				if(fieldLength - fieldWidth >= x){
					fieldLength -= (fieldWidth);
					col--;
				}else{
					break;
				}
			}
			model.cellSelect(row,col);
		}
	};

	//y = firstY + (row*height);
	//y - firstY = row * height
	//(y - firstY) = row
	@Override
	public void cellSelect(int row, int col){
		highlight.setY(batch.getFirstYCoordinate() + (row*batch.getRecordHeight()));
		highlight.setX(batch.getFields().get(col).getXCoordinate());
		highlight.setWidth(batch.getFields().get(col).getWidth());
		redraw();
	}

	@Override
	public void dataChange(int row, int col, String data){}
	
	private class Rectangle{
		private int x;
		private int y;
		private int width;
		private int height;
		public int getX(){
			return x;
		}
		public void setX(int x){
			this.x = x;
		}
		public int getY(){
			return y;
		}
		public void setY(int y){
			this.y = y;
		}
		public int getWidth(){
			return width;
		}
		public void setWidth(int width){
			this.width = width;
		}
		public int getHeight(){
			return height;
		}
		public void setHeight(int height){
			this.height = height;
		}
		
		
	}

	
}
