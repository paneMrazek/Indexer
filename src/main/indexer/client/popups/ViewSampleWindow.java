package main.indexer.client.popups;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewSampleWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	

	public ViewSampleWindow(String string, byte[] file){
		super(string);
		try{
			this.setSize(510,430);
			ImageIcon icon = new ImageIcon(redraw(ImageIO.read(new ByteArrayInputStream(file))));
			
			this.add(new JLabel(icon), BorderLayout.CENTER);
			
			JButton cancelButton = new JButton("Close");
			cancelButton.addActionListener(actionListener);
			JPanel panel = new JPanel();
			panel.add(cancelButton);
			this.add(panel, BorderLayout.SOUTH);
			
			this.setLocationRelativeTo(null);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private BufferedImage redraw(BufferedImage icon){
		double zoomLevel = 0.5;
		int newImageWidth = (int) (icon.getWidth() * zoomLevel);
		int newImageHeight = (int) (icon.getHeight() * zoomLevel);
		BufferedImage resizedImage = new BufferedImage(newImageWidth , newImageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(icon, 0, 0, newImageWidth , newImageHeight , null);
		g.dispose();
		return resizedImage;
	}
	
	private void cancel(){
		this.setVisible(false);
	}
	
	private ActionListener actionListener = new ActionListener(){
	    public void actionPerformed(ActionEvent e){
	    	cancel();
	    }
    };
	
	
}
