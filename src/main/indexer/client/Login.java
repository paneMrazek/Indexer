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
		
		this.setSize(150,70);
		
		JLabel username = new JLabel("Username:");
		JLabel password = new JLabel("Password:");
		JTextField usernameField = new JTextField(25);
		JTextField passwordField = new JTextField(25);
		
		Panel usernamePanel = new Panel();
		usernamePanel.add(username,BorderLayout.EAST);
		usernamePanel.add(usernameField,BorderLayout.WEST);
		
		Panel passwordPanel = new Panel();
		passwordPanel.add(password,BorderLayout.EAST);
		passwordPanel.add(passwordField,BorderLayout.WEST);
		
		Panel inputPanel = new Panel();
		inputPanel.add(usernamePanel, BorderLayout.NORTH);
		inputPanel.add(passwordPanel, BorderLayout.SOUTH);
		
		this.add(inputPanel, BorderLayout.CENTER);
		
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
