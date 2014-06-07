package main.indexer.client.popups;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.indexer.shared.communication.ClientCommunicator;
import main.indexer.shared.communication.params.ValidateUser_Params;
import main.indexer.shared.communication.results.ValidateUser_Result;
import main.indexer.shared.models.User;

public class LoginWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton loginButton;
	private JButton exitButton;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private List<LoginListener> listeners;
	
	
	public void addListener(LoginListener listener){
		listeners.add(listener);
	}
	
	public LoginWindow(String title){
		super(title);
		this.setResizable(false);
		listeners = new ArrayList<>();
        createComponents();
	}
	
	private void createComponents(){
		addWindowListener(windowAdapter);
		
		this.setSize(375,150);
		this.setLocationRelativeTo(null);
		
		JLabel username = new JLabel("Username:");
		JLabel password = new JLabel("Password:");
		usernameField = new JTextField(25);
		passwordField = new JPasswordField(25);
		
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
        loginButton.addActionListener(buttonClickListener);
        
        exitButton = new JButton("Exit");
        exitButton.addActionListener(buttonClickListener);
        
        Panel loginPanel = new Panel();
        loginPanel.add(loginButton);
        loginPanel.add(exitButton);
        
        this.add(loginPanel, BorderLayout.SOUTH);
	}
	
	private void displayInvalidLogin(){
		JOptionPane.showMessageDialog(this, 
				"Invalid username and/or password",
				"LoginWindow Failed",JOptionPane.ERROR_MESSAGE);
	}
	
	private void displayValidLogin(User user){
		JOptionPane.showMessageDialog((Component) this,
			    user.getWelcomeString(),
			    "Welcome to Indexer",
			    JOptionPane.PLAIN_MESSAGE);
		this.setVisible(false);
		for(LoginListener listener : listeners)
			listener.login(user);
	}
	
	private WindowAdapter windowAdapter = new WindowAdapter() {
    	
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    };
    
    private ActionListener buttonClickListener = new ActionListener(){
    	
	    public void actionPerformed(ActionEvent e){
	    	
	    	if (e.getSource() == loginButton){
	    		ValidateUser_Params params = new ValidateUser_Params(usernameField.getText(),new String(passwordField.getPassword()));
	    		ValidateUser_Result validationResult = ClientCommunicator.getInstance().validateUser(params);
	    		if(validationResult.isValid()){
	    			displayValidLogin(validationResult.getUser());
	    			
	    		}else{
	    			displayInvalidLogin();
	    		}
	    	}
	    	else if (e.getSource() == exitButton) {
	    		System.exit(0);
	    	}
	    }
    };
    
    public interface LoginListener{
    	
    	public void login(User user);
    }
}
