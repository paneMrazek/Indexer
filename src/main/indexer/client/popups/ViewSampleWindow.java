package main.indexer.client.popups;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewSampleWindow extends JDialog{

	private static final long serialVersionUID = 1L;
	
	public ViewSampleWindow(JFrame parent, String string, String url){
		super(parent,string,JDialog.DEFAULT_MODALITY_TYPE);
		try{
			this.setResizable(false);
			ImageIcon icon = new ImageIcon(redraw(ImageIO.read(new URL(url))));
			
			this.setSize(icon.getIconWidth(),icon.getIconHeight()+25);
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
