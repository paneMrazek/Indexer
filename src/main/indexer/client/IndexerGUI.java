package main.indexer.client;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import main.indexer.client.menus.IndexerButtonMenu;
import main.indexer.client.menus.IndexerMenu;
import main.indexer.client.menus.IndexerButtonMenu.ButtonMenuListener;
import main.indexer.client.menus.IndexerMenu.MenuListener;
import main.indexer.client.panels.ImageViewer;
import main.indexer.client.panels.IndexerDataModel;
import main.indexer.client.popups.DownloadBatchWindow;
import main.indexer.client.popups.LoginWindow;
import main.indexer.client.popups.ViewSampleWindow;
import main.indexer.client.popups.DownloadBatchWindow.DownloadBatchWindowListener;
import main.indexer.client.popups.LoginWindow.LoginListener;
import main.indexer.shared.communication.ClientCommunicator;
import main.indexer.shared.communication.params.DownloadBatch_Params;
import main.indexer.shared.communication.params.GetBatch_Params;
import main.indexer.shared.communication.params.GetProjects_Params;
import main.indexer.shared.communication.params.GetSampleImage_Params;
import main.indexer.shared.communication.results.GetProjects_Result;
import main.indexer.shared.models.Batch;
import main.indexer.shared.models.Project;
import main.indexer.shared.models.User;

public class IndexerGUI extends JFrame implements LoginListener, MenuListener, ButtonMenuListener, DownloadBatchWindowListener{

	private static final long serialVersionUID = 1L;
	
	private LoginWindow login;
	private IndexerMenu menu;
	private IndexerButtonMenu buttonToolBar;
	private ImageViewer imageViewer;
	private IndexerFooter footer;
	private JSplitPane splitPane;
	Properties properties;
	
	User user;
	
	//Creation
	
	public IndexerGUI(String title){
		super(title);
		this.createComponents();
	}
	
	private void createComponents(){
		addWindowListener(windowAdapter);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		
		menu = new IndexerMenu();
		this.setJMenuBar(menu);
		menu.addListener(this);
		
		buttonToolBar = new IndexerButtonMenu();
		this.add(buttonToolBar, BorderLayout.NORTH);
		buttonToolBar.addListener(this);
		
		IndexerDataModel model = new IndexerDataModel();
		
		imageViewer = new ImageViewer(model);	
		footer = new IndexerFooter(model);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,imageViewer,footer);
		splitPane.setDividerLocation(400);
		this.add(splitPane, BorderLayout.CENTER);
		
		
	}

	//LoginWindow at start and at logout menu call
	public void start(){
		displayLogin();
	}
	
	private void displayLogin(){
		login = new LoginWindow("Login to Indexer");
		login.setVisible(true);
		login.addListener(this);
	}
	
	public void saveState(){
		properties = new Properties();
		properties.setProperty("batchId",Integer.toString(imageViewer.getBatch().getId()));
		properties.setProperty("zoomlevel",Double.toString(imageViewer.getScale()));
		properties.setProperty("horizontalSplitPosition",Integer.toString(splitPane.getDividerLocation()));
		properties.setProperty("verticalSplitPosition",Integer.toString(footer.getDividerLocation()));
		properties.setProperty("guiHeight",Integer.toString(this.getHeight()));
		properties.setProperty("guiWidth",Integer.toString(this.getWidth()));
		properties.setProperty("guiX",Integer.toString(this.getX()));
		properties.setProperty("guiY",Integer.toString(this.getY()));
		properties.setProperty("inveted",Boolean.toString(imageViewer.getInverted()));
		try{
			properties.store(new PrintWriter(new File("Users/" + user.getUserName() + ".properties")),"");
		}catch(IOException e){
			e.printStackTrace();
		};
	}
	
	@Override
	public void login(User user){
		properties = new Properties();
		try{
			properties.load(new FileInputStream(new File("Users/" + user.getUserName() + ".properties")));
		}catch(IOException e){
			e.printStackTrace();
		}
		this.user = user;
		this.setVisible(true);
		String batchId = properties.getProperty("batchId","NONE");
		Batch batch;
		if(!batchId.equals("NONE")){
			System.out.println("Has Batch Already");
			batch = ClientCommunicator.getInstance().getBatch(new GetBatch_Params(Integer.parseInt(batchId),
					user.getUserName(),user.getPassword())).getBatch();
			imageViewer.setBatch(batch);
			buttonToolBar.setEnabled(true);
			footer.setBatch(batch);
			imageViewer.setBatch(batch);
			imageViewer.setScale(Double.parseDouble(properties.getProperty("scale","0.7")));
			imageViewer.setInverted(Boolean.parseBoolean(properties.getProperty("inverted","false")));
		}
	}
	
	@Override
	public void requestBatch(){
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		GetProjects_Result result = ClientCommunicator.getInstance()
				.getProjects(params);
		DownloadBatchWindow window = new DownloadBatchWindow(this,"Web Browser",
				result.getProjects());
		window.addListener(this);
		window.setVisible(true);
	}
	
	@Override
	public void logout(){
		saveState();
		this.setVisible(false);
		displayLogin();
	}
	
	//ButtonMenuListener Methods
	
	@Override
	public void zoomIn(){
		imageViewer.zoomIn();
	}

	@Override
	public void zoomOut(){
		imageViewer.zoomOut();
	}

	@Override
	public void invertImage(){
		imageViewer.invertImage();
	}

	@Override
	public void toggleHighlight(){
		
	}

	@Override
	public void save(){
		saveState();
	}

	@Override
	public void submit(){
		
	}
	
	//DownloadBatchWindowListener Methods
	
	@Override
	public void downloadSampleImage(Project project){
		GetSampleImage_Params params = new GetSampleImage_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		params.setProjectId(project.getId());
		String url = ClientCommunicator.getInstance().getSampleImage(params).getUrl();
	    ViewSampleWindow window = new ViewSampleWindow(this,"Sample Image from " + project.getTitle(), url);
	    window.setVisible(true);
	}
	
	@Override
	public void downloadBatch(int projectId){
		DownloadBatch_Params params = new DownloadBatch_Params();
		params.setUserName(user.getUserName());
		params.setPassword(user.getPassword());
		params.setProjectId(projectId);
		Batch batch = ClientCommunicator.getInstance().downloadBatch(params).getBatch();
		buttonToolBar.setEnabled(true);
		footer.setBatch(batch);
		imageViewer.setBatch(batch);
	}
	
	private WindowAdapter windowAdapter = new WindowAdapter() {
    	
        public void windowClosing(WindowEvent e) {
        	saveState();
            System.exit(0);
        }
    };

}
