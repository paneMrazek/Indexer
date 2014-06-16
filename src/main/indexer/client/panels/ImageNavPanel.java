package main.indexer.client.panels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.indexer.client.models.IndexerDataModel;
import main.indexer.client.models.IndexerDataModel.IndexerDataListener;
import main.indexer.shared.models.Batch;

public class ImageNavPanel extends JPanel implements IndexerDataListener{

    private Image image;
    private Image displayImage;

    public ImageNavPanel(IndexerDataModel model){
		super();
		model.addListener(this);
		this.setBackground(new Color(128, 128, 128));
	}

	public void setBatch(Batch batch){
        try{
            //this.batch = batch;
            //highlight.setHeight(batch.getRecordHeight());
            image = ImageIO.read(new URL(batch.getImageURL()));
            redraw();
            paintComponent(getGraphics());
            //this.addMouseListener(mouseListener);
            //this.addMouseMotionListener(mouseListener);
        }catch(IOException e){
            e.printStackTrace();
        }
	}

    private void redraw(){
        int newImageWidth = (int) (image.getWidth(null) * 0.3);
        int newImageHeight = (int) (image.getHeight(null) * 0.3);
        displayImage = new BufferedImage(newImageWidth , newImageHeight, ((BufferedImage) image).getType());
        Graphics2D g = ((BufferedImage) displayImage).createGraphics();
        g.drawImage(image, 0, 0, newImageWidth , newImageHeight , null);
        g.dispose();
        paintComponent(getGraphics());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(displayImage != null){
            int x = this.getWidth()/2-displayImage.getWidth(null)/2;
            int y = this.getHeight()/2-displayImage.getHeight(null)/2;
            g.drawImage(displayImage, x, y, null);
        }
    }
	
	public void removeBatch(){
		image = null;
        this.removeAll();
        repaint();
	}

	@Override
	public void cellSelect(int row, int col){}

	@Override
	public void dataChange(int row, int col, String data){}
	
}
