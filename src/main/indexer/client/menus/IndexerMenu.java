package main.indexer.client.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class IndexerMenu extends JMenuBar{
	
	private static final long serialVersionUID = 1L;
	
	private JMenuItem downloadBatchMenuItem;
    private JMenuItem logoutMenuItem;
    private JMenuItem exitMenuItem;
    
    private List<MenuListener> listeners;

	public IndexerMenu(){
		listeners = new ArrayList<>();
		
		JMenu menu = new JMenu("File");
        menu.setMnemonic('f');
        this.add(menu);

        downloadBatchMenuItem = new JMenuItem("Download Batch", KeyEvent.VK_D);
        downloadBatchMenuItem.addActionListener(actionListener);
        menu.add(downloadBatchMenuItem);
        
        logoutMenuItem = new JMenuItem("Logout", KeyEvent.VK_L);
        logoutMenuItem.addActionListener(actionListener);
        menu.add(logoutMenuItem);
        
        exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_E);
        exitMenuItem.addActionListener(actionListener);
        menu.add(exitMenuItem);
        
        setHasBatch(false);
	}
	
	public void addListener(MenuListener listener){
		this.listeners.add(listener);
	}
	
	public void setHasBatch(Boolean hasBatch){
		downloadBatchMenuItem.setEnabled(!hasBatch);
	}
	
	private ActionListener actionListener = new ActionListener() {
    	
	    public void actionPerformed(ActionEvent e) {
	    	
	        if(e.getSource() == downloadBatchMenuItem){
	        	for(MenuListener listener : listeners){
	        		listener.requestBatch();
	        	}
	        }else if(e.getSource() == logoutMenuItem){
	        	for(MenuListener listener : listeners){
	        		listener.logout();
	        	}
	        }
	        else if (e.getSource() == exitMenuItem) {
	        	for(MenuListener listener : listeners){
	        		listener.exit();
	        	}
	        }
	    }
    };
    
    public interface MenuListener{
    	
    	public void requestBatch();
    	public void logout();
    	public void exit();
    }
}
