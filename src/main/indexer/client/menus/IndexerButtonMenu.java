package main.indexer.client.menus;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class IndexerButtonMenu extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton invertImageButton;
    private JButton toggleHighlightsButton;
    private JButton saveButton;
    private JButton submitButton;
    
    private List<ButtonMenuListener> listeners;
	
	public IndexerButtonMenu(){
		super(new FlowLayout(FlowLayout.LEFT));
		
		createComponents();
	}
	
	private void createComponents(){
		listeners = new ArrayList<>();
		
		zoomInButton = new JButton("Zoom In");
		zoomInButton.addActionListener(actionListener);
        
        zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(actionListener);
        
        invertImageButton = new JButton("Invert Image");
        invertImageButton.addActionListener(actionListener);
        
        toggleHighlightsButton = new JButton("Toggle Highlights");
        toggleHighlightsButton.addActionListener(actionListener);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(actionListener);
        
        submitButton = new JButton("Submit");
        submitButton.addActionListener(actionListener);
        
        this.add(zoomInButton);
        this.add(zoomOutButton);
        this.add(invertImageButton);
        this.add(toggleHighlightsButton);
        this.add(saveButton);
        this.add(submitButton);
        
        setEnabled(false);
	}
	
	public void setEnabled(boolean enabled){
		zoomInButton.setEnabled(enabled);
		zoomOutButton.setEnabled(enabled);
		invertImageButton.setEnabled(enabled);
		toggleHighlightsButton.setEnabled(enabled);
		saveButton.setEnabled(enabled);
		submitButton.setEnabled(enabled);
	}
	
	public void addListener(ButtonMenuListener listener){
		this.listeners.add(listener);
	}
	
	private ActionListener actionListener = new ActionListener(){
    	
	    public void actionPerformed(ActionEvent e){
	    	
	    	if(e.getSource() == zoomInButton){
	    		
	    	}else if(e.getSource() == zoomOutButton){
	    		
	    	}else if(e.getSource() == invertImageButton){
	    		
	    	}else if(e.getSource() == toggleHighlightsButton){
	    		
	    	}else if(e.getSource() == saveButton){
	    		
	    	}else if(e.getSource() == submitButton){
	    		
	    	}
	    }
    };
    
    public interface ButtonMenuListener{
    	
    	public void zoomIn();
    	public void zoomOut();
    	public void invertImage();
    	public void toggleHighlight();
    	public void save();
    	public void submit();
    }
	
}
