package main.indexer.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Login extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton loginButton;
	private JButton exitButton;
	
	public Login(String title){
		super(title);
	       
        createComponents();
	}

	public static void main(String args[]){
		EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
               Login frame = new Login("Login to Indexer");
               frame.pack();
               frame.setVisible(true);
           }
        });
	}
	
	private void createComponents(){
		addWindowListener(windowAdapter);
		
		JLabel username = new JLabel("Username:");
		JLabel password = new JLabel("Password:");
		JTextField usernameField = new JTextField(25);
		JTextField passwordField = new JTextField(25);
		
		Panel usernamePanel = new Panel();
		usernamePanel.add(username);
		usernamePanel.add(usernameField);
		
		Panel passwordPanel = new Panel();
		passwordPanel.add(password);
		passwordPanel.add(passwordField);
		
		Panel inputPanel = new Panel();
		inputPanel.add(usernamePanel);
		inputPanel.add(passwordPanel);
		
		this.add(inputPanel, BorderLayout.NORTH);
		
		loginButton = new JButton("Login");
        loginButton.addActionListener(actionListener);
        
        exitButton = new JButton("Exit");
        exitButton.addActionListener(actionListener);
        
        Panel loginPanel = new Panel();
        loginPanel.add(loginButton);
        loginPanel.add(exitButton);
        
        this.add(loginPanel, BorderLayout.SOUTH);
	}
	
	private WindowAdapter windowAdapter = new WindowAdapter() {
    	
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    };
    
    private ActionListener actionListener = new ActionListener() {
    	
	    public void actionPerformed(ActionEvent e) {
	    	
	    	if (e.getSource() == loginButton) {
	    		//TODO login
	    	}
	    	else if (e.getSource() == exitButton) {
	    		System.exit(0);
	    	}
	    }
    };
}
